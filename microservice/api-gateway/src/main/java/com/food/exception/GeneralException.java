package com.food.exception;

import com.food.xss.XSSWrapper;
import lombok.extern.slf4j.Slf4j;

import java.io.Serial;

@Slf4j
public class GeneralException extends Exception {

    @Serial
    private static final long serialVersionUID = -2803799245666892878L;

    public GeneralException(String errorMessage) {
        super(XSSWrapper.stripXSS(errorMessage));
        log.error(errorMessage);
    }

    public GeneralException() {
        super();
    }
}

