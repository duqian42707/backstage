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
        String sql = "SELECT t.appoint_id,t.user_id,t.college,t.academy,t.student,t.teacher,t.type,t.appoint_date,t.week, " +
//                "DATE_FORMAT(t.start_time,'%Y-%m-%d %H:%i:%s') start_time, DATE_FORMAT(t.end_time,'%Y-%m-%d %H:%i:%s') end_time, " +
                "DATE_FORMAT(t.start_time,'%H:%i') start_time, DATE_FORMAT(t.end_time,'%H:%i') end_time, " +
                "t.independent,t.remark,t.mod_time,t.state FROM appointment t ORDER BY t.start_time";
        return findSqlListMap(sql, null);
    }

    public List getAppointmentById(Integer id) {
        String sql = "select * from appointment t where t.appoint_id = ?";
        List param = new ArrayList();
        param.add(id);
        return findSqlListMap(sql, param);
    }
}
