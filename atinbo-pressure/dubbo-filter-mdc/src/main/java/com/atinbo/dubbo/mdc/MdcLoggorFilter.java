package com.atinbo.dubbo.mdc;

import com.alibaba.fastjson.JSON;
import org.apache.dubbo.common.constants.CommonConstants;
import org.apache.dubbo.common.extension.Activate;
import org.apache.dubbo.common.utils.StringUtils;
import org.apache.dubbo.rpc.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.MDC;

import java.util.HashMap;
import java.util.Map;


/**
 * MDCFilter
 *
 * @author breggor
 */
@Activate(group = {CommonConstants.CONSUMER, CommonConstants.PROVIDER})
public class MdcLoggorFilter implements Filter {
    public static final String REQ_ID = "requestId";
    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Override
    public Result invoke(Invoker<?> invoker, Invocation invocation) throws RpcException {
        try {
            if (RpcContext.getContext().isConsumerSide()) {
                if (StringUtils.isBlank(MDC.get(REQ_ID))) {
                    logger.warn("slf4j MDC [requestId] don't setting!");
                } else {
                    RpcContext.getContext().setAttachment(REQ_ID, MDC.get(REQ_ID));
                }
            } else if (RpcContext.getContext().isProviderSide()) {
                String requestId = RpcContext.getContext().getAttachment(REQ_ID);
                if (StringUtils.isNotEmpty(requestId)) {
                    MDC.put(REQ_ID, requestId);
                }
            }
            logger.info("Dubbo MdcFilter Interface={}, Method={}, Params={}", invoker.getInterface().getName(), RpcContext.getContext().getMethodName(), JSON.toJSONString(getParamMap()));
        } catch (Exception e) {
            logger.warn("Exception in MDCFilter of service(" + invoker + " -> " + invocation + ")", e);
            throw new RpcException(e.getMessage(), e);
        }
        return invoker.invoke(invocation);
    }

    /**
     * dubbo参数
     *
     * @return
     */
    private Map<String, Object> getParamMap() {
        Map<String, Object> paramMap = new HashMap<>();
        Class<?>[] paramTypes = RpcContext.getContext().getParameterTypes();
        Object[] args = RpcContext.getContext().getArguments();
        if (paramTypes != null && paramTypes.length > 0) {
            for (int i = 0, len = paramTypes.length; i < len; i++) {
                paramMap.put(paramTypes[i].getSimpleName(), args[i]);
            }
        }
        return paramMap;
    }
}