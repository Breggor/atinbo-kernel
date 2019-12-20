package com.atinbo.mybatis;

import com.atinbo.mybatis.support.MetaObjectHandlerAdapter;
import com.baomidou.mybatisplus.core.handlers.MetaObjectHandler;
import com.baomidou.mybatisplus.core.incrementer.IKeyGenerator;
import com.baomidou.mybatisplus.extension.plugins.PaginationInterceptor;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author zenghao
 * @date 2019-11-06
 */
@Configuration
public class MybatisPlusAutoConfig {

    /**
     * 分页器
     *
     * @return
     */
    @Bean
    public PaginationInterceptor paginationInterceptor() {
        return new PaginationInterceptor();
    }

    @Bean
    @ConditionalOnMissingBean
    public MetaObjectHandler metaObjectHandler() {
        return new MetaObjectHandlerAdapter();
    }

    /**
     * 主键生成器 需要数据库存在 `id_seq` 表和  next_val 函数（创建语句见注释）
     *
     * @return
     */
    @Bean
    @ConditionalOnMissingBean
    public IKeyGenerator keyGenerator() {
//        CREATE TABLE IF NOT EXISTS `id_seq`(
//            `pk_name` VARCHAR(50) NOT NULL COMMENT '主键',
//            `next_val` bigint(20) DEFAULT 0 COMMENT '值',
//            PRIMARY KEY (`pk_name`)
//        ) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='主键自增表';
//
//        CREATE FUNCTION next_val(table_key varchar(50)) RETURNS BIGINT(20)
//        BEGIN
//            DECLARE maxId BIGINT(20) DEFAULT 0;
//            SELECT `next_val` + 1 INTO maxId from `id_seq` where `pk_name` = table_key for update;
//
//        		IF maxId = 0 THEN
//        			SET maxId = 1;
//        			INSERT INTO `id_seq` (`pk_name`, `next_val`) VALUES (table_key, maxId);
//        		ELSE
//        			UPDATE `id_seq` SET `next_val` = maxId where `pk_name` = table_key;
//        		END IF;
//            RETURN maxId;
//        END
        return new MysqlKeyGenerator();
    }
}
