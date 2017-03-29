package com.dqv5.backstage.model;

import java.util.Date;

/**
 * Created by dqwork on 2017/3/29.
 */
public class SysLog {
    private Long logId;
    private Long userId;
    private String detail;
    private Date operateTime;

    public Long getLogId() {
        return logId;
    }

    public void setLogId(Long logId) {
        this.logId = logId;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getDetail() {
        return detail;
    }

    public void setDetail(String detail) {
        this.detail = detail;
    }

    public Date getOperateTime() {
        return operateTime;
    }

    public void setOperateTime(Date operateTime) {
        this.operateTime = operateTime;
    }
}
