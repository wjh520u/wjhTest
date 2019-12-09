package com.foxconn.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * Web服务配置
 * @Author: 王俊辉
 * @Date: 2019/12/6 9:39 上午
 */
@Configuration
public class WebMvcConfigurerAdapter implements WebMvcConfigurer {

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        //配置静态资源处理
        registry.addResourceHandler("/view/**")
                .addResourceLocations("classpath:/view/").setCachePeriod(0);
    }

}
