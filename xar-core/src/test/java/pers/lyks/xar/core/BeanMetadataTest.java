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