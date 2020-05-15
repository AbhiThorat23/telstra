package com.telstra.codechallenge.github.exceptions;

import java.util.Arrays;
import java.util.Objects;
import java.util.stream.Collectors;

import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import lombok.extern.slf4j.Slf4j;

@ControllerAdvice
@Order(Ordered.LOWEST_PRECEDENCE)
@Slf4j
public class GenericExceptionHandler extends ResponseEntityExceptionHandler {

	@ExceptionHandler({ Exception.class })
	public ResponseEntity<Object> handleGenericException(final Exception ex) {
		// uncaught error happens, just log it here and return an user-friendly response
		log.error(String.format("Uncaught error happens. Stack trace is: %s", ex + getFullStackTraceLog(ex)));

		ApiException apiError = new ApiException("Please try after some time", HttpStatus.INTERNAL_SERVER_ERROR,
				Arrays.asList("System error!"));
		return new ResponseEntity<>(apiError, apiError.getStatus());
	}

	private String getFullStackTraceLog(Exception ex) {
		return Arrays.asList(ex.getStackTrace()).stream().map(Objects::toString).collect(Collectors.joining("\n"));
	}
}
