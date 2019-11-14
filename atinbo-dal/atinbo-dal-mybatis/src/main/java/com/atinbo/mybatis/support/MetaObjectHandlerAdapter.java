package com.atinbo.mybatis.support;

import com.atinbo.common.Converts;
import com.atinbo.core.context.ThreadLocalContext;
import com.atinbo.mybatis.base.BaseEntity;
import com.baomidou.mybatisplus.core.handlers.MetaObjectHandler;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.reflection.MetaObject;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

/**
 * mybatisplus自定义填充
 *
 * @author breggor
 */
@Slf4j
public class MetaObjectHandlerAdapter implements MetaObjectHandler {

    public static final String KEY_LOGIN_USER_ID = "LOGIN_USER_ID";

    /**
     * 新增时自动填充默认数据
     *
     * @param metaObject
     */
    @Override
    public void insertFill(MetaObject metaObject) {
        if (metaObject.getOriginalObject() instanceof BaseEntity) {
            Object userId = ThreadLocalContext.get(KEY_LOGIN_USER_ID);
            Long operatorId = Converts.toLong(userId, 0L);

            LocalDateTime now = LocalDateTime.now();
            setInsertFieldValByName("createBy" , operatorId, metaObject);
            setInsertFieldValByName("updateBy" , operatorId, metaObject);
            setInsertFieldValByName("createTime" , now, metaObject);
            setInsertFieldValByName("updateTime" , now, metaObject);
            setInsertFieldValByName("isDeleted" , BaseEntity.UN_DELETED, metaObject);
        }
    }

    /**
     * 修改时自动填充默认数据
     *
     * @param metaObject
     */
    @Override
    public void updateFill(MetaObject metaObject) {
        if (metaObject.getOriginalObject() instanceof BaseEntity) {
            Object userId = ThreadLocalContext.get(KEY_LOGIN_USER_ID);
            Long operatorId = Converts.toLong(userId, 0L);

            setUpdateFieldValByName("updateBy" , operatorId, metaObject);
            setUpdateFieldValByName("updateTime" , LocalDateTime.now(), metaObject);
        }
    }

}
