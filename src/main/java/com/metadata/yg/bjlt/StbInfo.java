package com.metadata.yg.bjlt;

import com.metadata.yg.enums.STB;
import com.metadata.yg.utils.DataUtils;
import com.metadata.yg.utils.RandomUtils;
import lombok.Data;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static com.metadata.yg.constant.Conf.EXETIME;
import static com.metadata.yg.utils.RandomUtils.getRandomNum;

/**
 * @author: Y.G
 * @description: 用户STB终端数据
 * @create: 2018-12-27 18:02
 **/
@Data
public class StbInfo {
    private byte[] reportingTime;
    private byte[] provinceCode;
    private byte[] cityCode;
    private byte[] stbId;
    private byte[] businessId;
    private byte[] manufaceturerName;
    private byte[] stbModel;
    private byte[] stbMac;
    private byte[] stbsoftwareVersion;
    private byte[] stbHardwareVsersion;
    private byte[] networkAccess;
    private byte[] networkConnction;
    private byte[] WIFITechnicalSpecifications;
    private byte[] WIFIAccessStrength;
    private byte[] stbUpGatewayMAC;
    private byte[] screenSize;
    private byte[] screenResolution;

    public StbInfo(){
        List<String> hhmiss_time = new ArrayList<>(Arrays.asList("071210","082436","094731","101117","114157","122327","112021","101213","051431","091631","071238","122048","112258","000000","050000","022131","071243","121111","010905","072457"));
        this.reportingTime=(EXETIME + RandomUtils.RandomListItem(hhmiss_time)).getBytes();
        this.stbId=(RandomUtils.getRandomString0(4) +STB.values()[getRandomNum(0,2)]+ RandomUtils.getRandomString0(2)).getBytes();
        this.manufaceturerName=(RandomUtils.RandomListItem(new ArrayList<>(Arrays.asList("中兴","华为")))).getBytes();
        this.stbModel=(RandomUtils.getRandomString0(4) +STB.values()[getRandomNum(0,2)]+ RandomUtils.getRandomString0(2)).getBytes();
        this.stbMac=(DataUtils.getMd5(RandomUtils.getRandomString0(4) +STB.values()[getRandomNum(0,2)]+ RandomUtils.getRandomString0(2)) ).getBytes();
        this.stbsoftwareVersion=(getRandomNum(1,10)+"."+getRandomNum(0,10)+"."+getRandomNum(0,10)).getBytes();
        this.stbHardwareVsersion=(getRandomNum(1,10)+"."+getRandomNum(0,10)+"."+getRandomNum(0,10)).getBytes();
        this.networkAccess=(RandomUtils.RandomListItem(new ArrayList<>(Arrays.asList("IPoE","DHCP","PPPOE","STATIC")))).getBytes();
        this.networkConnction=(RandomUtils.RandomListItem(new ArrayList<>(Arrays.asList("LAN","WiFi")))).getBytes();
        this.WIFITechnicalSpecifications=(RandomUtils.RandomListItem(new ArrayList<>(Arrays.asList("802.ln","802.11ac","1x1","2x2")))).getBytes();
        this.WIFIAccessStrength=("-"+RandomUtils.getRandomNum(40,86)+"dbm").getBytes();
        this.stbUpGatewayMAC=(DataUtils.getMd5(RandomUtils.getRandomString0(4) +STB.values()[getRandomNum(0,2)]+ RandomUtils.getRandomString0(2)) ).getBytes();
        this.screenSize=getRandomNum(10,24).toString().getBytes();
        this.screenResolution=(RandomUtils.RandomListItem(new ArrayList<>(Arrays.asList("1920x1080","1680x1050","1600x900","1440x1050")))).getBytes();
    }
}
