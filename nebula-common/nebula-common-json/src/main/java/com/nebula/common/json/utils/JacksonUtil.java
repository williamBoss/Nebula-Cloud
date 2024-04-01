package com.nebula.common.json.utils;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.PrettyPrinter;
import com.fasterxml.jackson.core.util.DefaultIndenter;
import com.fasterxml.jackson.core.util.DefaultPrettyPrinter;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.MapperFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.fasterxml.jackson.databind.SerializationConfig;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.nebula.common.json.handler.JsonSerializerHandler.CustomizeNullJsonSerializer;
import com.nebula.common.json.handler.JsonSerializerHandler.SerializerModifier;
import java.util.List;
import java.util.Map;
import lombok.extern.slf4j.Slf4j;

/**
 * Jackson工具类
 *
 * @author KING
 */
@Slf4j
public class JacksonUtil {

    /**
     * 实例化ObjectMapper对象
     */
    private static final ObjectMapper OBJECT_MAPPER = new ObjectMapper();

    static {
        // 序列化和反序列化null转为空
        OBJECT_MAPPER.setSerializerFactory(
            OBJECT_MAPPER.getSerializerFactory().withSerializerModifier(new SerializerModifier()));
        SerializerProvider serializerProvider = OBJECT_MAPPER.getSerializerProvider();
        serializerProvider.setNullValueSerializer(new CustomizeNullJsonSerializer.NullObjectJsonSerializer());
        OBJECT_MAPPER
            // 设置允许序列化空的实体类（否则会抛出异常）
            .disable(SerializationFeature.FAIL_ON_EMPTY_BEANS)
            // 设置 把java.util.Date, Calendar输出为数字（时间戳）
            .disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS)
            // 设置在遇到未知属性的时候不抛出异常
            .disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES)
            // 强制JSON 空字符串("")转换为null对象值
            .enable(DeserializationFeature.ACCEPT_EMPTY_STRING_AS_NULL_OBJECT)
            .enable(DeserializationFeature.ACCEPT_EMPTY_ARRAY_AS_NULL_OBJECT)
            // 设置数字丢失精度问题
            .enable(DeserializationFeature.USE_BIG_DECIMAL_FOR_FLOATS)
            // 设置没有引号的字段名
            .enable(JsonParser.Feature.ALLOW_UNQUOTED_FIELD_NAMES)
            // 设置允许单引号
            .enable(JsonParser.Feature.ALLOW_SINGLE_QUOTES)
            // 排序
            .enable(MapperFeature.SORT_PROPERTIES_ALPHABETICALLY)
            .configure(SerializationFeature.ORDER_MAP_ENTRIES_BY_KEYS, true)
            // 设置接受只有一个元素的数组的反序列化
            .configure(DeserializationFeature.ACCEPT_SINGLE_VALUE_AS_ARRAY, true);
    }

    /**
     * 私有化构造器
     */
    private JacksonUtil() {
    }

    /**
     * 对象转json字符串
     *
     * @param obj obj
     * @return
     */
    public static String toJsonString(Object obj) {
        try {
            return OBJECT_MAPPER.writeValueAsString(obj);
        } catch (JsonProcessingException e) {
            log.error(e.getMessage());
            return "";
        }
    }

    /**
     * 对象转json字符串
     *
     * @param obj obj
     * @return
     */
    public static String toJsonPrettyString(Object obj) {
        try {
            SerializationConfig config = OBJECT_MAPPER.getSerializationConfig();
            PrettyPrinter prettyPrinter = config.getDefaultPrettyPrinter();
            DefaultPrettyPrinter def = (DefaultPrettyPrinter) prettyPrinter;
            DefaultPrettyPrinter.Indenter indented = new DefaultIndenter("   ", DefaultIndenter.SYS_LF);
            def.indentArraysWith(indented);
            def.indentObjectsWith(indented);
            ObjectWriter objectWriter = OBJECT_MAPPER.writer(def);
            return objectWriter.writeValueAsString(obj);
        } catch (JsonProcessingException e) {
            log.error(e.getMessage());
            return "";
        }
    }

    /**
     * json字符串转换bean
     *
     * @param clazz   clazz
     * @param jsonStr jsonStr
     * @return
     */
    public static <T> T parseObject(Class<T> clazz, String jsonStr) {
        try {
            return OBJECT_MAPPER.readValue(jsonStr, clazz);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * json字符串转Map
     *
     * @param jsonStr jsonStr
     * @return
     */
    @SuppressWarnings(value = "unchecked")
    public static Map<String, Object> parseObject(String jsonStr) {
        try {
            return OBJECT_MAPPER.readValue(jsonStr, Map.class);
        } catch (JsonProcessingException e) {
            log.error(e.getMessage(), e);
            throw new RuntimeException(e);
        }
    }

    /**
     * json字符串转Map
     *
     * @param jsonStr jsonStr
     * @return
     */
    @SuppressWarnings(value = "unchecked")
    public static Map<String, Object> parseObject(Object jsonStr) {
        return OBJECT_MAPPER.convertValue(jsonStr, Map.class);
    }

    /**
     * 删除key
     *
     * @param jsonStr   json 字符
     * @param removeKey key
     * @return
     */
    public static String removeKey(Object jsonStr, String... removeKey) {
        Map<String, Object> map = parseObject(jsonStr);
        for (String key : removeKey) {
            map.remove(key);
        }
        return toJsonString(map);
    }

    /**
     * json字符串转对象集合
     *
     * @param jsonStr jsonStr
     * @param clazz   clazz
     * @return
     */
    public static <T> List<T> parseArray(String jsonStr, Class<T> clazz) {
        JavaType javaType = OBJECT_MAPPER.getTypeFactory().constructCollectionType(List.class, clazz);
        try {
            return OBJECT_MAPPER.readValue(jsonStr, javaType);
        } catch (JsonProcessingException e) {
            log.error(e.getMessage());
            return null;
        }
    }
}