package com.zoke.springboot;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * SpringBoot入口启动类
 *
 * @author ZOKE
 * @date 2018-9-22 / 下午 04:32
 */
@SpringBootApplication
public class SpringbootApplication {

    public static void main(String[] args) {
        SpringApplication.run(SpringbootApplication.class, args);
        System.out.println("SpringBoot Start!");
    }

}
