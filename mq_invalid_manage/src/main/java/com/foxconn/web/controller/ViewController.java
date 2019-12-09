package com.foxconn.web.controller;

import com.foxconn.service.rabbitmq.MQInvalidMessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * 跳转首页
 * @Author: 王俊辉
 * @Date: 2019/12/5 5:51 下午
 */
@Controller
public class ViewController {

    @Autowired
    MQInvalidMessageService MQInvalidMessageService;

    /**
     * 默认进首页
     * @Author: 王俊辉
     * @Date: 2019/12/6 9:38 上午
     * @Param: []
     * @Return: java.lang.String
     */
    @RequestMapping("")
    public String index(){
        return "view/index.html";
    }

    @RequestMapping("test")
    public String test(String id){
        return "index.html";
    }

}
