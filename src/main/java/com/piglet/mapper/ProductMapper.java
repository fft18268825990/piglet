package com.piglet.mapper;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.piglet.domain.Product;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Map;
@Mapper
public interface ProductMapper extends BaseMapper<Product> {
    int proCount(Map<String, Object> params);

    List<Map<String,Object>> proList(Map<String, Object> params);

    int selectCount(Map<String, Object> params);

    int edit(Map<String, Object> params);
}
