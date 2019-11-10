package com.atinbo.webmvc.resolver;

import com.alibaba.fastjson.JSON;
import com.atinbo.core.model.GatewayUser;
import com.atinbo.webmvc.exceptions.UserNotFoundException;
import lombok.extern.slf4j.Slf4j;

import javax.servlet.http.HttpServletRequest;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;


/**
 * Session用户解析
 *
 * @author breggor
 */
@Slf4j
public class SessionUserResolver {
    //登录用户信息
    public static final String X_GW_SESS_USER = "X-GW-SESS-USER" ;
    //编码后登录用户信息
    public static final String X_GW_SESS_USER_ENCODED = "X-GW-SESS-USER-ENCODED" ;


    public static GatewayUser getSessionUser(HttpServletRequest request) throws UserNotFoundException {
        return (GatewayUser) getSessionUser(request, GatewayUser.class);
    }

    private static <T> T getSessionUser(HttpServletRequest request, Class<T> clazz) throws UserNotFoundException {
        String encoded = request.getHeader(X_GW_SESS_USER_ENCODED);
        String userToken;
        if (null != encoded && !"".equals(encoded)) {
            try {
                userToken = URLDecoder.decode(encoded, "UTF-8");
                return JSON.parseObject(userToken, clazz);
            } catch (UnsupportedEncodingException ex) {
                log.debug("[X-GW-SESS-USER-ENCODED] -- 解析异常" , ex);
            }
        }

        userToken = request.getHeader(X_GW_SESS_USER);
        if (null == userToken || "".equals(userToken)) {
            throw new UserNotFoundException("[X-GW-SESSION-USER] -- not found!");
        } else {
            return JSON.parseObject(userToken, clazz);
        }
    }
}
