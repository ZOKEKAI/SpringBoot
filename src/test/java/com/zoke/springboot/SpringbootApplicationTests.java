package com.zoke.springboot;

import com.zoke.springboot.domain.User;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

@RunWith(SpringRunner.class)
@SpringBootTest
public class SpringbootApplicationTests {

    @Test
    public void restTemplateTest() {
        RestTemplate restTemplate = new RestTemplate();
        MultiValueMap<String,String> param = new LinkedMultiValueMap<>();
        param.add("name", "露西");
        param.add("age", "25");
        User user = restTemplate.postForObject("http://localhost:8080/template/get/user", param, User.class);
        System.out.println("使用restTemplate工具请求获取到的对象为：" + user);
    }

}
