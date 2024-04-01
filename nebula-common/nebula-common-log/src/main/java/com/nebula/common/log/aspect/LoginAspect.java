package com.nebula.common.log.aspect;

import com.nebula.common.core.utils.IpUtils;
import com.nebula.common.core.utils.SecurityUtils;
import com.nebula.common.core.utils.ServletUtils;
import com.nebula.common.log.annotation.LoginLog;
import com.nebula.common.log.client.LogClient;
import com.nebula.common.log.event.LogininforEvent;
import jakarta.servlet.http.HttpServletRequest;
import java.time.LocalDateTime;
import java.util.Objects;
import nl.basjes.parse.useragent.UserAgent;
import nl.basjes.parse.useragent.UserAgentAnalyzer;
import org.apache.commons.lang3.StringUtils;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.expression.ExpressionParser;
import org.springframework.expression.spel.standard.SpelExpressionParser;
import org.springframework.expression.spel.support.StandardEvaluationContext;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class LoginAspect {

    private final LogClient logClient;

    private final UserAgentAnalyzer userAgentAnalyzer;

    public LoginAspect(LogClient logClient, UserAgentAnalyzer userAgentAnalyzer) {
        this.logClient = logClient;
        this.userAgentAnalyzer = userAgentAnalyzer;
    }

    @AfterReturning("@annotation(loginLog)")
    public void logLoginSuccess(JoinPoint joinPoint, LoginLog loginLog) {
        // 记录用户登录日志
        handleLoginLog(joinPoint, loginLog, null);
    }

    @AfterThrowing(pointcut = "@annotation(loginLog)", throwing = "ex")
    public void logLoginFailure(JoinPoint joinPoint, LoginLog loginLog, Exception ex) {
        // 记录用户登录日志
        handleLoginLog(joinPoint, loginLog, ex);
    }

    private void handleLoginLog(JoinPoint joinPoint, LoginLog loginLog, Exception ex) {
        HttpServletRequest request = Objects.requireNonNull(ServletUtils.getRequest());
        String userAgentString = request.getHeader("User-Agent");
        UserAgent userAgent = userAgentAnalyzer.parse(userAgentString);
        LogininforEvent event = new LogininforEvent(this);
        String username = "";
        if (StringUtils.isNotBlank(loginLog.username())) {
            MethodSignature signature = (MethodSignature) joinPoint.getSignature();
            String[] parameterNames = signature.getParameterNames();
            Object[] parameterValues = joinPoint.getArgs();
            // 设置SpEL的上下文
            StandardEvaluationContext context = new StandardEvaluationContext();
            for (int i = 0; i < parameterNames.length; i++) {
                context.setVariable(parameterNames[i], parameterValues[i]);
            }
            // 解析SpEL表达式
            ExpressionParser parser = new SpelExpressionParser();
            username = parser.parseExpression(loginLog.username()).getValue(context, String.class);
        }
        if (StringUtils.isBlank(username)) {
            username = SecurityUtils.getUsername();
        }
        event.setUserName(username);
        event.setIpaddr(IpUtils.getIpAddr(request));
        event.setBrowser(userAgent.getValue("AgentNameVersion"));
        event.setOs(userAgent.getValue("OperatingSystemNameVersion"));
        event.setStatus(ex == null ? "0" : "1");
        event.setMsg(ex == null ? String.format("%s成功", loginLog.loginType())
            : String.format("%s失败:%s", loginLog.loginType(), ex));
        event.setLoginTime(LocalDateTime.now());
        logClient.loginLog(event);
    }
}
