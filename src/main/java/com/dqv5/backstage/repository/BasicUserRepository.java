package com.dqv5.backstage.repository;

import com.dqv5.backstage.model.BasicUser;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

/**
 * Created by dq on 2017/3/31.
 */
@Repository
public interface BasicUserRepository extends MongoRepository<BasicUser,Integer> {

    public BasicUser findBasicUserBy_id(String id) ;

    public BasicUser findBasicUserByOpenId(String openId);


}
