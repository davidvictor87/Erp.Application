package erp.application.entities;

import java.lang.reflect.Method;
import java.util.Map;

import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.core.annotation.AnnotatedElementUtils;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.mvc.condition.ConsumesRequestCondition;
import org.springframework.web.servlet.mvc.condition.HeadersRequestCondition;
import org.springframework.web.servlet.mvc.condition.ParamsRequestCondition;
import org.springframework.web.servlet.mvc.condition.PatternsRequestCondition;
import org.springframework.web.servlet.mvc.condition.ProducesRequestCondition;
import org.springframework.web.servlet.mvc.condition.RequestMethodsRequestCondition;
import org.springframework.web.servlet.mvc.method.RequestMappingInfo;
import org.springframework.web.servlet.mvc.method.RequestMappingInfoHandlerMapping;

@Component
public class ShowAllEndpoints {
	
	@Bean
	public RequestMappingInfoHandlerMapping mappingHandler() {
		return new RequestMappingInfoHandlerMapping() {
			@Override
			protected boolean isHandler(Class<?> beanType) {
				return (AnnotatedElementUtils.hasAnnotation(beanType, Controller.class) || 
						AnnotatedElementUtils.hasAnnotation(beanType, RestController.class));
			}
			@Override
			protected RequestMappingInfo getMappingForMethod(Method method, Class<?> handlerType) {
				RequestMapping annotation = AnnotatedElementUtils.findMergedAnnotation(method, RequestMapping.class);
				if (annotation != null) {
					return new RequestMappingInfo(
							new PatternsRequestCondition(annotation.value(), getUrlPathHelper(), getPathMatcher(), true, true),
							new RequestMethodsRequestCondition(annotation.method()),
							new ParamsRequestCondition(annotation.params()),
							new HeadersRequestCondition(annotation.headers()),
							new ConsumesRequestCondition(annotation.consumes(), annotation.headers()),
							new ProducesRequestCondition(annotation.produces(), annotation.headers()), null);
				}
				else {
					return null;
				}
			}
		};
	}
	
	@EventListener
	public void handleContextRefresh(ContextRefreshedEvent refresh) {
		ApplicationContext context = refresh.getApplicationContext();
		RequestMappingInfoHandlerMapping mappingHandler = context.getBean("mappingHandler", RequestMappingInfoHandlerMapping.class);
		Map<RequestMappingInfo, HandlerMethod> map = mappingHandler.getHandlerMethods();
		map.forEach((key, value) -> LOG.appLogger().info("{} {}", key, value));
	}

}
