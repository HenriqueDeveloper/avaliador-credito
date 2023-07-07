package br.com.henrique.avaliadorcredito.exception;

import lombok.Data;
import org.springframework.http.HttpStatus;

import java.util.List;

@Data
public class ExceptionResponseValidator {
    private HttpStatus status;
    private Integer statusCode;
    private List<String> errors;
    public ExceptionResponseValidator(HttpStatus status, Integer statusCode, List<String> errors) {
        super();
        this.errors = errors;
        this.statusCode = statusCode;
        this.status = status;
    }

}