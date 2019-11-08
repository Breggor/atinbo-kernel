package com.atinbo.generate.vo;

import com.atinbo.core.model.BaseVO;
import lombok.Data;

import java.util.List;

/**
 * 类信息
 *
 * @author code-generator
 * @date 2019-8-20
 */
@Data
public class ClassInfo implements BaseVO {

    /**
     * 包名
     */
    private String packageName;
    /**
     * 作者名
     */
    private String author;
    /**
     * 表名
     */
    private String tableName;
    /**
     * 类名
     */
    private String className;
    /**
     * 类注释
     */
    private String classComment;
    /**
     * 主键信息
     */
    private FieldInfo primaryField;
    /**
     * 类所有属性信息（包括主键）
     */
    private List<FieldInfo> fieldList;

}