package com.dqv5.backstage.service;

import com.dqv5.backstage.common.Constants;
import com.dqv5.backstage.model.BasicUser;
import com.dqv5.backstage.repository.BasicUserRepository;
import com.dqv5.backstage.util.HttpRequestUtil;
import com.dqv5.backstage.util.JsonUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
    private Logger logger = LoggerFactory.getLogger(BasicUserService.class);
    @Autowired
    private BasicUserRepository basicUserRepository;

    public BasicUser saveOrUpdate(BasicUser user) {
        BasicUser oldModel = basicUserRepository.findBasicUserByOpenId(user.getOpenId());
        if (oldModel != null) {
            oldModel.setNickName(user.getNickName());
            oldModel.setAvatarUrl(user.getAvatarUrl());
            oldModel.setModTime(new Date());
            oldModel.setUserId(oldModel.get_id());
            oldModel = basicUserRepository.save(oldModel);
            return oldModel;
        } else {
            user.setModTime(new Date());
            user.setIsAdmin("0");
            user.setStatus("1");
            user = basicUserRepository.save(user);
            user.setUserId(user.get_id());
            user = basicUserRepository.save(user);
            return user;
        }
    }

    public void updateUser(Map dataMap) {
        String user_id = dataMap.get("userId").toString();
        BasicUser oldModel = basicUserRepository.findBasicUserBy_id(user_id);
        oldModel.setModTime(new Date());
        if (dataMap.get("isAdmin") != null) {
            oldModel.setIsAdmin(dataMap.get("isAdmin").toString());
        }
        if (dataMap.get("status") != null) {
            oldModel.setStatus(dataMap.get("status").toString());
        }
        basicUserRepository.save(oldModel);
    }

    public List getUserList() {
        List list = basicUserRepository.findAll();
        return list;
    }

    public BasicUser getUserInfo(String userId) {
        BasicUser user = basicUserRepository.findBasicUserBy_id(userId);
        return user;
    }

    public String getOpenIdByCode(String code) {
        String openId = null;
        try {
            String url = "https://api.weixin.qq.com/sns/jscode2session";
            String params = "appid=" + Constants.APP_ID + "&secret=" + Constants.APP_SECRET + "&js_code=" + code + "&grant_type=authorization_code";
            String res = HttpRequestUtil.sendGet(url, params, null);
            Map ret = (Map) JsonUtil.toObject(res);
            if (ret.get("openid") != null) openId = ret.get("openid").toString();
        } catch (Exception e) {
            logger.error("", e);
        }
        return openId;
    }

}
