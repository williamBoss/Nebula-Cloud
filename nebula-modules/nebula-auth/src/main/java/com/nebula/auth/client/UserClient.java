package com.nebula.auth.client;

import com.nebula.auth.domain.LoginUser;
import com.nebula.common.core.domain.R;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.service.annotation.GetExchange;
import org.springframework.web.service.annotation.HttpExchange;
import reactor.core.publisher.Mono;

@HttpExchange("/sysUser")
public interface UserClient {

    @GetExchange("/info/{username}")
    Mono<R<LoginUser>> getInfo(@PathVariable("username") String username);

}
