package com.nebula.system.listener;

import com.nebula.common.log.event.OperLogEvent;
import com.nebula.system.domain.SysOperLog;
import com.nebula.system.service.ISysOperLogService;
import io.github.linpeilie.Converter;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

@Component
public class OperLogListener {

    private final ISysOperLogService sysOperLogService;

    private final Converter converter;

    public OperLogListener(ISysOperLogService sysOperLogService, Converter converter) {
        this.sysOperLogService = sysOperLogService;
        this.converter = converter;
    }

    @EventListener
    public void handleOperationLogEvent(OperLogEvent event) {
        SysOperLog sysOperLog = converter.convert(event, SysOperLog.class);
        sysOperLogService.save(sysOperLog);
    }
}
