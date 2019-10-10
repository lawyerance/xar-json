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
        if (source.isAssignableFrom(Boolean.class)) {
            return BooleanNode.valueOf((Boolean) obj);
        }

        // number
        if (source.isAssignableFrom(Integer.class)) {
            return new IntNode((int) obj);
        }

        if (source.isAssignableFrom(Long.class)) {
            return new LongNode((long) obj);
        }
        if (source.isAssignableFrom(Short.class)) {
            return new ShortNode((short) obj);
        }
        if (source.isAssignableFrom(Float.class)) {
            return new FloatNode((float) obj);
        }
        if (source.isAssignableFrom(Double.class)) {
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
        System.out.println(source);
        return NullNode.getInstance();
    }
}
