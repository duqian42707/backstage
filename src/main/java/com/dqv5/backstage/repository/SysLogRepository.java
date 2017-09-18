package com.dqv5.backstage.repository;

import com.dqv5.backstage.model.SysLog;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by dqwork on 2017/3/29.
 */
@Repository
public interface SysLogRepository extends MongoRepository<SysLog,Integer> {

    public SysLog findSysLogByLogId(Integer id);
}
