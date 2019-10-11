package pers.lyks.xar.core;

import com.jayway.jsonpath.JsonPath;
import org.junit.Assert;
import org.junit.jupiter.api.Test;
import pers.lyks.xar.bean.AnnotationBean;
import pers.lyks.xar.bean.PropertiesBean;

import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

/**
 * @author lawyerance
 * @version 1.0 2019-10-09
 */
class BeanMetadataTest {
    private Class<PropertiesBean> beanClass = PropertiesBean.class;
    private String typeName = beanClass.getTypeName();

    @Test
    void testConstructor() {
        BeanMetadata<PropertiesBean> beanMetadata = new BeanMetadata<>(beanClass);
        StringBuilder builder = new StringBuilder();
        builder.append("class: ").append(typeName)
                .append(", with fields [").append(JsonPath.compile("$.str").getPath()).append(" - ")
                .append(typeName).append(".").append("setStr(java.lang.String)")
                .append("]");
        Assert.assertEquals(beanMetadata.toString(), builder.toString());
    }

    @Test
    void testConstructorWithProperties() {
        Properties prop = new Properties();
        prop.setProperty("str", "$.data.str");
        BeanMetadata<PropertiesBean> beanMetadata = new BeanMetadata<>(beanClass, prop);
        StringBuilder builder = new StringBuilder();
        builder.append("class: ").append(typeName)
                .append(", with fields [").append(JsonPath.compile("$.data.str").getPath()).append(" - ")
                .append(typeName).append(".").append("setStr(java.lang.String)")
                .append("]");
        Assert.assertEquals(beanMetadata.toString(), builder.toString());
    }

    @Test
    void testConstructorWithAnnotation() {
        Class<AnnotationBean> annotationBeanClass = AnnotationBean.class;
        BeanMetadata<AnnotationBean> beanMetadata = new BeanMetadata<>(annotationBeanClass);
        StringBuilder builder = new StringBuilder();
        builder.append("class: ").append(annotationBeanClass.getTypeName())
                .append(", with fields [").append(JsonPath.compile("$.data.str").getPath()).append(" - ")
                .append(annotationBeanClass.getTypeName()).append(".").append("setStr(java.lang.String)")
                .append("]");
        Assert.assertEquals(beanMetadata.toString(), builder.toString());
    }

}