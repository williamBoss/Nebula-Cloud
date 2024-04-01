package com.nebula.system.mapper;

import com.nebula.system.domain.SysUser;
import com.mybatisflex.core.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
 * 用户信息表 映射层。
 *
 * @author William
 * @since 1.0
 */
@Mapper
public interface SysUserMapper extends BaseMapper<SysUser> {

}
