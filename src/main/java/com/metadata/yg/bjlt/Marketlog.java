package com.metadata.yg.bjlt;

import com.metadata.yg.utils.DataUtils;
import com.metadata.yg.utils.RandomUtils;
import lombok.Data;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static com.metadata.yg.constant.Conf.EXETIME;

/**
 * @author: Y.G
 * @description: 增值业务基地重大营销事件
 * @create: 2018-12-27 17:45
 **/
@Data
public class Marketlog {
    private byte[] MarketID;
    private byte[] MarketName;
    private byte[] Description;
    private byte[] BeginTime;
    private byte[] EndTime;
    private byte[] ProductID;
    private byte[] CPNAME;
    private byte[] Fee;
    private byte[] Ordernumbe;
    private byte[] price;
    private byte[] CP_Balance;
    private byte[] Settlement_interval;
    private byte[] provinceCode;
    private byte[] cityCode;

    public Marketlog(){
        this.MarketID=(DataUtils.getMd5(RandomUtils.getRandomNum(1,100).toString()) ).getBytes();
        this.MarketName=(DataUtils.getMd5(RandomUtils.getRandomNum(1,100).toString()) ).getBytes();
        this.Description=(DataUtils.getMd5(RandomUtils.getRandomNum(1,100).toString()) ).getBytes();
        List<String> hhmiss_time = new ArrayList<>(Arrays.asList("071210","082436","094731","101117","114157","122327","112021","101213","051431","091631","071238","122048","112258","000000","050000","022131","071243","121111","010905","072457"));
        this.BeginTime=(EXETIME + RandomUtils.RandomListItem(hhmiss_time)).getBytes();
        List<String> hhmiss_time_end = new ArrayList<>(Arrays.asList("171210","182436","194731","151117","124157","124327","132021","141213","151431","191631","171238","212048","142258","220000","150000","222131","171243","221111","210905","172457"));
        this.EndTime=(EXETIME + RandomUtils.RandomListItem(hhmiss_time_end)).getBytes();
        this.CPNAME=(RandomUtils.RandomListItem(new ArrayList<>(Arrays.asList("中兴","华为")))).getBytes();
        this.Fee=(RandomUtils.getRandomNum(100,100000)+".00").getBytes();
        this.Ordernumbe=(RandomUtils.getRandomNum(0,1000000)+".00").getBytes();
        this.price=(RandomUtils.getRandomNum(0,100)+".00").getBytes();
        this.CP_Balance=("[0."+RandomUtils.getRandomNum(0,10)).getBytes();
        this.Settlement_interval=RandomUtils.getRandomNum(1,13).toString().getBytes();
    }
}
