package com.metadata.yg.bjlt;

import com.metadata.yg.utils.RandomUtils;
import lombok.Data;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static com.metadata.yg.constant.Conf.EXETIME;

/**
 * @author: Y.G
 * @description:
 * @create: 2018-12-27 16:25
 **/
@Data
public class Contentviewlog {
    private byte[] UserID;
    private byte[] BeginTime;
    private byte[] EndTime;
    private byte[] ServiceType;
    private byte[] ContentCode;
    private byte[] ContentName;
    private byte[] SequenceID;
    private byte[] ShiftDuration;
    private byte[] Flux;
    private byte[] EndReason;
    private byte[] ChargeType;
    private byte[] SumFee;
    private byte[] Qos;
    private byte[] ProductID;
    private byte[] ServiceID;
    private byte[] MediaCode;
    private byte[] VisitPath;

    public Contentviewlog(){
        List<String> hhmiss_time = new ArrayList<>(Arrays.asList("071210","082436","094731","101117","114157","122327","112021","101213","051431","091631","071238","122048","112258","000000","050000","022131","071243","121111","010905","072457"));
        this.BeginTime=(EXETIME + RandomUtils.RandomListItem(hhmiss_time)).getBytes();
        List<String> hhmiss_time_end = new ArrayList<>(Arrays.asList("171210","182436","194731","151117","124157","124327","132021","141213","151431","191631","171238","212048","142258","220000","150000","222131","171243","221111","210905","172457"));
        this.EndTime=(EXETIME + RandomUtils.RandomListItem(hhmiss_time_end)).getBytes();
        this.ServiceType=RandomUtils.getRandomNum(1,7).toString().getBytes();
        List<String> sequenceId = new ArrayList<>(Arrays.asList("112204343","412654347","","312204948","212504781"));
        this.SequenceID=(RandomUtils.RandomListItem(sequenceId) + RandomUtils.getRandomString0(3)).getBytes();
        this.ShiftDuration=(RandomUtils.getRandomString0(2)).getBytes();
        this.Flux=RandomUtils.getRandomNum(0,100000).toString().getBytes();
        this.EndReason=RandomUtils.getRandomNum(0,6).toString().getBytes();
        this.ChargeType=RandomUtils.getRandomNum(0,4).toString().getBytes();
        this.SumFee=RandomUtils.getRandomNum(10000,100000).toString().getBytes();
        this.Qos=RandomUtils.getRandomNum(1,101).toString().getBytes();
        this.ProductID=("2400000" + RandomUtils.getRandomNum(100,150)).getBytes();
        this.ServiceID=(RandomUtils.getRandomString0(12)).getBytes();
        this.MediaCode=("351000010000000" + RandomUtils.getRandomString0(17)).getBytes();
        List<String> view_path = new ArrayList<>(Arrays.asList("/movie/Action/China","/movie/Action/USA","/TV/Action/USA","/TV-SHOW/USA/ALLEN","/MUSIC/CLASS/CHINA","/MUSIC/ROMANTIC/JAPAN"));
        this.VisitPath=(RandomUtils.RandomListItem(view_path)).getBytes();
    }
}
