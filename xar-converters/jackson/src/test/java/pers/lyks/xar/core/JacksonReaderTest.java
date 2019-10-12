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

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Assert;
import org.junit.jupiter.api.Test;
import pers.lyks.xar.bejson.BejsonAnnotation;
import pers.lyks.xar.bejson.BejsonProperty;
import pers.lyks.xar.jackson.JacksonParser;

/**
 * @author lawyerance
 * @version 1.0 2019-10-09
 */
class JacksonReaderTest extends AbstractJsonResolve {

    private static ObjectMapper objectMapper = new ObjectMapper();

    private static JsonResolve resolve;

    @Override
    protected void configuration() {
        // configuration
        objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);


        resolve = new JsonResolve(configurationBuilder.build(), new JacksonParser(objectMapper));
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