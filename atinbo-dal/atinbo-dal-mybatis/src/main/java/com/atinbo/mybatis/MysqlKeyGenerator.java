package com.atinbo.mybatis;

import com.baomidou.mybatisplus.core.incrementer.IKeyGenerator;

/**
 * CREATE FUNCTION IF NOT EXISTS next_val(pk_name varchar(50)) RETURNS BIGINT(20)
 * BEGIN
 *   DECLARE maxId BIGINT(20);
 *   SET maxId = 0;
 *   SELECT next_val INTO maxId FROM ls_sequence WHERE sequence_name = pk_name;
 *   SET maxId = maxId + 1;
 *   UPDATE ls_sequence SET next_val = maxId WHERE sequence_name = pk_name;
 *   RETURN maxId;
 * END
 *
 * mysql key 生成器
 * @author zenghao
 * @date 2019-11-06
 */
public class MysqlKeyGenerator implements IKeyGenerator {

    @Override
    public String executeSql(String incrementerName) {
        return "select next_val('" + incrementerName + "')";
    }
}
