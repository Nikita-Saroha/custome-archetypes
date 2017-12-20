package ${package}.interceptors;

import java.net.InetAddress;
import java.util.Date;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.slf4j.MDC;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import com.cox.amp.commons.util.CommonConstants.MDC_Constants;

@Component
public class LoggingInterceptor extends HandlerInterceptorAdapter {
	
	@Value("${service.name}")
	String servicename;

	@Value("${service.resourceName}")
	String resourceName;

	@Value("${service.operationName}")
	String operationName;

	@Value("${service.version}")
	String version;
	
	@Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception exception)
    throws Exception {
		// cleanup tasks
		//Logger logger = LoggerFactory.getLogger(((HandlerMethod)handler).getBeanType());
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView)
    throws Exception {
    	
    }

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {

		MDC.put(MDC_Constants.RESOURCE, request.getRequestURI());
		MDC.put(MDC_Constants.SERVICE_NAME, servicename);
		MDC.put(MDC_Constants.CHECK_POINT, String.valueOf(new Date().getTime()));
		MDC.put(MDC_Constants.START_TIME, String.valueOf(new Date().getTime()));
		MDC.put(MDC_Constants.TRANSACTION_ID,"ID_"+InetAddress.getLocalHost().getHostName() +"-" + UUID.randomUUID().toString());
		if(StringUtils.isNotBlank(request.getHeader(MDC_Constants.SITE_ID)))
			MDC.put(MDC_Constants.SITE_ID, request.getHeader(MDC_Constants.SITE_ID));
		if(StringUtils.isNotBlank(request.getHeader(MDC_Constants.CLIENT_ID)))
			MDC.put(MDC_Constants.CLIENT_ID, request.getHeader(MDC_Constants.CLIENT_ID));
		if(StringUtils.isNotBlank(request.getHeader(MDC_Constants.X_API_KEY)))
			MDC.put(MDC_Constants.X_API_KEY, request.getHeader(MDC_Constants.X_API_KEY));
		if(StringUtils.isNotBlank(request.getHeader(MDC_Constants.CLIENT_TRANSACTION_ID)))
			MDC.put(MDC_Constants.CLIENT_TRANSACTION_ID, request.getHeader(MDC_Constants.CLIENT_TRANSACTION_ID));
		MDC.put(MDC_Constants.RESOURCE_NAME, resourceName);
		MDC.put(MDC_Constants.OPERATION_NAME, operationName);
		MDC.put("version", version);
        return true;
	}
    
}
