package pers.lyks.xar.core;

import org.junit.Assert;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import pers.lyks.xar.bean.AnnotationBean;
import pers.lyks.xar.bean.PropertiesBean;

import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

/**
 * @author lawyerance
 * @version 1.0 2019-10-09
 */
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class XarFactoryTest {

    @Test
    @Order(1)
    void cacheFromAnnotation() {
        XarFactory.registerByAnnotation(AnnotationBean.class);
        Assert.assertNotNull(XarFactory.read(AnnotationBean.class));
    }

    @Test
    @Order(2)
    void cacheFromProperty() {
        Properties map = new Properties();
        map.put("str", "$.data.string");
        XarFactory.registerByProperty(PropertiesBean.class, map);
        Assert.assertNotNull(XarFactory.read(PropertiesBean.class));
    }

    @Test
    @Order(3)
    void read() {
        BeanMetadata<AnnotationBean> beanBeanMetadata = XarFactory.read(AnnotationBean.class);
        Assert.assertNotNull(beanBeanMetadata);
    }
}