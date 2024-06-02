package com.taoshao.exception;

import com.taoshao.domain.enums.AppHttpCodeEnum;

/**
 * @Author taoshao
 * @Date 2024/6/1
 */
public class SystemException extends RuntimeException {

    private int code;
    private String msg;

    public int getCode() {
        return code;
    }

    public String getMsg() {
        return msg;
    }

    public SystemException(AppHttpCodeEnum httpCodeEnum) {
        super(httpCodeEnum.getMsg());
        this.code = httpCodeEnum.getCode();
        this.msg = httpCodeEnum.getMsg();
    }

}
