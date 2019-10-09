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

import static org.junit.jupiter.api.Assertions.*;

/**
 * @author lawyerance
 * @version 1.0 2019-10-09
 */
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class PropertyCacheTest {

    @Test
    @Order(1)
    void cacheFromAnnotation() {
        PropertyCache.cacheFromAnnotation(AnnotationBean.class);
        Assert.assertNotNull(PropertyCache.get(AnnotationBean.class));
    }

    @Test
    @Order(2)
    void cacheFromProperty() {
        Map<String, String> map = new HashMap<>();
        map.put("str", "$.data.string");
        PropertyCache.cacheFromProperty(PropertiesBean.class, map);
        Assert.assertNotNull(PropertyCache.get(PropertiesBean.class));
    }

    @Test
    @Order(3)
    void read() {
        BeanMetadata<AnnotationBean> beanBeanMetadata = PropertyCache.read(AnnotationBean.class);
        Assert.assertNotNull(beanBeanMetadata);
    }
}