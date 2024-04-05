package com.nebula.system.service.impl;

import static com.nebula.system.domain.table.SysOperLogTableDef.SYS_OPER_LOG;

import com.mybatisflex.core.paginate.Page;
import com.mybatisflex.core.query.QueryWrapper;
import com.mybatisflex.spring.service.impl.ServiceImpl;
import com.nebula.datasource.page.PageQuery;
import com.nebula.system.domain.SysOperLog;
import com.nebula.system.domain.vo.SysOperLogVO;
import com.nebula.system.mapper.SysOperLogMapper;
import com.nebula.system.service.ISysOperLogService;
import jakarta.annotation.Resource;
import java.util.Map;
import org.springframework.stereotype.Service;

/**
 * 操作日志记录 服务层实现。
 *
 * @author William
 * @since 1.0
 */
@Service
public class SysOperLogServiceImpl extends ServiceImpl<SysOperLogMapper, SysOperLog> implements ISysOperLogService {

    @Resource
    private SysOperLogMapper sysOperLogMapper;

    @Override
    public Page<SysOperLogVO> selectPageOperLogList(SysOperLogVO operLog, PageQuery pageQuery) {
        Map<String, Object> params = operLog.getParams();
        QueryWrapper queryWrapper = QueryWrapper.create()
            .from(SYS_OPER_LOG)
            .where(SYS_OPER_LOG.TITLE.like(operLog.getTitle()))
            .and(SYS_OPER_LOG.STATUS.eq(operLog.getStatus()))
            .and(SYS_OPER_LOG.OPER_NAME.like(operLog.getOperName()))
            .and(SYS_OPER_LOG.OPER_TIME.between(params.get("beginTime"), params.get("endTime")))
            .and(SYS_OPER_LOG.BUSINESS_TYPE.eq(operLog.getBusinessType()))
            .orderBy(SYS_OPER_LOG.OPER_ID, false);
        return sysOperLogMapper.paginateAs(pageQuery.getPageNumber(), pageQuery.getPageSize(), queryWrapper,
            SysOperLogVO.class);
    }
}