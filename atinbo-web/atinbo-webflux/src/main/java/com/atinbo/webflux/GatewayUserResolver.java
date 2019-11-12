package com.atinbo.webflux;


import com.atinbo.core.model.GatewayUser;
import com.atinbo.webflux.exception.UserNotFoundException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.server.reactive.ServerHttpRequest;

import java.io.IOException;
import java.net.URLDecoder;


/**
 * 网关用户解析
 *
 * @author breggor
 */
@Slf4j
public class GatewayUserResolver {
    //登录用户信息
    public static final String X_GW_SESS_USER = "X-GW-SESS-USER";
    //编码后登录用户信息
    public static final String X_GW_SESS_USER_ENCODED = "X-GW-SESS-USER-ENCODED";

    private static final ObjectMapper OBJECT_MAPPER = new ObjectMapper();

    public static GatewayUser getSessionUserFromGateway(ServerHttpRequest request) throws UserNotFoundException {
        return (GatewayUser) getSessionUserFromGateway(request, GatewayUser.class);
    }

    private static <T> T getSessionUserFromGateway(ServerHttpRequest request, Class<T> clazz) throws UserNotFoundException {
        String encoded = request.getHeaders().getFirst(X_GW_SESS_USER_ENCODED);
        String userToken;
        if (null != encoded && !"".equals(encoded)) {
            try {
                userToken = URLDecoder.decode(encoded, "UTF-8");
                return OBJECT_MAPPER.readValue(userToken, clazz);
            } catch (Exception ex) {
                log.debug("[X-GW-SESS-USER-ENCODED] -- 解析异常", ex);
            }
        }

        userToken = request.getHeaders().getFirst(X_GW_SESS_USER);
        if (null == userToken || "".equals(userToken)) {
            throw new UserNotFoundException("[X-GW-SESSION-USER] -- not found!");
        }
        try {
            return OBJECT_MAPPER.readValue(userToken, clazz);
        } catch (IOException ex) {
            log.debug("[X-GW-SESSION-USER] -- 解析异常", ex);
        }
        return null;
    }
}
