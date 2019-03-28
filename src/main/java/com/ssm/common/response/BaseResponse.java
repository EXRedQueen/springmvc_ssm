package com.ssm.common.response;

import com.ssm.common.enums.MessageCodeEnum;

import java.io.Serializable;

/**
 * @description:
 * @auther: link(dingshengxiao)
 * @date: 2019/3/25 15:11
 */
public class BaseResponse<T> implements Serializable {

    private Byte status = 0;
    private String message = MessageCodeEnum.SUCCESS.getMessage();
    private String code = MessageCodeEnum.SUCCESS.getCode();
    private T data;

    public Byte getStatus() {
        return status;
    }

    public void setStatus(Byte status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }
}
