package com.lambdaschool.schools.handlers;


import com.lambdaschool.schools.exceptions.ResourceExistsException;
import com.lambdaschool.schools.exceptions.ResourceNotFoundException;
import com.lambdaschool.schools.models.ErrorDetail;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.Date;


@Order(Ordered.HIGHEST_PRECEDENCE)
@RestControllerAdvice
public class RestExceptionHandler
		extends ResponseEntityExceptionHandler {

	private final HelperFunctions helper;

	@Autowired
	public RestExceptionHandler(HelperFunctions helper) {
		super();
		this.helper = helper;
	}

	@Override
	protected ResponseEntity<Object> handleExceptionInternal(
			Exception ex,
			Object body,
			HttpHeaders headers,
			HttpStatus status,
			WebRequest request
	) {
		ErrorDetail errorDetail = new ErrorDetail();
		errorDetail.setTimestamp(new Date());
		errorDetail.setStatus(status.value());
		errorDetail.setTitle("REST Internal Exception");
		errorDetail.setDetail(ex.getMessage());
		errorDetail.setDeveloperMessage(ex.getClass()
		                                  .getName());
		errorDetail.setErrors(helper.getConstraintViolations(ex));
		return new ResponseEntity<>(
				errorDetail,
				null,
				status
		);
	}

	@ExceptionHandler(ResourceNotFoundException.class)
	public ResponseEntity<?> handleResourceNotFoundException(ResourceNotFoundException ex) {
		ErrorDetail errorDetail = new ErrorDetail();
		errorDetail.setTimestamp(new Date());
		errorDetail.setStatus(HttpStatus.NOT_FOUND.value());
		errorDetail.setTitle("Resource Not Found");
		errorDetail.setDetail(ex.getMessage());
		errorDetail.setDeveloperMessage(ex.getClass()
		                                  .getName());
		errorDetail.setErrors(helper.getConstraintViolations(ex));
		return new ResponseEntity<>(
				errorDetail,
				null,
				HttpStatus.NOT_FOUND
		);
	}

	@ExceptionHandler(ResourceExistsException.class)
	public ResponseEntity<?> handleResourceExistsException(ResourceExistsException ex) {
		ErrorDetail errorDetail = new ErrorDetail();
		errorDetail.setTimestamp(new Date());
		errorDetail.setStatus(HttpStatus.CONFLICT.value());
		errorDetail.setTitle("Resource Already Exists");
		errorDetail.setDetail(ex.getMessage());
		errorDetail.setDeveloperMessage(ex.getClass()
		                                  .getName());
		errorDetail.setErrors(helper.getConstraintViolations(ex));
		return new ResponseEntity<>(
				errorDetail,
				null,
				HttpStatus.CONFLICT
		);


	}

}
