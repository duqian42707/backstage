package com.dqv5.backstage.util;

import org.apache.http.Consts;
import org.apache.http.HttpEntity;
import org.apache.http.NameValuePair;
import org.apache.http.ParseException;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;

import java.io.*;
import java.util.*;

/**
 * Created by dqwork on 2016/10/13.
 */
public class HttpRequestUtil {
    private static final int TIME_OUT = 10 * 1000; // 超时时间
    private static final String CHARSET = "UTF-8"; // 设置编码

    public static void main(String[] args) throws UnsupportedEncodingException {
        String url = "http://rsf.qbox.me/list";
        String param = "bucket=dqv5";
//        String msg = "你好hello";
//        msg= URLEncoder.encode(msg,"GBK");
        Map<String,String> headers = new HashMap<String,String>();
        headers.put("Content-Type","application/x-www-form-urlencoded");
        headers.put("Authorization","QBox TzDxMGgp1cefk6_Q9koxhzKUCEutUZ6JWtavTZV0:5ekGslJbnkAPKCX_czfBRBQC3vw=");
//        System.out.println(sendGet(url, param,headers));
//        System.out.println(sendPost(url4, param));
//        HttpClientUtil.postHttp(url4+"?"+param,"GBK");

        String str = sendGet(url,param, headers);
        System.out.println(str);
    }


    /**
     * 发送HttpGet请求
     *
     * @param url
     * @return
     */
    public static String sendGet(String url, String param, Map<String, String> headers) {
        //1.获得一个httpclient对象
        CloseableHttpClient httpclient = HttpClients.createDefault();
        //2.生成一个get请求
        if (param != null && !"".equals(param)) url += "?" + param;
        HttpGet httpget = new HttpGet(url);
        //设置自定义头信息
        Iterator<Map.Entry<String, String>> iterator = headers.entrySet().iterator();
        while (iterator.hasNext()) {
            Map.Entry<String, String> entry = iterator.next();
            httpget.setHeader(entry.getKey(), entry.getValue());
        }
        CloseableHttpResponse response = null;
        try {
            //3.执行get请求并返回结果
            response = httpclient.execute(httpget);
        } catch (IOException e1) {
            e1.printStackTrace();
        }
        String result = null;
        try {
            //4.处理结果，这里将结果返回为字符串
            HttpEntity entity = response.getEntity();
            if (entity != null) {
                result = EntityUtils.toString(entity);
            }
        } catch (ParseException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                response.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return result;
    }

    /**
     * 向指定 URL 发送POST方法的请求
     *
     * @param url   发送请求的 URL
     * @param param 请求参数，请求参数应该是 name1=value1&name2=value2 的形式。
     * @return 所代表远程资源的响应结果
     */
    public static String sendPost(String url, String param, Map<String, String> headers) {
        CloseableHttpClient httpclient = HttpClients.createDefault();
        List<NameValuePair> formparams = new ArrayList<>();
        if (param != null) {
            String[] entrys = param.split("&");
            for (String entry : entrys) {
                String[] strArr = entry.split("=");
                //给参数赋值
                formparams.add(new BasicNameValuePair(strArr[0], strArr[1]));
            }
        }
        UrlEncodedFormEntity entity = new UrlEncodedFormEntity(formparams, Consts.UTF_8);
        HttpPost httppost = new HttpPost(url);
        httppost.setEntity(entity);
        //设置自定义头信息
        Iterator<Map.Entry<String, String>> iterator = headers.entrySet().iterator();
        while (iterator.hasNext()) {
            Map.Entry<String, String> entry = iterator.next();
            httppost.setHeader(entry.getKey(), entry.getValue());
        }
        CloseableHttpResponse response = null;
        try {
            response = httpclient.execute(httppost);
        } catch (IOException e) {
            e.printStackTrace();
        }
        HttpEntity entity1 = response.getEntity();
        String result = null;
        try {
            result = EntityUtils.toString(entity1);
        } catch (ParseException | IOException e) {
            e.printStackTrace();
        }
        return result;
    }

}
