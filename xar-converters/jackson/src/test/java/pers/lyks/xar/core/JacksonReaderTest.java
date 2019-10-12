package pers.lyks.xar.core;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Assert;
import org.junit.jupiter.api.Test;
import pers.lyks.xar.bejson.BejsonAnnotation;
import pers.lyks.xar.bejson.BejsonProperty;
import pers.lyks.xar.jackson.JacksonParser;

/**
 * @author lawyerance
 * @version 1.0 2019-10-09
 */
class JacksonReaderTest extends AbstractJsonResolve {

    private static ObjectMapper objectMapper = new ObjectMapper();

    private static JsonResolve resolve;

    @Override
    protected void configuration() {
        // configuration
        objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);


        resolve = new JsonResolve(configurationBuilder.build(), new JacksonParser(objectMapper));
    }

    @Test
    void readAnnotation() {
        Assert.assertFalse(XarFactory.contains(BejsonAnnotation.class));
        XarFactory.registerByAnnotation(BejsonAnnotation.class);
        Assert.assertTrue(XarFactory.contains(BejsonAnnotation.class));

        BejsonAnnotation bejsonAnnotation = resolve.read(is, BejsonAnnotation.class);
        assertObjectEquals(bejsonAnnotation);
    }

    @Test
    void readProperties() {
        Assert.assertFalse(XarFactory.contains(BejsonProperty.class));
        XarFactory.registerByProperty(BejsonProperty.class, prop);
        Assert.assertTrue(XarFactory.contains(BejsonProperty.class));

        BejsonProperty bejson = resolve.read(is, BejsonProperty.class);

        assertObjectEquals(bejson);
    }
}