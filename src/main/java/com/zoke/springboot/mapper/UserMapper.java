package com.zoke.springboot.mapper;

import com.zoke.springboot.domain.User;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * UserMapper
 *
 * @author ZOKE
 * @date 2018-9-30 / 下午 04:50
 */
@Mapper
public interface UserMapper {

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
