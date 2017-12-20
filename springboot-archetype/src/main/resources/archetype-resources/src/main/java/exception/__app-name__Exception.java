package ${package}.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;

import com.cox.amp.exception.BaseExceptionHandler;
import com.cox.amp.exception.ServiceException;
import com.cox.amp.exception.SystemException;

@ControllerAdvice
public class ${app-name}Exception extends BaseExceptionHandler{
	
	public ${app-name}Exception(){
		super();
		addMapping(ServiceException.class, "SERVICE_ERROR", HttpStatus.BAD_REQUEST);
        addMapping(SystemException.class, "SYSTEM_ERROR", HttpStatus.INTERNAL_SERVER_ERROR);
	}
}
