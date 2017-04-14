package com.dqv5.backstage.service;

import com.dqv5.backstage.dao.BasicUserDao;
import com.dqv5.backstage.model.BasicUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * Created by dq on 2017/3/31.
 */
@Service
public class BasicUserService {
    @Autowired
    private BasicUserDao basicUserDao;

    public BasicUser saveOrUpdate(BasicUser user) {
        BasicUser oldModel = basicUserDao.getUserByOpenId(user.getOpenId());
        if (oldModel != null) {
            oldModel.setNickName(user.getNickName());
            oldModel.setModTime(new Date());
            basicUserDao.updateUser(oldModel);
            return oldModel;
        } else {
            user.setModTime(new Date());
            user.setIsAdmin("0");
            basicUserDao.saveUser(user);
            return user;
        }
    }

    public void updateUser(Map dataMap) {
        String user_id = dataMap.get("user_id").toString();
        Integer userId = Integer.valueOf(user_id);

    }

    public List getUserList() {
        List list = basicUserDao.getAllUser();
        return list;
    }
}
