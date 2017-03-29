package com.dqv5.backstage.util;

import com.qiniu.common.QiniuException;
import com.qiniu.common.Zone;
import com.qiniu.http.Response;
import com.qiniu.storage.BucketManager;
import com.qiniu.storage.Configuration;
import com.qiniu.storage.UploadManager;
import com.qiniu.storage.model.FileInfo;
import com.qiniu.storage.model.FileListing;
import com.qiniu.util.Auth;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import sun.misc.BASE64Encoder;

import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by dqwork on 2016/06/21.
 */
public class QiniuUtil{
    private static final Logger log  = LoggerFactory.getLogger(QiniuUtil.class);

    //设置好账号的ACCESS_KEY和SECRET_KEY
    private static final String ACCESS_KEY = "TzDxMGgp1cefk6_Q9koxhzKUCEutUZ6JWtavTZV0";
    private static final String SECRET_KEY = "1EKuYZ4f7epqaipJBfUQiwdueiRhMcN31BU_XTMV";
    //要上传的空间
    private static final String bucketname = "dqv5";
    //文件下载域名
    public static final String URL = "http://ok9vt2hy4.bkt.clouddn.com/";
    //密钥配置
    Auth auth = Auth.create(ACCESS_KEY, SECRET_KEY);
    //构造一个带指定Zone对象的配置类
    Configuration cfg = new Configuration(Zone.zone0());
    //...其他参数参考类注释

    //创建上传对象
    UploadManager uploadManager = new UploadManager(cfg);

    //简单上传，使用默认策略，只需要设置上传的空间名就可以了
    public String getUpToken() {
        return auth.uploadToken(bucketname);
    }

    /**
     * 简单上传
     *
     * @param file 要上传的文件
     * @param key  上传到七牛后保存的文件名
     * @throws IOException
     */
    public String upload(File file, String key) {
        try {
            //调用put方法上传
            com.qiniu.http.Response res = uploadManager.put(file, key, getUpToken());
            //打印返回的信息
            return key;
        } catch (QiniuException e) {
            com.qiniu.http.Response r = e.response;
            // 请求失败时打印的异常的信息
            log.error(r.toString());
            try {
                //响应的文本信息
                log.debug(r.bodyString());
            } catch (QiniuException e1) {
                //ignore
            }
            return null;
        }
    }

    public List getFileList(){
        Configuration c = new Configuration(Zone.zone0());
        BucketManager bucketManager = new BucketManager(auth,c);
        List<String> list= new ArrayList();
        try {
            //调用listFiles方法列举指定空间的指定文件
            //参数一：bucket    空间名
            //参数二：prefix    文件名前缀
            //参数三：marker    上一次获取文件列表时返回的 marker
            //参数四：limit     每次迭代的长度限制，最大1000，推荐值 100
            //参数五：delimiter 指定目录分隔符，列出所有公共前缀（模拟列出目录效果）。缺省值为空字符串
            FileListing fileListing = bucketManager.listFiles(bucketname,null,null,100,null);
//            FileListing fileListing = bucketManager.listFiles(bucketname,null,"eyJjIjowLCJrIjoib18xYW4yOHRlZm0xNmF0anBvMWV0cGhsNjFvaW5kLmpwZWcifQ==",3,null);
            FileInfo[] items = fileListing.items;
//            log.info(fileListing.marker);
            for(FileInfo fileInfo:items){
                list.add(URL+fileInfo.key);
//                log.info(URL+fileInfo.key);
            }
        } catch (QiniuException e) {
            //捕获异常信息
            Response r = e.response;
            log.error(r.toString());
        }
        return list;
    }

    public String upload(File file) {
        String key = new SimpleDateFormat("yyyyMMddHHmmss").format(new Date());
        key += (int) Math.random() * 1000;
        key+=file.getName().substring(file.getName().lastIndexOf("."));;
        return upload(file, key);
    }
    //构造私有空间的需要生成的下载的链接

    public void download() {
        //调用privateDownloadUrl方法生成下载链接,第二个参数可以设置Token的过期时间
        String downloadRUL = auth.privateDownloadUrl(URL, 3600);
        log.debug(downloadRUL);
    }


    public String uploadFile(String filename, byte[] file) {
        try {
            //调用put方法上传
            com.qiniu.http.Response res = uploadManager.put(file, filename, getUpToken());
            //打印返回的信息
            return filename;
        } catch (QiniuException e) {
            com.qiniu.http.Response r = e.response;
            // 请求失败时打印的异常的信息
            log.error(r.toString());
            try {
                //响应的文本信息
                log.debug(r.bodyString());
            } catch (QiniuException e1) {
                //ignore
            }
            return null;
        }
    }

    /**
     * 根据url获取凭证
     * @param url 抽取请求 URL 中 <path> 或 <path>?<query> 的部分与请求内容部分即 HTTP Body，用 \n 连接起来。如无请求内容，该部分必须为空字符串。
     *            url = "<path>\n"
     *            或
     *            url = "<path>\n<body>"
     *            或
     *            url = "<path>?<query>\n<body>"
     *            例如：url = "/list?bucket=dqv5\n"
     * @return
     */
    public String getTokenByUrl(String url) {
        String sign = auth.sign(url);
        return sign;
    }

    public static String getBase64(String str) {
        byte[] b = null;
        String s = null;
        try {
            b = str.getBytes("utf-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        if (b != null) {
            s = new BASE64Encoder().encode(b);
        }
        if(s!=null) s=s.replace("+","-").replace("/","_");
        return s;
    }


    public static void main(String args[]) throws IOException {
        QiniuUtil qiniuUtil = new QiniuUtil();
        String url="/list?bucket=dqv5\n";
        String str = qiniuUtil.getTokenByUrl(url);
        System.out.println(str);
    }

}
