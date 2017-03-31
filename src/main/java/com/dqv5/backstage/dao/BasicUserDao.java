package com.dqv5.backstage.dao;

import com.dqv5.backstage.model.BasicUser;

import java.util.List;

/**
 * Created by dq on 2017/3/31.
 */
public interface BasicUserDao {
    BasicUser getByPrimaryKey(Integer id);

    BasicUser getByOpenId(Integer openId);

    void save(BasicUser record);

    void update(BasicUser record);

    List<BasicUser> getAll();
}
