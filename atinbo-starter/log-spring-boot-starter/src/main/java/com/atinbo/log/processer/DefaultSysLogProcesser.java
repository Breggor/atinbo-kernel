package com.atinbo.log.processer;

import com.atinbo.log.model.SysLogSource;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class DefaultSysLogProcesser implements ISysLogProcesser {

    @Override
    public void saveSysLog(SysLogSource logSource) {
        log.info("#DefaultSysLogProcesser# --- {}", logSource);
    }
}
