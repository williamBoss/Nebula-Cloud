package com.nebula.system.mapper;

import com.nebula.system.domain.SysUserRole;
import com.mybatisflex.core.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
 * 用户和角色关联表 映射层。
 *
 * @author William
 * @since 1.0
 */
@Mapper
public interface SysUserRoleMapper extends BaseMapper<SysUserRole> {

}
