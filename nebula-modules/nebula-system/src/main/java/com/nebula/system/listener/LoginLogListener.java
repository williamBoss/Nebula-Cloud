package com.nebula.system.listener;

import com.nebula.common.log.event.LogininforEvent;
import com.nebula.system.domain.SysLogininfor;
import com.nebula.system.service.ISysLogininforService;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

@Component
public class LoginLogListener {

    private final ISysLogininforService sysLogininforService;

    public LoginLogListener(ISysLogininforService sysLogininforService) {
        this.sysLogininforService = sysLogininforService;
    }

    @EventListener
    public void handleLoginLogEvent(LogininforEvent event) {
        SysLogininfor logininfor = new SysLogininfor();
        logininfor.setUserName(event.getUserName());
        logininfor.setClientKey(event.getClientKey());
        logininfor.setDeviceType(event.getDeviceType());
        logininfor.setIpaddr(event.getIpaddr());
        logininfor.setLoginLocation(event.getLoginLocation());
        logininfor.setBrowser(event.getBrowser());
        logininfor.setOs(event.getOs());
        logininfor.setStatus(event.getStatus());
        logininfor.setMsg(event.getMsg());
        logininfor.setLoginTime(event.getLoginTime());
        sysLogininforService.save(logininfor);
    }
}
