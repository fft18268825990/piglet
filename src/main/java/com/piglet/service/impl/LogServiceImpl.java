package com.piglet.service.impl;

import com.piglet.mapper.LogMapper;
import com.piglet.service.LogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class LogServiceImpl implements LogService {
    @Autowired
    LogMapper logMapper;

    @Override
    public int logCount() {
        return logMapper.logCount();
    }

    @Override
    public List<Map<String, Object>> logList(Map<String, Object> params) {
        if((String)params.get("page")!=null && (String)params.get("page")!=""
                &&(String)params.get("limit")!=null && (String)params.get("limit")!="") {
            int page = Integer.parseInt((String) params.get("page"));
            int limit = Integer.parseInt((String) params.get("limit"));
            params.put("offset", (page - 1 )* limit);
        }
        return logMapper.logList(params);
    }

    @Override
    public int edit(Map<String, Object> params) {
        return logMapper.edit(params);
    }
}
