package com.dqv5.backstage.dao;

import com.dqv5.backstage.hibernate.BaseDao;
import com.dqv5.backstage.model.BasicUser;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by dq on 2017/3/31.
 */
@Repository
public class BasicUserDao extends BaseDao {

    public Map getUserByPrimaryKey(Integer id) {
        String sql = "select u.user_id userId,open_id openId,u.nick_name nickName,user_name userName,remark," +
                " DATE_FORMAT(u.mod_time,'%Y-%m-%d %H:%i:%s') modTime,u.is_admin isAdmin ,status,avatarUrl avatarUrl  " +
                " from basic_user u where u.user_id = ? ";
        List param = new ArrayList();
        param.add(id);
        List<Map> list = findSqlListMap(sql, param);
        if (list.isEmpty()) {
            return null;
        } else {
            return list.get(0);
        }
    }

    public BasicUser getUserByOpenId(String openId) {
        String hql = "from BasicUser u where u.openId = ? ";
        List param = new ArrayList();
        param.add(openId);
        List<BasicUser> list = findHqlList(hql, param);
        if (list.isEmpty()) {
            return null;
        } else {
            return list.get(0);
        }
    }

    public BasicUser saveUser(BasicUser record) {
        return (BasicUser) save(record);
    }

    public BasicUser updateUser(BasicUser record) {
        return (BasicUser) merge(record);
    }

    public List getAllUser() {
        String hql = "from BasicUser ";
        List param = new ArrayList();
        List<BasicUser> list = findHqlList(hql, param);
        return list;
    }
}
