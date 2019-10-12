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
package pers.lyks.xar.fastjson;

import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.parser.ParserConfig;
import com.jayway.jsonpath.DocumentContext;
import pers.lyks.xar.XarException;
import pers.lyks.xar.core.BeanMetadata;
import pers.lyks.xar.core.FieldMetadata;
import pers.lyks.xar.core.Parser;

/**
 * @author lawyerance
 * @version 1.0 2019-10-10
 */
public class FastjsonParser implements Parser {
    private ParserConfig parserConfig;

    public FastjsonParser(ParserConfig parserConfig) {
        this.parserConfig = parserConfig;
    }

    @Override
    public <T> T deserialize(DocumentContext context, BeanMetadata<T> t) throws XarException {
        JSONObject jsonObject = new JSONObject();
        for (FieldMetadata f : t.getFields()) {
            Object v = context.read(f.getJsonPath());
            if (v != null) {
                jsonObject.put(f.getFieldName(), v);
            }
        }
        // There is a defect that the string conversion time is invalid whiling directly converting 'JSONObject' into java class
        return JSONObject.parseObject(jsonObject.toJSONString(), t.getClazz(), parserConfig);
    }
}
