package pers.lyks.xar.core;

import com.alibaba.fastjson.parser.ParserConfig;
import org.junit.Assert;
import org.junit.jupiter.api.Test;
import pers.lyks.xar.bejson.BejsonAnnotation;
import pers.lyks.xar.bejson.BejsonProperty;
import pers.lyks.xar.fastjson.FastjsonParser;

/**
 * @author lawyerance
 * @version 1.0 2019-10-09
 */
class FastjsonReaderTest extends AbstractJsonResolve {

    private static ParserConfig parserConfig;
    private static JsonResolve resolve;

    @Override
    protected void configuration() {
        // configuration
        parserConfig = ParserConfig.getGlobalInstance();

        resolve = new JsonResolve(configurationBuilder.build(), new FastjsonParser(parserConfig));
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