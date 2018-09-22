package com.zoke.springboot.common.filter;

import com.zoke.springboot.common.utils.XssFilterUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.util.HtmlUtils;

import javax.servlet.ReadListener;
import javax.servlet.ServletInputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;

/**
 * HttpServletRequest包装类，使用装饰者模式对 request 请求参数进行增强或者修改
 *
 * @author ZOKE
 * @date 2018-9-22 / 下午 05:20
 */
@Slf4j
public class XssFilterRequestWrapper extends HttpServletRequestWrapper {

    /**
     * 缓存输入流中的字节流数据
     */
    private byte[] requestInputStreamBytes;

    public XssFilterRequestWrapper(HttpServletRequest request) throws IOException {
        super(request);
        this.requestInputStreamBytes = inputStreamToByte(request.getInputStream());
    }

    private byte[] inputStreamToByte(InputStream inStream) throws IOException {
        if (inStream == null) {
            return new byte[] {};
        }
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        byte[] buff = new byte[1024];
        int length;
        while ((length = inStream.read(buff)) > 0) {
            byteArrayOutputStream.write(buff, 0, length);
        }
        return byteArrayOutputStream.toByteArray();
    }

    @Override
    public String getParameter(String name){
        String value = super.getParameter(name);
        if(XssFilterUtils.isNeedXssHandler(value)){
            log.info("处理前的普通格式请求参数：" + value);
            value = HtmlUtils.htmlEscape(value);
            log.info("处理后的普通格式请求参数：" + value);
        }
        return value;
    }


    @Override
    public String[] getParameterValues(String name) {
        String[] arr = super.getParameterValues(name);
        if(arr != null){
            for (int i=0;i<arr.length;i++) {
                arr[i] = HtmlUtils.htmlEscape(arr[i]);
            }
        }
        return arr;
    }

    /**
     * 重写request的getInputStream方法，用于突破只能执行一次request.getInputStream()方法的限制
     * 如果不重写此方法后面可能会引起 "request body missing 异常"
     *
     * @return Servlet输入流
     */
    @Override
    public ServletInputStream getInputStream() throws UnsupportedEncodingException {
        String body = new String(requestInputStreamBytes, getCharacterEncoding());
        if(XssFilterUtils.isJson(body)){
            log.info("处理前的JSON参数：" + body);
            String result = XssFilterUtils.paramXssHandler(body);
            log.info("处理后的JSON请求参数：" + result);
            this.requestInputStreamBytes = result.getBytes(getCharacterEncoding());
        }
        final ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(requestInputStreamBytes);
        return new DelegatingServletInputStream(byteArrayInputStream);
    }

    class DelegatingServletInputStream extends ServletInputStream {

        private boolean finished = false;

        private final InputStream sourceStream;

        private DelegatingServletInputStream(InputStream sourceStream) {
            if (sourceStream == null) {
                throw new IllegalArgumentException("Source InputStream can not be null");
            }
            this.sourceStream = sourceStream;
        }

        @Override
        public boolean isFinished() {
            return this.finished;
        }

        @Override
        public boolean isReady() {
            return true;
        }

        @Override
        public void setReadListener(ReadListener listener) {
            throw new UnsupportedOperationException();
        }

        @Override
        public int read() throws IOException {
            int data = this.sourceStream.read();
            if (data == -1) {
                this.finished = true;
            }
            return data;
        }
    }
}
