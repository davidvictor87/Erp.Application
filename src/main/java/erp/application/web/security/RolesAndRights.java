package erp.application.web.security;

import java.lang.annotation.ElementType;
import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

public enum RolesAndRights {
	
	ADMIN("ADMIN"), MANAGER("MANAGER"), USER("USER"), READ("READ"), WRITE("WRITE"), UPDATE("UPDATE"), DELETE("DELETE");
	
	private String param;
	
	private RolesAndRights(final String param) {
		this.param = param;
	}
	
	@Inherited
	@Target(ElementType.METHOD)
	@Retention(RetentionPolicy.RUNTIME)
	@interface roleAndRight{
		RolesAndRights[] value();
	}
	
	public String getRolesAndRights() {
		return this.param;
		
	}

}
