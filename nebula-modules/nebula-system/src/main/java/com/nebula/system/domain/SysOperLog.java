package com.nebula.system.domain;

import com.mybatisflex.annotation.Column;
import com.mybatisflex.annotation.Id;
import com.mybatisflex.annotation.KeyType;
import com.mybatisflex.annotation.Table;
import com.nebula.common.log.event.OperLogEvent;
import io.github.linpeilie.annotations.AutoMapper;
import java.time.LocalDateTime;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 操作日志记录 实体类。
 *
 * @author William
 * @since 1.0
 */
@Data
@NoArgsConstructor
@Table(value = "sys_oper_log")
@AutoMapper(target = OperLogEvent.class)
public class SysOperLog {

    /**
     * 日志主键
     */
    @Id(keyType = KeyType.Auto)
    private Long operId;

    /**
     * 模块标题
     */
    @Column(value = "title")
    private String title;

    /**
     * 业务类型（0其它 1新增 2修改 3删除）
     */
    @Column(value = "business_type")
    private Integer businessType;

    /**
     * 方法名称
     */
    @Column(value = "method")
    private String method;

    /**
     * 请求方式
     */
    @Column(value = "request_method")
    private String requestMethod;

    /**
     * 操作类别（0其它 1后台用户 2手机端用户）
     */
    @Column(value = "operator_type")
    private Integer operatorType;

    /**
     * 操作人id
     */
    @Column(value = "oper_user_id")
    private Long operUserId;

    /**
     * 操作人员
     */
    @Column(value = "oper_name")
    private String operName;

    /**
     * 部门名称
     */
    @Column(value = "dept_name")
    private String deptName;

    /**
     * 请求URL
     */
    @Column(value = "oper_url")
    private String operUrl;

    /**
     * 主机地址
     */
    @Column(value = "oper_ip")
    private String operIp;

    /**
     * 操作地点
     */
    @Column(value = "oper_location")
    private String operLocation;

    /**
     * 请求参数
     */
    @Column(value = "oper_param")
    private String operParam;

    /**
     * 返回参数
     */
    @Column(value = "json_result")
    private String jsonResult;

    /**
     * 操作状态（0正常 1异常）
     */
    @Column(value = "status")
    private Integer status;

    /**
     * 错误消息
     */
    @Column(value = "error_msg")
    private String errorMsg;

    /**
     * 操作时间
     */
    @Column(value = "oper_time")
    private LocalDateTime operTime;

    /**
     * 消耗时间
     */
    @Column(value = "cost_time")
    private Long costTime;
}
