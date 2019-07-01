package com.atinbo.support.core.framework.dubbo;

import com.alibaba.dubbo.common.json.JSON;
import com.alibaba.dubbo.rpc.*;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.MDC;

/**
 * 请求Id拦截
 */
public class RequestIdLogFilter implements Filter {

    private static final String REQUEST_ID = "requestId";
    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Override
    public Result invoke(Invoker<?> invoker, Invocation invocation) throws RpcException {

        if (RpcContext.getContext().isConsumerSide()) {
            String requestId = MDC.get(REQUEST_ID);
            RpcContext.getContext().setAttachment(REQUEST_ID, requestId);
        } else if (RpcContext.getContext().isProviderSide()) {
            try {
                String requestId = RpcContext.getContext().getAttachment(REQUEST_ID);
                if (StringUtils.isNotBlank(requestId)) {
                    MDC.put(REQUEST_ID, requestId);
                }
                Object[] args = RpcContext.getContext().getArguments();
                String argsJson = null;
                if (args != null && args.length > 0) {
                    argsJson = JSON.json(args);
                }

                logger.info("Interface={}, Method={}, Params={}", invoker.getInterface().getName(), RpcContext.getContext().getMethodName(), argsJson);
            } catch (Exception e) {
                logger.warn("Exception in RequestIdLogFilter of service(" + invoker + " -> " + invocation + ")", e);
            }
        }

        return invoker.invoke(invocation);
    }
}
