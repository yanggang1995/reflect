package com.metadata.yg.bjlt;

import com.metadata.yg.utils.RandomUtils;
import lombok.Data;

/**
 * @author: Y.G
 * @description: B侧统计数据采集
 * @create: 2018-12-27 17:59
 **/
@Data
public class BcountInfo {
    private byte[] IPTVUser;
    private byte[] NewIPTV;
    private byte[] NewIPTVUser;
    private byte[] BandUser;
    private byte[] FtthUser;
    private byte[] IPTVFee;
    private byte[] BIPTVFee;
    private byte[] ADDIPTVFee;

    public BcountInfo(){
        this.NewIPTV=RandomUtils.getRandomNum(0,1000000).toString().getBytes();
        this.NewIPTVUser=RandomUtils.getRandomNum(0,1000000).toString().getBytes();
        this.BandUser=RandomUtils.getRandomNum(0,1000000).toString().getBytes();
        this.FtthUser=RandomUtils.getRandomNum(0,1000000).toString().getBytes();
        this.IPTVFee=RandomUtils.getRandomNum(0,1000000).toString().getBytes();
        this.BIPTVFee=RandomUtils.getRandomNum(0,1000000).toString().getBytes();
        this.ADDIPTVFee=RandomUtils.getRandomNum(0,1000000).toString().getBytes();
    }
}
