package com.dqv5.backstage.controller;

import com.dqv5.backstage.util.HttpRequestUtil;
import com.dqv5.backstage.util.JsonUtil;
import com.dqv5.backstage.util.QiniuUtil;
import com.dqv5.backstage.util.ResponseUtil;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by dqwork on 2017/3/3.
 */
@Controller
@RequestMapping(value = "/file")
public class FileController {

    @RequestMapping(value = "/getToken")
    public void getToken(HttpServletResponse response,String signingStr){
        QiniuUtil qiniuUtil = new QiniuUtil();
        String token = qiniuUtil.getTokenByUrl(signingStr);
        Map map = new HashMap();
        map.put("token",token);
        ResponseUtil.sendJson(response, JsonUtil.toJson(map));
    }

    @RequestMapping(value = "/list")
    public void getFileList(HttpServletResponse response,String bucket) {
        String url = "http://rsf.qbox.me/list";
        String param = "bucket=" + bucket;
        String signingStr = "/list?bucket=" + bucket + "\n";
        String token = new QiniuUtil().getTokenByUrl(signingStr);
        Map<String, String> headers = new HashMap<>();
        headers.put("Content-Type", "application/x-www-form-urlencoded");
        headers.put("Authorization", "QBox " + token);
//        String result = HttpRequestUtil.sendGet(url, param, headers);
        String result = HttpRequestUtil.sendGet(url+"?"+param,null, headers);
        ResponseUtil.sendJson(response, result);
    }


    @RequestMapping(value = "/delete")
    public void deleteFile(HttpServletResponse response,String bucket,String key) {
        QiniuUtil qiniuUtil = new QiniuUtil();
        String encodedEntryURI = qiniuUtil.getBase64(bucket+":"+key);
        String url = "http://rs.qiniu.com/delete/"+encodedEntryURI;
        String signingStr = "/delete/" + encodedEntryURI + "\n";
        String token = qiniuUtil.getTokenByUrl(signingStr);

        Map<String, String> headers = new HashMap<>();
        headers.put("Content-Type", "application/x-www-form-urlencoded");
        headers.put("Authorization", "QBox " + token);

        String result = HttpRequestUtil.sendGet(url, null, headers);
        ResponseUtil.sendJson(response, result);
    }
}
