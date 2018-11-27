package com.metadata.yg.inf;

import java.util.List;

public interface CallBack<T> {
    T call(int num, List<String> str);
}
