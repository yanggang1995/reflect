package com.metadata.yg.transform;

import java.util.List;

public interface MetadataTransform {
    List getFormatRow(List<byte []> row);
}
