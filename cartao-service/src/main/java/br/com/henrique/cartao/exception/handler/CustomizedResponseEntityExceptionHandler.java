package br.com.henrique.cartao.exception.handler;
import br.com.henrique.cartao.exception.*;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@RestControllerAdvice
public class CustomizedResponseEntityExceptionHandler {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public final ExceptionResponseValidator handleMethodArgumentNotValidException(MethodArgumentNotValidException ex) {
        List<String> errors = ex.getBindingResult().getAllErrors()
                .stream()
                .map(erro -> erro.getDefaultMessage())
                .collect(Collectors.toList());
        return new ExceptionResponseValidator(HttpStatus.BAD_REQUEST, ex.getStatusCode().value(), errors);
    }

    @ExceptionHandler(TokenException.class)
    @ResponseStatus(HttpStatus.FORBIDDEN)
    public final ExceptionResponse handlerTokenException(Exception ex, WebRequest request) {
        ExceptionResponse exceptionResponse =
                new ExceptionResponse(
                        new Date(),
                        ex.getMessage(),
                        HttpStatus.FORBIDDEN.toString()
                        );
        return exceptionResponse;

    }

    @ExceptionHandler(KeycloakException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public final ExceptionResponse handlerTokenException(KeycloakException ex) {
        ExceptionResponse exceptionResponse =
                new ExceptionResponse(
                        new Date(),
                        ex.getMessage(),
                        HttpStatus.BAD_REQUEST.toString()
                );
        return exceptionResponse;

    }

    @ExceptionHandler(UserNotFoundException.class)
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    public final ExceptionResponse handlerTokenException(UserNotFoundException ex) {
        ExceptionResponse exceptionResponse =
                new ExceptionResponse(
                        new Date(),
                        ex.getMessage(),
                        HttpStatus.UNAUTHORIZED.toString()
                );
        return exceptionResponse;

    }

}
