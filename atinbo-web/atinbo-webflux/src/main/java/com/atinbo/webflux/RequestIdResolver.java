package com.atinbo.webflux;

import com.atinbo.webflux.exception.RequestIdNotFoundException;
import org.springframework.http.server.reactive.ServerHttpRequest;

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

    public static String getGatewayRequestId(ServerHttpRequest request) throws RequestIdNotFoundException {
        String reqId = request.getHeaders().getFirst(GW_REQUEST_ID);
        if (null == reqId || "".equals(reqId)) {
            throw new RequestIdNotFoundException("[X-GW-REQUEST-ID] -- not found!");
        } else {
            return reqId;
        }
    }
}
