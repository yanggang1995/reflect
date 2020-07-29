package com.metadata.yg.bjlt;

import com.metadata.yg.utils.DateUtils;
import lombok.Data;

/**
 * @author: Y.G
 * @description:
 * @create: 2018-12-27 16:42
 **/
@Data
public class UserlogoutInfo {
    private byte[] UserID;
    private byte[] Cancel_time;

    public UserlogoutInfo(Long loginTime,int randomTime){
        this.Cancel_time=(DateUtils.formatTime(loginTime+10000*randomTime*randomTime+10000*randomTime,"yyyyMMddHHmmss")).getBytes();
    }
}
