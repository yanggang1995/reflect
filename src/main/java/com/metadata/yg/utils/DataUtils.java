package com.metadata.yg.utils;

import com.google.common.primitives.Bytes;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.security.MessageDigest;
import java.util.Map;
import java.util.Random;
import java.util.stream.Collectors;

/**
 * @author: Y.G
 * @description:
 * @create: 2018-12-01 18:33
 **/
public class DataUtils {

    private static final Logger logger = LoggerFactory.getLogger(DataUtils.class);

    public static byte[] radix16To2(String str) {
        byte[] b = new byte[str.length() / 2];
        for (int i = 0; i < b.length; i++) {
            b[i] = ((byte) Integer.parseInt(str.substring(i * 2, (1 + i) * 2), 16));
        }
        return b;
    }

    public static byte[] radixStr(String str, int radix) {
        switch (radix) {
            case 8:
                return str.getBytes();
            case 16:
                return radix16To2(str);
            case 10:
                return str.getBytes();
            default:
                logger.info("未知的进制，默认为十进制");
                return str.getBytes();
        }
    }

    public static byte[] byteMerger(byte[] bt1, byte[] bt2) {
        byte[] bt3 = new byte[bt1.length + bt2.length];
        System.arraycopy(bt1, 0, bt3, 0, bt1.length);
        System.arraycopy(bt2, 0, bt3, bt1.length, bt2.length);
        return bt3;
    }

    /**
     * 用于获取一个String的md5值
     *
     * @param str
     * @return
     */
    public static String getMd5(String str) {
        MessageDigest md5 = null;
        try {
            md5 = MessageDigest.getInstance("MD5");
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return Bytes.asList(md5.digest(str.getBytes()))
                .stream()
                .map(x -> {
                    if ((x & 0xff) >> 4 == 0) {
                        return "0" + Integer.toHexString(x & 0xff);
                    } else {
                        return Integer.toHexString(x & 0xff);
                    }
                })
                .collect(Collectors.joining()).substring(8, 24);
    }

    public static int getMapMaxVal(Map loop) {
        int max = 0;
        for (Object key : loop.keySet()) {
            int tmp = (int) loop.get(key.toString());
            if (tmp > max) {
                max = tmp;
            }
        }
        return max;
    }

    public static String getKeyString(Map loop) {
        StringBuffer path = new StringBuffer();
        for (Object key : loop.keySet()) {
//            if(index<(int)loop.get(key)) {
            path.append(key);
            path.append("/");
//            }
        }
        return (path.length() > 0 ? path.deleteCharAt(path.length() - 1) : path).toString();
    }

    public static String getIp() {
        int[][] range = {{607649792, 608174079}, // 36.56.0.0-36.63.255.255
                {1038614528, 1039007743}, // 61.232.0.0-61.237.255.255
                {1783627776, 1784676351}, // 106.80.0.0-106.95.255.255
                {2035023872, 2035154943}, // 121.76.0.0-121.77.255.255
                {2078801920, 2079064063}, // 123.232.0.0-123.235.255.255
                {-1950089216, -1948778497}, // 139.196.0.0-139.215.255.255
                {-1425539072, -1425014785}, // 171.8.0.0-171.15.255.255
                {-1236271104, -1235419137}, // 182.80.0.0-182.92.255.255
                {-770113536, -768606209}, // 210.25.0.0-210.47.255.255
                {-569376768, -564133889}, // 222.16.0.0-222.95.255.255
        };

        Random rdint = new Random();
        int index = rdint.nextInt(10);
        int ip = range[index][0] + new Random().nextInt(range[index][1] - range[index][0]);
        int[] b = new int[4];
        b[0] = (ip >> 24) & 0xff;
        b[1] = (ip >> 16) & 0xff;
        b[2] = (ip >> 8) & 0xff;
        b[3] = ip & 0xff;
        return Integer.toString(b[0]) + "." + Integer.toString(b[1]) + "." + Integer.toString(b[2]) + "." + Integer.toString(b[3]);
    }

    public static String getServiceName(int i) {
        switch (i) {
            case 0:
                return "游戏";
            case 1:
                return "生活";
            case 2:
                return "电影";
            case 3:
                return "电视剧";
            case 4:
                return "娱乐";
            default:
                return "服务";
        }
    }

    public static void main(String[] args) {
        System.out.println(getIp());
    }
}
