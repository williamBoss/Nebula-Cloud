package com.nebula.system.service;

import com.mybatisflex.core.paginate.Page;
import com.mybatisflex.core.service.IService;
import com.nebula.datasource.page.PageQuery;
import com.nebula.system.domain.SysConfig;
import com.nebula.system.domain.vo.SysConfigVO;

/**
 * 参数配置表 服务层。
 *
 * @author William
 * @since 1.0
 */
public interface ISysConfigService extends IService<SysConfig> {

    Page<SysConfigVO> selectConfigList(String searchKey, PageQuery page);

    SysConfigVO selectConfigByKey(String configKey);

}