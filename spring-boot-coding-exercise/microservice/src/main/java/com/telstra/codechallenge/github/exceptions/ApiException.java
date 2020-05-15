package com.telstra.codechallenge.github.exceptions;

import java.util.List;

import org.springframework.http.HttpStatus;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ApiException {
	private String message;
	private HttpStatus status;
	private List<String> errors;
}
