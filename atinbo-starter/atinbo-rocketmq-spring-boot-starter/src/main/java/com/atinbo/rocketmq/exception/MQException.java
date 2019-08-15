package com.atinbo.rocketmq.exception;

/**
 * RocketMQ的自定义异常
 *
 * @author breggor
 */
public class MQException extends RuntimeException {
    public MQException(String msg) {
        super(msg);
    }
}
