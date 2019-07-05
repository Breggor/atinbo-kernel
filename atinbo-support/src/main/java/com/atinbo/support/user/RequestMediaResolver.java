//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.atinbo.support.user;

import com.atinbo.support.exceptions.RequestMediaNotFoundException;

import javax.servlet.http.HttpServletRequest;
import java.io.Serializable;

public class RequestMediaResolver implements Serializable {
    public static final String SGW_REQUEST_ID = "X-SGW-REQUEST-ID";

    public RequestMediaResolver() {
    }

    public static String getGatewayRequestId(HttpServletRequest request) throws RequestMediaNotFoundException {
        String reqId = request.getHeader("X-SGW-REQUEST-ID");
        if (null == reqId || "".equals(reqId)) {
            throw new RequestMediaNotFoundException();
        } else {
            return reqId;
        }
    }
}
