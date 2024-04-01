package com.nebula.common.core.handler;

import com.nebula.common.core.domain.R;
import com.nebula.common.core.exception.GlobalException;
import com.nebula.common.core.exception.ServiceException;
import com.nebula.common.core.exception.UserException;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * 接口异常统一处理
 *
 * @author KING
 */
@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(value = Exception.class)
    public R<String> exceptionHandler(HttpServletRequest request, Exception e) {
        log.error("{} 系统异常:{}", request.getRequestURI(), e.getMessage(), e);
        // 系统级异常，错误码固定为1000，提示语固定为 系统异常，请联系管理员
        return R.<String>fail().setMsg("系统异常，请联系管理员");
    }

    @ExceptionHandler(value = GlobalException.class)
    public R<String> globalExceptionHandler(HttpServletRequest request, Exception e) {
        // 自定义异常，返回提示的msg
        log.error("{} 自定义异常:{}", request.getContextPath(), e.getMessage(), e);
        // 自定义异常，自定义的msg不为空则展示自定义msg，否则提示失败
        String message = StringUtils.hasText(e.getMessage()) ? e.getMessage() : "系统异常，请联系管理员";
        return R.<String>fail().setMsg(message);
    }

    @ExceptionHandler(value = UserException.class)
    public R<String> userExceptionHandler(HttpServletRequest request, Exception e) {
        // 用户异常，返回提示的msg
        log.error("{} 用户异常:{}", request.getContextPath(), e.getMessage(), e);
        String message = StringUtils.hasText(e.getMessage()) ? e.getMessage() : "系统异常，请联系管理员";
        return R.<String>fail().setMsg(message);
    }

    @ExceptionHandler(value = ServiceException.class)
    public R<String> serviceExceptionHandler(HttpServletRequest request, Exception e) {
        log.error("{} 服务异常:{}", request.getContextPath(), e.getMessage(), e);
        String message = StringUtils.hasText(e.getMessage()) ? e.getMessage() : "系统异常，请联系管理员";
        return R.<String>fail().setMsg(message);
    }
}
