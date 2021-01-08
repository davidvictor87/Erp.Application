package erp.application.entities.errors;

import javax.servlet.http.HttpServletRequest;

import org.springframework.web.servlet.handler.SimpleMappingExceptionResolver;

public class SwitchableMappingExceptionResolver extends SimpleMappingExceptionResolver{
	
	protected boolean enabled = false;

	public SwitchableMappingExceptionResolver(boolean enabled) {
		this.enabled = enabled;
	}

	
	public boolean isEnabled() {
		return enabled;
	}

	
	public void setEnabled(boolean enabled) {
		this.enabled = enabled;
	}

	
	@Override
	protected boolean shouldApplyTo(HttpServletRequest request, Object handler) {
		return enabled && super.shouldApplyTo(request, handler);
	}

}
