package com.chengdu.jiq.model.bo.exception;

/**
 * Created by jiyiqin on 2018/1/1.
 */
public enum ResponseCode {
    PARAM_ERROR_CODE(400),
    SERVER_ERROR_CODE(500);

    private int code;

    public void setCode(int code) {
        this.code = code;
    }

    public int getCode() {
        return code;
    }

    private ResponseCode(int code) {
        this.code = code;
    }
}
