package com.metadata.yg.bjlt;

import com.metadata.yg.utils.DateUtils;
import com.metadata.yg.utils.RandomUtils;
import lombok.Data;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;

/**
 * @author: Y.G
 * @description: 用户EPG页面操作行为数据
 * @create: 2018-12-27 17:09
 **/
@Data
public class UserOperEpgInfo {
    private byte[] UserID;
    private byte[] EpgPageName;
    private byte[] EpgPagePath;
    private byte[] OperType;
    private byte[] LinkInTime;
    private byte[] LinkOutTime;
    private byte[] OperObjectCode;

    public UserOperEpgInfo(){
        this.EpgPageName=(RandomUtils.RandomListItem(new ArrayList<>(Arrays.asList("首页","直播","点播","辉煌90年","建党大业")))).getBytes();
        this.EpgPagePath=(RandomUtils.RandomListItem(new ArrayList<>(Arrays.asList("首页/点播/辉煌90年","首页/直播/建党大业","首页/点播/建党大业")))).getBytes();
        this.OperType=(RandomUtils.RandomListItem(new ArrayList<>(Arrays.asList("00","01","02","11","12","13","14","15","21","22","23","24","25","30")))).getBytes();
        this.LinkInTime=DateUtils.formatTime(new Date().getTime(),"yyyyMMddHHmmss").getBytes();
        this.LinkOutTime=(DateUtils.formatTime(new Date().getTime()+1000*RandomUtils.getRandomNum(0,20),"yyyyMMddHHmmss")).getBytes();
        this.OperObjectCode=RandomUtils.getRandomNum(100,1000).toString().getBytes();
    }
}
