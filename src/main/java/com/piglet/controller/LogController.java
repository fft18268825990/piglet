package com.piglet.controller;

import com.piglet.service.LogService;
import com.piglet.util.PageUtil;
import com.piglet.util.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@Controller
public class LogController {
    @Autowired
    LogService logService;

    @RequestMapping("/resultLog")
    public String resultLog(){
        return "result-log";
    }

    @GetMapping("/logList")
    @ResponseBody
    public PageUtil logList(@RequestParam Map<String, Object> params){
        return new PageUtil("0","返回成功",logService.logCount(),logService.logList(params));
    }

    @PostMapping("/editLog")
    @ResponseBody
    public Result editLog(@RequestParam Map<String, Object> params){
        String logId = (String)params.get("logId");
        if(logId==null || logId=="") {
            return Result.error();
        }
        if (logService.edit(params) > 0) {
            return Result.ok();
        }
        return Result.error();
    }
}
