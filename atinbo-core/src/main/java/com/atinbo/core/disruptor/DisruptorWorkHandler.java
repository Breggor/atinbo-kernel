package com.atinbo.core.disruptor;

import com.lmax.disruptor.WorkHandler;

import java.util.concurrent.Executor;

/**
 * disruptor consumer
 *
 * @author breggor
 */
public class DisruptorWorkHandler implements WorkHandler<DisruptorEvent> {

    private Executor executor;

    /**
     * Instantiates a new Soul data handler.
     *
     * @param executor the executor
     */
    public DisruptorWorkHandler(final Executor executor) {
        this.executor = executor;
    }

    @Override
    public void onEvent(final DisruptorEvent event) {
        executor.execute(() -> {
            //TODO 执行业务处理
            event.clear();
        });
    }
}
