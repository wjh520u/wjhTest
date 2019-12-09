package com.foxconn.config;

import com.github.pagehelper.PageHelper;
import org.springframework.context.annotation.Bean;

import java.util.Properties;

public class PgeHeplerConfig {
    //将分页插件注入到容器中
    @Bean
    public PageHelper pageHelper() {
    //分页插件
        PageHelper pageHelper = new PageHelper();
        Properties properties = new Properties();
        properties.setProperty("reasonable", "true");
        properties.setProperty("supportMethodsArguments", "true");
        properties.setProperty("helperDialect", "mysql");
        properties.setProperty("params", "count=countSql");
        pageHelper.setProperties(properties);
        return pageHelper;
    }
}
