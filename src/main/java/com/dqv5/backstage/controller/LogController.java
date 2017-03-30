package com.dqv5.backstage.controller;

import com.dqv5.backstage.service.SysLogService;
import com.dqv5.backstage.util.JsonUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by dqwork on 2017/3/29.
 */
@Controller
@RequestMapping(value = "/log")
public class LogController {

    private Logger logger = LoggerFactory.getLogger(LogController.class);

    @Autowired
    SysLogService sysLogService;

    @RequestMapping(value = "/load")
    @ResponseBody
    public Map load(String json) {
        Map map = new HashMap();
        try {
            logger.debug("json:---"+json);
            Map dataMap = (Map) JsonUtil.toObject(json);
            if (dataMap != null) {
                logger.debug("dataMap:---"+dataMap.toString());
            }
            Map userInfo = (Map) dataMap.get("userInfo");
            if (userInfo != null) {
                logger.debug("userInfo:---"+userInfo.toString());
            }
            logger.debug("detail:---"+userInfo.get("nickName")+"使用了系统");
//            sysLogService.saveLog(0, userInfo.get("nickName") + "使用了系统");
            map.put("status", "ok");
        } catch (Exception e) {
            logger.error("", e);
            map.put("status", "error");
        }
        return map;
    }

    @RequestMapping(value = "/logList")
    @ResponseBody
    public List logList() {
        try {
            List list = sysLogService.getLogList();
            return list;
        } catch (Exception e) {
            logger.error("", e);
            return null;
        }
    }
}
