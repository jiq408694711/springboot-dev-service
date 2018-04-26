package com.chengdu.jiq.model.bo.exception;

/**
 * Created by jiyiqin on 2018/1/1.
 */
public class ParamException extends GlobalException {

    public ParamException(String message) {
        super(message, ResponseCode.PARAM_ERROR_CODE.getCode());
    }
}
