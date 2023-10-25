package com.food.exception;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.server.ErrorPage;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Slf4j
@RestControllerAdvice
public class ExceptionHandler {

    @Autowired
    MessageSource messageSource;

    @org.springframework.web.bind.annotation.ExceptionHandler(GeneralException.class)
    public ResponseEntity<ExceptionResponse> generalException(Exception ex) {
        log.error("GeneralException", ex);
        ExceptionResponse error = new ExceptionResponse();
        error.errorMessage = getLangMessage(ex.getMessage(), null);
        return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
    }

    @org.springframework.web.bind.annotation.ExceptionHandler(GeneralWarning.class)
    public ResponseEntity<ExceptionResponse> generalWarning(Exception ex) {
        log.error("GeneralWarning", ex);
        ExceptionResponse warning = new ExceptionResponse();
        warning.warningMessage = getLangMessage(ex.getMessage(), null);
        return new ResponseEntity<>(warning, HttpStatus.BAD_REQUEST);
    }

    @org.springframework.web.bind.annotation.ExceptionHandler(value = {RuntimeException.class})
    public ResponseEntity<ExceptionResponse> runtimeException(Exception ex) {
        log.error("RuntimeException", ex);
        ExceptionResponse error = new ExceptionResponse();
        error.errorMessage = getLangMessage(ex.getMessage(), null);
        return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
    }

    @org.springframework.web.bind.annotation.ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<ExceptionResponse> constraintViolationException(ConstraintViolationException ex) {
        log.error("ConstraintViolationException", ex);
        ExceptionResponse error = new ExceptionResponse();
        error.errorMessage = StringUtils.join(getViolationsMsg(ex), ", ");
        return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
    }

    @org.springframework.web.bind.annotation.ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ExceptionResponse> methodArgumentNotValidException(MethodArgumentNotValidException ex) {
        log.error("MethodArgumentNotValidException", ex);
        ExceptionResponse error = new ExceptionResponse();
        final List<String> allError = ex.getBindingResult().getAllErrors().stream().map(m -> getLangMessage(m.getDefaultMessage(),
                m.getArguments() != null ? Arrays.stream(m.getArguments()).filter(f -> !(f instanceof DefaultMessageSourceResolvable)).toArray() : null)).toList();
        error.errorMessage = StringUtils.join(allError, ", ");
        return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
    }

    @org.springframework.web.bind.annotation.ExceptionHandler(value = {MethodArgumentTypeMismatchException.class})
    public void MethodArgumentTypeMismatchException(Exception ex) {
        log.error("MethodArgumentTypeMismatchException", ex);
        new ErrorPage(HttpStatus.NOT_FOUND, "/error");
    }

    @org.springframework.web.bind.annotation.ExceptionHandler(value = {Exception.class})
    public ResponseEntity<ExceptionResponse> unknownException(Exception ex) {
        log.error("Exception", ex);
        ExceptionResponse error = new ExceptionResponse();
        error.errorMessage = getLangMessage("{unknown.exception}", null);
        return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
    }

    private List<String> getViolationsMsg(ConstraintViolationException ex) {
        List<String> errorMessages = new ArrayList<>();
        for (ConstraintViolation<?> violation : ex.getConstraintViolations()) {
            Map<String, Object> attributes = violation.getConstraintDescriptor().getAttributes();

            List<String> keysForFiltering = Arrays.asList("groups", "inclusive", "message", "payload");
            List<Object> objects = attributes.entrySet().stream().filter(e -> !keysForFiltering.contains(e.getKey())).map(Map.Entry::getValue).collect(Collectors.toList());
            errorMessages.add(getLangMessage(violation.getMessage(), objects.toArray()));
        }
        return errorMessages;
    }

    private String getLangMessage(String msg, Object[] arguments) {
        String message = StringUtils.substringBetween(msg, "{", "}");
        if (message == null) {
            return msg;
        }
        String anotherMessage = msg.replace("{" + message + "}", "");
        return messageSource.getMessage(message, arguments, LocaleContextHolder.getLocale()) + "\n" + ((!anotherMessage.isEmpty()) ? anotherMessage : "");
    }
}
