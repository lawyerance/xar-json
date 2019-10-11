package pers.lyks.xar.core;

import com.jayway.jsonpath.Configuration;
import com.jayway.jsonpath.DocumentContext;
import com.jayway.jsonpath.JsonPath;
import com.jayway.jsonpath.ParseContext;
import lombok.extern.slf4j.Slf4j;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.Charset;

/**
 * Parses JSON as specified by class
 *
 * @author lawyerance
 * @version 1.0 2019-10-02
 */
@Slf4j
public class JsonResolve {
    private ParseContext context;
    private Parser invoke;

    public JsonResolve(Configuration conf, Parser invoke) {
        this.context = JsonPath.using(conf);
        this.invoke = invoke;
    }

    public <T> T read(String json, Class<T> clazz) {
        DocumentContext doc = context.parse(json);
        return read(clazz, doc);
    }

    public <T> T read(InputStream is, Class<T> clazz) {
        return read(clazz, context.parse(is));
    }

    public <T> T read(InputStream is, Charset charset, Class<T> clazz) {
        return read(clazz, context.parse(is, charset.name()));
    }

    public <T> T read(File file, Class<T> clazz) throws IOException {
        return read(clazz, context.parse(file));
    }

    private <T> T read(Class<T> clazz, DocumentContext doc) {
        BeanMetadata<T> read = XarFactory.read(clazz);
        return this.invoke.deserialize(doc, read);
    }
}
