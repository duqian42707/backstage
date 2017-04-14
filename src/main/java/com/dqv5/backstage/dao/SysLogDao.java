package com.dqv5.backstage.dao;

import com.dqv5.backstage.hibernate.BaseDao;
import com.dqv5.backstage.model.SysLog;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by dqwork on 2017/3/29.
 */
@Repository
public class SysLogDao extends BaseDao {

    public SysLog getLogByPrimaryKey(Integer id) {
        String hql = "from SysLog t where t.logId = ? ";
        List param = new ArrayList();
        param.add(id);
        List<SysLog> list = findHqlList(hql, param);
        if (list.isEmpty()) {
            return null;
        } else {
            return list.get(0);
        }
    }

    public void saveLog(SysLog record) {
        save(record);
    }

    public List<SysLog> getAllLog() {
        String hql = "from SysLog  ";
        List param = new ArrayList();
        List<SysLog> list = findHqlList(hql, param);
        return list;
    }
}
