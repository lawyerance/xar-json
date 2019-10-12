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
package pers.lyks.xar.gson;

import com.google.gson.*;
import com.jayway.jsonpath.DocumentContext;
import net.minidev.json.JSONArray;
import pers.lyks.xar.XarException;
import pers.lyks.xar.core.BeanMetadata;
import pers.lyks.xar.core.FieldMetadata;
import pers.lyks.xar.core.Parser;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Map;

/**
 * @author lawyerance
 * @version 1.0 2019-10-11
 */
public class GsonParser implements Parser {
    private Gson gson;

    public GsonParser(Gson gson) {
        this.gson = gson;
    }

    @Override
    public <T> T deserialize(DocumentContext context, BeanMetadata<T> t) throws XarException {
        JsonObject object = new JsonObject();
        for (FieldMetadata f : t.getFields()) {
            Object v = context.read(f.getJsonPath());
            if (v != null) {
                object.add(f.getFieldName(), element(v));
            }
        }
        return gson.fromJson(object, t.getClazz());
    }

    private JsonElement element(Object obj) {
        Class<?> source = obj.getClass();
        if (source.isAssignableFrom(Boolean.class) || source.isAssignableFrom(boolean.class)) {
            return new JsonPrimitive((Boolean) obj);
        }

        // number
        if (source.isAssignableFrom(Integer.class) || source.isAssignableFrom(int.class)) {
            return new JsonPrimitive((Integer) obj);
        }

        if (source.isAssignableFrom(Long.class) || source.isAssignableFrom(long.class)) {
            return new JsonPrimitive((Long) obj);
        }
        if (source.isAssignableFrom(Short.class) || source.isAssignableFrom(short.class)) {
            return new JsonPrimitive((Short) obj);
        }
        if (source.isAssignableFrom(Float.class) || source.isAssignableFrom(float.class)) {
            return new JsonPrimitive((Float) obj);
        }
        if (source.isAssignableFrom(Double.class) || source.isAssignableFrom(double.class)) {
            return new JsonPrimitive((Double) obj);
        }
        if (source.isAssignableFrom(BigInteger.class)) {
            return new JsonPrimitive((BigInteger) obj);
        }
        if (source.isAssignableFrom(BigDecimal.class)) {
            return new JsonPrimitive((BigDecimal) obj);
        }

        if (source.isAssignableFrom(String.class)) {
            return new JsonPrimitive((String) obj);
        }

        // list
        if (source.isAssignableFrom(JSONArray.class)) {
            JsonArray jsonArray = new JsonArray();
            for (Object o : (JSONArray) obj) {
                jsonArray.add(element(o));
            }
            return jsonArray;
        }

        // object
        if (obj instanceof Map) {
            Map<String, Object> map = (Map<String, Object>) obj;
            JsonObject jsonObject = new JsonObject();
            map.forEach((k, v) -> jsonObject.add(k, element(v)));
            return jsonObject;
        }

        return JsonNull.INSTANCE;
    }
}
