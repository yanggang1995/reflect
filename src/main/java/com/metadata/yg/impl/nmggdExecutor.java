package com.metadata.yg.impl;

import com.metadata.yg.inf.MetadataExecutor;

import java.util.ArrayList;
import java.util.List;

public class nmggdExecutor implements MetadataExecutor {

    public List<String> getFormatRow(List<String> row) {
        List<String> formatList = new ArrayList<>();
        formatList.add("ygID"+row.get(0));
//        formatList.add("ygID"+row.get(1));
//        formatList.add("ygID"+row.get(2));
        formatList.add("ygID_yg");
        return formatList;
    }
}
