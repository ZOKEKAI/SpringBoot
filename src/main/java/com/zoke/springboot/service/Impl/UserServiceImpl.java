package com.zoke.springboot.service.Impl;

import com.zoke.springboot.domain.User;
import com.zoke.springboot.mapper.UserMapper;
import com.zoke.springboot.service.UserService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * UserServiceImpl
 *
 * @author ZOKE
 * @date 2018-9-30 / 下午 04:50
 */
@Service
public class UserServiceImpl implements UserService {

    @Resource
    private UserMapper userMapper;

    @Override
    public List<User> search(User user) {
        return userMapper.search(user);
    }

    @Override
    public void save(User user) {
        userMapper.save(user);
    }

}
