package com.zoke.springboot.web;

import com.zoke.springboot.domain.User;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

/**
 * Thymeleaf 功能测试前端控制器
 *
 * @author ZOKE
 * @date 2018-10-9 / 上午 09:35
 */
@Controller
@RequestMapping("/")
public class ThymeleafController {

    /**
     * 想要直接转跳某个页面时控制器类上不能使用@RestController注解，否则会返回字符串
     *
     * @return 页面名称字符串，实际上是静态资源的路径，可认为返回的是: "/templates/" + "index" + ".html"
     */
    @RequestMapping
    public String index(){
        return "index";
    }

    /**
     * 使用ModelAndView对象封装数据并跳转页面
     *
     * @return ModelAndView
     */
    @GetMapping("/thymeleaf/user")
    public ModelAndView user(){
        ModelAndView modelAndView = new ModelAndView("user/user_info");
        User user = new User();
        user.setName("Anna");
        user.setAddress("深圳中心街");
        modelAndView.addObject("user", user);
        return modelAndView;
    }
}
