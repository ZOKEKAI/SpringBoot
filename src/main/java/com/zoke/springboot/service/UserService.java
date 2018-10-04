package com.zoke.springboot.service;

import com.zoke.springboot.domain.User;

import java.util.List;

/**
 * UserService
 *
 * @author ZOKE
 * @date 2018-9-30 / 下午 04:50
 */
public interface UserService {

    /**
     * 查找用户
     *
     * @param user  用户对象实体
     * @return 用户集合
     */
    List<User> search(User user);

    /**
     * 创建用户
     *
     * @param user  用户对象实体
     */
    void save(User user);

}
