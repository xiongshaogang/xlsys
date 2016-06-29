package xlsys.base.io.transfer.server;

import java.io.BufferedInputStream;
import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.io.Serializable;
import java.lang.reflect.InvocationTargetException;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.dom4j.DocumentException;

import xlsys.base.XLSYS;
import xlsys.base.XlsysFactory;
import xlsys.base.buffer.BufferManager;
import xlsys.base.buffer.BufferPool;
import xlsys.base.buffer.MapBufferPool;
import xlsys.base.buffer.ModelBuffer;
import xlsys.base.buffer.MutiLRUBufferPool;
import xlsys.base.buffer.XlsysBuffer;
import xlsys.base.cfg.BaseConfig;
import xlsys.base.database.ConnectionPool;
import xlsys.base.database.DBPoolFactory;
import xlsys.base.database.EnvDataBase;
import xlsys.base.database.IDataBase;
import xlsys.base.database.bean.ExecuteBean;
import xlsys.base.database.bean.ISqlBean;
import xlsys.base.database.bean.ParamBean;
import xlsys.base.database.util.AutoIdAllocate;
import xlsys.base.database.util.DBUtil;
import xlsys.base.database.util.ExportData;
import xlsys.base.database.util.ImportData;
import xlsys.base.dataset.IDataSet;
import xlsys.base.dataset.util.DataSetUtil;
import xlsys.base.env.Env;
import xlsys.base.env.EnvFactory;
import xlsys.base.exception.UnsupportedException;
import xlsys.base.io.XlsysResourceManager;
import xlsys.base.io.attachment.XlsysAttachment;
import xlsys.base.io.ftp.FtpModel;
import xlsys.base.io.pack.InnerPackage;
import xlsys.base.io.transport.Transport;
import xlsys.base.io.util.FileUtil;
import xlsys.base.io.util.IOUtil;
import xlsys.base.io.util.LockUtil;
import xlsys.base.io.xml.XmlModel;
import xlsys.base.log.LogUtil;
import xlsys.base.model.PairModel;
import xlsys.base.session.Session;
import xlsys.base.thread.XlsysThreadPool;
import xlsys.base.util.FTPUtil;
import xlsys.base.util.ObjectUtil;
import xlsys.base.util.StringUtil;

/**
 * 基础处理中心实现类，该类实现了一些常用的处理操作
 * @author Lewis
 *
 */
public class BasePackageProcessor extends PackageProcessor implements XlsysBuffer
{
	/**
	 * 对于排序查询的缓冲池
	 */
	private BufferPool<String, IDataSet> queryBuffer;
	/**
	 * 不需要使用缓冲的查询key集合
	 */
	private Set<String> noneedBufferQuery;
	/**
	 * 使用缓冲的查询结果集大小临界值，大于等于此值时使用缓冲，为负数时不启用
	 */
	private int criticalSize;
	
	protected BasePackageProcessor() throws Exception
	{
		super();
		// 加载配置，初始化缓冲池
		XmlModel serverCfgModel = BaseConfig.getInstance().getConfigXmlModel(BaseConfig.SERVER_CONFIG);
		XmlModel queryBufferModel = serverCfgModel.getChild("QueryBuffer");
		criticalSize = ObjectUtil.objectToInt(queryBufferModel.getChild("criticalSize").getText());
		if(criticalSize>=0)
		{
			int bufferSize = ObjectUtil.objectToInt(queryBufferModel.getChild("bufferSize").getText());
			if(bufferSize<=0) queryBuffer = new MapBufferPool<String, IDataSet>();
			else queryBuffer = new MutiLRUBufferPool<String, IDataSet>(bufferSize);
			noneedBufferQuery = new HashSet<String>();
			BufferManager.getInstance().registerBuffer(XLSYS.BUFFER_SQL_QUERY, this);
		}
	}

	@Override
	protected InnerPackage doProcess(InnerPackage innerPackage) throws Exception
	{
		InnerPackage outPkg = new InnerPackage(innerPackage.getSession());
		String command = innerPackage.getCommand();
		Serializable outObj = null;
		if(XLSYS.COMMAND_FOR_TEST.equals(command))
		{
			outObj = "Hello, every body, I am Xue Lang System Server!";
			outPkg.setCommand(XLSYS.COMMAND_OK);
		}
		else if(XLSYS.COMMAND_GET_ALL_DB_ID.equals(command))
		{
			outObj = getAllDbID(innerPackage);
			outPkg.setCommand(XLSYS.COMMAND_OK);
		}
		else if(XLSYS.COMMAND_GET_ALL_ENV_ID.equals(command))
		{
			outObj = getAllEnvID(innerPackage);
			outPkg.setCommand(XLSYS.COMMAND_OK);
		}
		else if(XLSYS.COMMAND_DB_GET_ALL_TABLE_BASE_INFO.equals(command))
		{
			outObj = getAllTableBaseInfo(innerPackage);
			outPkg.setCommand(XLSYS.COMMAND_OK);
		}
		else if(XLSYS.COMMAND_DB_GET_TABLE_INFO.equals(command))
		{
			outObj = doGetTableInfo(innerPackage);
			outPkg.setCommand(XLSYS.COMMAND_OK);
		}
		else if(XLSYS.COMMAND_DB_GET_RESULT_COUNT.equals(command))
		{
			outObj = doGetResultCount(innerPackage);
			outPkg.setCommand(XLSYS.COMMAND_OK);
		}
		else if(XLSYS.COMMAND_DB_SQL_SELECT.equals(command))
		{
			outObj = doSqlSelect(innerPackage, false);
			outPkg.setCommand(XLSYS.COMMAND_OK);
		}
		else if(XLSYS.COMMAND_DB_SQL_SELECT_AS_ONE_VALUE.equals(command))
		{
			outObj = doSqlSelect(innerPackage, true);
			outPkg.setCommand(XLSYS.COMMAND_OK);
		}
		else if(XLSYS.COMMAND_DB_SQL_EXECUTE.equals(command))
		{
			outObj = doSqlExecute(innerPackage);
			outPkg.setCommand(XLSYS.COMMAND_OK);
		}
		else if(XLSYS.COMMAND_DB_FUNC_LOWER.equals(command))
		{
			outObj = doGetLowerFunc(innerPackage);
			outPkg.setCommand(XLSYS.COMMAND_OK);
		}
		else if(XLSYS.COMMAND_DB_FUNC_UPPER.equals(command))
		{
			outObj = doGetUpperFunc(innerPackage);
			outPkg.setCommand(XLSYS.COMMAND_OK);
		}
		else if(XLSYS.COMMAND_DB_FUNC_BYTELENGTH.equals(command))
		{
			outObj = doGetByteLengthFunc(innerPackage);
			outPkg.setCommand(XLSYS.COMMAND_OK);
		}
		else if(XLSYS.COMMAND_DB_FUNC_SUBSTRING.equals(command))
		{
			outObj = doGetSubStringFunc(innerPackage);
			outPkg.setCommand(XLSYS.COMMAND_OK);
		}
		else if(XLSYS.COMMAND_DB_FUNC_TODATE.equals(command))
		{
			outObj = doGetToDateFunc(innerPackage);
			outPkg.setCommand(XLSYS.COMMAND_OK);
		}
		else if(XLSYS.COMMAND_DB_DISABLE_CONSTRAINT.equals(command))
		{
			outObj = doDisableConstraint(innerPackage);
			outPkg.setCommand(XLSYS.COMMAND_OK);
		}
		else if(XLSYS.COMMAND_DB_ENABLE_CONSTRAINT.equals(command))
		{
			outObj = doEnableConstraint(innerPackage);
			outPkg.setCommand(XLSYS.COMMAND_OK);
		}
		else if(XLSYS.COMMAND_ALLOC_ID.equals(command))
		{
			outObj = doAllocId(innerPackage);
			outPkg.setCommand(XLSYS.COMMAND_OK);
		}
		else if(XLSYS.COMMAND_GET_NEXT_VALUE.equals(command))
		{
			outObj = doGetNextValue(innerPackage);
			outPkg.setCommand(XLSYS.COMMAND_OK);
		}
		else if(XLSYS.COMMAND_GET_DATE.equals(command))
		{
			outObj = new Date();
			outPkg.setCommand(XLSYS.COMMAND_OK);
		}
		else if(XLSYS.COMMAND_GET_LOCK.equals(command))
		{
			outObj = getLock(innerPackage);
			outPkg.setCommand(XLSYS.COMMAND_OK);
		}
		else if(XLSYS.COMMAND_TRY_LOCK.equals(innerPackage.getCommand()))
		{
			outObj = tryLock(innerPackage);
			if(outObj==null) outPkg.setCommand(XLSYS.COMMAND_BUSY);
			else outPkg.setCommand(XLSYS.COMMAND_OK);
		}
		else if(XLSYS.COMMAND_RELEASE_LOCK.equals(innerPackage.getCommand()))
		{
			outObj = releaseLock(innerPackage);
			outPkg.setCommand(XLSYS.COMMAND_OK);
		}
		else if(XLSYS.COMMAND_UPLOAD_FILE.equals(innerPackage.getCommand()))
		{
			outObj = uploadFile(innerPackage);
			outPkg.setCommand(XLSYS.COMMAND_OK);
		}
		else if(XLSYS.COMMAND_DOWNLOAD_FILE.equals(innerPackage.getCommand()))
		{
			outObj = downFile(innerPackage);
			outPkg.setCommand(XLSYS.COMMAND_OK);
		}
		else if(XLSYS.COMMAND_GET_FILE_URL.equals(innerPackage.getCommand()))
		{
			outObj = getFileURL(innerPackage);
			outPkg.setCommand(XLSYS.COMMAND_OK);
		}
		else if(XLSYS.COMMAND_TRANSPORT_DATA.equals(innerPackage.getCommand()))
		{
			outObj = transportData(innerPackage);
			outPkg.setCommand(XLSYS.COMMAND_OK);
		}
		else if(XLSYS.COMMAND_REFRESH_BUFFER.equals(innerPackage.getCommand()))
		{
			outObj = refreshBuffer(innerPackage);
			outPkg.setCommand(XLSYS.COMMAND_OK);
		}
		else if(XLSYS.COMMAND_GET_ALL_BUFFER_NAME.equals(innerPackage.getCommand()))
		{
			outObj = getAllBufferName();
			outPkg.setCommand(XLSYS.COMMAND_OK);
		}
		else if(XLSYS.COMMAND_GET_ENV_MODEL.equals(innerPackage.getCommand()))
		{
			outObj = getEnvModel(innerPackage);
			outPkg.setCommand(XLSYS.COMMAND_OK);
		}
		else if(XLSYS.COMMAND_EXPORT_DATA.equals(innerPackage.getCommand()))
		{
			outObj = exportData(innerPackage);
			outPkg.setCommand(XLSYS.COMMAND_OK);
		}
		else if(XLSYS.COMMAND_EXPORT_DATA_PROGRESS.equals(innerPackage.getCommand()))
		{
			outObj = exportDataProgress(innerPackage);
			outPkg.setCommand(XLSYS.COMMAND_OK);
		}
		else if(XLSYS.COMMAND_DB_BACKUP.equals(innerPackage.getCommand()))
		{
			outObj = dbBackup(innerPackage);
			outPkg.setCommand(XLSYS.COMMAND_OK);
		}
		else if(XLSYS.COMMAND_IMPORT_DATA.equals(innerPackage.getCommand()))
		{
			outObj = importData(innerPackage);
			outPkg.setCommand(XLSYS.COMMAND_OK);
		}
		else if(XLSYS.COMMAND_IMPORT_DATA_PROGRESS.equals(innerPackage.getCommand()))
		{
			outObj = importDataProgress(innerPackage);
			outPkg.setCommand(XLSYS.COMMAND_OK);
		}
		outPkg.setObj(outObj);
		return outPkg;
	}

	private Serializable importDataProgress(InnerPackage innerPackage) throws InterruptedException
	{
		Session session = innerPackage.getSession();
		ImportData importData = ImportData.importSessionMap.get(session.getSessionId());
		if(importData==null) throw new NullPointerException();
		Serializable result = importData.popNextResult();
		PairModel<Serializable, String> ret = new PairModel<Serializable, String>(result, importData.popLog());
		if(result instanceof Integer)
		{
			Integer value = (Integer) result;
			if(value==importData.getMaximum()) ImportData.importSessionMap.remove(session.getSessionId());
		}
		else if(result instanceof Exception) ImportData.importSessionMap.remove(session.getSessionId());
		return ret;
	}

	private Serializable importData(InnerPackage innerPackage) throws DocumentException
	{
		Session session = innerPackage.getSession();
		Map<String, Serializable> paramMap = (Map<String, Serializable>) innerPackage.getObj();
		Object[] temp = (Object[]) paramMap.get("attachments");
		XlsysAttachment[] attachments = new XlsysAttachment[temp.length];
		for(int i=0;i<temp.length;++i) attachments[i] = (XlsysAttachment) temp[i];
		boolean batch = ObjectUtil.objectToBoolean(paramMap.get("batch"));
		boolean override = ObjectUtil.objectToBoolean(paramMap.get("override"));
		int envId = ObjectUtil.objectToInt(session.getAttribute(XLSYS.SESSION_ENV_ID));
		ImportData importData = new ImportData(envId, attachments, batch, override);
		ImportData.importSessionMap.put(session.getSessionId(), importData);
		XlsysThreadPool.getInstance().execute(importData);
		return importData.getMaximum();
	}

	private Serializable dbBackup(InnerPackage innerPackage) throws Exception
	{
		Session session = innerPackage.getSession();
		int envId = ObjectUtil.objectToInt(session.getAttribute(XLSYS.SESSION_ENV_ID));
		IDataBase dataBase = null;
		int size = 0;
		try
		{
			dataBase = EnvDataBase.getInstance(envId);
			List<PairModel<String, String>> sqlPairList = DBUtil.getAllTableExportSql(dataBase);
			size = sqlPairList.size();
			String[] sqls = new String[size];
			String[] tableNames = new String[size];
			for(int i=0;i<size;++i)
			{
				PairModel<String, String> pair = sqlPairList.get(i);
				sqls[i] = pair.first;
				tableNames[i] = pair.second;
			}
			ExportData exportData = new ExportData(envId, sqls, tableNames);
			ExportData.exportSessionMap.put(session.getSessionId(), exportData);
			XlsysThreadPool.getInstance().execute(exportData);
		}
		catch(Exception e)
		{
			throw e;
		}
		finally
		{
			DBUtil.close(dataBase);
		}
		return size+1;
	}

	private Serializable exportDataProgress(InnerPackage innerPackage) throws Exception
	{
		Session session = innerPackage.getSession();
		ExportData exportData = ExportData.exportSessionMap.get(session.getSessionId());
		if(exportData==null) throw new NullPointerException();
		Serializable result = exportData.popNextResult();
		if(result instanceof Integer)
		{
			Integer value = (Integer) result;
			if(value==exportData.getMaximum())
			{
				// 返回服务端的url
				byte[] datas = exportData.getDatas();
				String md5 = StringUtil.getMD5String(datas);
				String fileSuffix = "data";
				String resourceName = md5 + '.' + fileSuffix;
				XlsysResourceManager resourceManager = XlsysResourceManager.getInstance();
				resourceManager.registResource(resourceName, datas, fileSuffix);
				result = resourceManager.getResourceUrlWithName(resourceName);
				ExportData.exportSessionMap.remove(session.getSessionId());
			}
		}
		else if(result instanceof Exception)
		{
			ExportData.exportSessionMap.remove(session.getSessionId());
		}
		return result;
	}

	private Serializable exportData(InnerPackage innerPackage) throws DocumentException
	{
		Session session = innerPackage.getSession();
		String[] sqls = (String[]) innerPackage.getObj();
		int envId = ObjectUtil.objectToInt(session.getAttribute(XLSYS.SESSION_ENV_ID));
		ExportData exportData = new ExportData(envId, sqls);
		ExportData.exportSessionMap.put(session.getSessionId(), exportData);
		XlsysThreadPool.getInstance().execute(exportData);
		return sqls.length+1;
	}

	private Serializable getEnvModel(InnerPackage innerPackage) throws UnsupportedException, NoSuchMethodException, SecurityException, ClassNotFoundException, InstantiationException, IllegalAccessException, IllegalArgumentException, InvocationTargetException, DocumentException
	{
		int envId = ObjectUtil.objectToInt(innerPackage.getObj());
		return (Env) XlsysFactory.getFactoryInstance(XLSYS.FACTORY_ENV).getInstance(envId);
	}

	private Serializable getAllBufferName()
	{
		return BufferManager.getInstance().getAllRegisteredBufferNames();
	}

	private Serializable refreshBuffer(InnerPackage innerPackage)
	{
		Object inObj = innerPackage.getObj();
		Session session = innerPackage.getSession();
		if(inObj instanceof String)
		{
			String bufferName = (String) inObj;
			Map<String, Serializable> paramMap = new HashMap<String, Serializable>();
			paramMap.put(ModelBuffer.BUFFER_KEY_ENVID, session.getAttribute(XLSYS.SESSION_ENV_ID));
			BufferManager.getInstance().reloadBuffer(bufferName, paramMap);
		}
		else if(inObj instanceof Object[])
		{
			Object[] inParam = (Object[]) inObj;
			if(inParam.length==1)
			{
				String bufferName = (String) inParam[0];
				Map<String, Serializable> paramMap = new HashMap<String, Serializable>();
				paramMap.put(ModelBuffer.BUFFER_KEY_ENVID, session.getAttribute(XLSYS.SESSION_ENV_ID));
				BufferManager.getInstance().reloadBuffer(bufferName, paramMap);
			}
			else if(inParam.length==2)
			{
				String bufferName = (String) inParam[0];
				Map<String, Serializable> paramMap = (Map<String, Serializable>) inParam[1];
				paramMap.put(ModelBuffer.BUFFER_KEY_ENVID, session.getAttribute(XLSYS.SESSION_ENV_ID));
				BufferManager.getInstance().reloadBuffer(bufferName, paramMap);
			}
		}
		return null;
	}

	private Serializable transportData(InnerPackage innerPackage) throws NoSuchMethodException, SecurityException, ClassNotFoundException, InstantiationException, IllegalAccessException, IllegalArgumentException, InvocationTargetException, DocumentException
	{
		String tsRunId = (String) innerPackage.getObj();
		Session session = innerPackage.getSession();
		Transport transport = new Transport(session, tsRunId);
		return transport.transport();
	}
	
	private Serializable getFileURL(InnerPackage innerPackage) throws Exception
	{
		String url = null;
		Serializable inObj = innerPackage.getObj();
		if(inObj instanceof XlsysAttachment)
		{
			XlsysAttachment inAttachment = (XlsysAttachment) inObj;
			String fileMd5 = inAttachment.getMd5();
			String fileSuffix = FileUtil.getFileSuffix(inAttachment.getAttachmentName());
			String resourceName = fileMd5 + '.' + fileSuffix;
			XlsysResourceManager resourceManager = XlsysResourceManager.getInstance();
			synchronized(resourceManager)
			{
				if(!resourceManager.containsResourceWithName(resourceName))
				{
					byte[] bytes = null;
					if(inAttachment.getStyle()==XlsysAttachment.STYLE_FILE_SYSTEM)
					{
						// 从文件系统下载
						String workDir = null;
						if(inAttachment.getId()!=null) workDir = (String) XlsysFactory.getFactoryInstance(XLSYS.FACTORY_WORKDIR).getInstance(inAttachment.getId());
						else workDir = (String) XlsysFactory.getFactoryInstance(XLSYS.FACTORY_WORKDIR).getInstance();
						// 使用内部名称获取文件
						String filePath = workDir + '/' + (inAttachment.getPath()==null?"":inAttachment.getPath()) + '/' + inAttachment.getInnerName();
						filePath = FileUtil.fixFilePath(filePath);
						bytes = FileUtil.getByteFromFile(filePath);
					}
					else if(inAttachment.getStyle()==XlsysAttachment.STYLE_FTP)
					{
						// 从Ftp下载
						FTPUtil ftpUtil = null;
						InputStream is = null;
						try
						{
							if(inAttachment.getId()!=null) ftpUtil = ((FtpModel)XlsysFactory.getFactoryInstance(XLSYS.FACTORY_FTP).getInstance(inAttachment.getId())).getFtpInstance();
							else ftpUtil = ((FtpModel)XlsysFactory.getFactoryInstance(XLSYS.FACTORY_FTP).getInstance()).getFtpInstance();
							if(inAttachment.getPath()!=null) ftpUtil.cd(inAttachment.getPath());
							// 使用内部名称获取文件
							is = ftpUtil.get(inAttachment.getInnerName());
							BufferedInputStream bis = new BufferedInputStream(is);
							bytes = IOUtil.readBytesFromInputStream(bis, -1);
						}
						catch(Exception e)
						{
							throw e;
						}
						finally
						{
							if(ftpUtil!=null) ftpUtil.close();
							IOUtil.close(is);
						}
					}
					else if(inAttachment.getStyle()==XlsysAttachment.STYLE_DATA_BASE)
					{
						bytes = inAttachment.getAttachmentData();
					}
					if(inAttachment.isCompress()) bytes = IOUtil.decompress(bytes);
					resourceManager.registResource(resourceName, bytes, fileSuffix);
				}
			}
			url = resourceManager.getResourceUrlWithName(resourceName);
		}
		return url;
	}

	private Serializable downFile(InnerPackage innerPackage) throws Exception
	{
		XlsysAttachment outAttachment = null;
		Serializable inObj = innerPackage.getObj();
		if(inObj instanceof XlsysAttachment)
		{
			XlsysAttachment inAttachment = (XlsysAttachment) inObj;
			byte[] bytes = null;
			if(inAttachment.getStyle()==XlsysAttachment.STYLE_FILE_SYSTEM)
			{
				// 从文件系统下载
				String workDir = null;
				if(inAttachment.getId()!=null) workDir = (String) XlsysFactory.getFactoryInstance(XLSYS.FACTORY_WORKDIR).getInstance(inAttachment.getId());
				else workDir = (String) XlsysFactory.getFactoryInstance(XLSYS.FACTORY_WORKDIR).getInstance();
				// 使用内部名称获取文件
				String filePath = workDir + '/' + (inAttachment.getPath()==null?"":inAttachment.getPath()) + '/' + inAttachment.getInnerName();
				filePath = FileUtil.fixFilePath(filePath);
				bytes = FileUtil.getByteFromFile(filePath);
			}
			else if(inAttachment.getStyle()==XlsysAttachment.STYLE_FTP)
			{
				// 从Ftp下载
				FTPUtil ftpUtil = null;
				InputStream is = null;
				try
				{
					if(inAttachment.getId()!=null) ftpUtil = ((FtpModel)XlsysFactory.getFactoryInstance(XLSYS.FACTORY_FTP).getInstance(inAttachment.getId())).getFtpInstance();
					else ftpUtil = ((FtpModel)XlsysFactory.getFactoryInstance(XLSYS.FACTORY_FTP).getInstance()).getFtpInstance();
					if(inAttachment.getPath()!=null) ftpUtil.cd(inAttachment.getPath());
					// 使用内部名称获取文件
					is = ftpUtil.get(inAttachment.getInnerName());
					BufferedInputStream bis = new BufferedInputStream(is);
					bytes = IOUtil.readBytesFromInputStream(bis, -1);
				}
				catch(Exception e)
				{
					throw e;
				}
				finally
				{
					if(ftpUtil!=null) ftpUtil.close();
					IOUtil.close(is);
				}
			}
			outAttachment = new XlsysAttachment(inAttachment.getAttachmentName(), inAttachment.getLastModified(), inAttachment.getStyle(), bytes, inAttachment.isCompress(), inAttachment.getMd5());
			outAttachment.setId(inAttachment.getId());
			outAttachment.setPath(inAttachment.getPath());
		}
		return outAttachment;
	}

	private Serializable uploadFile(InnerPackage innerPackage) throws Exception
	{
		Serializable inObj = innerPackage.getObj();
		if(inObj instanceof XlsysAttachment)
		{
			XlsysAttachment xlsysAttachment = (XlsysAttachment) inObj;
			if(xlsysAttachment.getStyle()==XlsysAttachment.STYLE_FILE_SYSTEM)
			{
				// 上传至文件系统
				String workDir = null;
				if(xlsysAttachment.getId()!=null) workDir = (String) XlsysFactory.getFactoryInstance(XLSYS.FACTORY_WORKDIR).getInstance(xlsysAttachment.getId());
				else workDir = (String) XlsysFactory.getFactoryInstance(XLSYS.FACTORY_WORKDIR).getInstance();
				// 使用内部名称建立文件
				String filePath = workDir + '/' + (xlsysAttachment.getPath()==null?"":xlsysAttachment.getPath()) + '/' + xlsysAttachment.getInnerName();
				filePath = FileUtil.fixFilePath(filePath);
				FileUtil.createParentPath(filePath);
				FileUtil.writeFile(filePath, xlsysAttachment.getAttachmentData());
			}
			else if(xlsysAttachment.getStyle()==XlsysAttachment.STYLE_FTP)
			{
				// 上传至Ftp
				FTPUtil ftpUtil = null;
				ByteArrayInputStream bais = null;
				try
				{
					if(xlsysAttachment.getId()!=null) ftpUtil = ((FtpModel)XlsysFactory.getFactoryInstance(XLSYS.FACTORY_FTP).getInstance(xlsysAttachment.getId())).getFtpInstance();
					else ftpUtil = ((FtpModel)XlsysFactory.getFactoryInstance(XLSYS.FACTORY_FTP).getInstance()).getFtpInstance();
					if(xlsysAttachment.getPath()!=null)
					{
						ftpUtil.createDirectory(xlsysAttachment.getPath());
						ftpUtil.cd(xlsysAttachment.getPath());
					}
					bais = new ByteArrayInputStream(xlsysAttachment.getAttachmentData());
					// 使用内部名称建立文件
					if(!ftpUtil.uploadFile(bais, xlsysAttachment.getInnerName()))
					{
						throw ftpUtil.getCurException();
					}
				}
				catch(Exception e)
				{
					throw e;
				}
				finally
				{
					if(ftpUtil!=null) ftpUtil.close();
					IOUtil.close(bais);
				}
			}
		}
		return null;
	}

	private Serializable releaseLock(InnerPackage innerPackage) throws Exception
	{
		LockUtil.getInstance().releaseKey((String) innerPackage.getObj());
		return null;
	}

	private Serializable tryLock(InnerPackage innerPackage) throws Exception
	{
		Serializable outObj = null;
		Serializable inObj = innerPackage.getObj();
		if(inObj instanceof String)
		{
			outObj = LockUtil.getInstance().tryLockKey(innerPackage.getSession(), (String) inObj);
		}
		else if(inObj instanceof String[])
		{
			outObj = LockUtil.getInstance().tryLockKey(innerPackage.getSession(), (String[]) inObj);
		}
		return outObj;
	}

	private Serializable getLock(InnerPackage innerPackage) throws InterruptedException, Exception
	{
		Serializable outObj = null;
		Serializable inObj = innerPackage.getObj();
		if(inObj instanceof String)
		{
			outObj = LockUtil.getInstance().getLockKey(innerPackage.getSession(), (String) inObj);
		}
		else if(inObj instanceof String[])
		{
			outObj = LockUtil.getInstance().getLockKey(innerPackage.getSession(), (String[]) inObj);
		}
		return outObj;
	}

	private Serializable getAllDbID(InnerPackage innerPackage) throws NoSuchMethodException, SecurityException, ClassNotFoundException, InstantiationException, IllegalAccessException, IllegalArgumentException, InvocationTargetException, DocumentException
	{
		DBPoolFactory dbPoolFactory = (DBPoolFactory) XlsysFactory.getFactoryInstance(XLSYS.FACTORY_DATABASE);
		return dbPoolFactory.getAllDbId();
	}
	
	private Serializable getAllEnvID(InnerPackage innerPackage) throws NoSuchMethodException, SecurityException, ClassNotFoundException, InstantiationException, IllegalAccessException, IllegalArgumentException, InvocationTargetException, DocumentException
	{
		EnvFactory envFactory = (EnvFactory) XlsysFactory.getFactoryInstance(XLSYS.FACTORY_ENV);
		return envFactory.getAllEnvId();
	}
	
	private Serializable doGetResultCount(InnerPackage innerPackage)
	{
		Serializable outObj = null;
		IDataBase db = null;
		try
		{
			Serializable inObj = innerPackage.getObj();
			ParamBean paramBean = null;
			if(inObj instanceof ParamBean)
			{
				paramBean = (ParamBean) inObj;
				db = EnvDataBase.getInstance(ObjectUtil.objectToInt(innerPackage.getSession().getAttribute(XLSYS.SESSION_ENV_ID)));
			}
			else if(inObj instanceof Object[])
			{
				Object[] arr = (Object[]) innerPackage.getObj();
				int dbid = ObjectUtil.objectToInt(arr[0]);
				paramBean = (ParamBean) arr[1];
				db = ((ConnectionPool) XlsysFactory.getFactoryInstance(XLSYS.FACTORY_DATABASE).getInstance(dbid)).getNewDataBase();
			}
			outObj = db.getResultCount(paramBean);
		}
		catch(Exception e)
		{
			LogUtil.printlnError(e);
			outObj = e;
		}
		finally
		{
			DBUtil.close(db);
		}
		return outObj;
	}
	
	private Serializable getAllTableBaseInfo(InnerPackage innerPackage)
	{
		Serializable outObj = null;
		IDataBase db = null;
		try
		{
			Integer dbid = (Integer) innerPackage.getObj();
			if(dbid==null) db = EnvDataBase.getInstance((int)innerPackage.getSession().getAttribute(XLSYS.SESSION_ENV_ID));
			else db = ((ConnectionPool) XlsysFactory.getFactoryInstance(XLSYS.FACTORY_DATABASE).getInstance(dbid)).getNewDataBase();
			outObj = (Serializable) db.getAllTableBaseInfo();
		}
		catch(Exception e)
		{
			LogUtil.printlnError(e);
			outObj = e;
		}
		finally
		{
			DBUtil.close(db);
		}
		return outObj;
	}
	
	private Serializable doGetTableInfo(InnerPackage innerPackage)
	{
		Serializable outObj = null;
		IDataBase db = null;
		try
		{
			Serializable inObj = innerPackage.getObj();
			String tableName = null;
			if(inObj instanceof String)
			{
				tableName = (String) inObj;
				db = EnvDataBase.getInstance(ObjectUtil.objectToInt(innerPackage.getSession().getAttribute(XLSYS.SESSION_ENV_ID)));
			}
			else if(inObj instanceof Object[])
			{
				Object[] arr = (Object[]) inObj;
				int dbid = ObjectUtil.objectToInt(arr[0]);
				tableName = (String) arr[1];
				db = ((ConnectionPool) XlsysFactory.getFactoryInstance(XLSYS.FACTORY_DATABASE).getInstance(dbid)).getNewDataBase();
			}
			outObj = db.getTableInfo(tableName);
		}
		catch(Exception e)
		{
			LogUtil.printlnError(e);
			outObj = e;
		}
		finally
		{
			DBUtil.close(db);
		}
		return outObj;
	}
	
	private IDataSet getDataSetFromQueryBuffer(IDataBase db, ParamBean pb) throws Exception
	{
		return getDataSetFromQueryBuffer(db, pb, null);
	}
	
	private IDataSet getDataSetFromQueryBuffer(IDataBase db, ParamBean pb, Map<String, Integer> sortColMap) throws Exception
	{
		IDataSet dataSet = null;
		String bufferKey = null;
		if(criticalSize>=0)
		{
			// 启用缓冲处理
			bufferKey = "" + pb.hashCode();
			String srcBufferKey = bufferKey;
			if(sortColMap!=null)
			{
				bufferKey += XLSYS.KEY_CODE_SEPARATOR+sortColMap.hashCode();
			}
			if(!noneedBufferQuery.contains(bufferKey))
			{
				dataSet = queryBuffer.get(bufferKey);
				if(dataSet==null&&!srcBufferKey.equals(bufferKey))
				{
					// 如果源bufferKey有缓冲，则使用源bufferKey的dataSet来构造新的buffer
					IDataSet srcDataSet = queryBuffer.get(srcBufferKey);
					if(srcDataSet!=null)
					{
						dataSet = DataSetUtil.cloneDataSet(srcDataSet);
						dataSet.setSortColumn(sortColMap);
						dataSet.sort();
						queryBuffer.put(bufferKey, dataSet);
					}
				}
			}
		}
		if(dataSet==null)
		{
			// 没有缓冲，则先查询
			dataSet = db.sqlSelect(pb);
			if(sortColMap!=null)
			{
				dataSet.setSortColumn(sortColMap);
				dataSet.sort();
			}
			if(criticalSize>=0&&!noneedBufferQuery.contains(bufferKey))
			{
				// 判断一下查询记录数，如果不够缓冲则加入不需要缓冲的队列，下次就不用再次查询记录数了
				int retCount = dataSet.getRowCount();
				if(retCount>=criticalSize)
				{
					// 需要缓冲, 将dataSet放入缓冲池
					queryBuffer.put(bufferKey, dataSet);
				}
				else
				{
					// 不需要缓冲
					noneedBufferQuery.add(bufferKey);
				}
			}
		}
		else LogUtil.printlnInfo("Use query catch with sql : " + pb.getSelectSql());
		return dataSet;
	}

	private Serializable doSqlSelect(InnerPackage innerPackage, boolean asOneValue)
	{
		Serializable outObj = null;
		IDataBase db = null;
		try
		{
			Serializable inObj = innerPackage.getObj();
			if(inObj instanceof ParamBean)
			{
				ParamBean pb = (ParamBean) inObj;
				db = EnvDataBase.getInstance(ObjectUtil.objectToInt(innerPackage.getSession().getAttribute(XLSYS.SESSION_ENV_ID)));
				if(asOneValue)
				{
					outObj = db.sqlSelectAsOneValue(pb);
				}
				else
				{
					// 不分段查询使用缓冲
					outObj = getDataSetFromQueryBuffer(db, pb);
				}
			}
			else if(inObj instanceof Object[])
			{
				Object[] inArr = (Object[]) inObj;
				if(inArr[0] instanceof ParamBean)
				{
					// 使用环境数据库来查询
					db = EnvDataBase.getInstance(ObjectUtil.objectToInt(innerPackage.getSession().getAttribute(XLSYS.SESSION_ENV_ID)));
					if(inArr.length==3)
					{
						// 分段查询不使用缓冲
						ParamBean pb = (ParamBean)inArr[0];
						int beginRowNum = ObjectUtil.objectToInt(inArr[1]);
						int endRowNum = ObjectUtil.objectToInt(inArr[2]);
						outObj = db.sqlSelect(pb, beginRowNum, endRowNum);
					}
					else if(inArr.length==4)
					{
						// 先排序后分段查询使用缓冲
						ParamBean pb = (ParamBean)inArr[0];
						Map<String, Integer> sortColMap = (Map<String, Integer>)inArr[1];
						int beginRowNum = ObjectUtil.objectToInt(inArr[2]);
						int endRowNum = ObjectUtil.objectToInt(inArr[3]);
						IDataSet dataSet = getDataSetFromQueryBuffer(db, pb, sortColMap);
						outObj = DataSetUtil.subDataSet(dataSet, beginRowNum, endRowNum);
					}
				}
				else
				{
					// 使用非环境数据库来查询
					int dbid = ObjectUtil.objectToInt(inArr[0]);
					db = ((ConnectionPool) XlsysFactory.getFactoryInstance(XLSYS.FACTORY_DATABASE).getInstance(dbid)).getNewDataBase();
					if(inArr.length==2)
					{
						ParamBean selectBean = (ParamBean) inArr[1];
						if(asOneValue)
						{
							outObj = db.sqlSelectAsOneValue(selectBean);
						}
						else
						{
							// 不分段查询使用缓冲
							outObj = getDataSetFromQueryBuffer(db, selectBean);
						}
					}
					else if(inArr.length==4)
					{
						// 分段查询不使用缓冲
						ParamBean pb = (ParamBean)inArr[1];
						int beginRowNum = ObjectUtil.objectToInt(inArr[2]);
						int endRowNum = ObjectUtil.objectToInt(inArr[3]);
						outObj = db.sqlSelect(pb, beginRowNum, endRowNum);
					}
					else if(inArr.length==5)
					{
						// 先排序后分段查询使用缓冲
						ParamBean pb = (ParamBean)inArr[1];
						Map<String, Integer> sortColMap = (Map<String, Integer>)inArr[2];
						int beginRowNum = ObjectUtil.objectToInt(inArr[3]);
						int endRowNum = ObjectUtil.objectToInt(inArr[4]);
						IDataSet dataSet = getDataSetFromQueryBuffer(db, pb, sortColMap);
						outObj = DataSetUtil.subDataSet(dataSet, beginRowNum, endRowNum);
					}
				}
			}
		}
		catch(Exception e)
		{
			LogUtil.printlnError(e);
			outObj = e;
		}
		finally
		{
			DBUtil.close(db);
		}
		return outObj;
	}

	private Serializable doSqlExecute(InnerPackage innerPackage)
	{
		Serializable outObj = null;
		IDataBase db = null;
		try
		{
			Serializable inObj = innerPackage.getObj();
			Object param = null;
			if(inObj instanceof Object[])
			{
				Object[] inArr = (Object[]) inObj;
				int dbid = ObjectUtil.objectToInt(inArr[0]);
				db = ((ConnectionPool) XlsysFactory.getFactoryInstance(XLSYS.FACTORY_DATABASE).getInstance(dbid)).getNewDataBase();
				param = inArr[1];
			}
			else
			{
				// 使用环境数据库查询
				db = EnvDataBase.getInstance(ObjectUtil.objectToInt(innerPackage.getSession().getAttribute(XLSYS.SESSION_ENV_ID)));
				param = inObj;
			}
			if(param instanceof ExecuteBean)
			{
				outObj = db.sqlExecute((ExecuteBean)param);
			}
			else if(param instanceof ParamBean)
			{
				outObj = db.sqlExecute((ParamBean)param);
			}
			else if(param instanceof List<?>)
			{
				outObj = db.sqlExecute((List<ISqlBean>)param);
			}
		}
		catch(Exception e)
		{
			LogUtil.printlnError(e);
			outObj = e;
		}
		finally
		{
			DBUtil.close(db);
		}
		return outObj;
	}
	
	private Serializable doGetLowerFunc(InnerPackage innerPackage)
	{
		Serializable outObj = null;
		IDataBase db = null;
		try
		{
			Serializable inObj = innerPackage.getObj();
			String srcExp = null;
			if(inObj instanceof String)
			{
				db = EnvDataBase.getInstance(ObjectUtil.objectToInt(innerPackage.getSession().getAttribute(XLSYS.SESSION_ENV_ID)));
				srcExp = (String) inObj;
			}
			else if(inObj instanceof Object[])
			{
				Object[] inArr = (Object[]) inObj;
				int dbid = ObjectUtil.objectToInt(inArr[0]);
				db = ((ConnectionPool) XlsysFactory.getFactoryInstance(XLSYS.FACTORY_DATABASE).getInstance(dbid)).getNewDataBase();
				srcExp = ObjectUtil.objectToString(inArr[1]);
			}
			outObj = db.getLowerFunc(srcExp);
		}
		catch(Exception e)
		{
			LogUtil.printlnError(e);
			outObj = e;
		}
		finally
		{
			DBUtil.close(db);
		}
		return outObj;
	}
	
	private Serializable doGetUpperFunc(InnerPackage innerPackage)
	{
		Serializable outObj = null;
		IDataBase db = null;
		try
		{
			Serializable inObj = innerPackage.getObj();
			String srcExp = null;
			if(inObj instanceof String)
			{
				db = EnvDataBase.getInstance(ObjectUtil.objectToInt(innerPackage.getSession().getAttribute(XLSYS.SESSION_ENV_ID)));
				srcExp = (String) inObj;
			}
			else if(inObj instanceof Object[])
			{
				Object[] inArr = (Object[]) inObj;
				int dbid = ObjectUtil.objectToInt(inArr[0]);
				db = ((ConnectionPool) XlsysFactory.getFactoryInstance(XLSYS.FACTORY_DATABASE).getInstance(dbid)).getNewDataBase();
				srcExp = ObjectUtil.objectToString(inArr[1]);
			}
			outObj = db.getUpperFunc(srcExp);
		}
		catch(Exception e)
		{
			LogUtil.printlnError(e);
			outObj = e;
		}
		finally
		{
			DBUtil.close(db);
		}
		return outObj;
	}
	
	private Serializable doGetByteLengthFunc(InnerPackage innerPackage)
	{
		Serializable outObj = null;
		IDataBase db = null;
		try
		{
			Serializable inObj = innerPackage.getObj();
			String srcExp = null;
			if(inObj instanceof String)
			{
				db = EnvDataBase.getInstance(ObjectUtil.objectToInt(innerPackage.getSession().getAttribute(XLSYS.SESSION_ENV_ID)));
				srcExp = (String) inObj;
			}
			else if(inObj instanceof Object[])
			{
				Object[] inArr = (Object[]) inObj;
				int dbid = ObjectUtil.objectToInt(inArr[0]);
				db = ((ConnectionPool) XlsysFactory.getFactoryInstance(XLSYS.FACTORY_DATABASE).getInstance(dbid)).getNewDataBase();
				srcExp = ObjectUtil.objectToString(inArr[1]);
			}
			outObj = db.getByteLengthFunc(srcExp);
		}
		catch(Exception e)
		{
			LogUtil.printlnError(e);
			outObj = e;
		}
		finally
		{
			DBUtil.close(db);
		}
		return outObj;
	}
	
	private Serializable doGetSubStringFunc(InnerPackage innerPackage)
	{
		Serializable outObj = null;
		IDataBase db = null;
		try
		{
			Object[] inObj = (Object[]) innerPackage.getObj();
			if(inObj[0] instanceof String)
			{
				// 使用环境数据库查询
				db = EnvDataBase.getInstance(ObjectUtil.objectToInt(innerPackage.getSession().getAttribute(XLSYS.SESSION_ENV_ID)));
				if(inObj.length==2)
				{
					outObj = db.getSubStringFunc(ObjectUtil.objectToString(inObj[0]), ObjectUtil.objectToInt(inObj[1]));
				}
				else if(inObj.length==3)
				{
					outObj = db.getSubStringFunc(ObjectUtil.objectToString(inObj[0]), ObjectUtil.objectToInt(inObj[1]), ObjectUtil.objectToInt(inObj[2]));
				}
			}
			else
			{
				int dbid = ObjectUtil.objectToInt(inObj[0]);
				db = ((ConnectionPool) XlsysFactory.getFactoryInstance(XLSYS.FACTORY_DATABASE).getInstance(dbid)).getNewDataBase();
				if(inObj.length==3)
				{
					outObj = db.getSubStringFunc(ObjectUtil.objectToString(inObj[1]), ObjectUtil.objectToInt(inObj[2]));
				}
				else if(inObj.length==4)
				{
					outObj = db.getSubStringFunc(ObjectUtil.objectToString(inObj[1]), ObjectUtil.objectToInt(inObj[2]), ObjectUtil.objectToInt(inObj[3]));
				}
			}
			
		}
		catch(Exception e)
		{
			LogUtil.printlnError(e);
			outObj = e;
		}
		finally
		{
			DBUtil.close(db);
		}
		return outObj;
	}
	
	private Serializable doGetToDateFunc(InnerPackage innerPackage)
	{
		Serializable outObj = null;
		IDataBase db = null;
		try
		{
			Serializable inObj = innerPackage.getObj();
			Date date = null;
			if(inObj instanceof Date)
			{
				db = EnvDataBase.getInstance(ObjectUtil.objectToInt(innerPackage.getSession().getAttribute(XLSYS.SESSION_ENV_ID)));
				date = (Date) inObj;
			}
			else if(inObj instanceof Object[])
			{
				Object[] inArr = (Object[]) inObj;
				int dbid = ObjectUtil.objectToInt(inArr[0]);
				db = ((ConnectionPool) XlsysFactory.getFactoryInstance(XLSYS.FACTORY_DATABASE).getInstance(dbid)).getNewDataBase();
				date = (Date) inArr[1];
			}
			outObj = db.getToDateFunc(date);
		}
		catch(Exception e)
		{
			LogUtil.printlnError(e);
			outObj = e;
		}
		finally
		{
			DBUtil.close(db);
		}
		return outObj;
	}
	
	private Serializable doDisableConstraint(InnerPackage innerPackage)
	{
		Serializable outObj = null;
		IDataBase db = null;
		try
		{
			Object[] inArr = (Object[]) innerPackage.getObj();
			String tableName = null;
			String constraintName = null;
			if(inArr[0] instanceof String)
			{
				tableName = ObjectUtil.objectToString(inArr[0]);
				constraintName = ObjectUtil.objectToString(inArr[1]);
				db = EnvDataBase.getInstance(ObjectUtil.objectToInt(innerPackage.getSession().getAttribute(XLSYS.SESSION_ENV_ID)));
			}
			else
			{
				int dbid = ObjectUtil.objectToInt(inArr[0]);
				tableName = ObjectUtil.objectToString(inArr[1]);
				constraintName = ObjectUtil.objectToString(inArr[2]);
				db = ((ConnectionPool) XlsysFactory.getFactoryInstance(XLSYS.FACTORY_DATABASE).getInstance(dbid)).getNewDataBase();
			}
			outObj = db.disableConstraint(tableName, constraintName);
		}
		catch(Exception e)
		{
			LogUtil.printlnError(e);
			outObj = e;
		}
		finally
		{
			DBUtil.close(db);
		}
		return outObj;
	}
	
	private Serializable doEnableConstraint(InnerPackage innerPackage)
	{
		Serializable outObj = null;
		IDataBase db = null;
		try
		{
			Object[] inArr = (Object[]) innerPackage.getObj();
			String tableName = null;
			String constraintName = null;
			if(inArr[0] instanceof String)
			{
				tableName = ObjectUtil.objectToString(inArr[0]);
				constraintName = ObjectUtil.objectToString(inArr[1]);
				db = EnvDataBase.getInstance(ObjectUtil.objectToInt(innerPackage.getSession().getAttribute(XLSYS.SESSION_ENV_ID)));
			}
			else
			{
				int dbid = ObjectUtil.objectToInt(inArr[0]);
				tableName = ObjectUtil.objectToString(inArr[1]);
				constraintName = ObjectUtil.objectToString(inArr[2]);
				db = ((ConnectionPool) XlsysFactory.getFactoryInstance(XLSYS.FACTORY_DATABASE).getInstance(dbid)).getNewDataBase();
			}
			outObj = db.enableConstraint(tableName, constraintName);
		}
		catch(Exception e)
		{
			LogUtil.printlnError(e);
			outObj = e;
		}
		finally
		{
			DBUtil.close(db);
		}
		return outObj;
	}
	
	private Serializable doGetNextValue(InnerPackage innerPackage) throws Exception
	{
		String key = (String) innerPackage.getObj();
		AutoIdAllocate autoIdAllocate = AutoIdAllocate.getInstance();
		Serializable outObj = autoIdAllocate.getNextValueByKey(key);
		return outObj;
	}
	
	private Serializable doAllocId(InnerPackage innerPackage) throws Exception
	{
		Object[] inObj = (Object[]) innerPackage.getObj();
		Serializable outObj = null;
		int envId = ObjectUtil.objectToInt(innerPackage.getSession().getAttribute(XLSYS.SESSION_ENV_ID));
		Env env = (Env) XlsysFactory.getFactoryInstance(XLSYS.FACTORY_ENV).getInstance(envId);
		String tableName = ObjectUtil.objectToString(inObj[0]);
		int dbid = env.getDbIdByTableName(tableName);
		AutoIdAllocate autoIdAllocate = AutoIdAllocate.getInstance();
		if(inObj.length==2)
		{
			outObj = autoIdAllocate.allocateId(dbid, tableName, (String) inObj[1]);
		}
		else if(inObj.length==3)
		{
			outObj = autoIdAllocate.allocateId(dbid, tableName, (String) inObj[1], (String) inObj[2]);
		}
		else if(inObj.length==4)
		{
			outObj = autoIdAllocate.allocateId(dbid, tableName, (String) inObj[1], (String) inObj[2], (Boolean) inObj[3]);
		}
		else if(inObj.length==5)
		{
			outObj = autoIdAllocate.allocateId(dbid, tableName, (String) inObj[1], (String) inObj[2], (Boolean) inObj[3], ObjectUtil.objectToInt(inObj[4]));
		}
		return outObj;
	}

	@Override
	public void loadAllData()
	{
		queryBuffer.clear();
		noneedBufferQuery.clear();
	}

	@Override
	public void loadData(Map<String, Serializable> paramMap)
	{
		loadAllData();
	}
}