package com.chengdu.jiq.config;

import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.context.annotation.Configuration;

/**
 * Created by jiyiqin on 2017/11/23.
 * /**
 * When using an embedded servlet container you can register Servlets,
 * Filters and all the listeners from the Servlet spec (e.g. HttpSessionListener)
 * either by using Spring beans or by scanning for Servlet components.
 * When using an embedded container, automatic registration of @WebServlet, @WebFilter,
 * and @WebListener annotated classes can be enabled using @ServletComponentScan.
 *
 * @ServletComponentScan will have no effect in a standalone container, where
 * the container’s built-in discovery mechanisms will be used instead.
 */
@Configuration
@ServletComponentScan(value = "com.chengdu.jiq.service.servlet")
public class ServletConfig {
}
