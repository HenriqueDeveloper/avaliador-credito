package br.com.henrique.cliente.exception;

import lombok.Data;

import java.util.Date;

@Data
public class ExceptionResponse {

  private Date timestamp;
  private String message;
  private String statusCode;

  public ExceptionResponse(Date timestamp, String message, String statusCode) {
    super();
    this.timestamp = timestamp;
    this.message = message;
    this.statusCode = statusCode;
  }

}
