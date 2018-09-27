package com.zoke.springboot.common.response;

import lombok.Data;

/**
 * 统一响应对象
 *
 * @author ZOKE
 * @date 2018-9-27 / 下午 11:41
 */
@Data
public class ResponseMsg<T> {

    private String code;

    private String message;

    private T object;

    public ResponseMsg(String code, String message) {
        this.code = code;
        this.message = message;
    }

    public ResponseMsg(String code, String message, T object) {
        this.code = code;
        this.message = message;
        this.object = object;
    }

    public static ResponseMsg buildSuccessMsg() {
        return new ResponseMsg("success", "success");
    }

    public static <T> ResponseMsg buildSuccessMsg(T object) {
        return new ResponseMsg("success", "success", object);
    }

    public static ResponseMsg buildFailMsg() {
        return new ResponseMsg("fail", "fail");
    }

    public static ResponseMsg buildFailMsg(String code, String message) {
        return new ResponseMsg(code, message);
    }

    public static <T> ResponseMsg buildFailMsg(T object) {
        return new ResponseMsg("fail", "fail", object);
    }

}
