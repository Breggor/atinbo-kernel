package com.atinbo.model;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.stream.Stream;

/**
 * 分页传入参数
 *
 * @author breggor
 */
@Data
public class PageForm implements Serializable {

    public static final String REDUCE = "-";
    public static final String PLUS = "+";
    /**
     * 起始页
     */
    @ApiModelProperty(value = "起始页", notes = "默认起始页1", example = "1")
    private Integer page = 1;

    /**
     * 每页行数
     */
    @ApiModelProperty(value = "每页行数", notes = "默认10条记录", example = "10")
    private Integer size = 10;

    /**
     * 排序列
     */
    @ApiModelProperty(value = "排序:+为正序[asc], -为反序[desc],多字段排序用','分隔，如: sortBy=+name,-age", notes = "+为正序[asc], -为反序[desc],多字段排序用','分隔，如: sortBy=+name,-age", example = "+id")
    private String sortBy;

    /**
     * 转换
     *
     * @return PageParam
     */
    public PageParam toPageParam() {
        PageParam pageParam = new PageParam();
        if (this.getPage() >= 0) {
            pageParam.setPage(this.getPage());
        }
        if (this.getSize() > 0) {
            pageParam.setSize(this.getSize());
        }
        if (this.getSortBy() != null && getSortBy().trim().length() > 0) {
            SortInfo sortInfo = new SortInfo();
            String[] sorts = getSortBy().split(",");
            Stream.of(sorts).forEach(s -> {
                if (s.startsWith(REDUCE)) {
                    sortInfo.addField(SortDir.DESC, s.substring(1));
                } else {
                    if (s.startsWith(PLUS)) {
                        s = s.substring(1);
                    }
                    sortInfo.addField(SortDir.ASC, s);
                }
            });
            pageParam.setSort(sortInfo);
        }
        return pageParam;
    }

}