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
            oldModel.setAvatarUrl(user.getAvatarUrl());
            oldModel.setModTime(new Date());
            return basicUserDao.updateUser(oldModel);
        } else {
            user.setModTime(new Date());
            user.setIsAdmin("0");
            user.setStatus("1");
            return basicUserDao.saveUser(user);
        }
    }

    public void updateUser(Map dataMap) {
        String user_id = dataMap.get("userId").toString();
        Integer userId = Integer.valueOf(user_id);
        BasicUser oldModel = basicUserDao.get(BasicUser.class, userId);
        oldModel.setModTime(new Date());
        if (dataMap.get("isAdmin") != null) {
            oldModel.setIsAdmin(dataMap.get("isAdmin").toString());
        }
        if (dataMap.get("status") != null) {
            oldModel.setStatus(dataMap.get("status").toString());
        }
        basicUserDao.merge(oldModel);
    }

    public List getUserList() {
        List list = basicUserDao.getAllUser();
        return list;
    }

    public Map getUserInfo(String userId) {
        Integer id = Integer.valueOf(userId);
        Map user = basicUserDao.getUserByPrimaryKey(id);
        return user;
    }
}
