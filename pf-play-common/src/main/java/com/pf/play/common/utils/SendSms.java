package com.pf.play.common.utils;

import com.aliyuncs.CommonRequest;
import com.aliyuncs.CommonResponse;
import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.IAcsClient;
import com.aliyuncs.exceptions.ClientException;
import com.aliyuncs.exceptions.ServerException;
import com.aliyuncs.http.MethodType;
import com.aliyuncs.profile.DefaultProfile;

import javax.swing.text.html.FormSubmitEvent;

/**
 * @Description 短信验证码发送类
 * @Author yoko
 * @Date 2019/12/9 15:20
 * @Version 1.0
 */
public class SendSms {
    public static void main(String[] args) {
//        DefaultProfile profile = DefaultProfile.getProfile("cn-hangzhou", "<accessKeyId>", "<accessSecret>");
        DefaultProfile profile = DefaultProfile.getProfile("cn-hangzhou", "LTAI8UsQKfgMqzse", "<accessSecret>");
        IAcsClient client = new DefaultAcsClient(profile);

        CommonRequest request = new CommonRequest();
        request.setMethod(MethodType.POST);
        request.setDomain("dysmsapi.aliyuncs.com");
        request.setVersion("2017-05-25");
        request.setAction("SendSms");
        request.putQueryParameter("RegionId", "cn-hangzhou");
        request.putQueryParameter("PhoneNumbers", "15967171415");
        request.putQueryParameter("SignName", "玖大鲜");
        request.putQueryParameter("TemplateCode", "SMS_167745020");
        request.putQueryParameter("TemplateParam", "{\"code\":\"${content}\"}");
        try {
            CommonResponse response = client.getCommonResponse(request);
            System.out.println(response.getData());
        } catch (ServerException e) {
            e.printStackTrace();
        } catch (ClientException e) {
            e.printStackTrace();
        }
    }
}
