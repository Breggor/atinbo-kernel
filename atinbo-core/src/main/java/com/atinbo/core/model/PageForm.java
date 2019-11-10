package com.atinbo.core.model;

import com.atinbo.core.utils.StringUtil;
import com.atinbo.model.PageParam;
import com.atinbo.model.SortDir;
import com.atinbo.model.SortInfo;
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

    /**
     * 起始页
     */
    @ApiModelProperty(value = "起始页")
    private Integer page;

    /**
     * 每页行数
     */
    @ApiModelProperty(value = "每页行数")
    private Integer size;

    /**
     * 排序列
     */
    @ApiModelProperty(value = "+为正序[asc], -为反序[desc],多字段排序用','分隔，如: sortBy=+name,-age")
    private String sortBy;

    /**
     * 转换为 PageParam
     * @return
     */
    public PageParam toPageParam(){
        PageParam pageParam = new PageParam();
        if(this.getPage() != null){
            pageParam.setPage(this.getPage());
        }
        if(this.getSize() != null){
            pageParam.setSize(this.getSize());
        }
        if(this.getSortBy() != null && getSortBy().trim().length() > 0){
            SortInfo sortInfo = new SortInfo();
            String[] sorts = StringUtil.split(getSortBy(), ",");
            Stream.of(sorts).forEach(s -> {
                if(StringUtil.startsWithIgnoreCase(s,"-")){
                    sortInfo.addField(SortDir.DESC, s.substring(1));
                }else{
                    if(StringUtil.startsWithIgnoreCase(s,"+")){
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