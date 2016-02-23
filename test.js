importPackage(java.util); 
importClass(Packages.xlsys.base.database.bean.SelectBean); 

var list = new java.util.ArrayList();
list.add("a");
list.add("b");
list.add("c");

for(i=0;i<list.size();i++)
{
	log.printlnInfo(list.get(i));
	str += list.get(i)
}

var p1 = new person("刘旭东", 18);
log.printlnInfo(p1.name);
p1.changeName("郑瑶瑶");
log.printlnInfo(p1.name);

var sb = new SelectBean("select * from wcode");
log.printlnInfo(sb.getSelectSql());

var areaImport = JavaImporter(Packages.xlsys.session);
with (areaImport) {
    var s = new Session("lxd");
    log.printlnInfo(s.getSessionId());
}

// 在脚本中实现一个java接口
var maxFunc = {max:function(a,b){return (a > b) ?a:b;}}; 
maxImpl = Packages.xlsys.base.test.TestInterface(maxFunc); 
log.printlnInfo(maxImpl.max(23,43));


function person(name,age)
{
	this.name = name;
	this.age = age;

	this.changeName = function changeName(name)
	{
		this.name = name;
	}
}

function test(temp)
{
	log.printlnInfo("aaaaaaaaaaaa");
}

// 使用try...catch语句
try
{
   //在此运行代码
}
catch(err)
{
   log.printlnError(err.description);
}