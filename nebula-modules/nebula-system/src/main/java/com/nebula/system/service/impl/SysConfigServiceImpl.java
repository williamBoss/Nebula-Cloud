package com.nebula.system.service.impl;

import static com.nebula.system.domain.table.SysConfigTableDef.SYS_CONFIG;

import com.mybatisflex.core.paginate.Page;
import com.mybatisflex.core.query.QueryWrapper;
import com.mybatisflex.spring.service.impl.ServiceImpl;
import com.nebula.datasource.page.PageQuery;
import com.nebula.system.domain.SysConfig;
import com.nebula.system.domain.vo.SysConfigVO;
import com.nebula.system.mapper.SysConfigMapper;
import com.nebula.system.service.ISysConfigService;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;

/**
 * 参数配置表 服务层实现。
 *
 * @author William
 * @since 1.0
 */
@Service
public class SysConfigServiceImpl extends ServiceImpl<SysConfigMapper, SysConfig> implements ISysConfigService {

    @Resource
    private SysConfigMapper sysConfigMapper;

    @Override
    public Page<SysConfigVO> selectConfigList(String searchKey, PageQuery page) {
        QueryWrapper queryWrapper = QueryWrapper.create()
            .select(SYS_CONFIG.CONFIG_ID, SYS_CONFIG.CONFIG_NAME, SYS_CONFIG.CONFIG_KEY, SYS_CONFIG.CONFIG_VALUE,
                SYS_CONFIG.CONFIG_TYPE, SYS_CONFIG.REMARK)
            .from(SYS_CONFIG)
            .where(SYS_CONFIG.CONFIG_NAME.like(searchKey))
            .or(SYS_CONFIG.CONFIG_KEY.like(searchKey));
        return sysConfigMapper.paginateAs(page.getPageNum(), page.getPageSize(), queryWrapper, SysConfigVO.class);
    }

    @Override
    public SysConfigVO selectConfigByKey(String configKey) {
        QueryWrapper queryWrapper = QueryWrapper.create()
            .select()
            .from(SYS_CONFIG)
            .where(SYS_CONFIG.CONFIG_KEY.eq(configKey));
        return sysConfigMapper.selectOneByQueryAs(queryWrapper, SysConfigVO.class);
    }

}