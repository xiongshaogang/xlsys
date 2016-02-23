package xlsys.base.script.complier.study.symbols;

import xlsys.base.script.complier.study.lexer.Tag;

public class Array extends Type
{
	// 数组的元素类型
	public Type of;
	// 元素个数
	public int size;
	
	public Array(int size, Type of)
	{
		super("[]", Tag.INDEX, size*of.width);
		this.size = size;
		this.of = of;
	}

	@Override
	public String toString()
	{
		return "["+size+"] "+of.toString();
	}

}
