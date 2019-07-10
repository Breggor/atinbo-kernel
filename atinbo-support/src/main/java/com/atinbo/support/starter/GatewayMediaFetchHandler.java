//
// Source status recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.atinbo.support.starter;

import org.springframework.util.StringUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.Map;

public class GatewayMediaFetchHandler extends Handler {
    public GatewayMediaFetchHandler() {
    }

    @Override
    public void handle(String s, HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) throws Exception {
        String xSgwRequestId = httpServletRequest.getHeader("X-SGW-REQUEST-ID");
        String xSgwSessionUser = httpServletRequest.getHeader("X-SGW-SESSION-USER");
        String xSgwSessionUserEncoded = httpServletRequest.getHeader("X-SGW-SESSION-USER-ENCODED");
        this.logger.debug("request header param, {}:{}, {}:{}, {}:{}", new Object[]{"X-SGW-REQUEST-ID", xSgwRequestId, "X-SGW-SESSION-USER", xSgwSessionUser, "X-SGW-SESSION-USER-ENCODED", xSgwSessionUserEncoded});
        Map<String, String> headerMap = new HashMap(2);
        if (!StringUtils.isEmpty(xSgwRequestId)) {
            headerMap.put("X-SGW-REQUEST-ID", xSgwRequestId);
        }

        if (!StringUtils.isEmpty(xSgwSessionUser)) {
            headerMap.put("X-SGW-SESSION-USER", xSgwSessionUser);
        }

        if (!StringUtils.isEmpty(xSgwSessionUserEncoded)) {
            headerMap.put("X-SGW-SESSION-USER-ENCODED", xSgwSessionUserEncoded);
        }

        ThreadLocalContext.GLOBAL_THREAD_LOCAL.set(headerMap);
        super.next.handle(s, httpServletRequest, httpServletResponse);
        ThreadLocalContext.GLOBAL_THREAD_LOCAL.remove();
    }
}
