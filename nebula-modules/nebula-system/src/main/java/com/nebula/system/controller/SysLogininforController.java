package com.nebula.system.controller;

import static com.nebula.system.domain.table.SysLogininforTableDef.SYS_LOGININFOR;

import com.mybatisflex.core.paginate.Page;
import com.mybatisflex.core.query.QueryWrapper;
import com.nebula.datasource.page.PageQuery;
import com.nebula.system.domain.vo.SysLogininforVO;
import com.nebula.system.service.ISysLogininforService;
import java.io.Serializable;
import java.util.Arrays;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 系统访问记录 控制层。
 *
 * @author William
 * @since 1.0
 */
@RestController
@RequestMapping("/sysLogininfor")
public class SysLogininforController {

    private final ISysLogininforService sysLogininforService;

    public SysLogininforController(ISysLogininforService sysLogininforService) {
        this.sysLogininforService = sysLogininforService;
    }

    /**
     * 分页查询系统访问记录
     *
     * @param logininfor 系统访问记录
     * @param pageQuery  分页查询条件
     * @return 系统访问记录分页列表
     */
    @GetMapping("/list")
    public Page<SysLogininforVO> list(SysLogininforVO logininfor, PageQuery pageQuery) {
        return sysLogininforService.selectPageLogininforList(logininfor, pageQuery);
    }

    /**
     * 根据主键删除系统访问记录
     *
     * @param id 主键
     * @return {@code true} 删除成功，{@code false} 删除失败
     */
    @DeleteMapping("/remove/{id}")
    public boolean remove(@PathVariable Serializable id) {
        return sysLogininforService.removeById(id);
    }

    /**
     * 批量删除登录日志
     *
     * @param infoIds 日志ids
     */
    @DeleteMapping("/{infoIds}")
    public void remove(@PathVariable Long[] infoIds) {
        sysLogininforService.removeByIds(Arrays.stream(infoIds).toList());
    }

    /**
     * 清理系统访问记录
     */
    @DeleteMapping("/clean")
    public void clean() {
        sysLogininforService.remove(QueryWrapper.create().from(SYS_LOGININFOR));
    }
}