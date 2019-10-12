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

import pers.lyks.xar.XarException;

import java.util.*;

/**
 * @author lawyerance
 * @version 1.0 2019-10-02
 */
public class XarFactory {
    private static final Map<Class<?>, BeanMetadata> cache = new HashMap<>();

    public static <T> void registerByAnnotation(Class<T> clazz) {
        if (!cache.containsKey(clazz)) {
            BeanMetadata<T> beanMetadata = new BeanMetadata<>(clazz);
            cache.put(clazz, beanMetadata);
        }
    }

    public static <T> void registerByProperty(Class<T> clazz, Properties prop) {
        if (!cache.containsKey(clazz)) {
            BeanMetadata<T> beanMetadata = new BeanMetadata<>(clazz, prop);
            cache.put(clazz, beanMetadata);
        }
    }

    public static <T> void unregister(Class<T> clazz) {
        cache.remove(clazz);
    }


    @SuppressWarnings("unchecked")
    public static <T> BeanMetadata<T> read(Class<T> clazz) {
        if (cache.containsKey(clazz)) {
            return cache.get(clazz);
        }
        throw new XarException("The class has not been registered: " + clazz);
    }

    public static Collection<BeanMetadata> getBeanMetaData() {
        return cache.values();
    }

    public static boolean contains(Class<?> clazz) {
        return cache.containsKey(clazz);
    }
}
