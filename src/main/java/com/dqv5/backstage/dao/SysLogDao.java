package com.dqv5.backstage.dao;

import com.dqv5.backstage.model.SysLog;

import java.util.List;

/**
 * Created by dqwork on 2017/3/29.
 */
public interface SysLogDao {

    SysLog getByPrimaryKey(Long id);

    void save(SysLog record);

    List<SysLog> getAll();
}
