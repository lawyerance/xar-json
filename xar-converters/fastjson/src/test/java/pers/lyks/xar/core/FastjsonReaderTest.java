package pers.lyks.xar.core;

import com.alibaba.fastjson.parser.ParserConfig;
import org.junit.Assert;
import org.junit.jupiter.api.Test;
import pers.lyks.xar.bejson.Bejson;
import pers.lyks.xar.fastjson.FastjsonParser;

/**
 * @author lawyerance
 * @version 1.0 2019-10-09
 */
class FastjsonReaderTest extends AbstractJsonResolve {

    private static ParserConfig parserConfig;


    @Override
    protected void configuration() {
        // configuration
        parserConfig = ParserConfig.getGlobalInstance();
    }

    @Test
    void readAnnotation() {
        Assert.assertFalse(XarFactory.contains(Bejson.class));
        XarFactory.registerByAnnotation(Bejson.class);
        Assert.assertTrue(XarFactory.contains(Bejson.class));

        JsonResolve reader = new JsonResolve(configurationBuilder.build(), new FastjsonParser(parserConfig));
        Bejson bejson = reader.read(is, Bejson.class);

        assertObjectEquals(bejson);
    }

    @Test
    void readProperties() {
        Assert.assertFalse(XarFactory.contains(Bejson.class));
        XarFactory.registerByProperty(Bejson.class, prop);
        Assert.assertTrue(XarFactory.contains(Bejson.class));

        JsonResolve reader = new JsonResolve(configurationBuilder.build(), new FastjsonParser(parserConfig));
        Bejson bejson = reader.read(is, Bejson.class);

        assertObjectEquals(bejson);
    }
}