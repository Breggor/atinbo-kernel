package com.atinbo.core.utils;

import com.atinbo.core.spring.SpringContextHolder;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;

/**
 * messageSource工具类
 *
 * @author breggor
 */
public class MessageSourceUtil {

    /**
     * 根据消息键和参数 获取消息 委托给spring messageSource
     *
     * @param code 消息键
     * @return 获取国际化值
     */
    public static String message(String code) {
        MessageSource messageSource = SpringContextHolder.getBean(MessageSource.class);
        return messageSource.getMessage(code, null, LocaleContextHolder.getLocale());
    }

    /**
     * 根据消息键和参数 获取消息 委托给spring messageSource
     *
     * @param code 消息键
     * @param args 数组参数
     * @return 获取国际化值
     */
    public static String message(String code, Object... args) {
        MessageSource messageSource = SpringContextHolder.getBean(MessageSource.class);
        return messageSource.getMessage(code, args, LocaleContextHolder.getLocale());
    }

    /**
     * 根据消息键和参数 获取消息 委托给spring messageSource
     *
     * @param code           消息键
     * @param args           数组参数
     * @param defaultMessage 没有设置key的时候的默认值
     * @return 获取国际化翻译值
     */
    public static String message(String code, Object[] args, String defaultMessage) {
        MessageSource messageSource = SpringContextHolder.getBean(MessageSource.class);
        return messageSource.getMessage(code, args, defaultMessage, LocaleContextHolder.getLocale());
    }
}