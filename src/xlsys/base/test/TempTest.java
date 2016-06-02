package xlsys.base.test;

import org.json.JSONObject;

public class TempTest
{
	
	public static void main(String[] args) throws Exception
	{
		String json = "{\"_hashcode\":1372126285,\"_content\":{\"_datatype\":7,\"_content\":false}}";
		JSONObject jo = new JSONObject(json);
		System.out.println(jo.get("_hashcode"));
	}
}
