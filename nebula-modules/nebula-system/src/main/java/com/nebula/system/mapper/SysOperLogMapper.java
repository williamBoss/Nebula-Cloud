package com.nebula.system.mapper;

import com.nebula.system.domain.SysOperLog;
import com.mybatisflex.core.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
 * 操作日志记录 映射层。
 *
 * @author William
 * @since 1.0
 */
@Mapper
public interface SysOperLogMapper extends BaseMapper<SysOperLog> {

}
