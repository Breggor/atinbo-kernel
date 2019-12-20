package com.atinbo.core.disruptor;

import com.atinbo.core.concurrent.NamedThreadFactory;
import com.lmax.disruptor.BlockingWaitStrategy;
import com.lmax.disruptor.IgnoreExceptionHandler;
import com.lmax.disruptor.RingBuffer;
import com.lmax.disruptor.dsl.Disruptor;
import com.lmax.disruptor.dsl.ProducerType;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.InitializingBean;

import java.util.concurrent.Executor;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * disruptor start and publishEvent.
 *
 * @author breggor
 */
public class DisruptorEventPublisher implements InitializingBean, DisposableBean {

    private Disruptor<DisruptorEvent> disruptor;

    private int bufferSize = 4096;

    private int threadSize = 16;

    /**
     * disruptor start with bufferSize.
     */
    private void start() {
        disruptor = new Disruptor<>(new DisruptorEventFactory(), bufferSize, NamedThreadFactory.create("disruptor-thread-", false),
                ProducerType.MULTI, new BlockingWaitStrategy());

        final Executor executor = new ThreadPoolExecutor(threadSize, threadSize, 0, TimeUnit.MILLISECONDS,
                new LinkedBlockingQueue<>(), NamedThreadFactory.create("disruptor-executor-", false), new ThreadPoolExecutor.AbortPolicy());

        DisruptorWorkHandler[] consumers = new DisruptorWorkHandler[threadSize];
        for (int i = 0; i < threadSize; i++) {
            consumers[i] = new DisruptorWorkHandler(executor);
        }

        disruptor.handleEventsWithWorkerPool(consumers);
        disruptor.setDefaultExceptionHandler(new IgnoreExceptionHandler());
        disruptor.start();
    }

    /**
     * publish disruptor event.
     *
     * @param data
     */
    public void publishEvent(final DataEvent data) {
        final RingBuffer<DisruptorEvent> ringBuffer = disruptor.getRingBuffer();
        ringBuffer.publishEvent(new DisruptorEventTranslator(), data);
    }

    @Override
    public void destroy() {
        disruptor.shutdown();
    }

    @Override
    public void afterPropertiesSet() {
        start();
    }
}