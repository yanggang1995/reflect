package com.metadata.yg.transform.impl;

import com.metadata.yg.common.nns_user;
import com.metadata.yg.transform.MetadataTransform;

import java.util.ArrayList;
import java.util.List;

public class defaultTransform implements MetadataTransform {
    private nns_user nns_user= new nns_user();

    public List getFormatRow(List<byte []> row) {
        List formatList = new ArrayList<>();
        nns_user.setNns_id(row.get(0));
        return formatList;
    }
}

