package com.atguigu.tingshu.common.util;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;

import java.io.IOException;
import java.math.BigDecimal;

/**
 * @description 小数保留2位返回给前端序列化器
 */
public class Decimal2Serializer extends JsonSerializer<Object> {

    /**
     * 将返回的BigDecimal保留两位小数，再返回给前端
     * @param value
     * @param jsonGenerator
     * @param serializerProvider
     * @throws IOException
     * @throws JsonProcessingException
     */
    @Override
    public void serialize(Object value, JsonGenerator jsonGenerator, SerializerProvider serializerProvider) throws IOException, JsonProcessingException {
        if (value != null) {
            BigDecimal bigDecimal = new BigDecimal(value.toString()).setScale(2,BigDecimal.ROUND_HALF_UP);
            jsonGenerator.writeString(bigDecimal.toString());
        }
    }
}