package com.nebula.system.service;

import com.mybatisflex.core.service.IService;
import com.nebula.system.domain.SysRole;

/**
 * 角色信息表 服务层。
 *
 * @author William
 * @since 1.0
 */
public interface ISysRoleService extends IService<SysRole> {

    boolean checkRoleKey(SysRole role);

    Long selectRoleListByUserId(Long userId);
}