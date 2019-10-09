package pers.lyks.xar.core;

import com.jayway.jsonpath.Configuration;
import com.jayway.jsonpath.DocumentContext;
import com.jayway.jsonpath.JsonPath;
import com.jayway.jsonpath.ParseContext;
import lombok.extern.slf4j.Slf4j;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.net.URL;
import java.nio.charset.Charset;
import java.util.List;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Supplier;

/**
 * @author lawyerance
 * @version 1.0 2019-10-02
 */
@Slf4j
public class JsonReader {
    private final Configuration conf;
    private ParseContext context;
    private JsonInvoke invoke;

    public JsonReader(Configuration conf, JsonInvoke invoke) {
        this.conf = conf;
        this.context = JsonPath.using(conf);
        this.invoke = invoke;
    }

    public <T> T read(String json, Class<T> clazz) {
        return read(clazz, () -> context.parse(json));
    }

    public <T> T read(InputStream is, Class<T> clazz) {
        return read(clazz, () -> context.parse(is));
    }

    public <T> T read(InputStream is, Charset charset, Class<T> clazz) {
        return read(clazz, () -> context.parse(is, charset.name()));
    }

    private <S, T> T read(Class<T> clazz, Supplier<DocumentContext> supplier) {
        BeanMetadata<T> read = PropertyCache.read(clazz);
        DocumentContext context = supplier.get();
        return this.invoke.deserialize(context, read);
    }
}
