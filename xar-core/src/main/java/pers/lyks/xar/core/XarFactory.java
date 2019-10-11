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
