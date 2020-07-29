package com.metadata.yg.bjlt;

import com.metadata.yg.enums.SERVICE;
import com.metadata.yg.enums.STB;
import com.metadata.yg.enums.Surname;
import com.metadata.yg.utils.DateUtils;
import com.metadata.yg.utils.RandomUtils;
import lombok.Data;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static com.metadata.yg.utils.RandomUtils.getRandomNum;

/**
 * @author: Y.G
 * @description:
 * @create: 2018-12-27 15:17
 **/
@Data
public class UserInfo {
    private byte[] UserID;
    private byte[] UserGroup;
    private byte[] EPGgroup;
    private byte[] UserName;
    private byte[] STBID;
    private byte[] IDNumber;
    private byte[] Phone;
    private byte[] Sex;
    private byte[] Address;
    private byte[] PostalCode;
    private byte[] Email;
    private byte[] Areacode;
    private byte[] Services;
    private byte[] TimeStamp;
    private byte[] Products;
    private byte[] UpdateTime;
    private byte[] IsHDUser;
    private byte[] TerminalType;
    private byte[] Platform;
    private byte[] TerminalModel;
    private byte[] Status;
    private byte[] DateCreated;
    private byte[] DateActivated;
    private byte[] DateSuspended;
    private byte[] DateCancelled;

    public UserInfo(){
        this.UserGroup=("0"+getRandomNum(0,3)).getBytes();
        this.EPGgroup=getRandomNum(0,10).toString().getBytes();
        this.UserName=(Surname.values()[getRandomNum(0,498)] +"**").getBytes();
        this.STBID=( RandomUtils.getRandomString0(4) +STB.values()[getRandomNum(0,2)]+ RandomUtils.getRandomString0(2)).getBytes();
        this.IDNumber=(RandomUtils.getRandomString0(18)).getBytes();
        this.Phone=(RandomUtils.RandomListItem(new ArrayList<>(Arrays.asList("155","177","131"))) + RandomUtils.getRandomString0(8)).getBytes();
        this.Sex=RandomUtils.getRandomNum(0,2).toString().getBytes();
        this.Email=(RandomUtils.getRandomString2(6)+new ArrayList<>(Arrays.asList("@163.com","@162.com","gmail.com")).get(getRandomNum(0,3))).getBytes();
        this.Services=SERVICE.values()[getRandomNum(0,6)].toString().getBytes();
        String timeStamp = DateUtils.randomDateMinMax("20180601","20180901");
        this.TimeStamp=(DateUtils.date2Timestamp(timeStamp,"yyyyMMdd")).getBytes();
        List<String> productsName0 = new ArrayList<>(Arrays.asList("0元包","5元包","10元包","20元包","15元包","50元包","100元包","200元包","500元包","1000元包"));
        List<String> productsName1 = new ArrayList<>(Arrays.asList("游戏道具","热门点播","玖杨书画","流行音乐","超值游戏","知识课堂","名门课堂","家庭教育"));
        this.Products=(RandomUtils.RandomListItem(productsName1) + RandomUtils.RandomListItem(productsName0)).getBytes();
        List<String> hhmiss_time = new ArrayList<>(Arrays.asList("071210","082436","094731","101117","114157","124327","142021","131213","141431","151631","171238","182048","192258","000000","200000","212131","231243","231111","200905","182457"));
        this.UpdateTime=(DateUtils.randomDateMinMax1("20180701","20181201") + RandomUtils.RandomListItem(hhmiss_time)).getBytes();
        this.IsHDUser=RandomUtils.getRandomNum(0,2).toString().getBytes();
        this.TerminalType=RandomUtils.getRandomNum(1,5).toString().getBytes();
        this.Platform=RandomUtils.getRandomNum(1,6).toString().getBytes();
        List<String> TerminalModel = new ArrayList<>(Arrays.asList("DVC-8058","DVB-C8000HSC(NZ)","IP_4KSC","CHIPTV-SC001","DVC-7078C","DVC-8188","DVC-7078","DVB-C9000SCY","DVB-C8000HSC","OTS-4KSC","N9201-V200","HC3200","HMC3000","HC2600","coship","JY-HDC61F","TVOS-UTI-4K","0x0","DVB-C8000HSC(UZ)","HDC691090","EL3","N8808","EL4","NULL","HC3100","DVB-C9000SCS","DVB-C9000SCS","PTV-8698","GM102","Hi3798MV200","E900-S"));
        this.TerminalModel=(RandomUtils.RandomListItem(TerminalModel)).getBytes();
        String status="";
        String dateCreated="";
        String dateActivated="";
        String dateSuspended="";
        String dateCancelled="";
        switch(RandomUtils.getRandomNum(0,5)){
            case 0:
                status="0"; //Status 状态类型：0:开户 1: 激活 2: 停机 3: 拆机 4:欠费
                dateCreated=DateUtils.randomDateMinMax1("20180701","20180801") + RandomUtils.RandomListItem(hhmiss_time); // dateCreated    status = 0
                dateActivated="";// dateActivated  null status = 1 4
                dateSuspended="";// dateSuspended  null status = 2
                dateCancelled="";// dateCancelled  null status = 3
                break;
            case 1:
                status="1"; //Status 状态类型：0:开户 1: 激活 2: 停机 3: 拆机 4:欠费
                dateCreated=""; // dateCreated    status = 0
                dateActivated=DateUtils.randomDateMinMax1("20180701","20180801") + RandomUtils.RandomListItem(hhmiss_time); // dateActivated  null status = 1 4
                dateSuspended=""; // dateSuspended  null status = 2
                dateCancelled=""; // dateCancelled  null status = 3
                break;
            case 2:
                status="2"; //Status 状态类型：0:开户 1: 激活 2: 停机 3: 拆机 4:欠费
                dateCreated=""; // dateCreated    status = 0
                dateActivated=""; // dateActivated  null status = 1 4
                dateSuspended=DateUtils.randomDateMinMax1("20180701","20180901") + RandomUtils.RandomListItem(hhmiss_time); // dateSuspended  null status = 2
                dateCancelled=""; // dateCancelled  null status = 3
                break;
            case 3:
                status="3"; //Status 状态类型：0:开户 1: 激活 2: 停机 3: 拆机 4:欠费
                dateCreated=""; // dateCreated    status = 0
                dateActivated=""; // dateActivated  null status = 1 4
                dateSuspended=""; // dateSuspended  null status = 2
                dateCancelled=DateUtils.randomDateMinMax1("20180701","20181001") + RandomUtils.RandomListItem(hhmiss_time); // dateCancelled  null status = 3
                break;
            case 4:
                status="4"; //Status 状态类型：0:开户 1: 激活 2: 停机 3: 拆机 4:欠费
                dateCreated=""; // dateCreated    status = 0
                dateActivated=DateUtils.randomDateMinMax1("20180701","20180801") + RandomUtils.RandomListItem(hhmiss_time);// dateActivated  null status = 1 4
                dateSuspended=""; // dateSuspended  null status = 2
                dateCancelled=""; // dateCancelled  null status = 3
                break;
        }
        this.Status=(status).getBytes();
        this.DateCreated=(dateCreated).getBytes();
        this.DateActivated=(dateActivated).getBytes();
        this.DateSuspended=(dateSuspended).getBytes();
        this.DateCancelled=(dateCancelled).getBytes();
    }
}
