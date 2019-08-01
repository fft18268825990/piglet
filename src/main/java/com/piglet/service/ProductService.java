package com.piglet.service;

import com.piglet.domain.Product;

import java.io.InputStream;
import java.util.List;
import java.util.Map;

public interface ProductService {
    int proCount(Map<String, Object> params);

    List<Map<String, Object>> proList(Map<String, Object> params);

    int selectCount(Map<String, Object> params);

    int save(Product product);

    int edit(Map<String, Object> params);

    void importExcel(InputStream is, String originalFilename, Integer userId);
}
