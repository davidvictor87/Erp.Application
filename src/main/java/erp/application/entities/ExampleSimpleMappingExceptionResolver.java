package erp.application.entities;

import java.util.Date;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.SimpleMappingExceptionResolver;

public class ExampleSimpleMappingExceptionResolver extends SimpleMappingExceptionResolver {

	public ExampleSimpleMappingExceptionResolver() {
		setWarnLogCategory(getClass().getName());
	}

	@Override
	public String buildLogMessage(Exception e, HttpServletRequest req) {
		return "MVC exception: " + e.getLocalizedMessage();
	}

	@Override
	protected ModelAndView doResolveException(HttpServletRequest request, HttpServletResponse response, Object handler,
			Exception exception) {
        ModelAndView mav = super.doResolveException(request, response, handler, exception);
		mav.addObject("url", request.getRequestURL());
		mav.addObject("timestamp", new Date());
		mav.addObject("status", 500);
		return mav;
	}

	public boolean isEnabled() {
		// TODO Auto-generated method stub
		return false;
	}
}
