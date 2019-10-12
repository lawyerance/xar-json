/*
 * Copyright 2019 the original author or authors.
 * <p>
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * <p>
 * http://www.apache.org/licenses/LICENSE-2.0
 * <p>
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package pers.lyks.xar.jackson;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.*;
import com.jayway.jsonpath.DocumentContext;
import lombok.extern.slf4j.Slf4j;
import net.minidev.json.JSONArray;
import pers.lyks.xar.XarException;
import pers.lyks.xar.core.BeanMetadata;
import pers.lyks.xar.core.FieldMetadata;
import pers.lyks.xar.core.Parser;

import java.io.IOException;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Map;

/**
 * @author lawyerance
 * @version 1.0 2019-10-03
 */
@Slf4j
public class JacksonParser implements Parser {


    private ObjectMapper objectMapper;

    public JacksonParser(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }

    @Override
    public <T> T deserialize(DocumentContext context, BeanMetadata<T> beanMetadata) throws XarException {
        ObjectNode rootNode = objectMapper.createObjectNode();
        for (FieldMetadata f : beanMetadata.getFields()) {
            Object v = context.read(f.getJsonPath());
            if (v != null) {
                rootNode.set(f.getFieldName(), node(v));
            }
        }
        try {
            return objectMapper.readValue(objectMapper.treeAsTokens(rootNode), beanMetadata.getClazz());
        } catch (IOException e) {
            String message = String.format("read value from node [ %s ] error according to class %s", rootNode, beanMetadata.getClazz());
            throw new XarException(message, e);
        }
    }

    private JsonNode node(Object obj) {
        Class<?> source = obj.getClass();
        if (source.isAssignableFrom(Boolean.class) || source.isAssignableFrom(boolean.class)) {
            return BooleanNode.valueOf((Boolean) obj);
        }

        // number
        if (source.isAssignableFrom(Integer.class) || source.isAssignableFrom(int.class)) {
            return new IntNode((int) obj);
        }

        if (source.isAssignableFrom(Long.class) || source.isAssignableFrom(long.class)) {
            return new LongNode((long) obj);
        }
        if (source.isAssignableFrom(Short.class) || source.isAssignableFrom(short.class)) {
            return new ShortNode((short) obj);
        }
        if (source.isAssignableFrom(Float.class) || source.isAssignableFrom(float.class)) {
            return new FloatNode((float) obj);
        }
        if (source.isAssignableFrom(Double.class) || source.isAssignableFrom(double.class)) {
            return new DoubleNode((double) obj);
        }
        if (source.isAssignableFrom(BigInteger.class)) {
            return new BigIntegerNode((BigInteger) obj);
        }
        if (source.isAssignableFrom(BigDecimal.class)) {
            return new DecimalNode((BigDecimal) obj);
        }

        if (source.isAssignableFrom(String.class)) {
            return new TextNode((String) obj);
        }

        // list
        if (source.isAssignableFrom(JSONArray.class)) {
            ArrayNode arrayNode = objectMapper.createArrayNode();
            for (Object o : (JSONArray) obj) {
                arrayNode.add(node(o));
            }
            return arrayNode;
        }

        // object
        if (obj instanceof Map) {
            Map<String, Object> map = (Map<String, Object>) obj;
            ObjectNode objectNode = objectMapper.createObjectNode();
            map.forEach((k, v) -> objectNode.set(k, node(v)));
            return objectNode;
        }
        return NullNode.getInstance();
    }
}
