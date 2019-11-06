package com.atinbo.mybatis;

import com.baomidou.mybatisplus.core.config.GlobalConfig;
import com.baomidou.mybatisplus.extension.plugins.PaginationInterceptor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author zenghao
 * @date 2019-11-06
 */
@Configuration
public class MybatisPlusAutoConfig {

    @Bean
    public PaginationInterceptor paginationInterceptor() {
        return new PaginationInterceptor();
    }

    @Bean
    public GlobalConfig globalConfiguration() {
        GlobalConfig conf = new GlobalConfig();

//        CREATE FUNCTION next_val(pk_name varchar(50)) RETURNS BIGINT(20)
//        BEGIN
//            DECLARE maxId BIGINT(20);
//            SET maxId = 0;
//            SELECT `next_val` INTO maxId FROM `id_seq` WHERE `pk_name` = pk_name for update;
//            SET maxId = maxId + 1;
//            INSERT INTO `id_seq`(`pk_name`,`next_val`) VALUES (pk_name, maxId) ON DUPLICATE KEY UPDATE `next_val` = maxId;
//            RETURN maxId;
//        END

        conf.setDbConfig(new GlobalConfig.DbConfig().setKeyGenerator(new MysqlKeyGenerator()));
        return conf;
    }
}
