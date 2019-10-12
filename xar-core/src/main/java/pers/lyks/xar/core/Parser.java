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

import com.jayway.jsonpath.DocumentContext;
import pers.lyks.xar.XarException;

import java.io.Serializable;

/**
 * @author lawyerance
 * @version 1.0 2019-10-03
 */
public interface Parser extends Serializable {
    <T> T deserialize(DocumentContext context, BeanMetadata<T> t) throws XarException;
}
