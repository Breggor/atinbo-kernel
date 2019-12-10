package com.atinbo.Interceptor;

/**
 * MVC拦截器链
 *
 * @author breggor
 */
public class InterceptorChain {

    /**
     * 链头
     */
    private Interceptor head;

    /**
     * 链尾
     */
    private Interceptor tail;

    public InterceptorChain() {
    }

    public InterceptorChain add(Interceptor itcpt) {
        if (this.head == null) {
            this.head = itcpt;
        }

        itcpt.prev = this.tail;
        if (this.tail != null) {
            this.tail.next = itcpt;
        }

        this.tail = itcpt;
        return this;
    }
}
