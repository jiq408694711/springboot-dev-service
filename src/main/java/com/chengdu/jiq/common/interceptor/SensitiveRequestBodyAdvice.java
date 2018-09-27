package com.chengdu.jiq.common.interceptor;

import com.google.gson.Gson;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.MethodParameter;
import org.springframework.http.HttpInputMessage;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.RequestBodyAdvice;

import javax.inject.Inject;
import java.io.IOException;
import java.lang.reflect.Type;

/**
 * 拦截敏感词，并作出提示。
 * 特别注意：也可以用拦截器HandlerInterceptorAdapter实现，HttpServletRequest的body字段跟这里body一样。
 */
@ControllerAdvice
public class SensitiveRequestBodyAdvice implements RequestBodyAdvice {
    private static final Logger LOGGER = LoggerFactory.getLogger(SensitiveRequestBodyAdvice.class);
    @Inject
    private Gson gsonConvertor;

    @Override
    public boolean supports(MethodParameter methodParameter, Type targetType, Class<? extends HttpMessageConverter<?>> converterType) {
        return true;
    }

    @Override
    public Object handleEmptyBody(Object body, HttpInputMessage inputMessage, MethodParameter parameter, Type targetType, Class<? extends HttpMessageConverter<?>> converterType) {
        return body;
    }

    @Override
    public HttpInputMessage beforeBodyRead(HttpInputMessage inputMessage, MethodParameter parameter, Type targetType, Class<? extends HttpMessageConverter<?>> converterType) throws IOException {
        return inputMessage;
    }

    @Override
    public Object afterBodyRead(Object body, HttpInputMessage inputMessage, MethodParameter parameter, Type targetType, Class<? extends HttpMessageConverter<?>> converterType) {
        if (null != body) {
            String result = findSensitiveWord(gsonConvertor.toJson(body));
            if (null != result) {
                LOGGER.info("请求失败!包含敏感词：" + result);
                return null;    //建议抛出异常由exceptionHandler处理
            }
        }
        return body;
    }

    /**
     * 对接敏感词库
     * 检测输入是否包含敏感词，若包含则返回
     *
     * @param value
     * @return
     */
    private String findSensitiveWord(String value) {
        return null;
    }
}
