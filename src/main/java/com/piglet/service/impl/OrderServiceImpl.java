package com.piglet.service.impl;

import com.piglet.domain.Order;
import com.piglet.domain.ResultLog;
import com.piglet.mapper.LogMapper;
import com.piglet.mapper.OrderMapper;
import com.piglet.service.OrderService;
import com.piglet.util.Excel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.InputStream;
import java.util.*;

@Service
@Transactional(rollbackFor = Exception.class)
public class OrderServiceImpl implements OrderService {
    @Autowired
    OrderMapper orderMapper;

    @Autowired
    LogMapper logMapper;

    @Transactional(readOnly = false,rollbackFor = Exception.class)
    @Override
    public void excel(InputStream is, String originalFilename , Integer userId) {
        List<ArrayList<Object>> list;
        if (originalFilename.endsWith(".xls")) {
            list = Excel.readExcel2003(is);
        } else {
            list = Excel.readExcel2007(is);
        }
        int j = list.size();
        orderMapper.removeAll();
        for (int i = 0; i < j ; i++) {
            List<Object> row = list.get(i);
            if(row.get(0).toString()!=null && !"".equals(row.get(0).toString())) {
                Order order = new Order();
                order.setSku(row.get(0).toString());
                order.setAmount(Double.parseDouble(row.get(1).toString()));
                order.setProfit(Double.parseDouble(row.get(2).toString()));
                orderMapper.insert(order);
            }
        }
        orderMapper.monthResult();
        List<Map<String,Object>> resultList = orderMapper.monthResultList();
        String content = "";
        for(Map<String,Object> map : resultList){
            content += map.get("realname")+"：{一月内：（金额："+map.get("onemonth_amount")+"元，利润："+map.get("onemonth_profit")+"美元），"+
                    "一月到三月：（金额："+map.get("onethreemonth_amount")+"元，利润："+map.get("onethreemonth_profit")+"美元），"+
                    "三月到六月：（金额："+map.get("threesixmonth_amount")+"元，利润："+map.get("threesixmonth_profit")+"美元），"+
                    "六月到12月：（金额："+map.get("halfyearmonth_amount")+"元，利润："+map.get("halfyearmonth_profit")+"美元），"+
                    "12月前：（金额："+map.get("yearmonth_amount")+"元，利润："+map.get("yearmonth_profit")+"美元），"+
                    "总计：（金额："+map.get("sum_amount")+"元，利润："+map.get("sum_profit")+"美元）}                                        ";
        }
        ResultLog resultLog = new ResultLog();
        resultLog.setFileName(originalFilename);
        resultLog.setContent(content);
        resultLog.setCreateTime(new Date());
        resultLog.setCreatePerson(userId);
        resultLog.setDelFlag(0);
        logMapper.insert(resultLog);
    }

    @Override
    public int resultCount() {
        return orderMapper.resultCount();
    }

    @Override
    public List<Map<String, Object>> resultList() {
        return orderMapper.monthResultList();
    }
}
