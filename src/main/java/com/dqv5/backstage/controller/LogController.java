package com.dqv5.backstage.controller;

import com.dqv5.backstage.service.SysLogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by dqwork on 2017/3/29.
 */
@Controller
@RequestMapping(value = "/log")
public class LogController {
    @Autowired
    SysLogService sysLogService;

    @RequestMapping(value = "/load")
    @ResponseBody
    public Map load(String json) {
        sysLogService.saveLog(1L, "使用了系统");
        Map map = new HashMap();
        return map;
    }
}
