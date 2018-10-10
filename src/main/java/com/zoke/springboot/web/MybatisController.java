package com.zoke.springboot.web;

import com.zoke.springboot.common.response.ResponseMsg;
import com.zoke.springboot.domain.User;
import com.zoke.springboot.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * Mybatis 整合测试前端控制器
 *
 * @author ZOKE
 * @date 2018-9-30 / 下午 04:45
 */
@Slf4j
@RestController
@RequestMapping("/mybatis")
public class MybatisController {

    @Autowired
    private UserService userService;

    @RequestMapping("/search")
    public List<User> search(@RequestBody User user){
        List<User> userList;
        try {
            userList = userService.search(user);
            log.info("user list： " + userList);
        }catch (Exception exception){
            log.error("search user exception", exception);
            return null;
        }
        return userList;
    }

    @RequestMapping("/save")
    public ResponseMsg save(@Validated @RequestBody User user){
        try {
            userService.save(user);
        }catch (Exception exception){
            log.error("save user exception", exception);
            return ResponseMsg.buildFailMsg();
        }
        return ResponseMsg.buildSuccessMsg();
    }
}
