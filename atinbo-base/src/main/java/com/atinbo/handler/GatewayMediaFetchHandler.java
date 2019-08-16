//
// Source status recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.atinbo.handler;

import com.atinbo.context.ThreadLocalContext;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.StringUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.Map;

@Slf4j
public class GatewayMediaFetchHandler extends Handler {

    public static final String GW_REQUEST_ID = "X-GW-REQUEST-ID";
    public static final String GW_SESSION_USER = "X-GW-SESSION-USER";
    public static final String GW_SESSION_USER_ENCODED = "X-GW-SESSION-USER-ENCODED";

    public GatewayMediaFetchHandler() {
    }

    @Override
    public void handle(String url, HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) throws Exception {
        String reqId = httpServletRequest.getHeader(GW_REQUEST_ID);
        String user = httpServletRequest.getHeader("X-GW-SESSION-USER");
        String userEnc = httpServletRequest.getHeader("X-GW-SESSION-USER-ENCODED");
        log.debug("request header param, {}:{}, {}:{}, {}:{}", GW_REQUEST_ID, reqId, GW_SESSION_USER, user, GW_SESSION_USER_ENCODED, userEnc);

        Map<String, String> headerMap = new HashMap(2);
        if (!StringUtils.isEmpty(reqId)) {
            headerMap.put(GW_REQUEST_ID, reqId);
        }

        if (!StringUtils.isEmpty(user)) {
            headerMap.put(GW_SESSION_USER, user);
        }

        if (!StringUtils.isEmpty(userEnc)) {
            headerMap.put(GW_SESSION_USER_ENCODED, userEnc);
        }

        ThreadLocalContext.GLOBAL_THREAD_LOCAL.set(headerMap);
        try {
            super.next.handle(url, httpServletRequest, httpServletResponse);
        } finally {
            ThreadLocalContext.GLOBAL_THREAD_LOCAL.remove();
        }
    }
}
