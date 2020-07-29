package com.metadata.yg.bjlt;

import com.metadata.yg.enums.STB;
import com.metadata.yg.utils.DateUtils;
import com.metadata.yg.utils.RandomUtils;
import lombok.Data;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import static com.metadata.yg.constant.Conf.EXETIME;
import static com.metadata.yg.utils.RandomUtils.getRandomNum;

/**
 * @author: Y.G
 * @description:  用户实时收视纪录
 * @create: 2018-12-27 18:29
 **/
@Data
public class UserInfoReal {
    private byte[] reportingTime;
    private byte[] provinceCode;
    private byte[] cityCode;
    private byte[] stbId;
    private byte[] businessId;
    private byte[] viewType;
    private byte[] programAssdress;
    private byte[] programAvgCodeRate;
    private byte[] StartviewTime;
    private byte[] viewTime;

    public UserInfoReal(){
        List<String> hhmiss_time = new ArrayList<>(Arrays.asList("071210","082436","094731","101117","114157","122327","112021","101213","051431","091631","071238","122048","112258","000000","050000","022131","071243","121111","010905","072457"));
        this.reportingTime=(EXETIME + RandomUtils.RandomListItem(hhmiss_time)).getBytes();
        this.stbId=(RandomUtils.getRandomString0(4) +STB.values()[getRandomNum(0,2)]+ RandomUtils.getRandomString0(2)).getBytes();
        this.viewType=(RandomUtils.RandomListItem(new ArrayList<>(Arrays.asList("点播","直播","回看")))).getBytes();
        this.programAssdress=(getRandomNum(2000,4000)+"kbps").getBytes();
        this.programAvgCodeRate="".getBytes();
        this.StartviewTime=(DateUtils.formatTime(new Date().getTime(),"yyyy-MM-dd HH:mm:ss") ).getBytes();
        this.viewTime=getRandomNum(0,1000).toString().getBytes();
    }
}
