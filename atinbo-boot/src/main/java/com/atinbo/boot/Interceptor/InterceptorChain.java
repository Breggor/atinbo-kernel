package com.atinbo.boot.Interceptor;

/**
 * 拦截器链
 *
 * @author breggor
 */
public class InterceptorChain {
    protected Interceptor head;
    protected Interceptor tail;

    public InterceptorChain() {
    }

    public InterceptorChain add(Interceptor interceptor) {
        if (this.head == null) {
            this.head = interceptor;
        }

        interceptor.prev = this.tail;
        if (this.tail != null) {
            this.tail.next = interceptor;
        }

        this.tail = interceptor;
        return this;
    }
}
