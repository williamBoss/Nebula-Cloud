package com.nebula.common.log.event;

import java.time.LocalDateTime;
import lombok.Getter;
import lombok.Setter;
import org.springframework.context.ApplicationEvent;

/**
 * 登录事件
 *
 * @author KING
 */
@Getter
@Setter
public class LogininforEvent extends ApplicationEvent {

    /**
     * 用户账号
     */
    private String userName;

    /**
     * 客户端
     */
    private String clientKey;

    /**
     * 设备类型
     */
    private String deviceType;

    /**
     * 登录IP地址
     */
    private String ipaddr;

    /**
     * 登录地点
     */
    private String loginLocation;

    /**
     * 浏览器类型
     */
    private String browser;

    /**
     * 操作系统
     */
    private String os;

    /**
     * 登录状态 0成功 1失败
     */
    private String status;

    /**
     * 提示消息
     */
    private String msg;

    /**
     * 访问时间
     */
    private LocalDateTime loginTime;

    public LogininforEvent() {
        super("default");
    }

    public LogininforEvent(Object source) {
        super(source);
    }

}
