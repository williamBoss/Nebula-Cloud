package com.nebula.system.controller;

import static com.nebula.system.domain.table.SysRoleTableDef.SYS_ROLE;
import static com.nebula.system.domain.table.SysUserRoleTableDef.SYS_USER_ROLE;

import com.mybatisflex.core.paginate.Page;
import com.nebula.common.constants.UserConstants;
import com.nebula.common.core.exception.ServiceException;
import com.nebula.common.core.exception.UserException;
import com.nebula.datasource.page.PageQuery;
import com.nebula.system.domain.SysRole;
import com.nebula.system.domain.SysUserRole;
import com.nebula.system.domain.vo.SysRoleVO;
import com.nebula.system.domain.vo.SysUserVO;
import com.nebula.system.service.ISysRoleMenuService;
import com.nebula.system.service.ISysRoleService;
import com.nebula.system.service.ISysUserRoleService;
import io.github.linpeilie.Converter;
import java.util.List;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 角色信息表 控制层。
 *
 * @author William
 * @since 1.0
 */
@RestController
@RequestMapping("/sysRole")
public class SysRoleController {

    private final ISysRoleService sysRoleService;

    private final ISysUserRoleService sysUserRoleService;

    private final ISysRoleMenuService sysRoleMenuService;

    private final Converter converter;

    public SysRoleController(ISysRoleService sysRoleService, ISysUserRoleService sysUserRoleService,
        ISysRoleMenuService sysRoleMenuService, Converter converter) {
        this.sysRoleService = sysRoleService;
        this.sysUserRoleService = sysUserRoleService;
        this.sysRoleMenuService = sysRoleMenuService;
        this.converter = converter;
    }

    /**
     * 查询所有角色信息表
     *
     * @return 所有数据
     */
    @GetMapping("/all")
    public List<SysRoleVO> all() {
        return converter.convert(sysRoleService.list(), SysRoleVO.class);
    }

    /**
     * 根据角色信息表主键获取详细信息。
     *
     * @param id sysRole主键
     * @return 角色信息表详情
     */
    @GetMapping("/{id}")
    public SysRoleVO getInfo(@PathVariable Long id) {
        SysRole sysRole = sysRoleService.getById(id);
        return converter.convert(sysRole, SysRoleVO.class);
    }

    /**
     * 添加 角色信息表
     *
     * @param sysRole 角色信息表
     * @return {@code true} 添加成功，{@code false} 添加失败
     */
    @PostMapping
    public boolean add(@RequestBody SysRoleVO sysRole) {
        SysRole role = converter.convert(sysRole, SysRole.class);
        if (sysRoleService.checkRoleKey(role)) {
            throw new ServiceException("该角色编码已存在,请勿重复使用");
        }
        return sysRoleService.save(role);
    }

    /**
     * 根据主键更新角色信息表
     *
     * @param sysRole 角色信息表
     * @return {@code true} 更新成功，{@code false} 更新失败
     */
    @PutMapping
    public boolean edit(@RequestBody SysRoleVO sysRole) {
        if (sysRole.getRoleId().equals(UserConstants.SUPER_ADMIN_ID)) {
            throw new UserException("不允许修改超级管理员角色");
        }
        SysRole role = converter.convert(sysRole, SysRole.class);
        if (sysRoleService.checkRoleKey(role)) {
            throw new ServiceException("该角色编码已存在,请勿重复使用");
        }
        if (sysUserRoleService.checkUserExist(sysRole.getRoleId())) {
            throw new ServiceException("该角色包含未移除的成员，不可修改角色编码");
        }
        return sysRoleService.updateById(role);
    }

    /**
     * 根据主键删除角色信息表
     *
     * @param id 主键
     * @return {@code true} 删除成功，{@code false} 删除失败
     */
    @DeleteMapping("{id}")
    public boolean delete(@PathVariable Long id) {
        if (id.equals(UserConstants.SUPER_ADMIN_ID)) {
            throw new UserException("不允许删除超级管理员角色");
        }
        if (sysUserRoleService.checkUserExist(id)) {
            throw new ServiceException("该角色包含未移除的成员");
        }
        return sysRoleService.removeById(id);
    }

    /**
     * 根据角色key获取该角色下用户列表
     *
     * @param roleKey   角色key
     * @param pageQuery 分页查询对象
     * @return 用户列表
     */
    @GetMapping("/user/list/{roleKey}")
    public Page<SysUserVO> userList(@PathVariable String roleKey, String searchKey, PageQuery pageQuery) {
        return sysUserRoleService.getUserListByRoleKey(roleKey, searchKey, pageQuery);
    }

    /**
     * 添加用户角色关系
     *
     * @param roleKey 角色key
     * @param userId  用户id
     * @return {@code true} 添加成功，{@code false} 添加失败
     */
    @PostMapping("/addUserRole/{roleKey}/{userId}")
    public boolean addUserRole(@PathVariable String roleKey, @PathVariable Long userId) {
        SysRole sysRole = sysRoleService.getOne(SYS_ROLE.ROLE_KEY.eq(roleKey));
        return sysUserRoleService.save(new SysUserRole().setRoleId(sysRole.getRoleId()).setUserId(userId));
    }

    /**
     * 移除用户角色关系
     *
     * @param roleKey 角色Key
     * @param userId  用户id
     * @return {@code true} 移除成功，{@code false} 移除失败
     */
    @DeleteMapping("/removeUserRole/{roleKey}/{userId}")
    public boolean removeUserRole(@PathVariable String roleKey, @PathVariable Long userId) {
        SysRole sysRole = sysRoleService.getOne(SYS_ROLE.ROLE_KEY.eq(roleKey));
        return sysUserRoleService.remove(
            SYS_USER_ROLE.ROLE_ID.eq(sysRole.getRoleId()).and(SYS_USER_ROLE.USER_ID.eq(userId)));
    }

    /**
     * 获取角色菜单列表
     *
     * @param roleKey 角色key
     * @return 角色菜单列表
     */
    @GetMapping("/menu/list/{roleKey}")
    public List<Long> menuList(@PathVariable String roleKey) {
        return sysRoleMenuService.getMenuListByRoleKey(roleKey);
    }

    /**
     * 保存角色菜单关系
     *
     * @param roleKey 角色key
     * @param menuIds 菜单id列表
     */
    @PostMapping("/addRoleMenu/{roleKey}")
    public void addRoleMenu(@PathVariable String roleKey, @RequestBody Long[] menuIds) {
        SysRole sysRole = sysRoleService.getOne(SYS_ROLE.ROLE_KEY.eq(roleKey));
        sysRoleMenuService.saveRoleMenu(sysRole.getRoleId(), menuIds);
    }

}