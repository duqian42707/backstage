package com.dqv5.backstage.model;

import java.util.Date;

/**
 * Created by dq on 2017/3/31.
 */
public class BasicUser {
    private Integer userId;
    private Integer openId;
    private String nickName;
    private Date modTime;

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public Integer getOpenId() {
        return openId;
    }

    public void setOpenId(Integer openId) {
        this.openId = openId;
    }

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    public Date getModTime() {
        return modTime;
    }

    public void setModTime(Date modTime) {
        this.modTime = modTime;
    }
}
