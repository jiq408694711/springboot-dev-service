package com.chengdu.jiq.service.factorybean;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by jiyiqin on 2017/11/19.
 */
public class HttpClient {

    private static final Logger LOGGER = LoggerFactory.getLogger(HttpClient.class);

    private String address;
    private String otherAttr;

    public String sendRequest() {
        LOGGER.info("######### send request:{}, {}", address, otherAttr);
        return "response from request: " + address + "," + otherAttr;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getOtherAttr() {
        return otherAttr;
    }

    public void setOtherAttr(String otherAttr) {
        this.otherAttr = otherAttr;
    }
}
