package com.telstra.codechallenge.github.exceptions;

import java.util.Arrays;
import java.util.List;

import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.ResourceAccessException;
import org.springframework.web.client.RestClientException;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import lombok.extern.slf4j.Slf4j;

@ControllerAdvice
@Order(Ordered.HIGHEST_PRECEDENCE)
@Slf4j
public class RestResponseExceptionHandler extends ResponseEntityExceptionHandler {

	@ExceptionHandler({ MethodArgumentTypeMismatchException.class })
	private ResponseEntity<Object> handleNumberFormatException(MethodArgumentTypeMismatchException ex) {
		log.error(ex.getLocalizedMessage());
		String name = ex.getName();
		String type = ex.getRequiredType().getSimpleName();
		String message = String.format("'%s' should be a valid '%s'", name, type);

		List<String> errorList = Arrays.asList(message);
		ApiException apiError = new ApiException("Validation failure", HttpStatus.BAD_REQUEST, errorList);

		return new ResponseEntity<>(apiError, apiError.getStatus());
	}

	@ExceptionHandler({ HttpClientErrorException.class, ResourceAccessException.class })
	private ResponseEntity<Object> handleApiCallException(RestClientException ex) {
		log.error(ex.getLocalizedMessage());
		ApiException apiError = new ApiException("Please try after some time", HttpStatus.INTERNAL_SERVER_ERROR,
				Arrays.asList("System error!"));
		return new ResponseEntity<>(apiError, apiError.getStatus());
	}

	@Override
	protected ResponseEntity<Object> handleMissingServletRequestParameter(MissingServletRequestParameterException ex,
			HttpHeaders headers, HttpStatus status, WebRequest request) {
		log.error(ex.getLocalizedMessage());
		String error = ex.getParameterName() + " parameter is missing";

		ApiException apiError = new ApiException(ex.getLocalizedMessage(), HttpStatus.BAD_REQUEST,
				Arrays.asList(error));
		return new ResponseEntity<Object>(apiError, new HttpHeaders(), apiError.getStatus());
	}

}
