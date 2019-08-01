package com.piglet.domain;


import com.fasterxml.jackson.annotation.JsonFormat;

import java.util.Date;

public class PushMessage {

    private Integer loginUserNum;

    private String loginUserName;

    private String content;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone="GMT+8")
    private Date sendDate;


    public PushMessage(Integer loginUserNum,String loginUserName,String content,Date sendDate){
        this.loginUserNum = loginUserNum;
        this.loginUserName = loginUserName;
        this.content = content;
        this.sendDate = sendDate;
    }

    public Integer getLoginUserNum() {
        return loginUserNum;
    }

    public void setLoginUserNum(Integer loginUserNum) {
        this.loginUserNum = loginUserNum;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getLoginUserName() {
        return loginUserName;
    }

    public void setLoginUserName(String loginUserName) {
        this.loginUserName = loginUserName;
    }

    public Date getSendDate() {
        return sendDate;
    }

    public void setSendDate(Date sendDate) {
        this.sendDate = sendDate;
    }

    // Other Detail Property...
}
