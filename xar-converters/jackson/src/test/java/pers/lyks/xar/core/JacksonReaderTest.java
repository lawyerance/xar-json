package pers.lyks.xar.core;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Assert;
import org.junit.jupiter.api.Test;
import pers.lyks.xar.bejson.Bejson;
import pers.lyks.xar.jackson.JacksonParser;

/**
 * @author lawyerance
 * @version 1.0 2019-10-09
 */
class JacksonReaderTest extends AbstractJsonResolve {

    private static ObjectMapper objectMapper = new ObjectMapper();


    @Override
    protected void configuration() {
        // configuration
        objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
    }

    @Test
    void readAnnotation() {
        Assert.assertFalse(XarFactory.contains(Bejson.class));
        XarFactory.registerByAnnotation(Bejson.class);
        Assert.assertTrue(XarFactory.contains(Bejson.class));

        JsonResolve reader = new JsonResolve(configurationBuilder.build(), new JacksonParser(objectMapper));
        Bejson bejson = reader.read(is, Bejson.class);
        assertObjectEquals(bejson);
    }

    @Test
    void readProperties() {
        Assert.assertFalse(XarFactory.contains(Bejson.class));
        XarFactory.registerByProperty(Bejson.class, prop);
        Assert.assertTrue(XarFactory.contains(Bejson.class));

        JsonResolve reader = new JsonResolve(configurationBuilder.build(), new JacksonParser(objectMapper));
        Bejson bejson = reader.read(is, Bejson.class);

        assertObjectEquals(bejson);
    }
}