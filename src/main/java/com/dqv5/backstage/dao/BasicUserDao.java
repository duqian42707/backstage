package com.dqv5.backstage.dao;

import com.dqv5.backstage.model.BasicUser;

import java.util.List;

/**
 * Created by dq on 2017/3/31.
 */
public interface BasicUserDao {
    BasicUser getUserByPrimaryKey(Integer id);

    BasicUser getUserByOpenId(String openId);

    void saveUser(BasicUser record);

    void updateUser(BasicUser record);

    List<BasicUser> getAllUser();
}
