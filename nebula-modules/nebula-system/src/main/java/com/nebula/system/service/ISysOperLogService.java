package com.nebula.system.service;

import com.mybatisflex.core.paginate.Page;
import com.mybatisflex.core.service.IService;
import com.nebula.datasource.page.PageQuery;
import com.nebula.system.domain.SysOperLog;
import com.nebula.system.domain.vo.SysOperLogVO;

/**
 * 操作日志记录 服务层。
 *
 * @author William
 * @since 1.0
 */
public interface ISysOperLogService extends IService<SysOperLog> {

    Page<SysOperLogVO> selectPageOperLogList(SysOperLogVO operLog, PageQuery pageQuery);
}