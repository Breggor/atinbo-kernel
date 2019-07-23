package com.atinbo.log.event;

import com.atinbo.log.model.SystemLog;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.event.EventListener;
import org.springframework.core.annotation.Order;
import org.springframework.scheduling.annotation.Async;


/**
 * @author lengleng
 * 异步监听日志事件
 */
@Slf4j
public class SysLogListener {

	@Async
	@Order
	@EventListener(SysLogEvent.class)
	public void saveSysLog(SysLogEvent event) {
		SystemLog systemLog = (SystemLog) event.getSource();
		//TODO 日志存储
		log.info("systemLog:{}", systemLog);
	}
}
