package com.atinbo.webmvc.validator;


/**
 * 通用验证分组
 *
 * @author breggor
 */
public interface ValidatorGroup {

    /**
     * 保存验证分组
     */
    interface Save {
    }

    /**
     * 插入验证分组
     */
    interface Insert {
    }


    /**
     * 更新验证分组
     */
    interface Update {
    }

    /**
     * 查询验证分组
     */
    interface Query {
    }


    /**
     * 删除验证分组
     */
    interface Delete {
    }
}
