package com.metadata.yg.bjlt;

import com.metadata.yg.utils.DataUtils;
import com.metadata.yg.utils.DateUtils;
import com.metadata.yg.utils.RandomUtils;
import lombok.Data;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

/**
 * @author: Y.G
 * @description:
 * @create: 2018-12-27 16:58
 **/
@Data
public class Schedule {
    private byte[] ChannelCode;
    private byte[] ChannelName;
    private byte[] ProgramName;
    private byte[] StartTime;
    private byte[] EndTime;
    private byte[] Schedulecode;

    public Schedule(){
        List<String> channelName = new ArrayList<>(Arrays.asList("中国教育台-1","新疆卫视","福建东南卫视高清","劲爆体育高清","贵州卫视高清","江苏卫视高清","四川卫视高清","CCTV_1","CCTV_2","CCTV_3","CCTV_4","CCTV_5","CCTV_6","CCTV_7","CCTV_8","CCTV_9","CCTV_10"));
        List<String> programName = new ArrayList<>(Arrays.asList("上海三打一(1179)","转播中央台新闻联播","名篇佳作公开课(2)","大院子女(8)","导视","水浒传(38)","雄风剧场","特警力量(50)","一树桃花开(23)","四川新闻","东南早间天气预报","你好爸爸","国学小课堂:拔苗助长","退休好生活","中国福利彩票公益开奖","动画片","广宣时段"));
        int random = RandomUtils.getRandomNum(0,24);
        String channel=RandomUtils.RandomListItem(channelName);
        String program=RandomUtils.RandomListItem(programName);
        this.ChannelCode=DataUtils.getMd5(channel).getBytes();
        this.ChannelName=(channel).getBytes();
        this.ProgramName=(program).getBytes();
        this.StartTime=((DateUtils.formatTimeTo24(new Date())+1000*60*60*random)+"").getBytes();
        this.EndTime=(DateUtils.formatTimeTo24(new Date())+1000*60*60*(24-random)+"").getBytes();
        this.Schedulecode=(DataUtils.getMd5(program)).getBytes();
    }
}
