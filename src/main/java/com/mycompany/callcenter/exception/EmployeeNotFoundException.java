package com.mycompany.callcenter.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * The type Employee not found exception.
 *
 */
@ResponseStatus(value = HttpStatus.NOT_FOUND)
public class EmployeeNotFoundException extends Exception {

	private static final long serialVersionUID = 1L;
	
  /**
   * Instantiates a new Employee not found exception.
   *
   * @param message the message
   */
  public EmployeeNotFoundException(String message) {
    super(message);
  }
}
