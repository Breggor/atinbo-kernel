package com.atinbo.mvc.resolver;

import com.alibaba.fastjson.JSON;
import com.atinbo.mvc.user.SessionUser;

import javax.servlet.http.HttpServletRequest;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;

public class SessionUserResolver {
    public static final String SESSION_USER = "X-GW-SESSION-USER";
    public static final String SESSION_USER_ENCODED = "X-GW-SESSION-USER-ENCODED";


    public static SessionUser getSessionUserFromGateway(HttpServletRequest request) throws UserNotFoundException {
        return (SessionUser) getSessionUserFromGateway(request, SessionUser.class);
    }

    public static <T> T getSessionUserFromGateway(HttpServletRequest request, Class<T> clazz) throws UserNotFoundException {
        String encoded = request.getHeader(SESSION_USER_ENCODED);
        String sessionToken;
        if (null != encoded && !"".equals(encoded)) {
            try {
                sessionToken = URLDecoder.decode(encoded, "UTF-8");
                return JSON.parseObject(sessionToken, clazz);
            } catch (UnsupportedEncodingException ex) {
            }
        }

        sessionToken = request.getHeader(SESSION_USER);
        if (null == sessionToken || "".equals(sessionToken)) {
            throw new UserNotFoundException("[X-GW-SESSION-USER] -- not found!");
        } else {
            return JSON.parseObject(sessionToken, clazz);
        }
    }
}
