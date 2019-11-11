package com.atinbo.mybatis.support;

import com.atinbo.mybatis.base.BaseEntity;
import com.baomidou.mybatisplus.core.handlers.MetaObjectHandler;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.reflection.MetaObject;
import org.springframework.stereotype.Component;

import java.util.Date;

/**
 * mybatisplus自定义填充
 *
 * @author breggor
 */
@Slf4j
@Component
public class MetaObjectHandlerAdapter implements MetaObjectHandler {

    /**
     * 新增时自动填充默认数据
     *
     * @param metaObject
     */
    @Override
    public void insertFill(MetaObject metaObject) {
        if (metaObject.getOriginalObject() instanceof BaseEntity) {
            //TODO 操作者用户id
            Long operatorId = 1L;
            Date now = new Date();
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
            //TODO 操作者用户id
            Long operatorId = 1L;
            setUpdateFieldValByName("updateBy" , operatorId, metaObject);
            setUpdateFieldValByName("updateTime" , new Date(), metaObject);
        }
    }

}
