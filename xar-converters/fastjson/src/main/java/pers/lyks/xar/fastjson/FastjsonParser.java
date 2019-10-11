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
