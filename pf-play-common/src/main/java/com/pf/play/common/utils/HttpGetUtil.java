package com.pf.play.common.utils;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author df
 * @Description:get请求的公共类
 * @create 2019-05-27 19:35
 **/
public class HttpGetUtil {
    private final static Logger log = LoggerFactory.getLogger(HttpGetUtil.class);

    /**
     * @Description: TODO(get请求url地址，返回固定字段的值)
     * @author df
     * @param url-要请求的url
     * @param field-想要的字段，用于返回使用
     * @create 19:38 2019/5/27
     **/
    public static String sendGetUrl(String url, String field){
        String str = "";
        try{
            DefaultHttpClient httpClient = new DefaultHttpClient();
            HttpGet httpGet = new HttpGet(url);
            HttpResponse httpResponse = httpClient.execute(httpGet);
            HttpEntity entity = httpResponse.getEntity();
            if (httpResponse.getStatusLine().getStatusCode() != HttpStatus.SC_OK) {
                return null;
            }
            String data = EntityUtils.toString(entity);
            Object obj = JSON.parseObject(data);
            str = (String) ((JSONObject) obj).get(field);
            return str;
        }catch (Exception e){
            log.error(String.format("HttpGetUtil.getUrl is error!,  parame : url = %s, and field = %s", url, field));
            e.printStackTrace();
            return null;
        }
    }


    /**
     * @Description: TODO(get请求url地址，返回json值)
     * @author df
     * @param url-要请求的url
     * @create 19:38 2019/5/27
     **/
    public static String sendGetUrl(String url){
        String str = "";
        try{
            DefaultHttpClient httpClient = new DefaultHttpClient();
            HttpGet httpGet = new HttpGet(url);
            HttpResponse httpResponse = httpClient.execute(httpGet);
            HttpEntity entity = httpResponse.getEntity();
            if (httpResponse.getStatusLine().getStatusCode() != HttpStatus.SC_OK) {
                return null;
            }
            str = EntityUtils.toString(entity);
            return str;
        }catch (Exception e){
            log.error(String.format("HttpGetUtil.getUrl is error!,  parame : url = %s", url));
            e.printStackTrace();
            return null;
        }
    }

    public static void main(String[] args) {
        String url = "http://114.55.219.150:9999/wjyidongyueduziji/smsorder.wckj?productId=3&price=2000&imsi=1&mobile=15967171415&channelId=1344&exData=x";
        String field = "resultCode";
        String str = sendGetUrl(url, field);
        System.out.println(str);
        String str1 = sendGetUrl(url);
        System.out.println("str1:" + str1);
    }
}
