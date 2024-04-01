package com.nebula.common.core.exception;

import java.io.Serial;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class UserException extends RuntimeException {

    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * 错误提示
     */
    private String message;

    public UserException() {
        super();
    }

    public UserException(String message) {
        this.message = message;
    }
}
