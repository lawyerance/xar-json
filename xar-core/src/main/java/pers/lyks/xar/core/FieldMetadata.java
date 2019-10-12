package pers.lyks.xar.core;

import com.jayway.jsonpath.JsonPath;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import pers.lyks.xar.annotation.Property;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

/**
 * @author lawyerance
 * @version 1.0 2019-10-02
 */
@Getter
@Setter
@Slf4j
public class FieldMetadata {
    private final Class<?> origin;
    private final String fieldName;
    private final Class<?> fieldType;
    private Method method;
    private final JsonPath jsonPath;

    FieldMetadata(Class<?> clazz, Field field) {
        this.origin = clazz;
        this.fieldName = field.getName();
        this.fieldType = field.getType();
        declaredMethod(clazz);
        Property annotation = field.getAnnotation(Property.class);
        if (null == annotation || "".equals(annotation.value())) {
            this.jsonPath = JsonPath.compile("$." + this.fieldName);
        } else {
            this.jsonPath = JsonPath.compile(annotation.value());
        }
    }

    FieldMetadata(Class<?> clazz, Field field, String expression) {
        this.origin = clazz;
        this.fieldName = field.getName();
        this.fieldType = field.getType();
        declaredMethod(clazz);
        if (null == expression) {
            this.jsonPath = JsonPath.compile("$." + this.fieldName);
            logger.warn("The field [ {} ] has not been specified jsonpath and will default json path: {}", this.fieldName, this.jsonPath);
        } else {
            this.jsonPath = JsonPath.compile(expression);
        }
    }

    private void declaredMethod(Class<?> clazz) {
        String setMethodName = generateSetMethodName();
        try {
            this.method = clazz.getDeclaredMethod(setMethodName, this.fieldType);
        } catch (NoSuchMethodException e) {
            logger.warn("field '{}' not exists set method with method name '{}' and parameter type '{}'. ", this.fieldName, setMethodName, this.fieldType.getName());
        }
    }

    private String generateSetMethodName() {

        String first = this.fieldName.substring(0, 1);
        String next = this.fieldName.substring(1);
        return "set" + first.toUpperCase() + next;
    }

    public boolean hasSetter() {
        return this.method != null;
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append(this.jsonPath.getPath()).append(" - ");
        builder.append(this.origin.getName())
                .append(".")
                .append(this.method.getName())
                .append("(").append(this.fieldType.getTypeName()).append(")");
        return builder.toString();
    }
}
