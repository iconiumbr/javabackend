package br.com.fiap.myassist.config;

import org.springframework.cglib.core.Local;
import org.springframework.http.HttpStatus;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.http.ResponseEntity;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {

    public record ErrorResponse(String Error, LocalDateTime dateTime) {};

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(RuntimeException.class)
    public ErrorResponse handleRuntimeException(RuntimeException exception) {
        return new ErrorResponse(exception.getMessage(), LocalDateTime.now());
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public Map<String, String> handleMethodArgumentNotValidException(MethodArgumentNotValidException exception) {
        Map<String, String> resultado = new HashMap<>();

        exception.getBindingResult().getAllErrors().forEach(e->{
            var nomecampo = ((FieldError) e).getField();
            var mensagem = e.getDefaultMessage();
            resultado.put(nomecampo,mensagem);
        });

        return resultado;
    }

}
