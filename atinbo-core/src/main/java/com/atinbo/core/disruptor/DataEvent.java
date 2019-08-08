package com.atinbo.core.disruptor;

import lombok.NonNull;

import java.util.EventObject;

/**
 * 数据事件
 */
public class DataEvent extends EventObject {

    public DataEvent(@NonNull Object source) {
        super(source);
    }
}
