package com.metadata.yg.transform.impl;

import com.jayway.jsonpath.JsonPath;
import com.metadata.yg.bjlt.*;
import com.metadata.yg.constant.Conf;
import com.metadata.yg.transform.MetadataTransform;
import com.metadata.yg.utils.DataUtils;
import com.metadata.yg.utils.RandomUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.IOException;
import java.util.*;
import java.util.concurrent.locks.ReentrantReadWriteLock;
import java.util.stream.Collectors;

import static com.metadata.yg.utils.RandomUtils.getRandomNum;

/**
 * @author: Y.G
 * @description:
 * @create: 2018-12-27 18:42
 **/
public class BJLTTransform implements MetadataTransform {
    private static Logger logger = LoggerFactory.getLogger(BJLTTransform.class);
    private static LinkedHashMap tableNums;
    private static List products;
    private static List zProducts;
    private Map tableMap = new HashMap();
    public int index = 0;
    private ReentrantReadWriteLock readWriteLock = new ReentrantReadWriteLock();//读写锁
    private ReentrantReadWriteLock.WriteLock writeLock = readWriteLock.writeLock();
    List<String> hbAddrCity = new ArrayList<>(Arrays.asList("石家庄市", "唐山市", "邯郸市", "秦皇岛市", "保定市", "张家口市", "承德市", "廊坊市", "沧州市", "衡水市", "邢台市"));
    List<String> addr = new ArrayList<>(Arrays.asList("北京市", "天津市", "重庆市", "上海市", "河北省" + RandomUtils.RandomListItem(hbAddrCity)));


    static {
        try {
            tableNums = JsonPath.read(new File(Conf.TABLENUMS), "$");
            products = JsonPath.read(new File(Conf.PRODUCTSPATH), "$.[*]");
            zProducts = JsonPath.read(new File(Conf.ZPRODUCTSPATH), "$.[*]");
        } catch (IOException e) {
            e.printStackTrace();
            logger.error("product.json文件格式异常");
        }
    }

    @Override
    public List getFormatRow(List<byte[]> row) {

        String user_id = DataUtils.getMd5(index<900000?index+"":getRandomNum(900000, 1000000).toString()) + "@IPTV";
        String bussinessId = (getRandomNum(1, 1000000) + 10000)+"";
        Map productMap = (Map) products.get(index<1000?index:getRandomNum(1000, products.size()));
        Map zproductMap = (Map) zProducts.get(index<1000?index:getRandomNum(1000,zProducts.size()));
        String contentCode = productMap.get("product_id").toString();  // TODO: 2018/12/28 内容ID
        String contentName = productMap.get("product_name").toString(); // TODO: 2018/12/28 内容名称
        Long loginTime = new Date().getTime();
        int randomTime = RandomUtils.getRandomNum(0, 9);
        String orderId = DataUtils.getMd5(RandomUtils.getRandomNum(0, 1000000) + "order");

        String addrInfo = RandomUtils.RandomListItem(addr);

        String provinceCode = "";
        String postalCode = "";// 字段10  postalCode
        String areacode = "";// 字段12  Areacode
        if (addrInfo != null && addrInfo != "") {
            switch (addrInfo) {
                case "北京市":
                    provinceCode = "101";
                    postalCode = "100000";
                    areacode = "10100";
                    break;
                case "上海市":
                    provinceCode = "102";
                    postalCode = "200000";
                    areacode = "10201";
                    break;
                case "天津市":
                    provinceCode = "113";
                    postalCode = "300000";
                    areacode = "11301";
                    break;
                case "重庆市":
                    provinceCode = "104";
                    postalCode = "400000";
                    areacode = "10401";
                    break;
                case "河北省石家庄市":
                    provinceCode = "127";
                    postalCode = "050000";
                    areacode = "12701";
                    break;
                case "河北省唐山市":
                    provinceCode = "127";
                    postalCode = "063000";
                    areacode = "12702";
                    break;
                case "河北省邯郸市":
                    provinceCode = "127";
                    postalCode = "056000";
                    areacode = "12704";
                    break;
                case "河北省秦皇岛市":
                    provinceCode = "127";
                    postalCode = "066000";
                    areacode = "12703";
                    break;
                case "河北省保定市":
                    provinceCode = "127";
                    postalCode = "071000";
                    areacode = "12706";
                    break;
                case "河北省张家口市":
                    provinceCode = "127";
                    postalCode = "071000";
                    areacode = "12707";
                    break;
                case "河北省承德市":
                    provinceCode = "127";
                    postalCode = "067000";
                    areacode = "12708";
                    break;
                case "河北省廊坊市":
                    provinceCode = "127";
                    postalCode = "065000";
                    areacode = "12710";
                    break;
                case "河北省沧州市":
                    provinceCode = "127";
                    postalCode = "061000";
                    areacode = "12709";
                    break;
                case "河北省衡水市":
                    provinceCode = "127";
                    postalCode = "053000";
                    areacode = "12711";
                    break;
                case "河北省邢台市":
                    provinceCode = "127";
                    postalCode = "054000";
                    areacode = "12705";
                    break;
                default:
                    postalCode = "";
                    break;
            }
        }


        List rowList = new ArrayList();

        //用户信息数据
        UserInfo userInfo = new UserInfo();
        userInfo.setUserID(user_id.getBytes());
        userInfo.setAddress(provinceCode.getBytes());
        userInfo.setPostalCode(postalCode.getBytes());
        userInfo.setAreacode(areacode.getBytes());
        //用户登陆数据
        UserLogonInfo userLogonInfo = new UserLogonInfo(loginTime, randomTime);
        userLogonInfo.setUserID(user_id.getBytes());
        //用户销户数据
        UserlogoutInfo userlogoutInfo = new UserlogoutInfo(loginTime, randomTime);
        userlogoutInfo.setUserID(user_id.getBytes());
        //内容收视信息
        Contentviewlog contentviewlog = new Contentviewlog();
        contentviewlog.setUserID(user_id.getBytes());
        contentviewlog.setContentCode(contentCode.getBytes());
        contentviewlog.setContentName(contentName.getBytes());

        //产品信息
        ProductInfo productInfo = new ProductInfo();
        productInfo.setProduct_id(productMap.get("product_id").toString().getBytes());
        productInfo.setProduct_name(productMap.get("product_name").toString().getBytes());
        productInfo.setProduct_name_en(productMap.get("product_name_en").toString().getBytes());
        productInfo.setProduct_type(productMap.get("product_type").toString().getBytes());
        productInfo.setPro_des_info(productMap.get("pro_des_info").toString().getBytes());
        productInfo.setPrice(productMap.get("price").toString().getBytes());

        //用户订购消费信息
        Orderlog orderlog = new Orderlog();
        orderlog.setUserID(user_id.getBytes());
        orderlog.setContentCode(contentCode.getBytes());
        orderlog.setContentName(contentName.getBytes());
        orderlog.setProductID(productMap.get("product_id").toString().getBytes());
        orderlog.setProductName(productMap.get("product_name").toString().getBytes());
        orderlog.setOrderID(orderId.getBytes());

        //直播节目单数据
        Schedule schedule = new Schedule();

        //点播节目信息同步接口
        ContentC2 contentC2 = new ContentC2();
        contentC2.setContentID(contentCode.getBytes());
        contentC2.setName(contentName.getBytes());
        contentC2.setOriginalID(contentCode.getBytes());
        contentC2.setOriginalName(contentName.getBytes());
        contentC2.setProvinceCode(provinceCode.getBytes());
        contentC2.setProvinceCode(areacode.getBytes());
        contentC2.setCityCode(areacode.getBytes());
        //用户EPG页面操作行为数据
        UserOperEpgInfo userOperEpgInfo = new UserOperEpgInfo();
        userOperEpgInfo.setUserID(user_id.getBytes());

        //增值业务运营基地产品信息数据
        ZProductInfo zProductInfo = new ZProductInfo();
        zProductInfo.setProduct_id(zproductMap.get("product_id").toString().getBytes());
        zProductInfo.setProduct_name(zproductMap.get("product_name").toString().getBytes());
        zProductInfo.setProduct_name_en(zproductMap.get("product_name_en").toString().getBytes());
        zProductInfo.setProduct_type(zproductMap.get("product_type").toString().getBytes());
        zProductInfo.setPro_des_info(zproductMap.get("pro_des_info").toString().getBytes());
        zProductInfo.setPrice(zproductMap.get("price").toString().getBytes());
        zProductInfo.setProvinceCode(provinceCode.getBytes());
        zProductInfo.setCityCode(areacode.getBytes());

        //增值业务基地视频内容同步接口
        ZContentC2 zContentC2 = new ZContentC2();
        zContentC2.setContentID(contentCode.getBytes());
        zContentC2.setName(contentName.getBytes());
        zContentC2.setOriginalID(contentCode.getBytes());
        zContentC2.setOriginalName(contentName.getBytes());
        zContentC2.setProvinceCode(provinceCode.getBytes());
        zContentC2.setCityCode(areacode .getBytes());

        //增值业务基地订购业务数据
        ZuserInfo zuserInfo = new ZuserInfo();
        zuserInfo.setUserID(user_id.getBytes());
        zuserInfo.setContentCode(contentCode.getBytes());
        zuserInfo.setContentName(contentName.getBytes());
        zuserInfo.setProductID(zproductMap.get("product_id").toString().getBytes());
        zuserInfo.setProductName(zproductMap.get("product_name").toString().getBytes());
        zuserInfo.setOrderID(orderId.getBytes());
        zuserInfo.setProvinceCode(provinceCode.getBytes());
        zuserInfo.setCityCode(areacode.getBytes());

        //增值业务运营基地内容收视信息
        ZContentviewlog zContentviewlog = new ZContentviewlog();
        zContentviewlog.setUserID(user_id.getBytes());
        zContentviewlog.setContentCode(contentCode.getBytes());
        zContentviewlog.setContentName(contentName.getBytes());

        //增值业务基地消费情况
        ZIncomeinfo zIncomeinfo = new ZIncomeinfo();
        zIncomeinfo.setProductID(zproductMap.get("product_id").toString().getBytes());
        zIncomeinfo.setProductName(zproductMap.get("product_name").toString().getBytes());
        zIncomeinfo.setProvinceCode(provinceCode.getBytes());
        zIncomeinfo.setCityCode(areacode.getBytes());

        //增值业务基地重大营销事件
        Marketlog marketlog = new Marketlog();
        marketlog.setProductID(zproductMap.get("product_id").toString().getBytes());
        marketlog.setProvinceCode(provinceCode.getBytes());
        marketlog.setCityCode(areacode.getBytes());

        //B侧用户信息数据
        BUserinfo bUserinfo = new BUserinfo();
        bUserinfo.setUser_ID(user_id.getBytes());
        bUserinfo.setAccount_ID(DataUtils.getMd5(user_id).getBytes());
        bUserinfo.setIPTV_Account_ID((DataUtils.getMd5(user_id) + "@IPTV").getBytes());
        bUserinfo.setProduct_id(zproductMap.get("product_id").toString().getBytes());
        bUserinfo.setProduct_type(zproductMap.get("product_type").toString().getBytes());
        bUserinfo.setRegion_Code(areacode.getBytes());
        bUserinfo.setBoss_Area_Bind(areacode.getBytes());

        //B侧经营业务分析系统业务数据
        BasInfo basInfo = new BasInfo();
        basInfo.setUser_ID(user_id.getBytes());
        basInfo.setAccount_ID(DataUtils.getMd5(user_id).getBytes());
        basInfo.setIPTV_Account_ID((DataUtils.getMd5(user_id) + "@IPTV").getBytes());
        basInfo.setUser_Addr(areacode.getBytes());
        basInfo.setRegion_Code(areacode.getBytes());
        basInfo.setBoss_Area_Bind(areacode.getBytes());


        //B侧统计数据采集
        BcountInfo bcountInfo = new BcountInfo();
        bcountInfo.setIPTVUser(user_id.getBytes());

        //用户STB终端数据
        StbInfo stbInfo = new StbInfo();
        stbInfo.setBusinessId(bussinessId.getBytes());
        stbInfo.setProvinceCode(provinceCode.getBytes());
        stbInfo.setCityCode(areacode.getBytes());

        //用户视频质量数据
        Videoqualitylog videoqualitylog = new Videoqualitylog();
        videoqualitylog.setBussinessId(bussinessId.getBytes());
        videoqualitylog.setProvinceCode(provinceCode.getBytes());
        videoqualitylog.setCityCode(areacode.getBytes());

        //用户实时收视记录
        UserInfoReal userInfoReal = new UserInfoReal();
        userInfoReal.setBusinessId(bussinessId.getBytes());
        userInfoReal.setProvinceCode(provinceCode.getBytes());
        userInfoReal.setCityCode(areacode.getBytes());

        INSERV inserv = new INSERV();
        inserv.setProvinceCode(provinceCode.getBytes());
        inserv.setCityCode(areacode.getBytes());
        inserv.setBusinessId(bussinessId.getBytes());

        tableMap.put("USERINFO", userInfo);
        tableMap.put("USERLOGONINFO", userLogonInfo);
        tableMap.put("USERLOGOUTINFO", userlogoutInfo);
        tableMap.put("CONTENTVIEWLOG", contentviewlog);
        tableMap.put("PRODUCTINFO", productInfo);
        tableMap.put("ORDERLOG", orderlog);
        tableMap.put("SCHEDULE", schedule);
        tableMap.put("CONTENTC2", contentC2);
        tableMap.put("USEROPEREPGINFO", userOperEpgInfo);
        tableMap.put("ZPRODUCTINFO", zProductInfo);
        tableMap.put("ZCONTENTC2", zContentC2);
        tableMap.put("ZUSERINFO", zuserInfo);
        tableMap.put("ZCONTENTVIEWLOG", zContentviewlog);
        tableMap.put("ZINCOMEINFO", zIncomeinfo);
        tableMap.put("MARKETLOG", marketlog);
        tableMap.put("BUSERINFO", bUserinfo);
        tableMap.put("BASINFO", basInfo);
        tableMap.put("BCOUNTINFO", bcountInfo);
        tableMap.put("STBINFO", stbInfo);
        tableMap.put("VIDEOQUALITYLOG", videoqualitylog);
        tableMap.put("USERINFOREAL", userInfoReal);
        tableMap.put("INSERV", inserv);

        try {
            writeLock.lock();
            return (List)tableNums.keySet()
                    .stream()
                    .map(key -> {
                                if ((int) tableNums.get(key) > index) {
                                    Object object = tableMap.get(((String) key).toUpperCase());
                                    if(object==null){
                                        return  new Integer(0);
                                    }
                                    return object;
                                } else {
                                    return new Integer(0);
                                }
                            }
                    )
                    .collect(Collectors.toList());
        } finally {
            index++;
            writeLock.unlock();
        }
    }
}
