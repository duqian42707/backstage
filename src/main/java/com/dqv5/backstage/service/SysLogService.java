package com.dqv5.backstage.service;

import com.dqv5.backstage.model.SysLog;
import com.dqv5.backstage.repository.SysLogRepository;
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
    private SysLogRepository sysLogRepository;

    public void saveLog(Integer userId, String detail) {
        SysLog sysLog = new SysLog();
        sysLog.setUserId(userId);
        sysLog.setDetail(detail);
        sysLog.setOperateTime(new Date());
        sysLogRepository.save(sysLog);
    }


    public List getLogList() {
        return sysLogRepository.findAll();
    }
}
