package com.foxconn;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@SpringBootApplication
@EnableTransactionManagement
@MapperScan(basePackages = "com.foxconn.repo", annotationClass = Repository.class)
public class MqInvalidManageApplication {

    public static void main(String[] args) {
        SpringApplication.run(MqInvalidManageApplication.class, args);
    }

}
