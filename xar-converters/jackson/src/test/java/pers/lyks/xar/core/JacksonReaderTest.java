package pers.lyks.xar.core;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.jayway.jsonpath.Configuration;
import com.jayway.jsonpath.Option;
import org.junit.Assert;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import pers.lyks.xar.annotation.Property;
import pers.lyks.xar.bejson.Bejson;
import pers.lyks.xar.jackson.JacksonParser;

import java.io.IOException;
import java.io.InputStream;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Objects;

/**
 * @author lawyerance
 * @version 1.0 2019-10-09
 */
class JacksonReaderTest extends AbstractJsonReader {

    private static ObjectMapper objectMapper = new ObjectMapper();


    @Override
    protected void configuration() {
        // configuration
        objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
    }

    @Test
    void read() {
        JsonReader reader = new JsonReader(configurationBuilder.build(), new JacksonParser(objectMapper));
        Bejson bejson = reader.read(is, Bejson.class);
        assertObjectEquals(bejson);
    }
}