package com.zoke.springboot.common.exception;

import com.zoke.springboot.common.response.ResponseMsg;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * 全局异常处理类
 *
 * @author ZOKE
 * @date 2018-9-27 / 下午 11:34
 */
@ControllerAdvice
public class GlobalExceptionHandler {

    private Logger logger = LoggerFactory.getLogger(getClass());

    /**
     * 参数校验异常全局处理器
     * @param e 校验异常
     * @return  响应信息
     */
    @ExceptionHandler(value = MethodArgumentNotValidException.class)
    @ResponseBody
    public ResponseMsg handleMethodArgumentNotValidException(MethodArgumentNotValidException e) {
        BindingResult bindingResult = e.getBindingResult();
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("参数异常信息:  ");
        for (FieldError fieldError : bindingResult.getFieldErrors()) {
            stringBuilder.append(fieldError.getField());
            stringBuilder.append(":");
            stringBuilder.append(fieldError.getDefaultMessage());
            stringBuilder.append("; ");
        }
        logger.info("参数校验不通过",e);
        return ResponseMsg.buildFailMsg("参数校验不通过", stringBuilder.toString());
    }

}
