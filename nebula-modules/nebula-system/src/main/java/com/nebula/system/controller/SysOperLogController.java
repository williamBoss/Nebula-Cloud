package com.nebula.system.controller;

import static com.nebula.system.domain.table.SysOperLogTableDef.SYS_OPER_LOG;

import com.mybatisflex.core.paginate.Page;
import com.mybatisflex.core.query.QueryWrapper;
import com.nebula.datasource.page.PageQuery;
import com.nebula.system.domain.vo.SysOperLogVO;
import com.nebula.system.service.ISysOperLogService;
import java.util.Arrays;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 操作日志记录 控制层。
 *
 * @author William
 * @since 1.0
 */
@RestController
@RequestMapping("/sysOperLog")
public class SysOperLogController {

    private final ISysOperLogService sysOperLogService;

    public SysOperLogController(ISysOperLogService sysOperLogService) {
        this.sysOperLogService = sysOperLogService;
    }

    /**
     * 分页查询操作日志记录
     *
     * @param pageQuery 分页对象
     * @return 分页对象
     */
    @GetMapping("/list")
    public Page<SysOperLogVO> list(SysOperLogVO operLog, PageQuery pageQuery) {
        return sysOperLogService.selectPageOperLogList(operLog, pageQuery);
    }

    /**
     * 批量删除操作日志记录
     *
     * @param operIds 日志ids
     */
    @DeleteMapping("/{operIds}")
    public void remove(@PathVariable Long[] operIds) {
        sysOperLogService.removeByIds(Arrays.stream(operIds).toList());
    }

    /**
     * 清理操作日志记录
     */
    @DeleteMapping("/clean")
    public void clean() {
        sysOperLogService.remove(QueryWrapper.create().from(SYS_OPER_LOG));
    }
}