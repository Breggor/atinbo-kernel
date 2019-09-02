package com.atinbo.generate.mapper;

import com.atinbo.generate.model.ColumnInfo;
import com.atinbo.generate.model.TableInfo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 表查询接口
 * @author code-generator
 * @date 2019-8-20
 */
@Mapper
public interface GenerateMapper {

    /**
     * 查询数据库中所有表
     *
     * @return 数据库表集合
     */
    List<TableInfo> selectTableList();

    /**
     * 查询数据库中所有表
     *
     * @return 数据库表集合
     */
    TableInfo selectTableInfo(@Param("tableName") String tableName);



    /**
     * 根据表名查询表中所有列
     * @param tableName
     * @return
     */
    List<ColumnInfo> selectColumnList(@Param("tableName") String tableName);
}
