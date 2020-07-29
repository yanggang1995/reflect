package com.metadata.yg.bjlt;

import com.metadata.yg.utils.RandomUtils;
import lombok.Data;

/**
 * @author: Y.G
 * @description: 增值业务基地收入情况
 * @create: 2018-12-27 17:43
 **/
@Data
public class ZIncomeinfo {
    private byte[] ProductID;
    private byte[] ProductName;
    private byte[] Fee;
    private byte[] provinceCode;
    private byte[] cityCode;

    public ZIncomeinfo(){
        this.Fee=(RandomUtils.getRandomNum(0,100) +".00").getBytes();
    }
}
