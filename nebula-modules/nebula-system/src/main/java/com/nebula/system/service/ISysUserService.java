package com.nebula.system.service;

import com.mybatisflex.core.paginate.Page;
import com.mybatisflex.core.service.IService;
import com.nebula.datasource.page.PageQuery;
import com.nebula.system.domain.SysUser;
import com.nebula.system.domain.vo.SysUserVO;

/**
 * 用户信息表 服务层。
 *
 * @author William
 * @since 1.0
 */
public interface ISysUserService extends IService<SysUser> {

    Page<SysUserVO> selectUserPageList(SysUserVO user, PageQuery pageQuery);

    SysUserVO selectUserById(Long userId);

    SysUserVO selectUserByUserName(String username);

    boolean checkUserNameUnique(SysUserVO user);

    boolean checkPhoneUnique(SysUserVO user);

    boolean checkEmailUnique(SysUserVO user);

    boolean checkDeptExistUser(Long deptId);

    void save(SysUserVO user);

    void update(SysUserVO user);

    void resetUserPwd(SysUserVO user);

    void updateStatus(SysUserVO user);

}