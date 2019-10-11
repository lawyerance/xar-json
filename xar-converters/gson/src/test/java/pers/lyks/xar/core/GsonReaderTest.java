package pers.lyks.xar.core;

import com.google.gson.GsonBuilder;
import org.junit.Assert;
import org.junit.jupiter.api.Test;
import pers.lyks.xar.bejson.Bejson;
import pers.lyks.xar.gson.GsonParser;

/**
 * @author lawyerance
 * @version 1.0 2019-10-11
 */
class GsonReaderTest extends AbstractJsonResolve {

    private GsonBuilder builder = new GsonBuilder();

    @Override
    protected void configuration() {
        builder.setDateFormat("yyyyMMdd HHmmss");
    }

    @Test
    void readAnnotation() {
        Assert.assertFalse(XarFactory.contains(Bejson.class));
        XarFactory.registerByAnnotation(Bejson.class);
        Assert.assertTrue(XarFactory.contains(Bejson.class));

        JsonResolve reader = new JsonResolve(configurationBuilder.build(), new GsonParser(builder.create()));
        Bejson bejson = reader.read(is, Bejson.class);
        assertObjectEquals(bejson);
    }

    @Test
    void readProperties() {
        Assert.assertFalse(XarFactory.contains(Bejson.class));
        XarFactory.registerByProperty(Bejson.class, prop);
        Assert.assertTrue(XarFactory.contains(Bejson.class));

        JsonResolve reader = new JsonResolve(configurationBuilder.build(), new GsonParser(builder.create()));
        Bejson bejson = reader.read(is, Bejson.class);

        assertObjectEquals(bejson);
    }
}