package pl.miq3l.bttconnect.exceptions;

import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.NoSuchElementException;


@Order(Ordered.HIGHEST_PRECEDENCE)
@ControllerAdvice
public class RestExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(NoSuchElementException.class)
    protected ResponseEntity<Object> handleException(NoSuchElementException ex) {
        ApiError apiError = new ApiError(HttpStatus.NOT_FOUND, "NoSuchElement", ex);
        return buildResponseEntity(apiError);
    }

    @ExceptionHandler(IllegalArgumentException.class)
    protected ResponseEntity<Object> handleException(IllegalArgumentException ex) {
        ApiError apiError = new ApiError(HttpStatus.BAD_REQUEST, "IllegalArgument", ex);
        return buildResponseEntity(apiError);
    }

    @ExceptionHandler(InverterNotFoundException.class)
    protected ResponseEntity<Object> handleException(InverterNotFoundException ex) {
        ApiError apiError = new ApiError(HttpStatus.NOT_FOUND, "Nie znaleziono invertera", ex);
        return buildResponseEntity(apiError);
    }

    private ResponseEntity<Object> buildResponseEntity(ApiError apiError) {
        return new ResponseEntity<>(apiError, apiError.getStatus());
    }

}
