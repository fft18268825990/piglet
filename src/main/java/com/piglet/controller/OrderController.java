package com.piglet.controller;

import com.piglet.service.OrderService;
import com.piglet.util.PageUtil;
import com.piglet.util.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.InputStream;

import static com.piglet.util.ShiroUtils.getUserId;

@Controller
public class OrderController {
    @Autowired
    OrderService orderService;
    @RequestMapping("/excelResult")
    public String excelResult(){
        return "excel-result";
    }


    @RequestMapping(value = "/excel")
    @ResponseBody
    public Result InputExcel(@RequestParam("file") MultipartFile file) throws Exception {
        if (!file.isEmpty()) {
            try {
                String originalFilename = file.getOriginalFilename();// 原文件名字
                InputStream is = file.getInputStream();// 获取输入流
                orderService.excel(is,originalFilename,getUserId());
                return Result.ok();
            } catch (Exception e) {
                return Result.error();
            }
        }else return Result.error();
    }

    @GetMapping(value = "/resultList")
    @ResponseBody
    public PageUtil resultList() throws Exception {
        return new PageUtil("0","返回成功",orderService.resultCount(),orderService.resultList());
    }

}
