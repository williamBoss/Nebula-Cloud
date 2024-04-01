package com.nebula.system.domain;

import com.mybatisflex.annotation.Column;
import com.mybatisflex.annotation.Id;
import com.mybatisflex.annotation.KeyType;
import com.mybatisflex.annotation.Table;
import com.nebula.common.log.event.LogininforEvent;
import io.github.linpeilie.annotations.AutoMapper;
import java.time.LocalDateTime;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 系统访问记录 实体类。
 *
 * @author William
 * @since 1.0
 */
@Data
@NoArgsConstructor
@Table(value = "sys_logininfor")
@AutoMapper(target = LogininforEvent.class)
public class SysLogininfor {

    /**
     * 访问ID
     */
    @Id(keyType = KeyType.Auto)
    private Long infoId;

    /**
     * 用户账号
     */
    @Column(value = "user_name")
    private String userName;

    /**
     * 客户端
     */
    @Column(value = "client_key")
    private String clientKey;

    /**
     * 设备类型
     */
    @Column(value = "device_type")
    private String deviceType;

    /**
     * 登录IP地址
     */
    @Column(value = "ipaddr")
    private String ipaddr;

    /**
     * 登录地点
     */
    @Column(value = "login_location")
    private String loginLocation;

    /**
     * 浏览器类型
     */
    @Column(value = "browser")
    private String browser;

    /**
     * 操作系统
     */
    @Column(value = "os")
    private String os;

    /**
     * 登录状态（0成功 1失败）
     */
    @Column(value = "status")
    private String status;

    /**
     * 提示消息
     */
    @Column(value = "msg")
    private String msg;

    /**
     * 访问时间
     */
    @Column(value = "login_time")
    private LocalDateTime loginTime;
}
