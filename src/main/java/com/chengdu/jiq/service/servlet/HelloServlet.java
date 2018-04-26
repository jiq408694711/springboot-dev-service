package com.chengdu.jiq.service.servlet;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created by jiyiqin on 2017/11/23.
 * 当使用Spring-Boot时，嵌入式Servlet容器通过扫描注解的方式注册Servlet、
 * Filter和Servlet规范的所有监听器（如HttpSessionListener监听器）。
 * Spring boot 的主 Servlet 为 DispatcherServlet，其默认的url-pattern为“/”。
 * 也许我们在应用中还需要定义更多的Servlet
 *
 * a Servlet that serves GET requests and responds “hello-servlet”
 */
@WebServlet("/hello-servlet")
public class HelloServlet extends HttpServlet {

    private static final Logger LOGGER = LoggerFactory.getLogger(HelloServlet.class);

    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response) {
        try {
            LOGGER.info("Servlet be called: {}", request.getRequestURL());
            response.getOutputStream().write("SpringBoot-dev用户".getBytes("GBK"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
