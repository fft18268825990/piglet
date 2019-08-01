package com.piglet.controller;

import com.piglet.domain.Product;
import com.piglet.domain.User;
import com.piglet.service.ProductService;
import com.piglet.service.UserService;
import com.piglet.util.PageUtil;
import com.piglet.util.Result;
import com.piglet.util.ShiroUtils;
import org.apache.shiro.session.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.InputStream;
import java.util.Date;
import java.util.Map;

import static com.piglet.util.ShiroUtils.getUserId;

@Controller
public class ProductController {
    @Autowired
    ProductService productService;

    @Autowired
    UserService userService;

    @RequestMapping("/productManage")
    public String productManage(){
        return "/product-list";
    }

    @RequestMapping("/proAdd")
    public String productAdd(){
        return "/product-add";
    }
    @RequestMapping("/proEdit")
    public String productEdit(){
        return "/product-edit";
    }

    @GetMapping("/productList")
    @ResponseBody
    public PageUtil roleList(@RequestParam Map<String, Object> params){
        return new PageUtil("0","返回成功",productService.proCount(params),productService.proList(params));
    }

    @GetMapping("/proSelectCount")
    @ResponseBody
    public int selectCount(@RequestParam Map<String, Object> params){
        return productService.selectCount(params);
    }

    @PostMapping("/savePro")
    @ResponseBody
    public Result savePro(@RequestParam Map<String, Object> params){
        Product product = new Product();
        product.setSku((String)params.get("sku"));
        product.setCreateTime(new Date());
        product.setCreatePerson(getUserId());
        product.setDelFlag(0);
        if (productService.save(product) > 0) {
            return Result.ok();
        }
        return Result.error();
    }

    @PostMapping("/editPro")
    @ResponseBody
    public Result editPro(@RequestParam Map<String, Object> params){
        String productId = (String)params.get("productId");
        if(productId==null || productId=="") {
            return Result.error();
        }
        if (productService.edit(params) > 0) {
            return Result.ok();
        }
        return Result.error();
    }

    @RequestMapping(value = "/import")
    @ResponseBody
    public Result importExcel(@RequestParam("file") MultipartFile file) throws Exception {
        if (!file.isEmpty()) {
            try {
                String originalFilename = file.getOriginalFilename();// 原文件名字
                InputStream is = file.getInputStream();// 获取输入流
                productService.importExcel(is,originalFilename,getUserId());
                return Result.ok();
            } catch (Exception e) {
                return Result.error();
            }
        }else return Result.error();
    }
}
