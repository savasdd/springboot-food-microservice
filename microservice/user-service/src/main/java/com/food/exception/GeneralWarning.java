package com.food.exception;

import com.food.xss.XSSWrapper;
import lombok.extern.slf4j.Slf4j;

import java.io.Serial;

@Slf4j
public class GeneralWarning extends Exception {
    @Serial
    private static final long serialVersionUID = -8668297014578074295L;

    public GeneralWarning(String warningMessage) {
        super(XSSWrapper.stripXSS(warningMessage));
        log.error(warningMessage);
    }

    public GeneralWarning() {
        super();
    }
}
