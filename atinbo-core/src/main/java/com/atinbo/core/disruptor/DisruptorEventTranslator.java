package com.atinbo.core.disruptor;


import com.lmax.disruptor.EventTranslatorOneArg;

/**
 * @author breggor
 */
public class DisruptorEventTranslator implements EventTranslatorOneArg<DisruptorEvent, DataEvent> {

    @Override
    public void translateTo(final DisruptorEvent event, final long sequence, final DataEvent data) {
        event.setSource(data);
    }
}
