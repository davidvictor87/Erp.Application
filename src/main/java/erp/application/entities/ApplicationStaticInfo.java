package erp.application.entities;

public class ApplicationStaticInfo {
		
	public static final String MY_SQL_FILE_PATH = "classpath:login-mysql.properties";
	
	public static final String MY_SQL_LOGIN_SERVICE = "erp.application.login.repository";
	
	public static final String MY_SQL_LOGIN_ENTITY = "erp.application.login.model";
	
	public static final String MY_SQL_DRIVER_CLASS = "com.mysql.cj.jdbc.Driver";
	
	public static final String MY_SQL_URL = "jdbc:mysql://localhost:3306/login?useSSL=false&useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC";
	
	public static final String IMPORT_JSON_DATA = "http://localhost:5500/generating/json/list/endpoint";
	
	public static final String ORACLE_DRIVER_PSY_ENGINE = "com.p6spy.engine.spy.P6SpyDriver";
	
	public static final String ORACLE_URL = "jdbc:p6spy:oracle:thin:@localhost:1521:orcl";
	
	public static final String setExceptValue(final boolean except) {
		return except ? "true" : "false";
	}

	public static final String setFullTimeValue(final boolean fullTime) {
		return fullTime ? "true" : "false";
	}
	
	public static final String START_SCRIPT_PATH = "D:/Workspace/Erp.Application/startScript.txt";
	
	public static final String PRODUCTS_DOCUMENTS_DIRECTORY = "D:/Employee/documents/import/empFile.txt";
	
	public static final String EMPLOYEE_PROCCESSED_DOCUMENTS = "D:/Employee/documents/proccessed/";
	
	public static final String FILE_WITH_KEYS = "keys.txt";
	
	public static final String START_REDIS_SERVER_SCRIPT = "D:\\redis\\Redis-x64-3.2.100\\redis-server.exe";
	
	public static final String EMPLOYEE_LOG_DIRECTORY = "D:/Workspace/Erp.Application/";
	
	public static final String EMPLOYEE_LOG_FILE_EXTENSION = ".log";
	
}
