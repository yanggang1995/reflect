package com.metadata.yg.impl;

import com.metadata.yg.inf.MetadataExecutor;

import java.util.List;

public class XjcbcExecutor implements MetadataExecutor {
    @Override
    public List<String> getFormatRow(List<String> row) {
        return row;
    }
}
