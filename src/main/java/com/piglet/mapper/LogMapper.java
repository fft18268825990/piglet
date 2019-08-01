package com.piglet.mapper;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.piglet.domain.ResultLog;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Map;

@Mapper
public interface LogMapper extends BaseMapper<ResultLog> {

    int logCount();

    List<Map<String,Object>> logList(Map<String, Object> params);

    int edit(Map<String, Object> params);
}
