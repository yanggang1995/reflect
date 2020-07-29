package com.metadata.yg.bjlt;

import com.metadata.yg.enums.STB;
import com.metadata.yg.utils.RandomUtils;
import lombok.Data;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static com.metadata.yg.constant.Conf.EXETIME;
import static com.metadata.yg.utils.DataUtils.getIp;
import static com.metadata.yg.utils.RandomUtils.getRandomNum;

/**
 * @author: Y.G
 * @description: 业务交互质量指标
 * @create: 2018-12-27 18:34
 **/
@Data
public class INSERV {
    private byte[] reportingTime;
    private byte[] provinceCode;
    private byte[] cityCode;
    private byte[] businessId;
    private byte[] stbId;
    private byte[] epgServerIp;
    private byte[] EPGResQuestAvgTime;
    private byte[] EPGResQuestMaxTime;
    private byte[] EPGResQuestTotal;
    private byte[] EPGResQuestSuccessNum;
    private byte[] liveRequestNum;
    private byte[] liveRequestSuccessNum;
    private byte[] liveRequestAvgTime;
    private byte[] vodRequestNum;
    private byte[] vodRequestSuccessNum;
    private byte[] vodRequestAvgTime;
    private byte[] liveChaCRequestAvgTime;

    public INSERV(){
        List<String> hhmiss_time = new ArrayList<>(Arrays.asList("071210","082436","094731","101117","114157","122327","112021","101213","051431","091631","071238","122048","112258","000000","050000","022131","071243","121111","010905","072457"));
        this.reportingTime=(EXETIME + RandomUtils.RandomListItem(hhmiss_time)).getBytes();
        this.stbId=(RandomUtils.getRandomString0(4) +STB.values()[getRandomNum(0,2)]+ RandomUtils.getRandomString0(2)).getBytes();
        this.epgServerIp=getIp().getBytes();
        this.EPGResQuestAvgTime=getRandomNum(0,800).toString().getBytes();
        this.EPGResQuestMaxTime=getRandomNum(800,900).toString().getBytes();
        this.EPGResQuestTotal=getRandomNum(0,1000).toString().getBytes();
        this.EPGResQuestSuccessNum=getRandomNum(0,1000).toString().getBytes();
        this.liveRequestNum=getRandomNum(0,100).toString().getBytes();
        this.liveRequestSuccessNum=getRandomNum(0,100).toString().getBytes();
        this.liveRequestAvgTime=getRandomNum(0,100).toString().getBytes();
        this.vodRequestNum=getRandomNum(0,100).toString().getBytes();
        this.vodRequestSuccessNum=getRandomNum(0,100).toString().getBytes();
        this.vodRequestAvgTime=getRandomNum(0,100).toString().getBytes();
        this.liveChaCRequestAvgTime=getRandomNum(0,100).toString().getBytes();
    }
}
