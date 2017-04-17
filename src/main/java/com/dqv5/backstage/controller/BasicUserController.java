package com.dqv5.backstage.controller;

import com.dqv5.backstage.common.Constants;
import com.dqv5.backstage.model.BasicUser;
import com.dqv5.backstage.service.BasicUserService;
import com.dqv5.backstage.util.HttpRequestUtil;
import com.dqv5.backstage.util.JsonUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by dq on 2017/3/31.
 */
@Controller
@RequestMapping(value = "/user")
public class BasicUserController {
    private Logger logger = LoggerFactory.getLogger(BasicUserController.class);
    @Autowired
    private BasicUserService basicUserService;

    @RequestMapping(value = "/jscode2session")
    @ResponseBody
    public Map jscode2session(String code) {
        Map map = new HashMap();
        try {
            String url = "https://api.weixin.qq.com/sns/jscode2session";
            String params = "appid=" + Constants.APP_ID + "&secret=" + Constants.APP_SECRET + "&js_code=" + code + "&grant_type=authorization_code";
            String res = HttpRequestUtil.sendGet(url, params, null);
            Map ret = (Map) JsonUtil.toObject(res);
            logger.debug(res);
            map.put("status", "ok");
            map.put("res", ret);
        } catch (Exception e) {
            logger.error("", e);
            map.put("status", "error");
        }
        return map;
    }

    @RequestMapping(value = "/saveOrUpdate")
    @ResponseBody
    public Map saveOrUpdate(String json) {
        Map map = new HashMap();
        try {
            BasicUser model = JsonUtil.fromJson(json, BasicUser.class);
            basicUserService.saveOrUpdate(model);
            map.put("status", "ok");
        } catch (Exception e) {
            map.put("status", "error");
            logger.error("", e);
        }
        return map;
    }

    /**
     * @param userInfo {openId:'',nickName:'风轻云淡',remark:'我是dq，请同意！'}
     * @return {status:'ok',data:{isAdmin:'',status:'',userId:''}}
     */
    @RequestMapping(value = "/checkUser")
    @ResponseBody
    public Map checkUser(String userInfo) {
        Map map = new HashMap();
        try {
            Map modelInfo = (Map) JsonUtil.toObject(userInfo);
            BasicUser model = new BasicUser();
            model.setOpenId(modelInfo.get("openId").toString());
            model.setNickName(modelInfo.get("nickName").toString());
            model.setAvatarUrl(modelInfo.get("avatarUrl").toString());
            model = basicUserService.saveOrUpdate(model);
            map.put("data", model);
            map.put("status", "ok");
        } catch (Exception e) {
            map.put("status", "error");
            logger.error("", e);
        }
        return map;
    }

    /**
     * 获取用户列表
     *
     * @return
     */
    @RequestMapping(value = "/userList")
    @ResponseBody
    public List userList() {
        List list = basicUserService.getUserList();
        return list;
    }


    /**
     * 获取用户信息
     *
     * @return
     */
    @RequestMapping(value = "/getUserInfo")
    @ResponseBody
    public Map getUserInfo(String userId) {
        Map map = basicUserService.getUserInfo(userId);
        return map;
    }



    /**
     * 修改用户
     *
     * @param data {isAdmin:'1',status:'2',userId:'2'}
     * @return {status:'ok'}
     */
    @RequestMapping(value = "/updateUser")
    @ResponseBody
    public Map updateUser(String data) {
        Map dataMap = (Map) JsonUtil.toObject(data);
        basicUserService.updateUser(dataMap);
        Map map = new HashMap();
        map.put("status", "ok");
        return map;
    }


    public static void main(String[] args) {
        String code = "021eE2Om1inoRk0atPOm1yG5Om1eE2Op";
        String url = "https://api.weixin.qq.com/sns/jscode2session";
        String params = "appid=" + Constants.APP_ID + "&secret=" + Constants.APP_SECRET + "&js_code=" + code + "&grant_type=authorization_code";
        String res = HttpRequestUtil.sendGet(url, params, null);
        System.out.println(res);
    }
}
