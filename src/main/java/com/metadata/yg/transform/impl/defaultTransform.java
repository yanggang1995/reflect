package com.metadata.yg.transform.impl;

import com.metadata.yg.common.People;
import com.metadata.yg.transform.MetadataTransform;

import java.util.ArrayList;
import java.util.List;

public class defaultTransform implements MetadataTransform {

    public List<Object> getFormatRow(List<byte []> row) {
        List<Object> formatList = new ArrayList<>();
/*        formatList.add("ygID"+row.get(0));
//        formatList.add("ygID"+row.get(1));
//        formatList.add("ygID"+row.get(2));
        formatList.add("ygID_yg");*/
        formatList.add(new People(10,"ygID"));
        return formatList;
    }
}

