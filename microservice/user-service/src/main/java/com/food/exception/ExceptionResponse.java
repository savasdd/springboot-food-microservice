package com.food.exception;

import com.food.xss.XSSWrapper;
import org.apache.commons.lang3.StringUtils;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;

public class ExceptionResponse {
    private static MessageSource messageSource;

    public ExceptionResponse(String infoMessage) {
        this.infoMessage = XSSWrapper.stripXSS(getLangMessage(infoMessage));
    }

    public static void SetMessageSource(MessageSource ms) {
        messageSource = ms;
    }

    public ExceptionResponse() {
    }

    public String infoMessage;
    public String warningMessage;
    public String errorMessage;

    private String getLangMessage(String msg) {
        String message = StringUtils.substringBetween(msg, "{", "}");
        if (message == null) {
            return msg;
        }
        String anotherMessage = msg.replace("{" + message + "}", "");
        return messageSource.getMessage(message, null, LocaleContextHolder.getLocale()) + "\n" + ((!anotherMessage.isEmpty()) ? anotherMessage : "");
    }
}
