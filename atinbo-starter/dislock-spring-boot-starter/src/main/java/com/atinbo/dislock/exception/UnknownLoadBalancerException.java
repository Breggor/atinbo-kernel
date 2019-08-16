package com.atinbo.dislock.exception;

/**
 * 未知负载均衡算法类型
 *
 * @author breggor
 */
public class UnknownLoadBalancerException extends RuntimeException {

    private static final long serialVersionUID = 5793677066428295913L;

    public UnknownLoadBalancerException() {
        super("未知负载均衡算法类型");
    }

}
