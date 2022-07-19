package com.zoke.springboot.domain;

import lombok.Data;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.Range;

import javax.validation.constraints.AssertFalse;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;

/**
 * User实体
 *
 * @author ZOKE
 * @date 2018-9-22 / 下午 04:32
 */
@Data
public class User {

    /**
    * 姓名
    */
    @NotBlank(message = "{user.name.notBlank}")
    private String name;

    /**
     * 密码
     */
    private String password;

    /**
    * 年龄
    */
    @Range(min = 1, max = 150, message = "{user.age.range}")
    private int age;

    /**
     * 手机号码
     */
    private String phone;

    /**
    * 性别
    */
    @NotEmpty(message = "{user.sex.notEmpty}")
    private String sex;

    /**
    * 地址
    */
    @Length(min = 1, max = 15, message = "{user.address.length.exceed}")
    private String address;

    /**
    * 是否已婚
    */
    @AssertFalse(message = "{user.is.not.married}")
    private Boolean isMarried;

    /**
    * 邮箱
    */
    @Email(message = "{user.email.format.error}")
    private String email;

}
