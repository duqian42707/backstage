package com.dqv5.backstage.dao;

import com.dqv5.backstage.model.SysLog;

import java.util.List;

/**
 * Created by dqwork on 2017/3/29.
 */
public interface SysLogDao {

    SysLog getLogByPrimaryKey(Integer id);

    void saveLog(SysLog record);

    List<SysLog> getAllLog();
}
