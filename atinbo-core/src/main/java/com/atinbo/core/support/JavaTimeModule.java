package com.atinbo.core.support;

import com.atinbo.core.utils.Java8DateUtil;
import com.fasterxml.jackson.databind.module.SimpleModule;
import com.fasterxml.jackson.datatype.jsr310.PackageVersion;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateDeserializer;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateSerializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalTimeSerializer;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

/**
 * java 8 时间默认序列化
 *
 * @author breggor
 */
public class JavaTimeModule extends SimpleModule {

    public JavaTimeModule() {
        super(PackageVersion.VERSION);
        this.addDeserializer(LocalDateTime.class, new LocalDateTimeDeserializer(Java8DateUtil.DATETIME_FORMAT));
        this.addDeserializer(LocalDate.class, new LocalDateDeserializer(Java8DateUtil.DATE_FORMAT));
        this.addDeserializer(LocalTime.class, new LocalTimeDeserializer(Java8DateUtil.TIME_FORMAT));
        this.addSerializer(LocalDateTime.class, new LocalDateTimeSerializer(Java8DateUtil.DATETIME_FORMAT));
        this.addSerializer(LocalDate.class, new LocalDateSerializer(Java8DateUtil.DATE_FORMAT));
        this.addSerializer(LocalTime.class, new LocalTimeSerializer(Java8DateUtil.TIME_FORMAT));
    }

}
