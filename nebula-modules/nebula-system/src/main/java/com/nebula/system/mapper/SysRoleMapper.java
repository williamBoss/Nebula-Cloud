package com.nebula.system.mapper;

import com.nebula.system.domain.SysRole;
import com.mybatisflex.core.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
 * 角色信息表 映射层。
 *
 * @author William
 * @since 1.0
 */
@Mapper
public interface SysRoleMapper extends BaseMapper<SysRole> {

}
