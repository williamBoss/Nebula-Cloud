package com.nebula.system.domain.vo;

import com.nebula.common.core.domain.vo.BaseVO;
import com.nebula.system.domain.SysRole;
import io.github.linpeilie.annotations.AutoMapper;
import io.swagger.v3.oas.annotations.media.Schema;
import java.io.Serial;
import java.io.Serializable;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 角色信息视图对象 sys_role
 *
 * @author KING
 */
@EqualsAndHashCode(callSuper = true)
@Data
@Schema(description = "角色信息")
@AutoMapper(target = SysRole.class)
public class SysRoleVO extends BaseVO implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * 角色ID
     */
    @Schema(description = "角色Id")
    private Long roleId;

    /**
     * 角色名称
     */
    @Schema(description = "角色名称")
    private String roleName;

    /**
     * 角色Key字符串
     */
    @Schema(description = "角色Key")
    private String roleKey;

    /**
     * 数据范围（1：全部数据权限 2：自定数据权限 3：本部门数据权限 4：本部门及以下数据权限）
     */
    @Schema(description = "数据范围")
    private String dataScope;

    /**
     * 角色状态（0正常 1停用）
     */
    @Schema(description = "角色状态")
    private String status;
}
