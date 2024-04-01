package com.nebula.common.log.client;

import com.nebula.common.log.event.LogininforEvent;
import com.nebula.common.log.event.OperLogEvent;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.service.annotation.HttpExchange;
import org.springframework.web.service.annotation.PostExchange;

@HttpExchange("/log")
public interface LogClient {

    @PostExchange("/login")
    void loginLog(@RequestBody LogininforEvent event);

    @PostExchange("/operation")
    void operationLog(@RequestBody OperLogEvent event);

}
