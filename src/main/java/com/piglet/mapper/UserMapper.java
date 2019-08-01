package com.piglet.mapper;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.piglet.domain.User;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Map;

@Mapper
public interface UserMapper extends BaseMapper<User> {
    List<User> userList(Map<String, Object> params);

    Integer userCount(Map<String, Object> params);

    int edit(Map<String, Object> params);

    List<Map<String,Object>> countlist(Map<String, Object> params);

    int icount(Integer userId);
}
