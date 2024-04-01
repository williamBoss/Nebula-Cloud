package com.nebula.common.log.aspect;

import com.alibaba.ttl.TransmittableThreadLocal;
import com.nebula.common.core.utils.MapUtil;
import com.nebula.common.core.utils.SecurityUtils;
import com.nebula.common.core.utils.ServletUtils;
import com.nebula.common.json.utils.JacksonUtil;
import com.nebula.common.log.annotation.OperLog;
import com.nebula.common.log.client.LogClient;
import com.nebula.common.log.enums.BusinessStatus;
import com.nebula.common.log.event.OperLogEvent;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashSet;
import java.util.Map;
import java.util.StringJoiner;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.http.HttpMethod;
import org.springframework.util.StopWatch;
import org.springframework.validation.BindingResult;
import org.springframework.web.multipart.MultipartFile;

/**
 * 操作日志记录处理
 *
 * @author KING
 */
@Slf4j
@Aspect
@AutoConfiguration
public class LogAspect {

    private final LogClient logClient;

    /**
     * 排除敏感属性字段
     */
    public static final String[] EXCLUDE_PROPERTIES = {"password", "oldPassword", "newPassword", "confirmPassword"};

    /**
     * 计算操作消耗时间
     */
    private static final ThreadLocal<StopWatch> TIME_THREADLOCAL = new TransmittableThreadLocal<>();

    public LogAspect(LogClient logClient) {
        this.logClient = logClient;
    }

    /**
     * 处理请求前执行
     */
    @Before(value = "@annotation(controllerOperLog)")
    public void boBefore(JoinPoint joinPoint, OperLog controllerOperLog) {
        StopWatch stopWatch = new StopWatch();
        TIME_THREADLOCAL.set(stopWatch);
        stopWatch.start();
    }

    /**
     * 处理完请求后执行
     *
     * @param joinPoint 切点
     */
    @AfterReturning(pointcut = "@annotation(controllerOperLog)", returning = "jsonResult")
    public void doAfterReturning(JoinPoint joinPoint, OperLog controllerOperLog, Object jsonResult) {
        handleLog(joinPoint, controllerOperLog, null, jsonResult);
    }

    /**
     * 拦截异常操作
     *
     * @param joinPoint 切点
     * @param e         异常
     */
    @AfterThrowing(value = "@annotation(controllerOperLog)", throwing = "e")
    public void doAfterThrowing(JoinPoint joinPoint, OperLog controllerOperLog, Exception e) {
        handleLog(joinPoint, controllerOperLog, e, null);
    }

    protected void handleLog(final JoinPoint joinPoint, OperLog controllerOperLog, final Exception e,
        Object jsonResult) {
        try {
            OperLogEvent operLog = new OperLogEvent(this);
            // 设置标题
            operLog.setTitle(controllerOperLog.title());
            // 设置action动作
            operLog.setBusinessType(controllerOperLog.businessType().ordinal());
            // 设置方法名称
            String className = joinPoint.getTarget().getClass().getName();
            String methodName = joinPoint.getSignature().getName();
            operLog.setMethod(className + "." + methodName + "()");
            // 设置请求方式
            operLog.setRequestMethod(ServletUtils.getRequest().getMethod());
            // 操作人信息
            operLog.setOperUserId(SecurityUtils.getUserId());
            operLog.setOperName(SecurityUtils.getUsername());
            // 请求的地址
            operLog.setOperUrl(StringUtils.substring(ServletUtils.getRequest().getRequestURI(), 0, 255));
            // 请求的ip
            operLog.setOperIp(ServletUtils.getRequest().getRemoteAddr());
            operLog.setStatus(BusinessStatus.SUCCESS.ordinal());
            if (e != null) {
                operLog.setStatus(BusinessStatus.FAIL.ordinal());
                operLog.setErrorMsg(StringUtils.substring(e.getMessage(), 0, 2000));
            }
            // 处理设置注解上的参数
            // 是否需要保存request，参数和值
            if (controllerOperLog.isSaveRequestData()) {
                // 获取参数的信息，传入到数据库中。
                setRequestValue(joinPoint, operLog, controllerOperLog.excludeParamNames());
            }
            // 是否需要保存response，参数和值
            if (controllerOperLog.isSaveResponseData() && ObjectUtils.isNotEmpty(jsonResult)) {
                operLog.setJsonResult(StringUtils.substring(JacksonUtil.toJsonString(jsonResult), 0, 2000));
            }
            // 设置消耗时间
            StopWatch stopWatch = TIME_THREADLOCAL.get();
            stopWatch.stop();
            operLog.setCostTime(stopWatch.getTotalTimeMillis());
            operLog.setOperTime(LocalDateTime.now());
            logClient.operationLog(operLog);
        } catch (Exception exp) {
            // 记录本地异常日志
            log.error("异常信息:{}", exp.getMessage(), exp);
        } finally {
            TIME_THREADLOCAL.remove();
        }
    }

    /**
     * 获取请求的参数，放到log中
     *
     * @param operLog 操作日志
     */
    private void setRequestValue(JoinPoint joinPoint, OperLogEvent operLog, String[] excludeParamNames) {
        // 获取被调用方法的参数
        String requestMethod = operLog.getRequestMethod();
        if (HttpMethod.PUT.name().equals(requestMethod) || HttpMethod.POST.name().equals(requestMethod)) {
            String params = argsArrayToString(joinPoint.getArgs(), excludeParamNames);
            operLog.setOperParam(params);
        } else {
            Map<String, String[]> paramMap = ServletUtils.getRequest().getParameterMap();
            MapUtil.removeAny(paramMap, new HashSet<>(Arrays.asList(EXCLUDE_PROPERTIES)));
            MapUtil.removeAny(paramMap, new HashSet<>(Arrays.asList(excludeParamNames)));
            operLog.setOperParam(JacksonUtil.toJsonString(paramMap));
        }
    }

    /**
     * 参数拼装
     */
    private String argsArrayToString(Object[] paramsArray, String[] excludeParamNames) {
        StringJoiner params = new StringJoiner(" ");
        if (ArrayUtils.isEmpty(paramsArray)) {
            return params.toString();
        }
        for (Object o : paramsArray) {
            if (ObjectUtils.isNotEmpty(o) && !isFilterObject(o)) {
                String str = JacksonUtil.toJsonString(o);
                if (ArrayUtils.isNotEmpty(excludeParamNames)) {
                    Map<String, Object> paramMap = JacksonUtil.parseObject(str);
                    MapUtil.removeAny(paramMap, new HashSet<>(Arrays.asList(EXCLUDE_PROPERTIES)));
                    MapUtil.removeAny(paramMap, new HashSet<>(Arrays.asList(excludeParamNames)));
                    str = JacksonUtil.toJsonString(paramMap);
                }
                params.add(str);
            }
        }
        return params.toString();
    }

    /**
     * 判断是否需要过滤的对象。
     *
     * @param o 对象信息。
     * @return 如果是需要过滤的对象，则返回true；否则返回false。
     */
    @SuppressWarnings("rawtypes")
    public boolean isFilterObject(final Object o) {
        Class<?> clazz = o.getClass();
        if (clazz.isArray()) {
            return clazz.getComponentType().isAssignableFrom(MultipartFile.class);
        } else if (Collection.class.isAssignableFrom(clazz)) {
            Collection collection = (Collection) o;
            for (Object value : collection) {
                return value instanceof MultipartFile;
            }
        } else if (Map.class.isAssignableFrom(clazz)) {
            Map map = (Map) o;
            for (Object value : map.values()) {
                return value instanceof MultipartFile;
            }
        }
        return o instanceof MultipartFile || o instanceof HttpServletRequest || o instanceof HttpServletResponse
            || o instanceof BindingResult;
    }
}
