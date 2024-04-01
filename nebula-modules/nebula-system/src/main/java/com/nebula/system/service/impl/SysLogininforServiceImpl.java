package com.nebula.system.service.impl;

import static com.nebula.system.domain.table.SysLogininforTableDef.SYS_LOGININFOR;

import com.mybatisflex.core.paginate.Page;
import com.mybatisflex.core.query.QueryWrapper;
import com.mybatisflex.spring.service.impl.ServiceImpl;
import com.nebula.datasource.page.PageQuery;
import com.nebula.system.domain.SysLogininfor;
import com.nebula.system.domain.vo.SysLogininforVO;
import com.nebula.system.mapper.SysLogininforMapper;
import com.nebula.system.service.ISysLogininforService;
import jakarta.annotation.Resource;
import java.util.Map;
import org.springframework.stereotype.Service;

/**
 * 系统访问记录 服务层实现。
 *
 * @author William
 * @since 1.0
 */
@Service
public class SysLogininforServiceImpl extends ServiceImpl<SysLogininforMapper, SysLogininfor> implements
    ISysLogininforService {

    @Resource
    private SysLogininforMapper sysLogininforMapper;

    @Override
    public Page<SysLogininforVO> selectPageLogininforList(SysLogininforVO logininfor, PageQuery pageQuery) {
        Map<String, Object> params = logininfor.getParams();
        QueryWrapper queryWrapper = QueryWrapper.create()
            .from(SYS_LOGININFOR)
            .where(SYS_LOGININFOR.STATUS.eq(logininfor.getStatus()))
            .and(SYS_LOGININFOR.USER_NAME.like(logininfor.getUserName()))
            .and(SYS_LOGININFOR.LOGIN_TIME.between(params.get("beginTime"), params.get("endTime")))
            .orderBy(SYS_LOGININFOR.LOGIN_TIME, false);
        return sysLogininforMapper.paginateAs(pageQuery.getPageNum(), pageQuery.getPageSize(), queryWrapper,
            SysLogininforVO.class);
    }
}