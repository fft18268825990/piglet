package com.piglet.service;

import java.util.List;
import java.util.Map;

public interface LogService {
    int logCount();

    List<Map<String,Object>> logList(Map<String, Object> params);

    int edit(Map<String, Object> params);
}
