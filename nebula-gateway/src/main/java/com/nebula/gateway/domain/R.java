package com.nebula.gateway.domain;

import java.io.Serializable;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;
import org.springframework.http.HttpStatusCode;

/**
 * 通用的返回结果类
 */
@Data
@Accessors(chain = true)
@NoArgsConstructor
public class R implements Serializable {

    private Boolean success;

    private Integer code;

    private String msg;

    /**
     * 失败
     */
    public static R fail(HttpStatusCode httpStatusCode, String msg) {
        return new R().setSuccess(false).setCode(httpStatusCode.value()).setMsg(msg);
    }
}
