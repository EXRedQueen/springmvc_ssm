package com.ssm.common.exception;

import com.ssm.common.enums.MessageCodeEnum;
import com.ssm.common.response.BaseResponse;
import com.ssm.consts.Const;

/**
 * @description: 自定义异常类，错误统一由此抛出
 * @auther: link(dingshengxiao)
 * @date: 2019/3/20 19:59
 */
public class MyException extends RuntimeException {

    private Byte status = Const.ERROR_SERVER;
    private String message = MessageCodeEnum.SYSTEM_ERROR.getMessage();
    private String code = MessageCodeEnum.SYSTEM_ERROR.getCode();
    private MessageCodeEnum messageCodeEnum;
    private BaseResponse baseResponse;

    public MyException() {}

    public MyException(String message) {
        super(message);
        this.message = message;
    }

    public MyException(MessageCodeEnum messageCodeEnum) {
        super(messageCodeEnum.getMessage());
        this.messageCodeEnum = messageCodeEnum;
    }

    public MyException(String message, String messageCode) {
        super(message);
        this.message = message;
        this.code = messageCode;
    }

    public Byte getStatus() {
        return status;
    }

    public void setStatus(Byte status) {
        this.status = status;
    }

    @Override
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

    public MessageCodeEnum getMessageCodeEnum() {
        return messageCodeEnum;
    }

    public void setMessageCodeEnum(MessageCodeEnum messageCodeEnum) {
        this.messageCodeEnum = messageCodeEnum;
    }

    public BaseResponse getBaseResponse() {
        return baseResponse;
    }

    public void setBaseResponse(BaseResponse baseResponse) {
        this.baseResponse = baseResponse;
    }
}
