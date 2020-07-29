package com.metadata.yg.bjlt;

import com.metadata.yg.enums.Surname;
import com.metadata.yg.utils.DateUtils;
import com.metadata.yg.utils.RandomUtils;
import lombok.Data;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

/**
 * @author: Y.G
 * @description: 增值业务基地视频内容同步接口
 * @create: 2018-12-27 17:18
 **/
@Data
public class ZContentC2 {
    private byte[] ContentID;
    private byte[] OriginalID;
    private byte[] Name;
    private byte[] ProgramType;
    private byte[] PictureType;
    private byte[] PictureUrl;
    private byte[] OriginalName;
    private byte[] ActorDisplay;
    private byte[] WriterDisplay;
    private byte[] OriginalCountry;
    private byte[] Language;
    private byte[] ReleaseYear;
    private byte[] OrgAirDate;
    private byte[] LicensingWindowsStart;
    private byte[] LicensingWindowEnd;
    private byte[] StartLevel;
    private byte[] KeyWords;
    private byte[] Length;
    private byte[] SeriesFlag;
    private byte[] ContentProvider;
    private byte[] ViewPoint;
    private byte[] Publisher;
    private byte[] provinceCode;
    private byte[] cityCode;

    public ZContentC2(){
        this.ProgramType=(RandomUtils.RandomListItem(new ArrayList<>(Arrays.asList("live","playback")))).getBytes();
        this.PictureType=(RandomUtils.RandomListItem(new ArrayList<>(Arrays.asList("live","playback")))).getBytes();
        this.PictureUrl=(RandomUtils.RandomListItem(new ArrayList<>(Arrays.asList("http://img1","http://img2")))).getBytes();
        this.ActorDisplay=(Surname.values()[RandomUtils.getRandomNum(0,100)]+"**").getBytes();
        this.WriterDisplay=(Surname.values()[RandomUtils.getRandomNum(0,100)]+"**").getBytes();
        this.OriginalCountry="".getBytes(); // TODO: 2018/12/28 国家地区
        this.Language=(RandomUtils.RandomListItem(new ArrayList<>(Arrays.asList("汉语","英语","日语","泰语")))).getBytes();
        this.ReleaseYear=DateUtils.formatTime(new Date().getTime(),"yyyy-MM-dd HH:mm:ss").getBytes();
        this.OrgAirDate=(DateUtils.formatTime(new Date().getTime(),"yyyy-MM-dd HH:mm:ss")).getBytes();
        List<String> expired_time = new ArrayList<>(Arrays.asList("07:12:10","08:24:36","09:47:31","10:11:17","11:41:57","12:43:27","14:20:21","13:12:13","14:14:31","15:16:31","17:12:38","18:20:48","19:22:58","00:00:00","20:00:00","21:21:31","23:12:43","23:11:11","20:09:05","18:24:57"));
        this.LicensingWindowsStart=(DateUtils.formatTime(new Date().getTime(),"yyyy-MM-dd") + " " +  RandomUtils.RandomListItem(expired_time)).getBytes();
        this.LicensingWindowEnd=(DateUtils.randomDateMinMaxFix("2019-01-01","2019-06-01") +" "+ RandomUtils.RandomListItem(expired_time)).getBytes();
        this.StartLevel=RandomUtils.getRandomNum(0,6).toString().getBytes();
        this.KeyWords=(RandomUtils.RandomListItem(new ArrayList<>(Arrays.asList("动作","喜剧","恐怖","悬疑","战争")))).getBytes();
        this.Length=RandomUtils.getRandomNum(10,121).toString().getBytes();
        this.SeriesFlag=(RandomUtils.RandomListItem(new ArrayList<>(Arrays.asList("动作","喜剧","恐怖","悬疑","战争")))).getBytes();
        this.ContentProvider=(RandomUtils.RandomListItem(new ArrayList<>(Arrays.asList("芒果tv","华为","中兴")))).getBytes();
        this.ViewPoint="".getBytes();
        this.Publisher=(RandomUtils.RandomListItem(new ArrayList<>(Arrays.asList("芒果tv","华为","中兴")))).getBytes();
        this.provinceCode="".getBytes(); // TODO: 2018/12/28  省地区码
        this.cityCode="".getBytes(); // TODO: 2018/12/28  市地区码
    }
}
