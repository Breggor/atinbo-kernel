package com.atinbo.generate.mapper;

import com.atinbo.generate.model.ColumnInfo;
import com.atinbo.generate.model.TableInfo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * 表查询接口
 *
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
    @Select("select TABLE_NAME as tableName, TABLE_COMMENT as tableComment, CREATE_TIME as createTime, UPDATE_TIME as updateTime" +
            " from information_schema.TABLES" +
            " where TABLE_SCHEMA = (select database())")
    List<TableInfo> selectTableList();

    /**
     * 查询数据库中所有表
     *
     * @return 数据库表集合
     */
    @Select("<script>" +
            "select TABLE_NAME as tableName, TABLE_COMMENT as tableComment, CREATE_TIME as createTime, UPDATE_TIME as updateTime" +
            " from information_schema.TABLES" +
            " where TABLE_SCHEMA = (select database())" +
            " <if test=\"tableName != null and tableName != ''\">" +
            " AND TABLE_NAME = #{tableName}" +
            " </if>" +
            "</script>")
    TableInfo selectTableInfo(@Param("tableName") String tableName);


    /**
     * 根据表名查询表中所有列
     *
     * @param tableName
     * @return
     */
    @Select("<script>" +
            "select COLUMN_NAME as columnName, COLUMN_COMMENT as columnComment, DATA_TYPE as dataType, COLUMN_KEY as columnKey" +
            " from information_schema.COLUMNS" +
            " where TABLE_SCHEMA = (select database())" +
            " <if test=\"tableName != null and tableName != ''\">" +
            " AND TABLE_NAME = #{tableName}" +
            " </if>" +
            "</script>")
    List<ColumnInfo> selectColumnList(@Param("tableName") String tableName);
}
