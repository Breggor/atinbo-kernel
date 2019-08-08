package com.atinbo.core.disruptor;

import lombok.Data;

import java.io.Serializable;

/**
 * @author breggor
 */
@Data
public class DisruptorEvent<E> implements Serializable {
    private E source;


    public void clear() {
        source = null;
    }
}
