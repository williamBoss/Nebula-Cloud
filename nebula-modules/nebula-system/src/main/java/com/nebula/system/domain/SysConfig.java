package com.nebula.system.domain;

import com.mybatisflex.annotation.Column;
import com.mybatisflex.annotation.Id;
import com.mybatisflex.annotation.KeyType;
import com.mybatisflex.annotation.Table;
import com.nebula.datasource.domain.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

/**
 * 参数配置表 实体类。
 *
 * @author William
 * @since 1.0
 */
@EqualsAndHashCode(callSuper = true)
@Data
@NoArgsConstructor
@Table(value = "sys_config")
public class SysConfig extends BaseEntity {

    /**
     * 参数主键
     */
    @Id(keyType = KeyType.Auto)
    private Integer configId;

    /**
     * 参数名称
     */
    @Column(value = "config_name")
    private String configName;

    /**
     * 参数键名
     */
    @Column(value = "config_key")
    private String configKey;

    /**
     * 参数键值
     */
    @Column(value = "config_value")
    private String configValue;

    /**
     * 系统内置（Y是 N否）
     */
    @Column(value = "config_type")
    private String configType;

}
