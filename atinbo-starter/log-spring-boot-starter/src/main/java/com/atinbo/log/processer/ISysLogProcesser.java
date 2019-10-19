package com.atinbo.log.processer;

import com.atinbo.log.model.SysLogSource;

/**
 * @author zenghao
 * @date 2019-10-19
 */
public interface ISysLogProcesser {

    void saveSysLog(SysLogSource logSource);
}
