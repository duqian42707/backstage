package com.dqv5.backstage.service;

import com.dqv5.backstage.dao.BasicUserDao;
import com.dqv5.backstage.model.BasicUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;

/**
 * Created by dq on 2017/3/31.
 */
@Service
public class BasicUserService {
    @Autowired
    private BasicUserDao basicUserDao;

    void saveOrUpdate(BasicUser user){
        if(user.getUserId()==null){
            user.setModTime(new Date());
            basicUserDao.save(user);
        }else{
            user.setModTime(new Date());
            basicUserDao.update(user);
        }
    }
}
