package xlsys.base;

/**
 * 系统中静态常量定义专用类
 * @author Lewis
 *
 */
public interface XLSYS
{
	public final static String PLATFORM = "_PLATFORM";
	public final static int PLATFORM_WEB = 1;
	public final static int PLATFORM_MOBILE = 2;
	
	// 以下是Base包中的Command命令
	/**
	 * 当没有权限运行时，返回该命令字
	 */
	public final static String COMMAND_PERMISSION_DENIED = "_COMMAND_PERMISSION_DENIED";
	/**
	 * 执行正常时，返回该命令字
	 */
	public final static String COMMAND_OK = "_COMMAND_OK";
	/**
	 * 执行任务忙时(通常是线程池满，进程锁获取不到或内存不足等情况)，返回该命令字
	 */
	public final static String COMMAND_BUSY = "_COMMAND_BUSY";
	/**
	 * 返回为提示信息
	 */
	public final static String COMMAND_INFO = "_COMMAND_INFO";
	/**
	 * 返回为错误信息
	 */
	public final static String COMMAND_ERROR = "_COMMAND_ERROR";
	/**
	 * 用于测试与服务端的通信是否畅通
	 */
	public final static String COMMAND_FOR_TEST = "_COMMAND_FOR_TEST";
	/**
	 * 获取数据库中的所有表名
	 */
	public final static String COMMAND_DB_GET_ALL_TABLE_BASE_INFO = "_COMMAND_DB_GET_ALL_TABLE_BASE_INFO";
	/**
	 * 获取表信息的命令字
	 */
	public final static String COMMAND_DB_GET_TABLE_INFO = "_COMMAND_DB_GET_TABLE_INFO";
	/**
	 * 获取查询总行数的命令字
	 */
	public final static String COMMAND_DB_GET_RESULT_COUNT = "_COMMAND_DB_GET_RESULT_COUNT";
	/**
	 * 查询sql命令字
	 */
	public final static String COMMAND_DB_SQL_SELECT = "_COMMAND_DB_SQL_SELECT";
	/**
	 * 查询sql返回第一行第一个数据的命令字
	 */
	public final static String COMMAND_DB_SQL_SELECT_AS_ONE_VALUE = "_COMMAND_DB_SQL_SELECT_AS_ONE_VALUE";
	/**
	 * 执行sql命令的命令字
	 */
	public final static String COMMAND_DB_SQL_EXECUTE = "_COMMAND_DB_SQL_EXECUTE";
	/**
	 * 获取转化小写函数表达式
	 */
	public final static String COMMAND_DB_FUNC_LOWER = "_COMMAND_DB_FUNC_LOWER";
	/**
	 * 获取转化大写函数表达式
	 */
	public final static String COMMAND_DB_FUNC_UPPER = "_COMMAND_DB_FUNC_UPPER";
	/**
	 * 获取字符串字节长度函数表达式
	 */
	public final static String COMMAND_DB_FUNC_BYTELENGTH = "_COMMAND_DB_FUNC_BYTELENGTH";
	/**
	 * 获取截取子字符串表达式
	 */
	public final static String COMMAND_DB_FUNC_SUBSTRING = "_COMMAND_DB_FUNC_SUBSTRING";
	/**
	 * 获取转换成Date类型的表达式
	 */
	public final static String COMMAND_DB_FUNC_TODATE = "_COMMAND_DB_FUNC_TODATE";
	/**
	 * 禁用约束条件
	 */
	public final static String COMMAND_DB_DISABLE_CONSTRAINT = "_COMMAND_DB_DISABLE_CONSTRAINT";
	/**
	 * 启用约束条件
	 */
	public final static String COMMAND_DB_ENABLE_CONSTRAINT = "_COMMAND_DB_ENABLE_CONSTRAINT";
	/**
	 * 分配ID的命令字
	 */
	public final static String COMMAND_ALLOC_ID = "_COMMAND_ALLOC_ID";
	/**
	 * 获取序列下一个值
	 */
	public final static String COMMAND_GET_NEXT_VALUE = "_COMMAND_GET_NEXT_VALUE";
	/**
	 * 获取日期的命令字
	 */
	public final static String COMMAND_GET_DATE = "_COMMAND_GET_DATE";
	/**
	 * 当登录时有多个身份可选，返回该命令字
	 */
	public final static String COMMAND_MULTI_IDENTITY = "_COMMAND_MULTI_IDENTITY";
	/**
	 * 当登录时有多个用户可选，返回该命令字
	 */
	public final static String COMMAND_MULTI_USER = "_COMMAND_MULTI_USER";
	/**
	 * 获取进程锁的命令字
	 */
	public final static String COMMAND_GET_LOCK = "_COMMAND_GET_LOCK";
	/**
	 * 尝试获取进程锁的命令字
	 */
	public final static String COMMAND_TRY_LOCK = "_COMMAND_TRY_LOCK";
	/**
	 * 释放进程锁的命令字
	 */
	public final static String COMMAND_RELEASE_LOCK = "_COMMAND_RELEASE_LOCK";
	/**
	 * 上传文件的命令字
	 */
	public final static String COMMAND_UPLOAD_FILE = "_COMMAND_UPLOAD_FILE";
	/**
	 * 下载文件的命令字
	 */
	public final static String COMMAND_DOWNLOAD_FILE = "_COMMAND_DOWNLOAD_FILE";
	/**
	 * 获取文件URL的命令字
	 */
	public final static String COMMAND_GET_FILE_URL = "_COMMAND_GET_FILE_URL";
	/**
	 * 数据传输的命令字
	 */
	public final static String COMMAND_TRANSPORT_DATA = "_COMMAND_TRANSPORT_DATA";
	/**
	 * 刷新缓存的命令字
	 */
	public final static String COMMAND_REFRESH_BUFFER = "_COMMAND_REFRESH_BUFFER";
	/**
	 * 获取所有缓存名称
	 */
	public final static String COMMAND_GET_ALL_BUFFER_NAME = "_COMMAND_GET_ALL_BUFFER_NAME";
	/**
	 * 获取环境模型
	 */
	public final static String COMMAND_GET_ENV_MODEL = "_COMMAND_GET_ENV_MODEL";
	
	// Client端专用的Command命令
	/**
	 * 获取可连接的所有数据库命令字
	 */
	public final static String COMMAND_GET_ALL_DB_ID = "_COMMAND_GET_ALL_DB_ID";
	/**
	 * 获取可连接的所有环境的命令字
	 */
	public final static String COMMAND_GET_ALL_ENV_ID = "_COMMAND_GET_ALL_ENV_ID";
	/**
	 * 登录时所使用的身份认证命令字
	 */
	public final static String COMMAND_IDENTITY_VERIFICATION = "_COMMAND_IDENTITY_VERIFICATION";
	/**
	 * 获取界面框架模型
	 */
	public final static String COMMAND_GET_FRAME_MODEL = "_COMMAND_GET_FRAME_MODEL";
	/**
	 * 获取所有界面框架模型
	 */
	public final static String COMMAND_GET_ALL_FRAME_MODEL = "_COMMAND_GET_ALL_FRAME_MODEL";
	/**
	 * 获取UI模块模型
	 */
	public final static String COMMAND_GET_UIMODULE_MODEL = "_COMMAND_GET_UIMODULE_MODEL";
	/**
	 * 获取所有UI模块模型
	 */
	public final static String COMMAND_GET_ALL_UIMODULE_MODEL = "_COMMAND_GET_ALL_UIMODULE_MODEL";
	/**
	 * 获取菜单模型
	 */
	public final static String COMMAND_GET_MENU_MODEL = "_COMMAND_GET_MENU_MODEL";
	/**
	 * 获取所有菜单模型
	 */
	public final static String COMMAND_GET_ALL_MENU_MODEL = "_COMMAND_GET_ALL_MENU_MODEL";
	/**
	 * 获取视图模型
	 */
	public final static String COMMAND_GET_VIEW_MODEL = "_COMMAND_GET_VIEW_MODEL";
	/**
	 * 获取所有视图模型
	 */
	public final static String COMMAND_GET_ALL_VIEW_MODEL = "_COMMAND_GET_ALL_VIEW_MODEL";
	/**
	 * 获取所有handler模型
	 */
	public final static String COMMAND_GET_ALL_HANDLER_MODEL = "_COMMAND_GET_ALL_HANDLER_MODEL";
	/**
	 * 获取handler模型
	 */
	public final static String COMMAND_GET_HANDLER_MODEL = "_COMMAND_GET_HANDLER_MODEL";
	/**
	 * 获取所有工具模型
	 */
	public final static String COMMAND_GET_ALL_TOOL_MODEL = "_COMMAND_GET_ALL_TOOL_MODEL";
	/**
	 * 获取工具模型
	 */
	public final static String COMMAND_GET_TOOL_MODEL = "_COMMAND_GET_TOOL_MODEL";
	/**
	 * 获取指定的模型
	 */
	public final static String COMMAND_GET_MODEL = "_COMMAND_GET_MODEL";
	/**
	 * 获取指定的所有模型
	 */
	public final static String COMMAND_GET_ALL_MODEL = "_COMMAND_GET_ALL_MODEL";
	/**
	 * 获取指定模型的数量
	 */
	public final static String COMMAND_GET_MODEL_COUNT = "_COMMAND_GET_MODEL_COUNT";
	
	// 以下是Base包中的Session相关常量
	/**
	 * 未登录时的默认Session ID
	 */
	public final static String SESSION_DEFAULT_ID = "_SESSION_DEFAULT_ID";
	/**
	 * 当前Session所使用的数据库ID
	 */
	// public final static String SESSION_ENV_DBID = "_SESSION_ENV_DBID";
	
	/**
	 * 当前Session所使用的环境ID
	 */
	public final static String SESSION_ENV_ID = "_SESSION_ENV_ID";
	/**
	 * 当前用户的ID
	 */
	public final static String SESSION_USER_ID = "_SESSION_USER_ID";
	/**
	 * 当前用户的用户名
	 */
	public final static String SESSION_USER_NAME = "_SESSION_USER_NAME";
	/**
	 * 当前用户的密码
	 */
	public final static String SESSION_PASS_WORD = "_SESSION_PASS_WORD";
	/**
	 * 当前用户的部门ID
	 */
	public final static String SESSION_DEPT_ID = "_SESSION_DEPT_ID";
	/**
	 * 当前用户的职位ID
	 */
	public final static String SESSION_PST_ID = "_SESSION_PST_ID";
	/**
	 * 当前用户记账部门ID
	 */
	public final static String SESSION_KEEPDEPT_ID = "_SESSION_KEEPDEPT_ID";
	/**
	 * 当前用户的身份
	 */
	public final static String SESSION_IDENTITY = "_SESSION_IDENTITY";
	/**
	 * 标识当前用户是否是系统管理员
	 */
	public final static String SESSION_ADMIN = "_SESSION_ADMIN";
	/**
	 * 标识当前用户是否开启了超级模式
	 */
	public final static String SESSION_SUPERMODE = "_SESSION_SUPERMODE";
	/**
	 * 使用超级模式登陆时的管理员用户ID
	 */
	public final static String SESSION_SUPERMODE_ADMIN_ID = "_SESSION_SUPERMODE_ADMIN_ID";
	/**
	 * 使用超级模式登陆时的管理员用户名
	 */
	public final static String SESSION_SUPERMODE_ADMIN_NAME = "_SESSION_SUPERMODE_ADMIN_NAME";
	/**
	 * 语言环境
	 */
	public final static String SESSION_LANGUAGE = "_SESSION_LANGUAGE";
	/**
	 * Session中临时传递的context
	 */
	public final static String SESSION_TEMP_CONTEXT = "_SESSION_TEMP_CONTEXT";
	/**
	 * Session中临时传递的dataSet
	 */
	public final static String SESSION_TEMP_DATASET = "_SESSION_TEMP_DATASET";
	/**
	 * Session中临时传递的表名
	 */
	public final static String SESSION_TEMP_TABLE_NAME = "_SESSION_TEMP_TABLE_NAME";
	/**
	 * Session中临时传递的多个表名
	 */
	public final static String SESSION_TEMP_TABLE_NAMES = "_SESSION_TEMP_TABLE_NAMES";
	/**
	 * Session中临时传递的列名
	 */
	public final static String SESSION_TEMP_COLUMN_NAME = "_SESSION_TEMP_COLUMN_NAME";
	/**
	 * Session中临时传递的多个列名
	 */
	public final static String SESSION_TEMP_COLUMN_NAMES = "_SESSION_TEMP_COLUMN_NAMES";
	/**
	 * Session中临时传递的编码获取模板
	 */
	public final static String SESSION_TEMP_CODE_LIKE = "_SESSION_TEMP_CODE_LIKE";
	/**
	 * Session中临时传递的获取编码是否使用缓存
	 */
	public final static String SESSION_TEMP_USE_CACHE = "_SESSION_TEMP_USE_CACHE";
	/**
	 * Session中临时传递的分配编码的步长
	 */
	public final static String SESSION_TEMP_STEP = "_SESSION_TEMP_STEP";
	
	/**
	 * 系统默认的配置名称(非文件名称)
	 */
	public final static String CONFIG_DEFAULT_NAME = "_CONFIG_DEFAULT_NAME";
	/**
	 * 默认的管理员编号
	 */
	public final static String DEFAULT_ADMIN_CODE = "999999";
	
	// 用于码名映射的分隔符
	/**
	 * 用于码名映射的分隔符
	 */
	public final static String CODE_NAME_RELATION = ":";
	/**
	 * 用于不同码之间的分隔符
	 */
	public final static String KEY_CODE_SEPARATOR = ";";
	/**
	 * use KEY_CODE_SEPARATOR instand
	 */
	@Deprecated
	public final static String CODE_NAME_SEPARATOR = ";";
	
	// 命令字符串的分隔符以及赋值符
	/**
	 * use KEY_CODE_SEPARATOR instand
	 */
	@Deprecated
	public final static String COMMAND_SEPARATOR = ";";
	/**
	 * 用于命令字与其所对应值之间的分隔符
	 */
	public final static String PARAM_RELATION = "=";
	/**
	 * use PARAM_RELATION instand
	 */
	@Deprecated
	public final static String COMMAND_RELATION = "=";
	
	/**
	 * 用于表示命令字后所跟参数字符串的开始的分隔符
	 */
	public final static String PARAM_QUESTION = "?";
	/**
	 * use PARAM_QUESTION instand
	 */
	@Deprecated
	public final static String COMMAND_QUESTION = "?";
	
	/**
	 * 用于命令字后所跟不同参数之间的分隔符
	 */
	public final static String PARAM_AND = "&";
	/**
	 * use PARAM_AND instand
	 */
	@Deprecated
	public final static String COMMAND_AND = "&";
	
	// 用于反射方法调用的前缀
	/**
	 * Java静态成员变量前缀
	 */
	public final static String JAVA_STATIC_FIELD_PREFIX = "JavaStaticField:";
	/**
	 * Java成员变量前缀
	 */
	public final static String JAVA_FIELD_PREFIX = "JavaField:";
	/**
	 * Java静态方法前缀
	 */
	public final static String JAVA_STATIC_METHOD_PREFIX = "JavaStaticMethod:";
	/**
	 * Java方法前缀
	 */
	public final static String JAVA_METHOD_PREFIX = "JavaMethod:";
	
	// 系统自用字段
	/**
	 * 标识当前数据的建立时间的字段
	 */
	public final static String COL_CREATIONDATE = "creationdate";
	/**
	 * 标识当前数据最近修改时间的字段
	 */
	public final static String COL_MODIFYDATE = "modifydate";
	
	/**
	 * 常用codeLike字符串
	 */
	public final static String CODELIKE_COMMON = "________________";
	
	/**
	 * 默认的小数精度
	 */
	public final static int NUMBER_SCALE = 6;
	
	// 数据流中的数据格式
	/**
	 * 空对象
	 */
	public final static byte DATA_TYPE_NULL = 0;
	/**
	 * 字符串对象
	 */
	public final static byte DATA_TYPE_STR = 1;
	/**
	 * short类型数据
	 */
	public final static byte DATA_TYPE_SHORT = 2;
	/**
	 * int类型数据
	 */
	public final static byte DATA_TYPE_INT = 3;
	/**
	 * long类型数据
	 */
	public final static byte DATA_TYPE_LONG = 4;
	/**
	 * char类型数据
	 */
	public final static byte DATA_TYPE_CHAR = 5;
	/**
	 * boolean类型数据
	 */
	public final static byte DATA_TYPE_BOOL = 7;
	/**
	 * float类型数据
	 */
	public final static byte DATA_TYPE_FLOAT = 8;
	/**
	 * double类型数据
	 */
	public final static byte DATA_TYPE_DOUBLE = 9;
	/**
	 * byte类型数据
	 */
	public final static byte DATA_TYPE_BYTE = 10;
	/**
	 * byte数组类型数据
	 */
	public final static byte DATA_TYPE_BYTE_ARRAY = 11;
	/**
	 * Object对象数据
	 */
	public final static byte DATA_TYPE_OBJECT = 12;
	/**
	 * Object对象数组数据
	 */
	public final static byte DATA_TYPE_OBJECT_ARRAY = 13;
	/**
	 * 集合对象数据
	 */
	public final static byte DATA_TYPE_COLLECTION = 14;
	/**
	 * Map对象数据
	 */
	public final static byte DATA_TYPE_MAP = 15;
	/**
	 * 大数字类型的数据
	 */
	public final static byte DATA_TYPE_BIGDECIMAL = 16;
	/**
	 * 日期类型的数据
	 */
	public final static byte DATA_TYPE_DATE = 17;
	/**
	 * Entry类型的数据
	 */
	public final static byte DATA_TYPE_ENTRY = 18;
	/**
	 * Entry数组类型的数据
	 */
	public final static byte DATA_TYPE_ENTRY_ARRAY = 19;
	/**
	 * String数组类型的数据
	 */
	public final static byte DATA_TYPE_STR_ARRAY = 20;
	/**
	 * Exception类型的数据
	 */
	public final static byte DATA_TYPE_EXCEPTION = 21;
	
	// 序列化模式
	/**
	 * Java内置的序列化方式
	 */
	public final static byte SERIALIZATION_MODE_JDK = 0;
	/**
	 * 系统内置的序列化方式
	 */
	public final static byte SERIALIZATION_MODE_INTERNAL = 1;
	/**
	 * JSON序列化方式
	 */
	public final static byte SERIALIZATION_MODE_JSON = 2;
	
	/**
	 * 封装JSON对象的外层JSON数据的内部私有成员名称
	 */
	public final static String JSON_OBJ_HASHCODE = "_hashcode";
	public final static String JSON_OBJ_CONTENT = "_content";
	/**
	 * 封装JSON对象的内层JSON数据的内部私有成员名称
	 */
	public final static String JSON_OBJ_DATA_TYPE = "_datatype";
	public final static String JSON_OBJ_CLASS = "_classname";
	
	// 系统内置缓冲名称
	/**
	 * 自动分配编码缓冲
	 */
	public final static String BUFFER_AUTOIDALLOCATION = "_BUFFER_AUTOIDALLOCATION";
	/**
	 * 翻译缓冲
	 */
	public final static String BUFFER_TRANSLATION = "_BUFFER_TRANSLATION";
	/**
	 * 数据库查询缓冲
	 */
	public final static String BUFFER_SQL_QUERY = "_BUFFER_SQL_QUERY";
	/**
	 * 表信息缓冲
	 */
	public final static String BUFFER_TABLE_INFO_PREFIX = "_BUFFER_TABLE_INFO_";
	/**
	 * 界面框架模型缓冲
	 */
	public final static String BUFFER_FRAME_MODEL = "_BUFFER_FRAME_MODEL";
	/**
	 * UI模块模型缓冲
	 */
	public final static String BUFFER_UIMODULE_MODEL = "_BUFFER_UIMODULE_MODEL";
	/**
	 * 菜单模型缓冲
	 */
	public final static String BUFFER_MENU_MODEL = "_BUFFER_MENU_MODEL";
	/**
	 * 视图模型缓冲
	 */
	public final static String BUFFER_VIEW_MODEL = "_BUFFER_VIEW_MODEL";
	/**
	 * handler模型缓冲
	 */
	public final static String BUFFER_HANDLER_MODEL = "_BUFFER_HANDLER_MODEL";
	/**
	 * 工具模型缓冲
	 */
	public final static String BUFFER_TOOL_MODEL = "_BUFFER_TOOL_MODEL";
	
	// 系统内置工厂
	/**
	 * 数据库工厂
	 */
	public final static String FACTORY_DATABASE = "_FACTORY_DATABASE";
	/**
	 * FTP工厂
	 */
	public final static String FACTORY_FTP = "_FACTORY_FTP";
	/**
	 * 客户端传输工厂
	 */
	public final static String FACTORY_CLIENT_TRANSFER = "_FACTORY_CLIENT_TRANSFER";
	/**
	 * 工作路径工厂
	 */
	public final static String FACTORY_WORKDIR = "_FACTORY_WORKDIR";
	/**
	 * 日志工厂
	 */
	public final static String FACTORY_LOG = "_FACTORY_LOG";
	/**
	 * 环境工厂
	 */
	public final static String FACTORY_ENV = "_FACTORY_ENV";
	
	// 系统额外请求处理
	/**
	 * 系统额外请求处理, 主要为了处理第三方的网页版客户端请求
	 */
	public final static String EXTRA_COMMAND = "EXTRA_COMMAND";
	/**
	 * 额外请求对应的环境ID
	 */
	public final static String EXTRA_ENVID = "EXTRA_ENVID";
	/**
	 * 额外请求对应的数据
	 */
	public final static String EXTRA_DATA = "EXTRA_DATA";

	/**
	 * 客户端类型 : WEB
	 */
	public final static String WEB_COMMAND = "WEB_COMMAND";
	public final static String WEB_SESSION = "WEB_SESSION";
	public final static String WEB_DATA = "WEB_DATA";
	public final static String WEB_RETPKG = "WEB_RETPKG";
	
	/**
	 * 统计方式 : 加总
	 */
	public final static int AGGREGATION_SUM = 1;
	/**
	 * 统计方式 : 最大值
	 */
	public final static int AGGREGATION_MAX = 2;
	/**
	 * 统计方式 : 最小值
	 */
	public final static int AGGREGATION_MIN = 3;
	/**
	 * 统计方式 : 平均值
	 */
	public final static int AGGREGATION_AVG = 4;
	/**
	 * 统计方式 : 计数
	 */
	public final static int AGGREGATION_COUNT = 5;
	
	/**
	 * 建立查询索引使用缓存数量(MB)
	 */
	public final static double SEARCH_INDEX_WRITER_BUFFER = 256;
	
	/**
	 * 雪狼系统路径配置虚拟机参数
	 */
	public final static String SYSTEM_PROPERTY_CONFIG_PATH = "xlsys.configPath";
	
	/**
	 * 雪狼系统绝对路径配置虚拟机参数
	 */
	public final static String SYSTEM_PROPERTY_ABSOLUTELY_CONFIG_PATH = "xlsys.absolutelyConfigPath";
	
	public final static String RESOURCE_BASE_URL = "/xlsys-resource";
	
	public final static String UPLOAD_PARAM_ATTACHMENT = "_UPLOAD_PARAM_ATTACHMENT";
	public final static String DOWNLOAD_PARAM_URL = "_DOWNLOAD_PARAM_URL";
	public final static String DOWNLOAD_PARAM_FILE_NAME = "_DOWNLOAD_PARAM_FILE_NAME";
}
