package pers.lyks.xar.bean;


import pers.lyks.xar.annotation.Property;

/**
 * @author lawyerance
 * @version 1.0 2019-10-09
 */
public class AnnotationBean extends SupperBean {
    @Property("$.data.str")
    private String str;
    @Property("$.data.noStr")
    private String notSet;

    public void setStr(String str) {
        this.str = str;
    }
}
