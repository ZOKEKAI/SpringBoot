package com.zoke.springboot.web;

import com.zoke.springboot.pojo.User;
import com.zoke.springboot.pojo.UserWithGroup;
import com.zoke.springboot.pojo.UserWithGroup.Must;
import com.zoke.springboot.pojo.UserWithGroup.Optional;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Validation 功能测试前端控制器
 *
 * @author ZOKE
 * @date 2018-9-27 / 下午 10:20
 */
@RestController
@RequestMapping("/validate")
public class ValidationController {

    /**
     * 常规校验，校验所有加上注解的参数
     * 注意：必须加上 @RequestBody 注解才会使用全局异常处理器处理异常
     *
     * @param user          用户对象
     * @return              用户对象
     */
    @PostMapping("/user")
    public User validateAll(@Validated @RequestBody User user){
        return user;
    }

    /**
     * 校验使用Must分组的属性
     * 注意：属性分组后(加上group属性)后在校验时必须指定分组，否则不会校验
     *
     * @param userWithGroup 属性分组用户的对象
     * @return              用户对象
     */
    @PostMapping("/must/group")
    public UserWithGroup validateByMustGroup(@Validated(Must.class) @RequestBody UserWithGroup userWithGroup){
        return userWithGroup;
    }

    /**
     * 校验使用Optional分组的属性
     * 注意：属性分组后(加上group属性)后在校验时必须指定分组，否则不会校验
     *
     * @param userWithGroup 属性分组用户的对象
     * @return              用户对象
     */
    @PostMapping("/optional/group")
    public UserWithGroup validateByOptionalGroup(@Validated(Optional.class) @RequestBody UserWithGroup userWithGroup){
        return userWithGroup;
    }

    /**
     * 同时校验多个分组的属性
     *
     * @param userWithGroup 属性分组用户的对象
     * @return              用户对象
     */
    @PostMapping("/two/group")
    public UserWithGroup validateTwoGroup(@Validated({Must.class, Optional.class}) @RequestBody UserWithGroup userWithGroup){
        return userWithGroup;
    }

}
