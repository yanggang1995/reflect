package com.metadata.yg.impl;

import com.metadata.yg.inf.MetadataExecutor;

import java.util.ArrayList;
import java.util.List;

public class nmggdExecutor implements MetadataExecutor {

    private List<String> formatList;

    public nmggdExecutor(){
        this.formatList=new ArrayList<String>();
    }

    public List<String> getFormatRow(List<String> metadata) {
        for(String data:metadata){
            formatList.add(data+"_0.0");
        }
        return formatList;
    }
}
