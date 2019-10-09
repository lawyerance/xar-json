package pers.lyks.xar.core;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.jayway.jsonpath.Configuration;
import com.jayway.jsonpath.Option;
import org.junit.Assert;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.platform.engine.support.descriptor.FileSource;
import pers.lyks.xar.annotation.Property;
import pers.lyks.xar.jaskson.JacksonJsonInvoke;

import java.io.IOException;
import java.io.InputStream;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.Objects;

/**
 * @author lawyerance
 * @version 1.0 2019-10-09
 */
class JsonReaderTest {

    private static Configuration.ConfigurationBuilder configurationBuilder = Configuration.builder();
    private static InputStream is;
    private static ObjectMapper objectMapper = new ObjectMapper();

    @BeforeAll
    static void setUp() throws URISyntaxException, IOException {
        configurationBuilder.options(Option.DEFAULT_PATH_LEAF_TO_NULL);
        is = JsonReaderTest.class.getClassLoader().getResourceAsStream("json/bejson.json");

        // configuration
        objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
    }

    @Test
    void read() {
        JsonReader reader = new JsonReader(configurationBuilder.build(), new JacksonJsonInvoke(objectMapper));
        Bejson bejson = reader.read(is, Bejson.class);

        Assert.assertTrue(bejson.isImageCompressEnable());

        Assert.assertEquals(bejson.getImageCompressBorder(), 1600L);

        Assert.assertEquals(bejson.getScrawlMaxSize(), 204800000133L);

        Assert.assertEquals(bejson.getImageActionName(), "uploadimage");

        Assert.assertEquals(bejson.getImageAllowFiles(), List.of(".png", ".jpg", ".jpeg", ".gif", ".bmp"));

        Snap snap = new Snap();
        snap.setSnapscreenActionName("uploadimage");
        Assert.assertEquals(bejson.getSnap(), snap);
    }

    public static class Bejson {
        @Property("$.imageActionName")
        private String imageActionName;

        @Property("$.imageAllowFiles")
        private List<String> imageAllowFiles;

        @Property("$.imageCompressEnable")
        private boolean imageCompressEnable;

        @Property("$.imageCompressBorder")
        private long imageCompressBorder;

        @Property("$.scrawlMaxSize")
        private long scrawlMaxSize;

        @Property("$.snap")
        private Snap snap;

        public String getImageActionName() {
            return imageActionName;
        }

        public void setImageActionName(String imageActionName) {
            this.imageActionName = imageActionName;
        }

        public List<String> getImageAllowFiles() {
            return imageAllowFiles;
        }

        public void setImageAllowFiles(List<String> imageAllowFiles) {
            this.imageAllowFiles = imageAllowFiles;
        }

        public boolean isImageCompressEnable() {
            return imageCompressEnable;
        }

        public void setImageCompressEnable(boolean imageCompressEnable) {
            this.imageCompressEnable = imageCompressEnable;
        }

        public long getImageCompressBorder() {
            return imageCompressBorder;
        }

        public void setImageCompressBorder(long imageCompressBorder) {
            this.imageCompressBorder = imageCompressBorder;
        }

        public long getScrawlMaxSize() {
            return scrawlMaxSize;
        }

        public void setScrawlMaxSize(long scrawlMaxSize) {
            this.scrawlMaxSize = scrawlMaxSize;
        }

        public Snap getSnap() {
            return snap;
        }

        public void setSnap(Snap snap) {
            this.snap = snap;
        }
    }

    public static class Snap {
        private String snapscreenActionName;


        public String getSnapscreenActionName() {
            return snapscreenActionName;
        }

        public void setSnapscreenActionName(String snapscreenActionName) {
            this.snapscreenActionName = snapscreenActionName;
        }


        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            Snap snap = (Snap) o;
            return Objects.equals(snapscreenActionName, snap.snapscreenActionName);
        }

        @Override
        public int hashCode() {
            return Objects.hash(snapscreenActionName);
        }
    }
}