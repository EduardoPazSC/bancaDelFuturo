package com.citibanamex.bne.poc-d-accounttransfer.errorhandling;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import com.citibanamex.bne.poc-d-accounttransfer.errorhandling.exception.MicroserviceClientException;


@ControllerAdvice
public class ErrorResolver {

	private final Logger logger = LoggerFactory.getLogger(ErrorResolver.class);
	
	
	public static final String HTTPMESSAGENOTREADABLEEXCEPTION_ERROR_CODE = "BNEE-430";
	public static final String METHODARGUMENTNOTVALIDEXCEPTION_ERROR_CODE = "BNEE-431";
	public static final String CONSTRAINTVIOLATIONEXCEPTION_ERROR_CODE = "BNEE-432";
	
	public static final String EXCEPTION_ERROR_CODE = "BNEE-500";
	public static final String MICROSERVICE_CLIENT_ERROR_CODE = "BNEE-530";
	
	public static final String GENERIC_ERROR_DESC = "Something went wrong! Further details may be available in logs.";
	public static final String GENERIC_FAILURE_DESC = "See error description at the corresponding catalog.";
		
	
	
	@ExceptionHandler(HttpMessageNotReadableException.class)
	@ResponseStatus(value = HttpStatus.BAD_REQUEST)
	public @ResponseBody ErrorResponse resolveHttpMessageNotReadableException(HttpServletRequest req, HttpMessageNotReadableException e) {
		logger.error(e.getMessage(), e);
		
		ErrorResponse errorResponse = new ErrorResponse();
		
		String message = e.getMessage();
		int index = message.indexOf(':');
		message = (index != -1) ? message.substring(0, index) : GENERIC_ERROR_DESC;
		

		errorResponse.setType(ErrorType.INVALID.name());
		errorResponse.setCode(HTTPMESSAGENOTREADABLEEXCEPTION_ERROR_CODE);
		errorResponse.setDetails(message);

		return errorResponse;
	}
	
	@ExceptionHandler(ConstraintViolationException.class)
	@ResponseStatus(value = HttpStatus.BAD_REQUEST)
	public @ResponseBody ErrorResponse resolveConstraintViolation(HttpServletRequest req, ConstraintViolationException e){
		logger.debug(e.getMessage(), e);
		
		ErrorResponse errorResponse = new ErrorResponse();

		errorResponse.setType(ErrorType.INVALID.name());
		errorResponse.setCode(CONSTRAINTVIOLATIONEXCEPTION_ERROR_CODE);
		
		
		Set<ConstraintViolation<?>> violations = e.getConstraintViolations();
		int i=1;
		StringBuilder messaggeBuilder = new StringBuilder();
		for (ConstraintViolation<?> violation : violations ) {
			String description = "Test: "+violation.getConstraintDescriptor().getAttributes().toString();
			logger.debug(description);
			if(i<violations.size()){
				messaggeBuilder.append(violation.getMessage() + ",");				
			}else{
				messaggeBuilder.append(violation.getMessage());
			}
			i++;
       }
		errorResponse.setDetails(messaggeBuilder.toString());
		
		return errorResponse;
	}
	
	@ExceptionHandler(MethodArgumentNotValidException.class)
	@ResponseStatus(value = HttpStatus.BAD_REQUEST)
	public @ResponseBody ErrorResponse resolveMethodArgumentNotValidException(HttpServletRequest req, MethodArgumentNotValidException e) {
		logger.error(e.getMessage(), e);
		
		ErrorResponse errorResponse = new ErrorResponse();

		errorResponse.setType(ErrorType.INVALID.name());
		errorResponse.setCode(METHODARGUMENTNOTVALIDEXCEPTION_ERROR_CODE);
		
		Map<String, List<String>> groupedErrors = new HashMap<>();
		List<String> fields = new ArrayList<>();
		List<FieldError> fieldErrors = e.getBindingResult().getFieldErrors();
		for (FieldError fieldError : fieldErrors) {
			String message = fieldError.getDefaultMessage();
			String field = fieldError.getField();
			
			List<String> fieldsByMessage = groupedErrors.get(message);
			if (fieldsByMessage == null) {
				fieldsByMessage = new ArrayList<>();
				groupedErrors.put(message, fieldsByMessage);
			}
			fieldsByMessage.add(field);
			fields.add(field);
		}
		
		if (!groupedErrors.isEmpty()) {
			errorResponse.setDetails(groupedErrors.toString());
		}
		errorResponse.setLocation(fields.toString());
			
		return errorResponse;
	}
	

	
	@ExceptionHandler(MicroserviceClientException.class)
	@ResponseStatus(value = HttpStatus.INTERNAL_SERVER_ERROR)
	@ResponseBody
	public ErrorResponse resolveBadRequestFeignException(HttpServletRequest req, MicroserviceClientException e) {
		logger.error(e.getMessage(), e);

		ErrorResponse errorResponse = new ErrorResponse();

		errorResponse.setType(ErrorType.ERROR.name());
		errorResponse.setCode(ErrorResolver.MICROSERVICE_CLIENT_ERROR_CODE);
		errorResponse.setDetails(GENERIC_ERROR_DESC);

		return errorResponse;
	}
   
   
	
	@ExceptionHandler(Exception.class)
	@ResponseStatus(value = HttpStatus.INTERNAL_SERVER_ERROR)
	public ErrorResponse resolveException(HttpServletRequest req, Exception e) {
		logger.error(e.getMessage(), e);

		ErrorResponse errorResponse = new ErrorResponse();

		errorResponse.setType(ErrorType.FATAL.name());
		errorResponse.setCode(EXCEPTION_ERROR_CODE);
		errorResponse.setDetails(GENERIC_ERROR_DESC);

		return errorResponse;
	}
	
}

