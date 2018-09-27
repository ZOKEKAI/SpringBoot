package com.zoke.springboot.pojo;

import lombok.Data;
import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.NotEmpty;
import org.hibernate.validator.constraints.Range;

import javax.validation.constraints.AssertFalse;
import javax.validation.constraints.Pattern;

import static javax.validation.constraints.Pattern.Flag.CASE_INSENSITIVE;

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
    * 年龄
    */
    @Range(min = 1, max = 150, message = "{user.age.range}")
    private int age;

    /**
     * 手机号码
     */
    @Pattern(regexp = "^1(3|4|5|7|8)\\\\d{9}$", flags = CASE_INSENSITIVE, message = "{user.phone.format.error}")
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
