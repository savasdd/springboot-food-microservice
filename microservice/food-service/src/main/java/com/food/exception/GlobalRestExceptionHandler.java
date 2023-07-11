package com.food.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.server.MethodNotAllowedException;
import org.springframework.web.servlet.NoHandlerFoundException;

import javax.el.MethodNotFoundException;
import javax.servlet.http.HttpServletRequest;
import java.util.Locale;
import java.util.Objects;


@RestControllerAdvice
public class GlobalRestExceptionHandler {

    private static String RETRY_AFTER_MINUTES = "5";


    @ExceptionHandler(value = {NoHandlerFoundException.class, MethodNotFoundException.class})
    public ResponseEntity<RestErrorDto> processNoHandlerFoundException(final Exception exception, final HttpServletRequest request) {
        return responseEntity(RestErrorDto.builder()
                .status(HttpStatus.NOT_FOUND.value())
                .message((HttpStatus.NOT_FOUND.getReasonPhrase()))
                .build());
    }


    @ExceptionHandler(value = {Exception.class})
    public ResponseEntity<RestErrorDto> processException(final Exception exception, final HttpServletRequest request) {
        final String locale = CommonUtil.getHeaderValue("locale");

        return responseEntity(RestErrorDto.builder()
                .status(HttpStatus.INTERNAL_SERVER_ERROR.value())
                .message(MessageUtilService.getMessage("customException.internalServerError.message", Objects.nonNull(locale) ? new Locale(locale) : null, RETRY_AFTER_MINUTES))
                .build());
    }


    @ExceptionHandler(value = {MethodArgumentNotValidException.class})
    public ResponseEntity<RestErrorDto> processBadRequestException(final Exception exception, final HttpServletRequest request) {
        return responseEntity(RestErrorDto.builder()
                .status(HttpStatus.BAD_REQUEST.value())
                .message(HttpStatus.BAD_REQUEST.getReasonPhrase())
                .build());
    }


    @ExceptionHandler(value = {MethodNotAllowedException.class, HttpClientErrorException.MethodNotAllowed.class})
    public ResponseEntity<RestErrorDto> processMethodNotAllowedException(final Exception exception, final HttpServletRequest request) {
        final String locale = CommonUtil.getHeaderValue("locale");

        return responseEntity(RestErrorDto.builder()
                .status(HttpStatus.METHOD_NOT_ALLOWED.value())
                .message(MessageUtilService.getMessage("customException.methodNotAllowedException.message", Objects.nonNull(locale) ? new Locale(locale) : null))
                .build());
    }


    private ResponseEntity<RestErrorDto> responseEntity(RestErrorDto error) {
        return new ResponseEntity(error, HttpStatus.valueOf(error.getStatus()));
    }
}