package com.lambdaschool.schools.handlers;


import com.lambdaschool.schools.models.ValidationError;
import org.hibernate.exception.ConstraintViolationException;
import org.springframework.stereotype.Component;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;

import java.util.ArrayList;
import java.util.List;


@Component
public class HelperFunctions {

	public List<ValidationError> getConstraintViolations(Throwable cause) {
		while ((cause != null) &&
		       !(cause instanceof ConstraintViolationException || cause instanceof MethodArgumentNotValidException)) {
			//			System.out.println(cause.getClass().toString());
			cause = cause.getCause();
		}
		List<ValidationError> validationErrorsList = new ArrayList<>();
		if (cause != null) {
			if (cause instanceof ConstraintViolationException) {
				ConstraintViolationException ex = (ConstraintViolationException) cause;
				ValidationError newValErr = new ValidationError();
				newValErr.setCode(ex.getMessage());
				newValErr.setMessage(ex.getConstraintName());
				validationErrorsList.add(newValErr);
			} else {
				MethodArgumentNotValidException ex = (MethodArgumentNotValidException) cause;
				List<FieldError> fieldErrors = ex.getBindingResult()
				                                 .getFieldErrors();
				for (FieldError fieldError : fieldErrors) {
					ValidationError newValErr = new ValidationError();
					newValErr.setCode(fieldError.getField());
					newValErr.setMessage(fieldError.getDefaultMessage());
					validationErrorsList.add(newValErr);
				}
			}
		}
		return validationErrorsList;
	}

}
