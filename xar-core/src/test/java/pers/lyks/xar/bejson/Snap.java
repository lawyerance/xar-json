package pers.lyks.xar.bejson;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.util.Date;
import java.util.Objects;

/**
 * @author lawyerance
 * @version 1.0 2019-10-10
 */
@Data
public class Snap {
    private String snapscreenActionName;

    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss.SSSXXX", timezone = "GMT+8")
    private Date snapscreenTime;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Snap snap = (Snap) o;
        return Objects.equals(snapscreenActionName, snap.snapscreenActionName)
                && Objects.equals(snapscreenTime, snap.snapscreenTime);
    }

    @Override
    public int hashCode() {
        return Objects.hash(snapscreenActionName, snapscreenTime);
    }
}
