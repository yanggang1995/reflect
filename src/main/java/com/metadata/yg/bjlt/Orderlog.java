package com.metadata.yg.bjlt;

import com.metadata.yg.utils.DataUtils;
import com.metadata.yg.utils.DateUtils;
import com.metadata.yg.utils.RandomUtils;
import lombok.Data;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import static com.metadata.yg.utils.DataUtils.getMd5;

/**
 * @author: Y.G
 * @description:
 * @create: 2018-12-27 16:52
 **/
@Data
public class Orderlog {
    private byte[] UserID;
    private byte[] SubscribeTime;
    private byte[] ContentCode;
    private byte[] ContentName;
    private byte[] OrderType;
    private byte[] ServiceID;
    private byte[] Fee;
    private byte[] ValidTime;
    private byte[] ExpiredTime;
    private byte[] VisitPath;
    private byte[] SequenceID;
    private byte[] ProductID;
    private byte[] ProductName;
    private byte[] ServiceName;
    private byte[] MediaCode;
    private byte[] OrderID;
    private byte[] UpdateTime;
    private byte[] OrderValid;
    private byte[] UnsubscribeTime;
    private byte[] OrderExtend;
    private byte[] FeeType;

    public Orderlog(){
        int serviceRandom=RandomUtils.getRandomNum(0,5);
        this.SubscribeTime=(DateUtils.transforDate(new Date().getTime()+"")).getBytes();
        this.OrderType=RandomUtils.getRandomNum(1,3).toString().getBytes();
        this.ServiceID="2".equals(new String(this.OrderType))?getMd5(serviceRandom+"service").getBytes():"".getBytes();
        this.Fee=(RandomUtils.getRandomNum(0,100)+".00").getBytes();
        this.ValidTime=(DateUtils.transforDate(new Date().getTime()+"")).getBytes();
        List<String> expired_time = new ArrayList<>(Arrays.asList("071210","082436","094731","101117","114157","124327","142021","131213","141431","151631","171238","182048","192258","000000","200000","212131","231243","231111","200905","182457"));
        this.ExpiredTime=(DateUtils.randomDateMinMax1("20181201","20190601") + RandomUtils.RandomListItem(expired_time)).getBytes();
        this.VisitPath=(RandomUtils.RandomListItem(new ArrayList<>(Arrays.asList("/movie/Action/China","/movie/Action/USA","/TV/Action/USA","/TV-SHOW/USA/ALLEN","/MUSIC/CLASS/CHINA","/MUSIC/ROMANTIC/JAPAN","")))).getBytes();
        this.SequenceID=(RandomUtils.RandomListItem(new ArrayList<>(Arrays.asList("112204343","412654347","","312204948","212504781"))) + RandomUtils.getRandomString0(3)).getBytes();
        this.ServiceName="2".equals(new String(this.OrderType))?DataUtils.getServiceName(serviceRandom).getBytes():"".getBytes();
        this.MediaCode=(RandomUtils.RandomListItem(new ArrayList<>(Arrays.asList("service1","service2","service3","service4","service5","service6","service7")))).getBytes();
        this.UpdateTime=(DateUtils.transforDate(new Date().getTime()+"")).getBytes();
        this.OrderValid=RandomUtils.getRandomNum(0,2).toString().getBytes();
        this.UnsubscribeTime=(DateUtils.randomDateMinMax1("20181201","20190601") + RandomUtils.RandomListItem(expired_time)).getBytes();
        this.OrderExtend=RandomUtils.getRandomNum(0,2).toString().getBytes();
        this.FeeType=RandomUtils.getRandomNum(1,6).toString().getBytes();
    }
}
