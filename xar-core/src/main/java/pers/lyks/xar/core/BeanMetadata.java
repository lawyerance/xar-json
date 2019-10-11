package pers.lyks.xar.core;

import com.jayway.jsonpath.DocumentContext;
import com.jayway.jsonpath.JsonPath;
import lombok.Getter;

import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Properties;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * @author lawyerance
 * @version 1.0 2019-10-09
 */
@Getter
public class BeanMetadata<T> {
    private final Class<T> clazz;
    private List<FieldMetadata> fields;

    public BeanMetadata(Class<T> clazz) {
        this.clazz = clazz;
        this.fields = Stream.of(this.clazz.getDeclaredFields()).map(item -> new FieldMetadata(this.clazz, item)).filter(FieldMetadata::hasSetter).collect(Collectors.toList());
    }

    public BeanMetadata(Class<T> clazz, Properties prop) {
        this.clazz = clazz;
        this.fields = Stream.of(this.clazz.getDeclaredFields()).map(item -> new FieldMetadata(this.clazz, item, prop.getProperty(item.getName()))).filter(FieldMetadata::hasSetter).collect(Collectors.toList());
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        BeanMetadata that = (BeanMetadata) o;
        return Objects.equals(clazz, that.clazz);
    }

    @Override
    public int hashCode() {
        return Objects.hash(clazz);
    }

    @Override
    public String toString() {
        return "class: " + clazz.getTypeName() +
                ", with fields " + fields;
    }
}
