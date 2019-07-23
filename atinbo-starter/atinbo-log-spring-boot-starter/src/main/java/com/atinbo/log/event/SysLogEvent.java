package com.atinbo.log.event;

import com.atinbo.log.model.SystemLog;
import org.springframework.context.ApplicationEvent;

/**
 * @author zenghao
 * @date 2019-7-23
 * 系统日志事件
 */
public class SysLogEvent extends ApplicationEvent {

	public SysLogEvent(SystemLog source) {
		super(source);
	}
}
