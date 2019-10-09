package pers.lyks.xar.annotation;

import java.lang.annotation.*;

/**
 * @author lawyerance
 * @version 1.0 2019-10-02
 */
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target(value = ElementType.FIELD)
public @interface Property {
    String value() default "";
}
