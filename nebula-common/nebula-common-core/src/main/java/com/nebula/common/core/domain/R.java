package com.nebula.common.core.domain;

import com.nebula.common.constants.Constants;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

/**
 * 通用的返回结果类
 *
 * @param <T>
 */
@Data
@Accessors(chain = true)
@NoArgsConstructor
@Schema(description = "返回结果")
public class R<T> {

    @Schema(description = "成功失败标志")
    private Boolean success;

    @Schema(description = "状态值")
    private Integer code;

    @Schema(description = "提示信息")
    private String msg;

    @Schema(description = "数据")
    private T data;

    /**
     * 成功
     */
    public static <T> R<T> success() {
        return new R<T>().setSuccess(true).setCode(Constants.SUCCESS).setMsg("成功");
    }

    /**
     * 失败
     */
    public static <T> R<T> fail() {
        return new R<T>().setSuccess(false).setCode(Constants.FAIL).setMsg("失败");
    }
}
