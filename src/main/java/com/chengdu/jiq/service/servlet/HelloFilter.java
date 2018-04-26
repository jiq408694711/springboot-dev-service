package com.chengdu.jiq.service.servlet;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import java.io.IOException;

/**
 * Created by jiyiqin on 2017/11/23.
 * a filter that filters requests to target “/hello-servlet”,
 * and prepends “filtering “ to the output.
 *
 *  Servlet Filter和Spring Interceptor区别：
 * preHandle方法在执行完Filter（如果项目配置了Filter的话）之后，执行Controller方法之前执行，
 * 如果返回false，则后面的Controller方法以及postHandle方法都不会执行。postHandle方法在执行
 * 完Controller方法之后，返回ModelAndView之前执行，此时可以修改ModelAndView中的属性。
 * afterCompletion方法在postHandle之后执行，通常执行一些资源的清理。
 *
 * Filter有如下几个用处。
 * 1.在HttpServletRequest到达Servlet之前，拦截客户的HttpServletRequest。
 * 2.根据需要检查HttpServletRequest，也可以修改HttpServletRequest头和数据。
 * 3.在HttpServletResponse到达客户端之前，拦截HttpServletResponse。
 * 4.根据需要检查HttpServletResponse，也可以修改HttpServletResponse头和数据。
 */
@WebFilter("/hello-servlet")
public class HelloFilter implements Filter {

    private static final Logger LOGGER = LoggerFactory.getLogger(HelloFilter.class);

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse,
                         FilterChain filterChain) throws IOException, ServletException {
        LOGGER.info("filter be called:{}", servletRequest.getRemoteAddr());
        servletResponse.getOutputStream().print("this is append content, hello: ");
        filterChain.doFilter(servletRequest, servletResponse);
    }

    @Override
    public void destroy() {

    }
}
