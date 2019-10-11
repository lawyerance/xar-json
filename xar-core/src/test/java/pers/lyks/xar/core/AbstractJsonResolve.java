package pers.lyks.xar.core;

import com.jayway.jsonpath.Configuration;
import com.jayway.jsonpath.Option;
import org.junit.Assert;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import pers.lyks.xar.annotation.Property;
import pers.lyks.xar.bejson.Bejson;
import pers.lyks.xar.bejson.Snap;

import java.io.IOException;
import java.io.InputStream;
import java.net.URISyntaxException;
import java.util.Calendar;
import java.util.List;
import java.util.Properties;

/**
 * @author lawyerance
 * @version 1.0 2019-10-10
 */
public abstract class AbstractJsonResolve {

    protected static Configuration.ConfigurationBuilder configurationBuilder = Configuration.builder();
    protected static InputStream is;
    private static Calendar calendar = Calendar.getInstance();
    protected Properties prop = new Properties();

    @BeforeEach
    void setUp() throws URISyntaxException, IOException {
        configurationBuilder.options(Option.DEFAULT_PATH_LEAF_TO_NULL);
        is = Bejson.class.getClassLoader().getResourceAsStream("pers/lyks/xar/bejson/a.json");

        calendar.set(Calendar.MILLISECOND, 0);

        prop.put("imageCompressEnable", "$.imageCompressEnable");
        // imageCompressEnable use default
        prop.put("scrawlMaxSize", "$.scrawlMaxSize");

        prop.setProperty("imageActionName", "$.imageActionName");
        // imageAllowFiles use default
        prop.setProperty("catcherTime", "$.snap.catcherTime");
        prop.setProperty("snap", "$.snap");

        // configuration
        configuration();
    }

    @AfterEach
    void after() {
        XarFactory.unregister(Bejson.class);
    }

    protected abstract void configuration();

    protected void assertObjectEquals(Bejson bejson) {

        Assert.assertTrue(bejson.isImageCompressEnable());

        Assert.assertEquals(bejson.getImageCompressBorder(), 1600L);

        Assert.assertEquals(bejson.getScrawlMaxSize(), 204800000133L);

        Assert.assertEquals(bejson.getImageActionName(), "uploadimage");

        Assert.assertEquals(bejson.getImageAllowFiles(), List.of(".png", ".jpg", ".jpeg", ".gif", ".bmp"));

        calendar.set(2019, Calendar.OCTOBER, 10, 20, 20, 21);
        Assert.assertEquals(bejson.getCatcherTime(), calendar.getTime());


        Snap snap = new Snap();
        snap.setSnapscreenActionName("uploadimage");
        calendar.set(2019, Calendar.OCTOBER, 10, 19, 20, 20);
        snap.setSnapscreenTime(calendar.getTime());

        Assert.assertEquals(snap, bejson.getSnap());
    }
}
