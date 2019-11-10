package com.atinbo.webmvc.resolver;

import com.atinbo.webmvc.exceptions.RequestIdNotFoundException;

import javax.servlet.http.HttpServletRequest;
import java.io.Serializable;

/**
 * 解析请求头中请求ID
 *
 * @author breggor
 */
public class RequestIdResolver implements Serializable {
    public static final String GW_REQUEST_ID = "X-GW-REQUEST-ID" ;

    public RequestIdResolver() {
    }

    public static String getGatewayRequestId(HttpServletRequest request) throws RequestIdNotFoundException {
        String reqId = request.getHeader(GW_REQUEST_ID);
        if (null == reqId || "".equals(reqId)) {
            throw new RequestIdNotFoundException("[X-GW-REQUEST-ID] -- not found!");
        } else {
            return reqId;
        }
    }
}
