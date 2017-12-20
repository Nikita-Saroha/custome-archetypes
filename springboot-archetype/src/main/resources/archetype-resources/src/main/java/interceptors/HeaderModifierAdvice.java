package ${package}.interceptors;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;

import org.apache.commons.lang.StringUtils;
import org.slf4j.MDC;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.MethodParameter;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyAdvice;

import com.cox.amp.commons.logging.LoggerUtil;
import com.cox.amp.commons.util.CommonConstants.MDC_Constants;

@ControllerAdvice
public class HeaderModifierAdvice implements ResponseBodyAdvice<Object>{
	
	@Value("${service.name}")
	String servicename;
	
	@Value("${service.resourceName}")
	String resourceName;

	@Value("${service.operationName}")
	String operationName;

	@Value("${service.version}")
	String version;

    @Override
    public boolean supports(MethodParameter returnType, Class<? extends HttpMessageConverter<?>> converterType) {
        return true;
    }

    @Override
    public Object beforeBodyWrite(Object body, MethodParameter returnType, MediaType selectedContentType, Class<? extends HttpMessageConverter<?>> selectedConverterType, ServerHttpRequest request, ServerHttpResponse response) {
		response.getHeaders().add("servicename", servicename);
		response.getHeaders().add("resourcename", resourceName);
		response.getHeaders().add("operationname", operationName);
		response.getHeaders().add("version", version);
		String transactionid = null;
		try {
			if(StringUtils.isNotBlank(MDC.get(MDC_Constants.TRANSACTION_ID)))
				transactionid = MDC.get(MDC_Constants.TRANSACTION_ID);
			else
				transactionid = "ID-" + InetAddress.getLocalHost().getHostName() + "-" + UUID.randomUUID().toString();
			response.getHeaders().add("transactionId", transactionid);
		} catch (UnknownHostException e) {
			e.printStackTrace();
		}
		if(MDC.get(MDC_Constants.START_TIME) == null) {
			MDC.put(MDC_Constants.START_TIME, String.valueOf(System.currentTimeMillis()));
		}
		long duration = System.currentTimeMillis() - Long.parseLong((String) MDC.get(MDC_Constants.START_TIME));
		response.getHeaders().add("duration", String.valueOf(duration));
		response.getHeaders().add("exitTimestamp", new SimpleDateFormat("yyyyMMdd HH:mm:ss.SSS z").format(new Date()));

		if (request.getHeaders().containsKey(MDC_Constants.CLIENT_ID))
			MDC.put(MDC_Constants.CLIENT_ID, request.getHeaders().get(MDC_Constants.CLIENT_ID).get(0));
		if (request.getHeaders().containsKey(MDC_Constants.X_API_KEY))
			MDC.put(MDC_Constants.X_API_KEY, request.getHeaders().get(MDC_Constants.X_API_KEY).get(0));
		if (request.getHeaders().containsKey(MDC_Constants.CLIENT_TRANSACTION_ID))
			MDC.put(MDC_Constants.CLIENT_TRANSACTION_ID,
					request.getHeaders().get(MDC_Constants.CLIENT_TRANSACTION_ID).get(0));
		MDC.put(MDC_Constants.RESOURCE_NAME, resourceName);
		MDC.put(MDC_Constants.OPERATION_NAME, operationName);
		MDC.put(MDC_Constants.SERVICE_NAME, servicename);
		MDC.put(MDC_Constants.TRANSACTION_ID, transactionid);
		if (request.getHeaders().containsKey(MDC_Constants.SITE_ID))
			MDC.put(MDC_Constants.SITE_ID, request.getHeaders().get(MDC_Constants.SITE_ID).get(0));

		String message = null;
		if (response.getHeaders().containsKey(MDC_Constants.CUSTOM_CODE)) {
			MDC.put(MDC_Constants.IS_FAILURE, "failed");
			MDC.put(MDC_Constants.CUSTOM_CODE, response.getHeaders().get(MDC_Constants.CUSTOM_CODE).get(0));
			message = response.getHeaders().get("message").get(0);
		} else {
			MDC.put(MDC_Constants.HTTP_CODE, "200");
			MDC.put(MDC_Constants.CUSTOM_CODE,"SUCCESS");
			message = MDC.get("success_message");
		}
		MDC.put(MDC_Constants.CATEGORY, LoggerUtil.Category.SUMMARY.toString());
		LoggerUtil.logSummaryMessage(null, message);

		return body;

	}

}