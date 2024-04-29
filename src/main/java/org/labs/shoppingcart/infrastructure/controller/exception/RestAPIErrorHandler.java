package org.labs.shoppingcart.infrastructure.controller.exception;

import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.client.HttpClientErrorException;

@ControllerAdvice
@Slf4j
public class RestAPIErrorHandler {
    private final MessageSource messageSource;

    @Autowired
    public RestAPIErrorHandler(MessageSource messageSource) {
        this.messageSource = messageSource;
    }

//    @ExceptionHandler(HttpMediaTypeNotSupportedException.class)
//    public ResponseEntity<Error> handleHttpMediaTypeNotSupportedException(HttpServletRequest request) {
//        var httpStatus = HttpStatus.UNSUPPORTED_MEDIA_TYPE;
//        var error = new Error(ErrorCode.HTTP_MEDIATYPE_NOT_SUPPORTED, request, httpStatus);
//        return new ResponseEntity<>(error, httpStatus);
//    }
//
//    @ExceptionHandler(HttpMessageNotWritableException.class)
//    public ResponseEntity<Error> handleHttpMessageNotWritableException(HttpServletRequest request) {
//        var httpStatus = HttpStatus.BAD_REQUEST;
//        var error = new Error(ErrorCode.HTTP_MESSAGE_NOT_WRITABLE, request, httpStatus);
//
//        return new ResponseEntity<>(error, httpStatus);
//    }
//
//    @ExceptionHandler(HttpMediaTypeNotAcceptableException.class)
//    public ResponseEntity<Error> handleHttpMediaTypeNotAcceptableException(HttpServletRequest request) {
//        var httpStatus = HttpStatus.NOT_ACCEPTABLE;
//        var error = new Error(ErrorCode.HTTP_MEDIA_TYPE_NOT_ACCEPTABLE, request, httpStatus);
//
//        return new ResponseEntity<>(error, httpStatus);
//    }
//
//    @ExceptionHandler(HttpMessageNotReadableException.class)
//    public ResponseEntity<Error> handleHttpMessageNotReadableException(HttpServletRequest request) {
//        var httpStatus = HttpStatus.BAD_REQUEST;
//        var error = new Error(ErrorCode.HTTP_MESSAGE_NOT_READABLE, request, httpStatus);
//
//        return new ResponseEntity<>(error, httpStatus);
//    }
//
//    @ExceptionHandler(JsonParseException.class)
//    public ResponseEntity<Error> handleJsonParseException(HttpServletRequest request) {
//        var httpStatus = HttpStatus.BAD_REQUEST;
//        var error = new Error(ErrorCode.JSON_PARSE_ERROR, request, httpStatus);
//
//        return new ResponseEntity<>(error, httpStatus);
//    }
//
//
//    @ExceptionHandler(MethodArgumentNotValidException.class)
//    public ResponseEntity<Error> handleMethodArgumentNotValidException(HttpServletRequest request) {
//        var httpStatus = HttpStatus.BAD_REQUEST;
//        var error = new Error(ErrorCode.APPLICATION_VALIDATION_ERROR, request, httpStatus);
//
//        return new ResponseEntity<>(error, httpStatus);
//    }

    @ExceptionHandler(HttpClientErrorException.class)
    public ResponseEntity<Error> handleHttpClientErrorException(
            HttpServletRequest request, HttpClientErrorException ex) {
        var httpStatus = ex.getStatusCode();
        var error = new Error(ErrorCode.APPLICATION_VALIDATION_ERROR, request, ex);

        return new ResponseEntity<>(error, httpStatus);
    }

    @ExceptionHandler({Exception.class, RuntimeException.class})
    public ResponseEntity<Error> handleException(HttpServletRequest request) {
        var httpStatus = HttpStatus.INTERNAL_SERVER_ERROR;
        var error = new Error(ErrorCode.GENERIC_ERROR, request, httpStatus);

        return new ResponseEntity<>(error, httpStatus);
    }

}
