package com.nebula.common.json.config;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateDeserializer;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateSerializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalTimeSerializer;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Locale;
import java.util.TimeZone;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.jackson.Jackson2ObjectMapperBuilderCustomizer;
import org.springframework.boot.autoconfigure.jackson.JacksonAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Primary;
import org.springframework.http.converter.json.Jackson2ObjectMapperBuilder;

/**
 * Jackson配置
 *
 * @author KING
 */
@Slf4j
@ConditionalOnClass(ObjectMapper.class)
@AutoConfiguration(before = JacksonAutoConfiguration.class)
public class JacksonConfig {

    public static final String DATETIME_FORMATTER_SLASH = "yyyy/MM/dd HH:mm:ss";

    public static final String DATE_FORMATTER_SLASH = "yyyy/MM/dd";

    public static final String TIME_FORMATTER_SLASH = "HH:mm:ss";

    /**
     * Jackson全局转化long类型为String，解决jackson序列化时long类型缺失精度问题
     *
     * @return Jackson2ObjectMapperBuilderCustomizer 注入的对象
     */
    @Bean
    @ConditionalOnMissingBean
    public Jackson2ObjectMapperBuilderCustomizer jackson2ObjectMapperBuilderCustomizer() {
        return builder -> {
            JavaTimeModule javaTimeModule = new JavaTimeModule();
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern(DATETIME_FORMATTER_SLASH);
            javaTimeModule.addSerializer(LocalDateTime.class, new LocalDateTimeSerializer(formatter));
            javaTimeModule.addDeserializer(LocalDateTime.class, new LocalDateTimeDeserializer(formatter));
            builder.modules(javaTimeModule);
            builder.locale(Locale.CHINA);
            builder.timeZone(TimeZone.getTimeZone(ZoneId.systemDefault()));
            builder.simpleDateFormat(DATETIME_FORMATTER_SLASH);
            // long 转换为字符串
            builder.serializerByType(BigInteger.class, ToStringSerializer.instance);
            builder.serializerByType(BigDecimal.class, ToStringSerializer.instance);
            builder.serializerByType(Long.TYPE, ToStringSerializer.instance);
            builder.serializerByType(long.class, ToStringSerializer.instance);
            builder.serializerByType(Long.class, ToStringSerializer.instance);
            log.info("初始化 jackson 配置");
        };
    }

    @Bean
    @Primary
    @ConditionalOnMissingBean(ObjectMapper.class)
    public ObjectMapper jacksonObjectMapper(Jackson2ObjectMapperBuilder builder) {
        ObjectMapper objectMapper = builder.createXmlMapper(false).build();
        objectMapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
        JavaTimeModule javaTimeModule = new JavaTimeModule();
        javaTimeModule.addSerializer(LocalDateTime.class,
            new LocalDateTimeSerializer(DateTimeFormatter.ofPattern(DATETIME_FORMATTER_SLASH)));
        javaTimeModule.addDeserializer(LocalDateTime.class,
            new LocalDateTimeDeserializer(DateTimeFormatter.ofPattern(DATETIME_FORMATTER_SLASH)));
        javaTimeModule.addSerializer(LocalDate.class,
            new LocalDateSerializer(DateTimeFormatter.ofPattern(DATE_FORMATTER_SLASH)));
        javaTimeModule.addDeserializer(LocalDate.class,
            new LocalDateDeserializer(DateTimeFormatter.ofPattern(DATE_FORMATTER_SLASH)));
        javaTimeModule.addSerializer(LocalTime.class,
            new LocalTimeSerializer(DateTimeFormatter.ofPattern(TIME_FORMATTER_SLASH)));
        javaTimeModule.addDeserializer(LocalTime.class,
            new LocalTimeDeserializer(DateTimeFormatter.ofPattern(TIME_FORMATTER_SLASH)));
        objectMapper.registerModule(javaTimeModule);
        objectMapper.configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false);
        return objectMapper;
    }
}