package com.atinbo.mvc.user;

import lombok.Getter;
import lombok.Setter;

/**
 * 操作人
 *
 * @author breggor
 */
@Setter
@Getter
public abstract class AbstractOperator {
    /**
     * 操作人Id
     */
    private Long operatorId;

    /**
     * 操作人名
     */
    private String operatorName;
}
