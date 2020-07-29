package com.metadata.yg.utils;


import com.metadata.yg.transform.MetadataTransform;
import com.metadata.yg.transform.impl.defaultTransform;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Field;

import static com.metadata.yg.constant.Conf.COLUMN;
import static com.metadata.yg.constant.Conf.RADIX;
import static com.metadata.yg.constant.Conf.ROW;

/**
 * @author: Y.G
 * @description:
 * @create: 2018-12-01 17:19
 **/
public class ObjectUtils {
    private static final Logger logger = LoggerFactory.getLogger(ObjectUtils.class);

    public static MetadataTransform getTransform(String className) {
        try {
            Class<?> clazz = Class.forName(className);
            MetadataTransform executor = (MetadataTransform) clazz.newInstance();
            return executor;
        } catch (ClassNotFoundException e) {
            logger.error("未找到配置文件中的plugin类，将使用默认处理：" + e.getMessage());
        } catch (IllegalAccessException e) {
            logger.error("加载plugin类失败，将使用默认处理：" + e.getMessage());
        } catch (InstantiationException e) {
            logger.error("加载plugin类失败，将使用默认处理：" + e.getMessage());
        }
        return new defaultTransform();
    }

    public static byte[] getObjectValues(Object object) {
        byte[] row = "".getBytes();
        Class objectCla = object.getClass();
        Field[] fs = objectCla.getDeclaredFields();// 得到类中的所有属性集合
        for (int i = 0; i < fs.length; i++) {
            Field f = fs[i];
            f.setAccessible(true); // 设置些属性是可以访问的
            String val = "";
            try {
                val = new String((byte [])f.get(object));  // 得到此属性的值
            } catch (Exception e) {
                logger.error("未获取到" + objectCla.getName() + "属性" + f.getName() + "的值");
            }
            row = DataUtils.byteMerger(row, val.getBytes());
            if (i < fs.length - 1) {
                row = DataUtils.byteMerger(row, DataUtils.radixStr(COLUMN, RADIX));
            }
        }
        row = DataUtils.byteMerger(row, DataUtils.radixStr(ROW, RADIX));
        return row;
    }

    public static String getClassName(Object object){
        String [] classFullName=object.getClass().getName().split("\\.");
        return classFullName[classFullName.length-1].toUpperCase();
    }
}
