package xlsys.base.test;

import java.io.File;
import java.io.IOException;

import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

public class DocumentTest
{

	public static void main(String[] args) throws SAXException, IOException, ParserConfigurationException
	{
		Document document = DocumentBuilderFactory.newInstance().newDocumentBuilder().parse(new File("config.xml"));
		NodeList nodeList = document.getChildNodes();
		for(int i=0;i<nodeList.getLength();++i)
		{
			printlnNode(0, nodeList.item(i));
		}
	}
	
	private static void printlnNode(int deepLevel, Node node)
	{
		StringBuilder sb = new StringBuilder();
		for(int i=0;i<deepLevel;++i) sb.append('\t');
		sb.append(node.getNodeName()).append('(').append(node.getNodeType()).append(')');
		if(node.getNodeType()==Node.TEXT_NODE) sb.append('=').append(node.getNodeValue());
		if(node.hasAttributes())
		{
			NamedNodeMap attrs = node.getAttributes();
			for(int i=0;i<attrs.getLength();++i)
			{
				Node attr = attrs.item(i);
				sb.append(' ').append(attr.getNodeName()).append('=').append(attr.getNodeValue());
			}
		}
		sb.append('\n');
		System.out.print(sb.toString());
		if(node.hasChildNodes())
		{
			NodeList nodeList = node.getChildNodes();
			for(int i=0;i<nodeList.getLength();++i) printlnNode(deepLevel+1, nodeList.item(i));
		}
	}

}
