package com.metadata.yg.bjlt;

import com.metadata.yg.enums.Surname;
import com.metadata.yg.utils.RandomUtils;
import lombok.Data;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static com.metadata.yg.constant.Conf.EXETIME;

/**
 * @author: Y.G
 * @description: B侧经营业务分析系统业务数据
 * @create: 2018-12-27 17:52
 **/
@Data
public class BasInfo {
    private byte[] User_ID;
    private byte[] IF_IPTV;
    private byte[] Account_ID;
    private byte[] IPTV_Account_ID;
    private byte[] User_Name;
    private byte[] User_State;
    private byte[] User_Telephone;
    private byte[] User_Addr;
    private byte[] User_Contact;
    private byte[] Create_Time;
    private byte[] Enable_Date;
    private byte[] IPTV_End_Date;
    private byte[] User_Age;
    private byte[] User_Sex;
    private byte[] Region_Code;
    private byte[] Modify_Date;
    private byte[] Boss_Area_Bind;
    private byte[] Occupation;
    private byte[] Salary;

    public BasInfo(){
        this.IF_IPTV=RandomUtils.getRandomNum(0,2).toString().getBytes();
        this.User_Name=Surname.values()[RandomUtils.getRandomNum(0,400)].toString().getBytes();
        this.User_State=RandomUtils.getRandomNum(0,3).toString().getBytes();
        this.User_Telephone=(RandomUtils.RandomListItem(new ArrayList<>(Arrays.asList("155","177","131"))) + RandomUtils.getRandomString0(8)).getBytes();
        this.User_Contact=(RandomUtils.RandomListItem(new ArrayList<>(Arrays.asList("155","177","131"))) + RandomUtils.getRandomString0(8)).getBytes();
        List<String> hhmiss_time = new ArrayList<>(Arrays.asList("071210","082436","094731","101117","114157","122327","112021","101213","051431","091631","071238","122048","112258","000000","050000","022131","071243","121111","010905","072457"));
        this.Create_Time=(EXETIME + RandomUtils.RandomListItem(hhmiss_time)).getBytes();
        List<String> hhmiss_time_end = new ArrayList<>(Arrays.asList("171210","182436","194731","151117","124157","124327","132021","141213","151431","191631","171238","212048","142258","220000","150000","222131","171243","221111","210905","172457"));
        this.Enable_Date=(EXETIME + RandomUtils.RandomListItem(hhmiss_time_end)).getBytes();
        this.IPTV_End_Date=(EXETIME + RandomUtils.RandomListItem(hhmiss_time_end)).getBytes();
        this.User_Age=RandomUtils.getRandomNum(1,80).toString().getBytes();
        this.User_Sex=(RandomUtils.RandomListItem(new ArrayList<>(Arrays.asList("female","male")))).getBytes();
        this.Modify_Date=(EXETIME + RandomUtils.RandomListItem(hhmiss_time)).getBytes();
        this.Occupation=(RandomUtils.RandomListItem(new ArrayList<>(Arrays.asList("教师","工人","IT","厨师","大堂经理")))).getBytes();
        this.Salary=RandomUtils.getRandomNum(1000,10000).toString().getBytes();
    }
}
