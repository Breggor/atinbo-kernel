package com.atinbo.log.event;

import com.atinbo.log.model.SysLogSource;
import com.atinbo.log.processer.ISysLogProcesser;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.event.EventListener;
import org.springframework.core.annotation.Order;
import org.springframework.scheduling.annotation.Async;

/**
 * @author lengleng
 * 异步监听日志事件
 */
@Slf4j
@AllArgsConstructor
public class SysLogListener {

    private final ISysLogProcesser sysLogProcesser;

    @Async
    @Order
    @EventListener(SysLogEvent.class)
    public void saveSysLog(SysLogEvent event) {
        SysLogSource logSource = (SysLogSource) event.getSource();
        if (sysLogProcesser != null) {
            sysLogProcesser.saveSysLog(logSource);
        }
        log.info("logSource:{}" , logSource);
    }
}
