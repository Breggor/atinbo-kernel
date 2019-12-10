package com.atinbo.handler;

import lombok.extern.slf4j.Slf4j;

import javax.servlet.FilterChain;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 处理器链
 *
 * @author breggor
 */
@Slf4j
public final class HandlerChain {
    protected Handler head;
    protected Handler tail;

    public HandlerChain add(Handler handler) {
        Handler proxyHandler = new PatternProxyHandler(handler);
        if (this.head == null) {
            this.head = proxyHandler;
        }

        proxyHandler.prev = this.tail;
        if (this.tail != null) {
            this.tail.next = proxyHandler;
        }

        this.tail = proxyHandler;
        handler.prev = proxyHandler.prev;
        if (handler.prev != null) {
            ((PatternProxyHandler) (proxyHandler.prev)).target.next = proxyHandler;
        }

        return this;
    }

    public HandlerChain copyAndPushPoison(final FilterChain chain) {
        HandlerChain handlers = this.copyHandlers();
        handlers.add(new Handler() {
            @Override
            public void handle(String url, HttpServletRequest request, HttpServletResponse response) throws Exception {
                chain.doFilter(request, response);
            }
        });
        return handlers;
    }

    private HandlerChain copyHandlers() {
        HandlerChain handlers = new HandlerChain();
        handlers.head = this.head;
        PatternProxyHandler temp = new PatternProxyHandler(((PatternProxyHandler) this.tail).target);
        temp.prev = this.tail.prev;
        handlers.tail = temp;
        return handlers;
    }
}
