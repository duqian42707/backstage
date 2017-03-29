package com.dqv5.backstage.controller;

import com.dqv5.backstage.util.JsonUtil;
import com.dqv5.backstage.util.QiniuUtil;
import com.dqv5.backstage.util.ResponseUtil;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletResponse;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;


/**
 * Created by dqwork on 2017/2/3.
 */
@Controller
@RequestMapping(value = "/demo")
public class DemoController {

    @RequestMapping(value = "/getTime")
    @ResponseBody
    public Map getTime() {
        Map map = new HashMap();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        map.put("result",sdf.format(new Date()));
        return map;
    }

    @RequestMapping("/getUptoken")
    public Map getUptoken(HttpServletResponse response){
        QiniuUtil qiniu = new QiniuUtil();
        String uptoken = qiniu.getUpToken();
        Map map = new HashMap();
        map.put("uptoken",uptoken);
        ResponseUtil.sendJson(response, JsonUtil.toJson(map));
        return null;
    }
}
