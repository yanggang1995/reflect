package com.metadata.yg.bjlt;

import com.metadata.yg.enums.STB;
import com.metadata.yg.utils.RandomUtils;
import lombok.Data;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static com.metadata.yg.constant.Conf.EXETIME;
import static com.metadata.yg.utils.RandomUtils.getRandomNum;

/**
 * @author: Y.G
 * @description: 用户视频质量数据
 * @create: 2018-12-27 18:23
 **/
@Data
public class Videoqualitylog {
    private byte[] reportingTime;
    private byte[] provinceCode;
    private byte[] cityCode;
    private byte[] stbId;
    private byte[] bussinessId;
    private byte[] liveMosAvg;
    private byte[] liveMosMax;
    private byte[] liveOsMin;
    private byte[] vodMosAvg;
    private byte[] vodMosMax;
    private byte[] vodMosMin;
    private byte[] mosdegrandation;

    public Videoqualitylog(){
        List<String> hhmiss_time = new ArrayList<>(Arrays.asList("071210","082436","094731","101117","114157","122327","112021","101213","051431","091631","071238","122048","112258","000000","050000","022131","071243","121111","010905","072457"));
        this.reportingTime=(EXETIME + RandomUtils.RandomListItem(hhmiss_time)).getBytes();
        this.stbId=(RandomUtils.getRandomString0(4) +STB.values()[getRandomNum(0,2)]+ RandomUtils.getRandomString0(2)).getBytes();
        this.liveMosAvg=getRandomNum(0,10).toString().getBytes();
        this.liveMosMax=getRandomNum(8,10).toString().getBytes();
        this.liveOsMin=getRandomNum(0,5).toString().getBytes();
        this.vodMosAvg=getRandomNum(8,10).toString().getBytes();
        this.vodMosMax=getRandomNum(0,10).toString().getBytes();
        this.vodMosMin=getRandomNum(0,5).toString().getBytes();
        this.mosdegrandation=(getRandomNum(0,3)+"."+getRandomNum(0,6)).getBytes();
    }
}
