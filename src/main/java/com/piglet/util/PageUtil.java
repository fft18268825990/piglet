package com.piglet.util;

import java.io.Serializable;
import java.util.List;

public class PageUtil implements Serializable{
    private static final long serialVersionUID = 1L;
    private String code;
    private String msg;
    private int count;
    private List<?> data;
    public PageUtil (String code,String msg,int count,List<?> data) {
        this.code = code;
        this.msg = msg;
        this.count = count;
        this.data = data;
    }
    public String getCode() {
        return code;
    }
    public void setCode(String code) {
        this.code = code;
    }
    public String getMsg() {
        return msg;
    }
    public void setMsg(String msg) {
        this.msg = msg;
    }
    public int getCount() {
        return count;
    }
    public void setCount(int count) {
        this.count = count;
    }
    public List<?> getData() {
        return data;
    }
    public void setData(List<?> data) {
        this.data = data;
    }
}
