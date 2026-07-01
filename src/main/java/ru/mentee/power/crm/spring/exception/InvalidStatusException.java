package ru.mentee.power.crm.spring.exception;

public class InvalidStatusException extends RuntimeException {
  public InvalidStatusException(String message) {
    super(message);
  }
}
