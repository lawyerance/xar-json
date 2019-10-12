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
public class BejsonProperty {
    private String imageActionName;

    private List<String> imageAllowFiles;

    private boolean imageCompressEnable;

    private long imageCompressBorder;

    private long scrawlMaxSize;

    @JsonFormat(pattern = "yyyyMMdd HHmmss", timezone = "GMT+8")
    @JSONField(format = "yyyyMMdd HHmmss")
    private Date catcherTime;

    private Snap snap;
}
