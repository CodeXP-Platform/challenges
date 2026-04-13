package com.codexp.challengessolutions.shared.domain.exceptions;

public class UnauthorizedActionException extends RuntimeException {
  public UnauthorizedActionException(String message) {
    super(message);
  }
}
