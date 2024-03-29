package com.mycompany.callcenter.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * The type Listener not found exception.
 *
 */
@ResponseStatus(value = HttpStatus.NOT_FOUND)
public class ListenerNotFoundException extends Exception {

	private static final long serialVersionUID = 1L;

/**
   * Instantiates a new Listener not found exception.
   *
   * @param message the message
   */
  public ListenerNotFoundException(String message) {
    super(message);
  }
}
