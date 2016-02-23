package xlsys.base.test;

import java.io.Serializable;
import java.lang.reflect.Method;

public class ReflectTest
{

	public static void dynamicParam(String command, Serializable ... param)
	{
		System.out.println(param.length);
	}
	
	public static void main(String[] args) throws NoSuchMethodException, SecurityException
	{
		Method m = ReflectTest.class.getDeclaredMethod("dynamicParam", String.class, Serializable[].class);
		System.out.println(m);
	}

}
