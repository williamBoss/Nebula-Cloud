package com.nebula.system.service;

import com.mybatisflex.core.paginate.Page;
import com.nebula.datasource.page.PageQuery;
import com.nebula.system.domain.SysLogininfor;
import com.mybatisflex.core.service.IService;
import com.nebula.system.domain.vo.SysLogininforVO;

/**
 * 系统访问记录 服务层。
 *
 * @author William
 * @since 1.0
 */
public interface ISysLogininforService extends IService<SysLogininfor> {

    Page<SysLogininforVO> selectPageLogininforList(SysLogininforVO logininfor, PageQuery pageQuery);
}