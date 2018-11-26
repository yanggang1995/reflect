package com.metadata.yg.impl;

import com.metadata.yg.inf.MetadataExecutor;

public class XjcbcExecutor implements MetadataExecutor {
    @Override
    public String getFormatRow(String line) {
        return line+"*_*";
    }
}
