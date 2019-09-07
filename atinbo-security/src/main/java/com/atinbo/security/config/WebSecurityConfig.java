package com.atinbo.security.config;


import com.atinbo.security.JwtAuthenticationEntryPoint;
import com.atinbo.security.filter.CustomUsernamePasswordProcessingFilterFilter;
import com.atinbo.security.filter.JwtTokenAuthorizationFilter;
import com.atinbo.security.jwt.JwtTokenOps;
import com.atinbo.security.service.JwtUserDetailsService;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.util.StringUtils;

import java.util.Objects;

/**
 * web权限配置类
 *
 * @author breggor
 */
@Slf4j
@Configuration
@EnableWebSecurity
@Order(2)
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    private final static String[] ALLOW_VISIT_PATH = new String[]{"/", "/*.html", "/actuator/**", "/v2/**", "/webjars/**", "/swagger-resources", "/swagger-resources/**", "/favicon.ico", "/**/*.html", "/**/*.css", "/**/*.js"};

    @Autowired
    private JwtUserDetailsService jwtUserDetailsService;

    /**
     * 多个路径逗号分隔
     */
    @Value("${security.allowPath}")
    private String allowPath;


    @Override
    protected void configure(HttpSecurity httpSecurity) throws Exception {
        String[] allowPathArr = ALLOW_VISIT_PATH;
        if (!StringUtils.isEmpty(this.allowPath)) {
            String[] arr = this.allowPath.split(",");
            allowPathArr = new String[arr.length + ALLOW_VISIT_PATH.length];

            System.arraycopy(ALLOW_VISIT_PATH, 0, allowPathArr, 0, ALLOW_VISIT_PATH.length);
            System.arraycopy(arr, 0, allowPathArr, ALLOW_VISIT_PATH.length, arr.length);
        }
        log.info("web security allow paths={}", String.join(",", allowPathArr));

        httpSecurity.cors().and()
                // 由于使用的是JWT，我们这里不需要csrf
                .csrf().disable()
                .exceptionHandling().authenticationEntryPoint(jwtAuthenticationEntryPoint()).and()
                // 基于token，所以不需要session
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS).and()
                .authorizeRequests()
                // 允许对于网站静态资源的无授权访问
                .antMatchers(allowPathArr).permitAll()
                .antMatchers(HttpMethod.OPTIONS).permitAll()
                .antMatchers(HttpMethod.POST, "/login").permitAll()
                // 除上面外的所有请求全部需要鉴权认证
                .anyRequest().authenticated();

        // 添加JWT filter
        httpSecurity.addFilterBefore(jwtTokenAuthorizationFilter(), UsernamePasswordAuthenticationFilter.class);

        httpSecurity.addFilterBefore(customUsernamePasswordProcessingFilterFilter(), UsernamePasswordAuthenticationFilter.class);
        // 禁用缓存
        httpSecurity.headers().cacheControl();
    }

    @Autowired
    public void configureAuthentication(AuthenticationManagerBuilder authenticationManagerBuilder) throws Exception {
        Objects.requireNonNull(jwtUserDetailsService, "jwtUserDetailsService为空，请项目中实现JwtUserDetailsService");

        authenticationManagerBuilder
                // 设置UserDetailsService
                .userDetailsService(this.jwtUserDetailsService)
                // 使用BCrypt进行密码的hash
                .passwordEncoder(passwordEncoder());
    }

    @Bean
    @ConditionalOnMissingBean
    public ObjectMapper objectMapper() {
        return new ObjectMapper();
    }

    @Bean
    @ConditionalOnMissingBean
    public JwtTokenOps jwtTokenOps() {
        return new JwtTokenOps();
    }

    @Bean
    @ConditionalOnMissingBean
    public JwtAuthenticationEntryPoint jwtAuthenticationEntryPoint() {
        return new JwtAuthenticationEntryPoint();
    }

    @Bean
    @ConditionalOnMissingBean
    public JwtTokenAuthorizationFilter jwtTokenAuthorizationFilter() {
        return new JwtTokenAuthorizationFilter();
    }

    @Bean
    @ConditionalOnMissingBean
    public CustomUsernamePasswordProcessingFilterFilter customUsernamePasswordProcessingFilterFilter() throws Exception {
        return new CustomUsernamePasswordProcessingFilterFilter(authenticationManager());
    }

    @Bean
    @ConditionalOnMissingBean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}