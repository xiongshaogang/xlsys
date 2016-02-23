package xlsys.base.test;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.TokenStream;
import org.apache.lucene.analysis.cn.smart.SmartChineseAnalyzer;
import org.apache.lucene.analysis.tokenattributes.CharTermAttribute;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.DoubleField;
import org.apache.lucene.document.Field;
import org.apache.lucene.document.StringField;
import org.apache.lucene.document.TextField;
import org.apache.lucene.index.DirectoryReader;
import org.apache.lucene.index.IndexReader;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.IndexWriterConfig;
import org.apache.lucene.index.IndexWriterConfig.OpenMode;
import org.apache.lucene.index.Term;
import org.apache.lucene.queryparser.classic.QueryParser;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.ScoreDoc;
import org.apache.lucene.search.TopDocs;
import org.apache.lucene.store.Directory;

import xlsys.base.database.EnvDataBase;
import xlsys.base.database.IEnvDataBase;
import xlsys.base.database.util.DBUtil;
import xlsys.base.search.DBDirectory;

public class LuceneTest
{
	private static String[] names = new String[]{"苹果","梨","葡萄","红提","枣","柑橘","柚","桃","西瓜","杏","甜瓜","香瓜","荔枝","甘蔗","柿","柠檬","香蕉","芒果","菠萝","哈密瓜","李","石榴","枸杞","山楂","椰子","桑葚","荸荠","柚子","草莓","沙糖桔","木瓜","橙","圣女果","龙眼","黄瓜"};
	
	private static String[] descriptions = new String[]{"鲜红透亮","白里透红","紫中带绿","晶莹透亮","玛瑙似的","又大又圆","果实肥硕","枇杷泛金","果香四溢","酸甜可口","冰凉爽口","又香又甜","汁多味甜","瓜绿瓤红","颗粒饱满","香甜可口","酸甜可口","清甜爽口","清脆可口","甘甜无比","甘美多汁","又酸又甜","清香诱人","扁圆形","月牙形","甜津津","黄澄澄的","红通通的","红扑扑的","青里透红的","棕黄色的","红艳艳的","白里透红的","红黄色的","汉白玉一般的果肉","晶莹洁白的果肉","乳白色的果肉","新鲜红嫩的果肉","黑而发亮的核","乳白色的硬核","米粒大的核","有小瓷碗大小","有我们的拳头大小"};
	
	private static void indexVitrualGoods(IndexWriter writer) throws IOException
	{
		Random random = new Random();
		for(int i=0;i<100;++i)
		{
			Goods goods = new Goods();
			// 生成13位条码
			String barcode = "";
			for(int j=0;j<13;++j)
			{
				barcode += random.nextInt(10);
			}
			goods.barcode = barcode;
			// 生成名称
			goods.name = names[random.nextInt(names.length)];
			// 生成描述
			String description = "";
			int descCount = random.nextInt(10);
			for(int j=0;j<descCount;++j)
			{
				description += descriptions[random.nextInt(descriptions.length)];
				if(j!=descCount-1) description += "，";
			}
			goods.description = description;
			// 分类
			goods.category = "水果";
			goods.price = random.nextDouble()*30;
			// 打印水果
			System.out.println(goods.toString());
			// 将水果写入
			Document doc = new Document();
			Field barcodeField = new StringField("barcode", goods.barcode, Field.Store.YES);
			doc.add(barcodeField);
			Field nameField = new StringField("name", goods.name, Field.Store.YES);
			doc.add(nameField);
			Field descriptionField = new TextField("description", goods.description, Field.Store.NO);
			doc.add(descriptionField);
			Field categoryField = new StringField("category", goods.category, Field.Store.YES);
			doc.add(categoryField);
			Field priceField = new DoubleField("price", goods.price, Field.Store.YES);
			doc.add(priceField);
			if(writer.getConfig().getOpenMode() == OpenMode.CREATE) writer.addDocument(doc);
			else writer.updateDocument(new Term("barcode", barcode), doc);
		}
	}
	
	public static void createIndex()
	{
		IEnvDataBase dataBase = null;
		try
		{
			dataBase = EnvDataBase.getInstance(1001);
			Directory dir = new DBDirectory(dataBase, "xlem_goodsindex", "filename", "idx", "content");
			// Directory dir = FSDirectory.open(Paths.get("index"));
			// Analyzer analyzer = new StandardAnalyzer();
			Analyzer analyzer = new SmartChineseAnalyzer();
			IndexWriterConfig iwc = new IndexWriterConfig(analyzer);
			iwc.setOpenMode(OpenMode.CREATE);
			// iwc.setOpenMode(OpenMode.CREATE_OR_APPEND);
			iwc.setRAMBufferSizeMB(256.0);
			IndexWriter writer = new IndexWriter(dir, iwc);
			indexVitrualGoods(writer);
			writer.close();
			dir.close();
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		finally
		{
			DBUtil.close(dataBase);
		}
	}
	
	public static void search(String searchStr)
	{
		IEnvDataBase dataBase = null;
		try
		{
			dataBase = EnvDataBase.getInstance(1001);
			Directory dir = new DBDirectory(dataBase, "xlem_goodsindex", "filename", "idx", "content");
			// Directory dir = FSDirectory.open(Paths.get("index"));
			IndexReader reader = DirectoryReader.open(dir);
			IndexSearcher searcher = new IndexSearcher(reader);
			Analyzer analyzer = new SmartChineseAnalyzer();
			QueryParser parser = new QueryParser("description", analyzer);
			Query query = parser.parse(searchStr);
			TopDocs results = searcher.search(query, 100);
			ScoreDoc[] hits = results.scoreDocs;
			int numTotalHits = results.totalHits;
			System.out.println(numTotalHits + " total matching documents");
			for(int i=0;i<hits.length;++i)
			{
				Document hitDoc = searcher.doc(hits[i].doc);
				String barcode = hitDoc.get("barcode");
				String name = hitDoc.get("name");
				System.err.println("name="+name+"\t\tbarcode="+barcode);
			}
			reader.close();
			dir.close();
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		finally
		{
			DBUtil.close(dataBase);
		}
	}
	
	public static ArrayList<String> tokenizer(String str)
	{
		ArrayList<String> result = new ArrayList<String>();
		SmartChineseAnalyzer analyzer = new SmartChineseAnalyzer();
		try
		{
			//获得分词流
			TokenStream tokenStream = analyzer.tokenStream("", str);
			//分词
			CharTermAttribute term = tokenStream.addAttribute(CharTermAttribute.class);
			tokenStream.reset();
			while(tokenStream.incrementToken())
			{
				result.add(term.toString()); 
			}
			tokenStream.end();
			tokenStream.close();
		}
		catch(IOException e)
		{
			e.printStackTrace();
		}
		finally
		{
			analyzer.close();
		}
		return result;
	}
	
	public static void main(String[] args) throws Exception
	{
		// System.out.println(tokenizer("我是中国人，我叫刘旭东，我的老婆是郑瑶瑶，她的英文名叫Sarah，我喜欢吃西瓜和葡萄，我们都出生在新疆维吾尔族自治区。"));
		createIndex();
		search("鲜红");
		// System.out.println(tokenizer("新鲜红嫩的果肉"));
		System.exit(0);
	}
}

class Goods
{
	public String barcode;
	public String name;
	public String description;
	public String category;
	public double price;
	
	@Override
	public String toString()
	{
		StringBuilder sb = new StringBuilder();
		sb.append("barcode=").append(barcode).append('\t');
		sb.append("name=").append(name).append('\t');
		sb.append("description=").append(description).append('\t');
		sb.append("category=").append(category).append('\t');
		sb.append("price=").append(price);
		return sb.toString();
	}
}
