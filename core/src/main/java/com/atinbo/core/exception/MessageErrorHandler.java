package com.atinbo.core.exception;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.ErrorHandler;

/**
 * 队列消费者消息异常
 */
public class MessageErrorHandler implements ErrorHandler {
    private static final Logger logger = LoggerFactory.getLogger(MessageErrorHandler.class);

    @Override
    public void handleError(Throwable e) {
        e.printStackTrace();
        logger.error(e.getMessage(), e);
    }
}