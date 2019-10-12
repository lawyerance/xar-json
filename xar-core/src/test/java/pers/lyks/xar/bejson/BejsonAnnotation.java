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
