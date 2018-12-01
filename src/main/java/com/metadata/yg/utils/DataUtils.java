package com.metadata.yg.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

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

    public static byte[] radixStr(String str,int radix){
        switch (radix){
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

    public static byte[] byteMerger(byte[] bt1, byte[] bt2){
        byte[] bt3 = new byte[bt1.length+bt2.length];
        System.arraycopy(bt1, 0, bt3, 0, bt1.length);
        System.arraycopy(bt2, 0, bt3, bt1.length, bt2.length);
        return bt3;
    }
}
