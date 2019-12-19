package com.pf.play.common.alipay;

import com.alipay.api.AlipayApiException;
import com.alipay.api.AlipayClient;
import com.alipay.api.DefaultAlipayClient;
import com.alipay.api.domain.AlipayTradeAppPayModel;
import com.alipay.api.request.AlipayTradeAppPayRequest;
import com.alipay.api.response.AlipayTradeAppPayResponse;
import com.pf.play.common.alipay.model.AlipayModel;

/**
 * @Description 阿里支付的公共方法（支付宝）
 * @Author yoko
 * @Date 2019/12/19 16:56
 * @Version 1.0
 */
public class Alipay {

    public static String APP_PRIVATE_KEY = "MIIEvwIBADANBgkqhkiG9w0BAQEFAASCBKkwggSlAgEAAoIBAQCQz9TKf+gTF6OZqxDpZ8wBp7k2ablZxdzJi5NGON9LK6H9nIkWpgnJLB8Lm2Gde3ZIZ9X6n34GKDGg2Dpe9b0q7BQt13sJ/yEE8H+W9H4Q/hjzvQs6UYuHAcK+d2tno4MM6KIT2X6zhbOj6kGkThuf7U0D+Eg29ydUyuWNfE0SkdkNxvAeQwe1S+1a/Fyy0EtvCYZpp+RrUFI5vgpqoCkAqDRUODJ4T4ceHbfnliE5TRoGPVI/CUc9Ni1rNptNk6ussdC4zcoTjHqJpcUwE2q62KiZm88FmBQo/R1pRfJFvjWqvWt6+gzTtIjetkY2cj+AqisKPKKxYvLsWuCBz+/tAgMBAAECggEATe05QjtwZI3NAQ2YTTIJCz75oTllf9TFCkQs3ZYPO0Fgq7xH3UM+ct5mWnWkIv3kWfRepr6bL68Dfd2+E4nD4UwnU7/oOynq1+CfmFk7WeOTA97QIvLs1Zrx9FMJHj1UcWbiiTH6R5sEX4nZBxTtMrOdRSyfl8yKgsuomemxA/Nc2LJxETSeotmaBh3YBV12Ix6VVka1rWNL5tjufa/9FQ5f4z4W51QHo5SYSxQp2PTximKczOgjtIzRNGn+bi7cC8ecomCUP8q2b7zMcwcuRBKt2Mutcg2k0lvigg1k1KBzjzfmbog5KV+2lwCwlTllOceMOnDAVWYCS3L2FYTW1QKBgQDe8f9S2Hkm/aWbqVCbKo0NODtUr5ZUCNVbDlYtQwHWzS42ckomrC18OV5UiE16rOKhn7ItiDjWjarKJX4/fNnw5i6Fi/ebHfHuB7cZ+qs3mfxSelv+dKh/gGNLITcyfm04vVQ4A3Gr4bwkS7DHobJSZPzvrP89rwGOK8W+1uIqzwKBgQCmSD0M+4g+bwtZfCFR/2KZDh0wjUm9tWgEhIRuuG//FDdiZkKNom5XWONzjCP6pjzDvQhHRvvfgKQQBoXLUXPysYXHy9O31oV1xtFR1La5d5kKJZjWE98W4Ez1MKX5kQ3QDEXCIWLwgBYNlAzZyjY09nn2T277WPZViMYwzI14gwKBgQDeCJrk8ixudYyaY1ygvBbwBIGqTJjlpkp+Pd/7gdFyELQmi1pn+2/tWOEmRP0M4ONwXbBBAnrAyyQ94GtEZV5UOZo5bHUzafZIvixP1kLwxA30QmIeICaznLTG3RSw2BKEwKIAiwWJTe5nI26y0snanzL8rAkjcIiXA+cTscRbJwKBgQCG+Qo9WIs+CosO5vhxA7k3/cHp4DXkPLUjPaH18dZPGkzenZ0WNKwWULvH1hB6a6fVRsLKgK8Au/3wWCsQX5ybekfaZvQDPKl2cZ0FLIHpyE+8cco+s0CZ5BXzzLpc+sZYgy24p7sU9xNvkCZiPoaDtTJIoi+27H5/7Zbak5+eGwKBgQCfvBLhUTC2PxJbCenxc0BBsTj27GI4dW/tzDXqS18yEJuozhuhGrbv1O8YRaKBp1AIgUwFbY4jjmmhswbfTqQZ204cQPK1YpdT3PrvvysJGiDe8T/a4xyaU+kwew8ZYMyyfwhra8c/bs2JdoCes3AYLZNGF6y6EfdeFyG4C8up+A==";
    public static String ALIPAY_PUBLIC_KEY = "MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEAkM/Uyn/oExejmasQ6WfMAae5Nmm5WcXcyYuTRjjfSyuh/ZyJFqYJySwfC5thnXt2SGfV+p9+BigxoNg6XvW9KuwULdd7Cf8hBPB/lvR+EP4Y870LOlGLhwHCvndrZ6ODDOiiE9l+s4Wzo+pBpE4bn+1NA/hINvcnVMrljXxNEpHZDcbwHkMHtUvtWvxcstBLbwmGaafka1BSOb4KaqApAKg0VDgyeE+HHh2355YhOU0aBj1SPwlHPTYtazabTZOrrLHQuM3KE4x6iaXFMBNqutiomZvPBZgUKP0daUXyRb41qr1revoM07SI3rZGNnI/gKorCjyisWLy7Frggc/v7QIDAQAB";

    /**
     * @Description: 组装阿里支付的订单号
     * @param alipayModel - 基本数据
     * @param notifyUrl - 同步地址
     * @return String
     * @author yoko
     * @date 2019/12/19 19:38
    */
    public static String createAlipaySend(AlipayModel alipayModel, String notifyUrl){
        String resultData;
        //实例化客户端
//        AlipayClient alipayClient = new DefaultAlipayClient("https://openapi.alipay.com/gateway.do", APP_ID, APP_PRIVATE_KEY, "json", CHARSET, ALIPAY_PUBLIC_KEY, "RSA2");
        AlipayClient alipayClient = new DefaultAlipayClient("https://openapi.alipay.com/gateway.do",
                "2018031502376903", APP_PRIVATE_KEY, "json", "UTF-8", ALIPAY_PUBLIC_KEY, "RSA2");
        //实例化具体API对应的request类,类名称和接口名称对应,当前调用接口名称：alipay.trade.app.pay
        AlipayTradeAppPayRequest request = new AlipayTradeAppPayRequest();
        //SDK已经封装掉了公共参数，这里只需要传入业务参数。以下方法为sdk的model入参方式(model和biz_content同时存在的情况下取biz_content)。
        AlipayTradeAppPayModel model = new AlipayTradeAppPayModel();
        model.setBody(alipayModel.body);
        model.setSubject(alipayModel.subject);
        model.setOutTradeNo(alipayModel.outTradeNo);
        model.setTimeoutExpress(alipayModel.timeoutExpress);
        model.setTotalAmount(alipayModel.totalAmount);
        model.setProductCode(alipayModel.productCode);
        request.setBizModel(model);
        request.setNotifyUrl(notifyUrl);
//        model.setBody("我是测试数据");
//        model.setSubject("App支付测试Java");
////        model.setOutTradeNo(outtradeno);
//        model.setOutTradeNo("df_test_hzhz_001");
//        model.setTimeoutExpress("30m");
//        model.setTotalAmount("0.01");
//        model.setProductCode("QUICK_MSECURITY_PAY");
//        request.setBizModel(model);
//        request.setNotifyUrl("商户外网可以访问的异步地址");
        try {
            //这里和普通的接口调用不同，使用的是sdkExecute
            AlipayTradeAppPayResponse response = alipayClient.sdkExecute(request);
            resultData = response.getBody();
            System.out.println(response.getBody());//就是orderString 可以直接给客户端请求，无需再做处理。
        } catch (AlipayApiException e) {
            e.printStackTrace();
            return null;
        }
        return resultData;
    }

    public static void main(String [] args){
        createAlipaySend(new AlipayModel(), null);
    }
}
