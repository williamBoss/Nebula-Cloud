package com.nebula.system.service.impl;

import static com.nebula.system.domain.table.SysMenuTableDef.SYS_MENU;

import com.mybatisflex.core.paginate.Page;
import com.mybatisflex.core.query.QueryWrapper;
import com.mybatisflex.spring.service.impl.ServiceImpl;
import com.nebula.datasource.page.PageQuery;
import com.nebula.system.domain.SysMenu;
import com.nebula.system.domain.vo.SysMenuVO;
import com.nebula.system.mapper.SysMenuMapper;
import com.nebula.system.service.ISysMenuService;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;

/**
 * 菜单权限表 服务层实现。
 *
 * @author William
 * @since 1.0
 */
@Service
public class SysMenuServiceImpl extends ServiceImpl<SysMenuMapper, SysMenu> implements ISysMenuService {

    @Resource
    private SysMenuMapper sysMenuMapper;

    @Override
    public Page<SysMenuVO> selectMenuPageList(String searchKey, PageQuery page) {
        QueryWrapper queryWrapper = QueryWrapper.create()
            .select(SYS_MENU.ALL_COLUMNS)
            .from(SYS_MENU)
            .where(SYS_MENU.MENU_NAME.like(searchKey))
            .or(SYS_MENU.PATH.like(searchKey));
        return sysMenuMapper.paginateAs(page.getPageNum(), page.getPageSize(), queryWrapper, SysMenuVO.class);
    }

}