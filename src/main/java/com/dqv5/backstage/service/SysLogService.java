package com.dqv5.backstage.service;

import com.dqv5.backstage.dao.SysLogDao;
import com.dqv5.backstage.model.SysLog;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

/**
 * Created by dqwork on 2017/3/29.
 */
@Service
public class SysLogService {
    @Autowired
    private SysLogDao sysLogDao;

    public void saveLog(Integer userId, String detail) {
        SysLog sysLog = new SysLog();
        sysLog.setUserId(userId);
        sysLog.setDetail(detail);
        sysLog.setOperateTime(new Date());
        sysLogDao.save(sysLog);
    }


    public List getLogList() {
        return sysLogDao.getAll();
    }
}
