/*
 * Copyright 2019 the original author or authors.
 * <p>
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * <p>
 * http://www.apache.org/licenses/LICENSE-2.0
 * <p>
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
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