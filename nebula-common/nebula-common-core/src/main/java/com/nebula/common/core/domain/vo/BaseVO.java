package com.nebula.common.core.domain.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import java.io.Serial;
import java.io.Serializable;
import java.util.Map;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class BaseVO implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    @Schema(description = "请求参数")
    private Map<String, Object> params;
}
