package com.nebula.system.service;

import com.mybatisflex.core.paginate.Page;
import com.nebula.datasource.page.PageQuery;
import com.nebula.system.domain.SysUserRole;
import com.mybatisflex.core.service.IService;
import com.nebula.system.domain.vo.SysUserVO;

/**
 * 用户和角色关联表 服务层。
 *
 * @author William
 * @since 1.0
 */
public interface ISysUserRoleService extends IService<SysUserRole> {

    boolean checkUserExist(Long id);

    Page<SysUserVO> getUserListByRoleKey(String roleKey, String searchKey, PageQuery pageQuery);
}