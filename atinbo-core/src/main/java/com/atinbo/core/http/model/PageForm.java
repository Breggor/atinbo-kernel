package com.atinbo.core.http.model;

import com.atinbo.common.Strings;
import org.springframework.util.StringUtils;

import java.io.Serializable;
import java.util.StringJoiner;

/**
 * 分页传入参数
 *
 * @author breggor
 */
public class PageForm implements Serializable {

    /**
     * 起始页
     */
    private Integer offset;

    /**
     * 每页行数
     */
    private Integer limit;

    /**
     * 排序列
     */
    private String orderBy;

    /**
     * 排序的方向 "desc" 或者 "asc".
     */
    private String dir = "asc";

    public Integer getOffset() {
        return offset;
    }

    public void setOffset(Integer offset) {
        this.offset = offset;
    }

    public Integer getLimit() {
        return limit;
    }

    public void setLimit(Integer limit) {
        this.limit = limit;
    }

    public void setOrderBy(String orderBy) {
        this.orderBy = orderBy;
    }

    public String getOrderBy() {
        if (StringUtils.isEmpty(orderBy)) {
            return "";
        }
        return Strings.toUnderScoreCase(orderBy) + " " + dir;
    }

    public String getDir() {
        return dir;
    }

    public void setDir(String dir) {
        this.dir = dir;
    }

    @Override
    public String toString() {
        return new StringJoiner(", ", PageForm.class.getSimpleName() + "[", "]")
                .add("offset=" + offset)
                .add("limit=" + limit)
                .add("orderBy='" + orderBy + "'")
                .add("dir='" + dir + "'")
                .toString();
    }
}
