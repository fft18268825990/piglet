package com.piglet.service;

import java.io.InputStream;
import java.util.List;
import java.util.Map;

public interface OrderService {

    void excel(InputStream is, String originalFilename ,Integer userId);

    int resultCount();

    List<Map<String,Object>> resultList();
}
