package xlsys.base.model;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.util.Calendar;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import xlsys.base.LibraryLoader;
import xlsys.base.exception.NativeException;
import xlsys.base.io.util.FileUtil;
import xlsys.base.io.util.IOUtil;
import xlsys.base.util.DateUtil;
import xlsys.base.util.EDCoder;
import xlsys.base.util.SystemUtil;

public final class SystemInfoModel implements IModel
{
	private static final long serialVersionUID = 4047857035063338752L;
	
	/**
	 * mac地址集
	 */
	public Set<String> macSet;
	/**
	 * cpu数量
	 */
	public int cpuCount;
	/**
	 * 操作系统名称
	 */
	public String osName;
	/**
	 * 操作系统架构
	 */
	public String osArch;
	
	/**
	 * 有效日期
	 */
	private Date effectiveDate;
	/**
	 * 可用权限集合
	 */
	private Set<String> rightSet;
	/**
	 * 可用权限正则表达式集合
	 */
	private Set<String> rightRegexSet;
	
	/**
	 * 当前环境是否匹配
	 */
	private Boolean matchCurrentEnv;
	
	private long internetStartTime;
	private long localStartTime;
	
	private SystemInfoModel() throws IOException
	{
		//internetStartTime = DateUtil.getInternetTime_zh().getTime();
		localStartTime = System.currentTimeMillis();
		internetStartTime = localStartTime;
	}
	
	/**
	 * 获取当前系统的环境信息
	 * @return
	 * @throws IOException 
	 */
	public static SystemInfoModel getCurrentSystemInfoModel() throws IOException
	{
		SystemInfoModel sim = new SystemInfoModel();
		Set<String> macSet = new HashSet<String>();
		String[] macArr = SystemUtil.getMacAddresses();
		for(String mac : macArr)
		{
			macSet.add(mac);
		}
		sim.macSet = macSet;
		sim.cpuCount = SystemUtil.getCpuCount();
		sim.osName = SystemUtil.getOsName();
		sim.osArch = SystemUtil.getOsArch();
		return sim;
	}
	
	public boolean isMatchCurrentEnv() throws IOException
	{
		if(matchCurrentEnv==null)
		{
			// 暂时只针对网卡进行匹配
			SystemInfoModel curSim = getCurrentSystemInfoModel();
			matchCurrentEnv = macSet.containsAll(curSim.macSet);
					/*&& this.cpuCount==curSim.cpuCount
					&& this.osName==curSim.osName
					&& this.osArch==curSim.osArch;*/
		}
		return matchCurrentEnv;
	}
	
	/**
	 * 判断当前的命令是否经过许可
	 * @param command
	 * @return
	 * @throws IOException 
	 */
	public boolean hasPermission(String command) throws IOException
	{
		boolean can = false;
		if(isMatchCurrentEnv())
		{
			// 判断有效期
			long passTime = System.currentTimeMillis()-localStartTime;
			if(passTime<0) return false;
			long curFactTime = internetStartTime + passTime;
			if(curFactTime<effectiveDate.getTime())
			{
				// 判断是否有直接权限
				if(rightSet.contains(command))
				{
					can = true;
				}
				else
				{
					// 判断是否有间接权限
					for(String rightRegex : rightRegexSet)
					{
						if(command.matches(rightRegex))
						{
							can = true;
							break;
						}
					}
				}
			}
		}
		return can;
	}
	
	/**
	 * 从正式的许可证文件读取SystemInfoModel
	 * @param filePath
	 * @return
	 * @throws IOException
	 * @throws ClassNotFoundException 
	 * @throws NativeException 
	 */
	public static SystemInfoModel readOfficalSystemInfoModel(String filePath) throws IOException, ClassNotFoundException, NativeException
	{
		EDCoder edCode = EDCoder.getDefaultInstance();
		// 读出字节
		byte[] fileBytes = FileUtil.getByteFromFile(filePath);
		// 解密
		byte[] srcByte = edCode.decrypt(fileBytes);
		// 读出SystemInfoModel
		return (SystemInfoModel) IOUtil.readObject(srcByte);
	}
	
	/**
	 * 将SystemInfoModel写成正式的许可证文件
	 * @param sim
	 * @param filePath
	 * @return
	 * @throws NativeException 
	 */
	public static boolean writeOfficalSystemInfoModel(SystemInfoModel sim, String filePath) throws NativeException
	{
		boolean success = false;
		ByteArrayOutputStream baos = null;
		try
		{
			EDCoder edCode = EDCoder.getDefaultInstance();
			// 写成字节
			baos = new ByteArrayOutputStream();
			ObjectOutputStream oos = new ObjectOutputStream(baos);
			oos.writeObject(sim);
			byte[] srcBytes = baos.toByteArray();
			// 加密
			byte[] encryptBytes = edCode.encrypt(srcBytes);
			// 写入文件
			FileUtil.writeFile(filePath, encryptBytes);
			success = true;
		}
		catch(IOException e)
		{
			success = false;
			e.printStackTrace();
		}
		finally
		{
			IOUtil.close(baos);
		}
		return success;
	}
	
	/**
	 * 根据现有的SystemInfoModel生成有效的SystemInfoModel
	 * @param srcModel 原始的SystemInfoModel
	 * @param effectiveDate 有效期
	 * @param rightSet 权限集
	 * @param rightRegexSet 权限正则表达式集
	 * @return 有效的SystemInfoModel
	 * @throws IOException 
	 */
	public static SystemInfoModel generateEffectiveSystemInfoModel(SystemInfoModel srcModel, Date effectiveDate, Set<String> rightSet, Set<String> rightRegexSet) throws IOException
	{
		SystemInfoModel model = new SystemInfoModel();
		Set<String> macSet = new HashSet<String>();
		macSet.addAll(srcModel.macSet);
		model.macSet = macSet;
		model.cpuCount = srcModel.cpuCount;
		model.osName = srcModel.osName;
		model.osArch = srcModel.osArch;
		model.effectiveDate = effectiveDate;
		model.rightSet = new HashSet<String>();
		if(rightSet!=null) model.rightSet.addAll(rightSet);
		model.rightRegexSet = new HashSet<String>();
		if(rightRegexSet!=null) model.rightRegexSet.addAll(rightRegexSet);
		return model;
	}
	
	public static void main(String[] args) throws Exception
	{
		//SystemInfoModel currentInfoModel = SystemInfoModel.getCurrentSystemInfoModel();
		LibraryLoader.loadLibrary();
		SystemInfoModel currentInfoModel = (SystemInfoModel) IOUtil.readObject(FileUtil.getByteFromFile("D:/systeminfo.xlsys"));
		Set<String> set = new HashSet<String>();
		set.add(".*");
		SystemInfoModel effective = SystemInfoModel.generateEffectiveSystemInfoModel(currentInfoModel, DateUtil.addTime(new Date(), Calendar.YEAR, 5), null, set);
		SystemInfoModel.writeOfficalSystemInfoModel(effective, "D:/license.xlsys");
	}
}
