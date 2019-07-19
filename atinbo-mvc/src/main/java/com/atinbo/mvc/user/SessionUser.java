package com.atinbo.mvc.user;

import lombok.Data;
import lombok.experimental.Accessors;

import java.util.Date;

/**
 * 当前会话用户
 *
 * @author breggor
 */
@Data
@Accessors(chain = true)
public class SessionUser extends AbstractOperator {
    public static final SessionUser ANONYMOUS = new SessionUser().setUserName("anonymous").setUserId(-1L);

    private Long userId;
    private String userName;
    private Date lastLoginTime;
    private String extras;
}
