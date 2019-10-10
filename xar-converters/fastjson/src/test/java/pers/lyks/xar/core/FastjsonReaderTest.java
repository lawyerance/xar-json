package pers.lyks.xar.core;

import com.alibaba.fastjson.parser.ParserConfig;
import org.junit.jupiter.api.Test;
import pers.lyks.xar.bejson.Bejson;
import pers.lyks.xar.fastjson.FastjsonParser;

/**
 * @author lawyerance
 * @version 1.0 2019-10-09
 */
class FastjsonReaderTest extends AbstractJsonReader {

    private static ParserConfig parserConfig;


    @Override
    protected void configuration() {
        // configuration
        parserConfig = ParserConfig.getGlobalInstance();
    }

    @Test
    void read() {
        JsonReader reader = new JsonReader(configurationBuilder.build(), new FastjsonParser(parserConfig));
        Bejson bejson = reader.read(is, Bejson.class);

        assertObjectEquals(bejson);
    }

}