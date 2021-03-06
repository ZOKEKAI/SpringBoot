package com.zoke.springboot.web;

import com.zoke.springboot.domain.User;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

/**
 * RestTemplate 功能测试前端控制器
 *
 * @author ZOKE
 * @date 2018-9-22 / 下午 04:32
 */
@RestController
@RequestMapping("/template")
public class RestTemplateController {

    @RequestMapping("/get/user")
    public User getUser(@RequestParam String name, @RequestParam(value = "age") int age){
        User user = new User();
        user.setName(name);
        user.setAge(age);
        return user;
    }

    /**
     * RestTemplate使用方法
     *
     * 添加请求参数使用的是 LinkedMultiValueMap ，其他的例如 HashMap 和 LinkHashMap 不可以
     */
    @GetMapping("/user/param")
    public void restTemplate(@RequestParam("name") String name, @RequestParam("age") String age){
        RestTemplate restTemplate = new RestTemplate();
        MultiValueMap<String,String> param = new LinkedMultiValueMap<>();
        param.add("name", name);
        param.add("age", age);
        User user = restTemplate.postForObject("http://localhost:8080/template/get/user", param, User.class);
        System.out.println("使用restTemplate工具请求获取到的对象为：" + user);
    }
}
