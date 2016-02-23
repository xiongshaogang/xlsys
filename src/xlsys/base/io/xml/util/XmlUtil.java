package xlsys.base.io.xml.util;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.io.Writer;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;

import org.dom4j.Attribute;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

import xlsys.base.io.util.IOUtil;
import xlsys.base.io.xml.XmlModel;

/**
 * Xml工具
 * @author Lewis
 *
 */
public class XmlUtil
{
	/**
	 * 从一个文件路径中读取XmlModel
	 * @param filePath 文件路径
	 * @return
	 * @throws DocumentException
	 */
	public static XmlModel readXml(String filePath) throws DocumentException
	{
		
		SAXReader reader = new SAXReader();
		File file = new File(filePath).getAbsoluteFile();
		System.out.println("ReadXml From " + file.getAbsolutePath());
		
		Document document = reader.read(file);
		Element root = document.getRootElement();
		return buildXmlModelFromElement(root);
	}
	
	/**
	 * 从一个输入流中读取XmlModel
	 * @param stream 输入流
	 * @return
	 * @throws DocumentException
	 */
	public static XmlModel readXml(InputStream stream) throws DocumentException
	{
		SAXReader reader = new SAXReader();
		Document document = reader.read(stream);
		Element root = document.getRootElement();
		return buildXmlModelFromElement(root);
	}

	@SuppressWarnings("unchecked")
	private static XmlModel buildXmlModelFromElement(Element element)
	{
		XmlModel xmlModel = new XmlModel(element.getName(), element.getText());
		java.util.List<Attribute> attributeList = element.attributes();
		if(attributeList!=null&&!attributeList.isEmpty())
		{
			for(int i=0;i<attributeList.size();i++)
			{
				xmlModel.addAttribute(attributeList.get(i).getName(), attributeList.get(i).getStringValue());
			}
		}
		java.util.List<Element> elementList = element.elements();
		if(elementList!=null&&!elementList.isEmpty())
		{
			for(int i=0;i<elementList.size();i++)
			{
				xmlModel.addChild(buildXmlModelFromElement(elementList.get(i)));
			}
		}
		return xmlModel;
	}
	
	/**
	 * 将XmlModel写入指定的文件路径
	 * @param filePath 文件路径
	 * @param xmlModel xmlModel对象
	 * @throws IOException
	 */
	public static void writerXml(String filePath, XmlModel xmlModel) throws IOException
	{
        PrintWriter pw = IOUtil.getPrintWriter(filePath, false);
        writerXml(pw, xmlModel);
	}
	
	/**
	 * 将XmlModel写入指定的writer
	 * @param writer writer对象
	 * @param xmlModel xmlModel对象
	 * @throws IOException
	 */
	public static void writerXml(Writer writer, XmlModel xmlModel) throws IOException
	{
		Document document = DocumentHelper.createDocument();
		Element root = document.addElement(xmlModel.getName());
		buildElementFromXmlModel(root, xmlModel);
		document.write(writer);
		writer.close();
	}

	private static void buildElementFromXmlModel(Element element, XmlModel xmlModel)
	{
		if(xmlModel.getText()!=null)
		{
			element.addText(xmlModel.getText());
		}
		if(xmlModel.getAttribute()!=null)
		{
			LinkedHashMap<String, String> attributeMap = xmlModel.getAttribute();
			Iterator<String> keyIter = attributeMap.keySet().iterator();
			while(keyIter.hasNext())
			{
				String key = keyIter.next();
				element.addAttribute(key, attributeMap.get(key));
			}
		}
		if(xmlModel.getChildren()!=null)
		{
			List<XmlModel> children = xmlModel.getChildren();
			for(int i=0;i<children.size();i++)
			{
				Element child = element.addElement(children.get(i).getName());
				buildElementFromXmlModel(child, children.get(i));
			}
		}
	}
}
