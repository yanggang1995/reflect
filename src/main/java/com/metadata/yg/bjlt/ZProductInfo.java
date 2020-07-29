package com.metadata.yg.bjlt;

import com.metadata.yg.utils.DateUtils;
import com.metadata.yg.utils.RandomUtils;
import lombok.Data;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

/**
 * @author: Y.G
 * @description: 增值业务运营基地产品信息数据
 * @create: 2018-12-27 17:12
 **/
@Data
public class ZProductInfo {
    private byte[] Product_id;
    private byte[] Product_name;
    private byte[] Product_type;
    private byte[] Pro_des_info;
    private byte[] Product_state;
    private byte[] Eff_date;
    private byte[] Exp_date;
    private byte[] Product_class;
    private byte[] Auth_type;
    private byte[] Life_cycle;
    private byte[] Station_acct_no;
    private byte[] Party_code;
    private byte[] Party_info_create_time;
    private byte[] Product_name_en;
    private byte[] Product_base_type;
    private byte[] Price;
    private byte[] CP_Balance;
    private byte[] Settlement_interval;
    private byte[] PValidTime;
    private byte[] PExpiredTime;
    private byte[] provinceCode;
    private byte[] cityCode;

    public ZProductInfo(){
        this.Product_state=(RandomUtils.RandomListItem(new ArrayList<>(Arrays.asList("1","2","3","9")))).getBytes();
        List<String> expired_time = new ArrayList<>(Arrays.asList("07:12:10","08:24:36","09:47:31","10:11:17","11:41:57","12:43:27","14:20:21","13:12:13","14:14:31","15:16:31","17:12:38","18:20:48","19:22:58","00:00:00","20:00:00","21:21:31","23:12:43","23:11:11","20:09:05","18:24:57"));
        this.Eff_date=(DateUtils.formatTime(new Date().getTime(),"yyyy-MM-dd") + " " +  RandomUtils.RandomListItem(expired_time)).getBytes();
        this.Exp_date=(DateUtils.randomDateMinMaxFix("2019-01-01","2019-06-01") +" "+ RandomUtils.RandomListItem(expired_time)).getBytes();
        this.Product_class=RandomUtils.getRandomNum(1,3).toString().getBytes();
        this.Auth_type=RandomUtils.getRandomNum(1,4).toString().getBytes();
        this.Life_cycle=RandomUtils.getRandomNum(-1,1).toString().getBytes();
        this.Station_acct_no=(RandomUtils.getRandomString2(8)).getBytes();
        this.Party_code=(RandomUtils.getRandomString2(4)).getBytes();  // TODO: 2018/12/28 提供合作方编码表
        this.Party_info_create_time=(DateUtils.randomDateMinMaxFix("2018-10-01","2018-10-30")).getBytes();
        this.Product_base_type=RandomUtils.getRandomNum(0,2).toString().getBytes();
        this.CP_Balance=("0."+RandomUtils.getRandomNum(0,10)).getBytes();
        this.Settlement_interval=RandomUtils.getRandomNum(1,13).toString().getBytes();
        this.PValidTime=(DateUtils.formatTime(new Date().getTime(),"yyyyMMddHHmmss")).getBytes();
        this.PValidTime=(DateUtils.formatTime(new Date().getTime()+1000*60*60*24*RandomUtils.getRandomNum(1,20),"yyyyMMddHHmmss")).getBytes();
        this.PExpiredTime=(DateUtils.formatTime(new Date().getTime()+1000*60*60*24*RandomUtils.getRandomNum(1,20),"yyyyMMddHHmmss")).getBytes();
        this.provinceCode="".getBytes(); // TODO: 2018/12/28
        this.cityCode="".getBytes(); // TODO: 2018/12/28
    }
}
