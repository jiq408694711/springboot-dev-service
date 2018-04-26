package com.chengdu.jiq.service.servlet;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;

/**
 * Created by jiyiqin on 2017/11/23.
 * a listener that sets a custom attribute in ServletContext
 */
@WebListener
public class HelloListener implements ServletContextListener {

    private static final Logger LOGGER = LoggerFactory.getLogger(HelloListener.class);

    @Override
    public void contextInitialized(ServletContextEvent servletContextEvent) {
        LOGGER.info("Listener be called: {}", servletContextEvent.getServletContext().getContextPath());
        servletContextEvent.getServletContext().setAttribute("servlet-context-attr", "test");
    }

    @Override
    public void contextDestroyed(ServletContextEvent servletContextEvent) {

    }
}
