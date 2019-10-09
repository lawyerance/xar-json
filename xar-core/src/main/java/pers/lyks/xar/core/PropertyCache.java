package pers.lyks.xar.core;

import java.util.HashMap;
import java.util.Map;

/**
 * @author lawyerance
 * @version 1.0 2019-10-02
 */
public class PropertyCache {
    private static final Map<Class<?>, BeanMetadata> cache = new HashMap<>();


    public static <T> void cacheFromAnnotation(Class<T> clazz) {
        if (!cache.containsKey(clazz)) {
            BeanMetadata<T> beanMetadata = new BeanMetadata<>(clazz);
            cache.put(clazz, beanMetadata);
        }
    }


    public static <T> void cacheFromProperty(Class<T> clazz, Map<String, String> prop) {
        if (!cache.containsKey(clazz)) {
            BeanMetadata<T> beanMetadata = new BeanMetadata<>(clazz, prop);
            cache.put(clazz, beanMetadata);
        }
    }

    public static <T> BeanMetadata<T> read(Class<T> clazz) {
        if (cache.containsKey(clazz)) {
            return get(clazz);
        }
        cacheFromAnnotation(clazz);
        return get(clazz);
    }


    @SuppressWarnings("unchecked")
    public static <T> BeanMetadata<T> get(Class<T> key) {
        return cache.get(key);
    }

}
