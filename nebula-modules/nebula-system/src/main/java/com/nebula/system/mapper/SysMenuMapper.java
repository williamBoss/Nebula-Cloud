package com.nebula.system.mapper;

import com.nebula.system.domain.SysMenu;
import com.mybatisflex.core.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
 * 菜单权限表 映射层。
 *
 * @author William
 * @since 1.0
 */
@Mapper
public interface SysMenuMapper extends BaseMapper<SysMenu> {

}
