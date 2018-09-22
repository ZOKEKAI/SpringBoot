package com.zoke.springboot.common.filter;

import org.springframework.web.filter.OncePerRequestFilter;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 过滤器对象，可以对JSON以及普通的请求格式参数进行XSS防注入处理
 * 1、继承 OncePerRequestFilter 可以直接获取到封装过的 HttpServletRequest
 * 2、可以省略传统过滤器的 init 和 destroy 方法的书写
 *
 * @author ZOKE
 * @date 2018-9-22 / 下午 05:20
 */
public class XssFilter extends OncePerRequestFilter {

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        XssFilterRequestWrapper httpServletRequestWrapper = new XssFilterRequestWrapper(request);
        filterChain.doFilter(httpServletRequestWrapper, response);
    }
}
