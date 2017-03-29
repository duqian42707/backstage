package com.dqv5.backstage.controller;

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

    @RequestMapping(value = "/load")
    @ResponseBody
    public Map load(String json){


        Map map = new HashMap();
        return map;
    }
}
