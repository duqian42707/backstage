package com.dqv5.backstage.dao;

import com.dqv5.backstage.hibernate.BaseDao;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by dqwork on 2017/4/14.
 */
@Repository
public class AppointmentDao extends BaseDao {

    public List getAppointmentList() {
        String sql = "select * from appointment ";
        return findSqlListMap(sql, null);
    }

    public List getAppointmentById(Integer id) {
        String sql = "select * from appointment t where t.appoint_id = ?";
        List param = new ArrayList();
        param.add(id);
        return findSqlListMap(sql, param);
    }
}
