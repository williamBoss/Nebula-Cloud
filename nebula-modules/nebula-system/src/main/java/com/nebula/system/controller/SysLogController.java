package com.nebula.system.controller;

import com.nebula.common.log.event.LogininforEvent;
import com.nebula.common.log.event.OperLogEvent;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/log")
public class SysLogController {

    private final ApplicationEventPublisher eventPublisher;

    public SysLogController(ApplicationEventPublisher eventPublisher) {
        this.eventPublisher = eventPublisher;
    }

    @PostMapping("/login")
    void loginLog(@RequestBody LogininforEvent event) {
        eventPublisher.publishEvent(event);
    }

    @PostMapping("/operation")
    void operationLog(@RequestBody OperLogEvent event) {
        eventPublisher.publishEvent(event);
    }

}
