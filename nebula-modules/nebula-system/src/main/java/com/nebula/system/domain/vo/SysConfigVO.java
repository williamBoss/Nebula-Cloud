package com.nebula.system.domain.vo;

import com.nebula.system.domain.SysConfig;
import io.github.linpeilie.annotations.AutoMapper;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 参数配置表
 */
@Schema(description = "参数配置表")
@Data
@NoArgsConstructor
@AutoMapper(target = SysConfig.class)
public class SysConfigVO {

    /**
     * 参数主键
     */
    @Schema(description = "参数主键")
    private Integer configId;

    /**
     * 参数名称
     */
    @Schema(description = "参数名称")
    private String configName;

    /**
     * 参数键名
     */
    @Schema(description = "参数键名")
    private String configKey;

    /**
     * 参数键值
     */
    @Schema(description = "参数键值")
    private String configValue;

    /**
     * 系统内置（Y是 N否）
     */
    @Schema(description = "系统内置（Y是 N否）")
    private String configType;

    /**
     * 备注
     */
    @Schema(description = "备注")
    private String remark;

}