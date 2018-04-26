package com.chengdu.jiq.model.bo.exception;

/**
 * Created by jiyiqin on 2018/1/1.
 */
public class ServerException extends GlobalException {

    public ServerException(String message) {
        super(message, ResponseCode.SERVER_ERROR_CODE.getCode());
    }
}
