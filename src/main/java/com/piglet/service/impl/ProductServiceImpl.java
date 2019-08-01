package com.piglet.service.impl;

import com.piglet.domain.Product;
import com.piglet.mapper.ProductMapper;
import com.piglet.service.ProductService;
import com.piglet.util.Excel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

@Service
public class ProductServiceImpl implements ProductService{
    @Autowired
    ProductMapper productMapper;

    @Override
    public int proCount(Map<String, Object> params) {
        return productMapper.proCount(params);
    }

    @Override
    public List<Map<String, Object>> proList(Map<String, Object> params) {
        if((String)params.get("page")!=null && (String)params.get("page")!=""
                &&(String)params.get("limit")!=null && (String)params.get("limit")!="") {
            int page = Integer.parseInt((String) params.get("page"));
            int limit = Integer.parseInt((String) params.get("limit"));
            params.put("offset", (page - 1 )* limit);
        }
        return productMapper.proList(params);
    }

    @Override
    public int selectCount(Map<String, Object> params) {
        return productMapper.selectCount(params);
    }

    @Override
    public int save(Product product) {
        return productMapper.insert(product);
    }

    @Override
    public int edit(Map<String, Object> params) {
        return productMapper.edit(params);
    }

    @Override
    public void importExcel(InputStream is, String originalFilename, Integer userId) {
        List<ArrayList<Object>> list;
        if (originalFilename.endsWith(".xls")) {
            list = Excel.readExcel2003(is);
        } else {
            list = Excel.readExcel2007(is);
        }
        int j = list.size();
        for (int i = 0; i < j ; i++) {
            List<Object> row = list.get(i);
            if(row.get(0).toString()!=null && !"".equals(row.get(0).toString())) {
                Product product = new Product();
                product.setSku(row.get(0).toString());
                product.setCreatePerson(userId);
                product.setCreateTime(new Date());
                product.setDelFlag(0);
                productMapper.insert(product);
            }
        }
    }
}
