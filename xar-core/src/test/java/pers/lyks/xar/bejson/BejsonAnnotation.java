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
package pers.lyks.xar.bejson;

import com.alibaba.fastjson.annotation.JSONField;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;
import lombok.Setter;
import pers.lyks.xar.annotation.Property;

import java.util.Date;
import java.util.List;

/**
 * @author lawyerance
 * @version 1.0 2019-10-10
 */
@Setter
@Getter
public class BejsonAnnotation {
    @Property("$.imageActionName")
    private String imageActionName;

    @Property("$.imageAllowFiles")
    private List<String> imageAllowFiles;

    @Property("$.imageCompressEnable")
    private boolean imageCompressEnable;

    @Property("$.imageCompressBorder")
    private long imageCompressBorder;

    @Property("$.scrawlMaxSize")
    private long scrawlMaxSize;

    @Property("$.snap.catcherTime")
    @JsonFormat(pattern = "yyyyMMdd HHmmss", timezone = "GMT+8")
    @JSONField(format = "yyyyMMdd HHmmss")
    private Date catcherTime;

    @Property("$.snap")
    private Snap snap;
}
