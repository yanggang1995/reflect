package com.metadata.yg.impl;

import com.metadata.yg.inf.MetadataExecutor;

import java.util.ArrayList;
import java.util.List;

public class nmggdExecutor implements MetadataExecutor {

    private List<String> formatList;

    public nmggdExecutor(){
        this.formatList=new ArrayList<String>();
    }

    public String getFormatRow(String metadata) {
        return metadata+"_0.0";
    }
}
