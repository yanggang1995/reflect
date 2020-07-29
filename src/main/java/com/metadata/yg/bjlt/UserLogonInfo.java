package com.metadata.yg.bjlt;

import com.metadata.yg.utils.DateUtils;
import com.metadata.yg.utils.RandomUtils;
import lombok.Data;

/**
 * @author: Y.G
 * @description:
 * @create: 2018-12-27 16:40
 **/
@Data
public class UserLogonInfo {
    private byte[] UserID;
    private byte[] LoginTime;
    private byte[] LogoutTime;
    private byte[] Open_status;

    public UserLogonInfo(Long loginTime,int randomTime){
        this.LoginTime=(DateUtils.formatTime(loginTime+10000*randomTime,"yyyyMMddHHmmss")).getBytes();
        this.LogoutTime=(DateUtils.formatTime(loginTime+10000*randomTime*randomTime,"yyyyMMddHHmmss")).getBytes();
        this.Open_status=RandomUtils.getRandomNum(0,2).toString().getBytes();
    }
}
