package org.labs.shoppingcart.infrastructure.controller.exception;

import jakarta.servlet.http.HttpServletRequest;
import lombok.Getter;
import org.springframework.http.HttpStatus;
import org.springframework.web.client.HttpClientErrorException;

@Getter
public class Error {

    /**
     * Application error code, which is different from HTTP error code.
     */
    private String errorCode;

    /**
     * Short, human-readable summary of the problem.
     */
    private String message;

    /**
     * HTTP status code for this occurrence of the problem, set by the origin server.
     */
    private Integer status;

    /**
     * Url of request that produced the error.
     */
    private String url = "Not available";

    /**
     * Method of request that produced the error.
     */
    private String reqMethod = "Not available";


    public Error(ErrorCode errorCode, HttpServletRequest request, HttpStatus status) {
        this.errorCode = errorCode.getErrCode();
        this.message = errorCode.getErrMsgKey();
        this.status = status.value();
        this.url = request.getRequestURL().toString();
        this.reqMethod = request.getMethod();
    }

    public Error(ErrorCode errorCode, HttpServletRequest request, HttpClientErrorException clientErrorException) {
        this.errorCode = errorCode.getErrCode();
        this.message = clientErrorException.getMessage();
        this.status = clientErrorException.getStatusCode().value();
        this.url = request.getRequestURL().toString();
        this.reqMethod = request.getMethod();
    }
}
