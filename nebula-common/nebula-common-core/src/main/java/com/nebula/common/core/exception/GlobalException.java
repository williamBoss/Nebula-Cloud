package com.nebula.common.core.exception;

import java.io.Serial;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 全局异常
 *
 * @author KING
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class GlobalException extends RuntimeException {

    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * 错误提示
     */
    private String message;

    /**
     * 空构造方法，避免反序列化问题
     */
    public GlobalException() {
    }

    public GlobalException(String message) {
        this.message = message;
    }
}