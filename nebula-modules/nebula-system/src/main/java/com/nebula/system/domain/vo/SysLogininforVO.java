package com.nebula.system.domain.vo;

import com.nebula.common.core.domain.vo.BaseVO;
import io.swagger.v3.oas.annotations.media.Schema;
import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDateTime;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

/**
 * 系统访问记录 实体类。
 *
 * @author William
 * @since 1.0
 */
@EqualsAndHashCode(callSuper = true)
@Data
@NoArgsConstructor
@Schema(description = "系统访问记录")
public class SysLogininforVO extends BaseVO implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * 访问ID
     */
    private Long infoId;

    /**
     * 用户账号
     */
    @Schema(description = "用户账号")
    private String userName;

    /**
     * 客户端
     */
    @Schema(description = "客户端")
    private String clientKey;

    /**
     * 设备类型
     */
    @Schema(description = "设备类型")
    private String deviceType;

    /**
     * 登录IP地址
     */
    @Schema(description = "登录IP地址")
    private String ipaddr;

    /**
     * 登录地点
     */
    @Schema(description = "登录地点")
    private String loginLocation;

    /**
     * 浏览器类型
     */
    @Schema(description = "浏览器类型")
    private String browser;

    /**
     * 操作系统
     */
    @Schema(description = "操作系统")
    private String os;

    /**
     * 登录状态（0成功 1失败）
     */
    @Schema(description = "登录状态（0成功 1失败）")
    private String status;

    /**
     * 提示消息
     */
    @Schema(description = "提示消息")
    private String msg;

    /**
     * 访问时间
     */
    @Schema(description = "访问时间")
    private LocalDateTime loginTime;
}
