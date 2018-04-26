package com.chengdu.jiq.common.utils;

import com.fasterxml.jackson.databind.AnnotationIntrospector;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.MapperFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.introspect.JacksonAnnotationIntrospector;

import java.io.IOException;
import java.text.SimpleDateFormat;

public class JsonConvertor {

    private static final ObjectMapper DEFAULT_OBJECT_MAPPER = createMapper();

    public static ObjectMapper getObjectMapper() {
        return DEFAULT_OBJECT_MAPPER;
    }

    private static ObjectMapper createMapper() {
        ObjectMapper mapper = new ObjectMapper();
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        mapper.setDateFormat(dateFormat);
        final AnnotationIntrospector primary = new JacksonAnnotationIntrospector();
        // Swagger与jaxb冲突，因此在这里关闭JAXB注解方式，直接采用Json注解，Close by Chris.qin
        mapper.setAnnotationIntrospectors(primary, primary);
        mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        mapper.configure(MapperFeature.USE_WRAPPER_NAME_AS_PROPERTY_NAME, true);
//        mapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
        return mapper;
    }

    public <T> T fromJSON(String json, Class<T> targetClass, Class<?>... elementClasses) throws IOException {
        return elementClasses == null || elementClasses.length == 0 ?
                DEFAULT_OBJECT_MAPPER.readValue(json, targetClass) :
                fromJSONToGeneric(json, targetClass, elementClasses);
    }

    private <T> T fromJSONToGeneric(String json, Class<T> targetClass, Class<?>... elementClasses) throws IOException {
        return DEFAULT_OBJECT_MAPPER.readValue(json, DEFAULT_OBJECT_MAPPER.getTypeFactory().constructParametricType(targetClass, elementClasses));
    }
}