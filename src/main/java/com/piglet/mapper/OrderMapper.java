package com.piglet.mapper;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.piglet.domain.Order;
import com.piglet.domain.ResultLog;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Map;

@Mapper
public interface OrderMapper extends BaseMapper<Order> {
    void removeAll();

    void monthResult();

    List<Map<String,Object>> monthResultList();

    void saveLog(ResultLog resultLog);

    int resultCount();
}
