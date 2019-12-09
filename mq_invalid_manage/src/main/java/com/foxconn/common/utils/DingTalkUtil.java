package com.foxconn.common.utils;

import com.dingtalk.api.DefaultDingTalkClient;
import com.dingtalk.api.DingTalkClient;
import com.dingtalk.api.request.OapiRobotSendRequest;
import com.dingtalk.api.response.OapiRobotSendResponse;
import com.taobao.api.ApiException;

import java.util.Arrays;

/**
 * 钉钉工具类，用于发送异常同知到企业群
 * @Author: 王俊辉
 * @Date: 2019/12/7 3:37 下午
 * @Param:
 * @Return:
 */
public class DingTalkUtil {

    private static String MQInvalidGroupSendUrl =
            SpringUtils.getApplicationContext().getEnvironment().getProperty("dingTtalk.MQInvalidGroupSendUrl");

    /**
     * 发送到MQ失效管理群
     * @Author: 王俊辉
     * @Date: 2019/12/7 3:37 下午
     * @Param: [message]
     * @Return: void
     */
    public static void sendToMQInvalidGroup(String message){
        System.out.println(message);
        //sendMessage(message, MQInvalidGroupSendUrl);
    }

    /**
     * 发送钉钉文本信息
     * @Author: 王俊辉
     * @Date: 2019/12/7 3:38 下午
     * @Param: [message, url]
     * @Return: void
     */
    public static void sendMessage(String message, String url){
        DingTalkClient client = new DefaultDingTalkClient(url);
        OapiRobotSendRequest request = new OapiRobotSendRequest();
        request.setMsgtype("text");
        OapiRobotSendRequest.Text text = new OapiRobotSendRequest.Text();
        text.setContent(message);
        request.setText(text);
        OapiRobotSendRequest.At at = new OapiRobotSendRequest.At();
        at.setIsAtAll("true");
        request.setAt(at);
        try {
            OapiRobotSendResponse response = client.execute(request);
        } catch (ApiException e) {
            e.printStackTrace();
        }
    }

}
