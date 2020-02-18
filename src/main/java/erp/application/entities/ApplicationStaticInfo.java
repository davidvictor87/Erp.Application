package erp.application.entities;

public class ApplicationStaticInfo {
		
	public static final String MySqlFilePath = "classpath:login-mysql.properties";
	
	public static final String MySqlLoginService = "erp.application.login.repository";
	
	public static final String MySqlLoginEntity = "erp.application.login.model";
	
	public static final String MY_SQL_URL = "jdbc:mysql://localhost:3306/login?useSSL=false&useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC";
	
	public static final String IMPORT_JSON_DATA = "http://localhost:5500/generating/json/list/endpoint";
	
	public static final String setExceptValue(boolean except) {
		return except ? "true" : "false";
	}

	public static final String setFullTimeValue(boolean fulltime) {
		return fulltime ? "true" : "false";
	}
	
	public static final String startScriptPath = "D:\\Workspace\\Erp.Application\\startScript.txt";
	
}
