package com.nebula.system.mapper;

import com.nebula.system.domain.SysRoleMenu;
import com.mybatisflex.core.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
 * 角色和菜单关联表 映射层。
 *
 * @author William
 * @since 1.0
 */
@Mapper
public interface SysRoleMenuMapper extends BaseMapper<SysRoleMenu> {

}
