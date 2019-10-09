package pers.lyks.xar.core;

import com.jayway.jsonpath.DocumentContext;
import pers.lyks.xar.XarException;

import java.io.Serializable;

/**
 * @author lawyerance
 * @version 1.0 2019-10-03
 */
public interface JsonInvoke extends Serializable {
    <T> T deserialize(DocumentContext context, BeanMetadata<T> t) throws XarException;
}
