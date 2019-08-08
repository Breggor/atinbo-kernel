package com.atinbo.core.disruptor;

import com.lmax.disruptor.EventFactory;

/**
 * DisruptorEventFactory
 *
 * @author breggor
 */
public class DisruptorEventFactory implements EventFactory<DisruptorEvent> {

    @Override
    public DisruptorEvent newInstance() {
        return new DisruptorEvent();
    }
}