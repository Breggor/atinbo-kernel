package com.atinbo.support.core.framework.spring;

import com.atinbo.support.core.model.PageVO;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

/**
 * 设置分页策略
 * <p>
 * application.xml 配置
 * page.strategy.enabled=true
 * page.strategy.value=1
 *
 * @author Breggor
 */
@Configuration
public class PageStrategyConfiguration {


    /**
     * @see PageVO
     * 设置page字段显示策略：0）data字段，1）rows字段
     */
    @Bean
    @ConditionalOnProperty(prefix = "page.strategy", name = "enabled", havingValue = "true", matchIfMissing = false)
    public WebMvcConfigurer pageStrategyConfigurer(Environment environment) {
        return new WebMvcConfigurerAdapter() {
            @Override
            public void addInterceptors(InterceptorRegistry registry) {
                String strategy = environment.getProperty("page.strategy.value", "0");
                PageVO.setViewStrategy(Integer.parseInt(strategy));
                super.addInterceptors(registry);
            }
        };
    }

}
