package com.ssm.common.enums;

import com.ssm.consts.Const;

/**
 * 接口返回码定义
 * 错误码
 */
public enum MessageCodeEnum {

    SUCCESS("0", "Sucess", "返回成功", Const.YES),
    SYSTEM_ERROR("1", "System is busy, please try again.", "系统异常", Const.NO),
    YOU_PLAY_SEKIRO_LIKE_CAI("2", "你打只狼像蔡徐坤", "其他异常", Const.NO);

    private String code;
    private String message;
    private String cnMessage;
    private Byte isNormalException;

    MessageCodeEnum(String code, String message, String cnMessage, Byte isNormalException) {
        this.code = code;
        this.message = message;
        this.cnMessage = cnMessage;
        this.isNormalException = isNormalException;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getCnMessage() {
        return cnMessage;
    }

    public void setCnMessage(String cnMessage) {
        this.cnMessage = cnMessage;
    }

    public Byte getIsNormalException() {
        return isNormalException;
    }

    public void setIsNormalException(Byte isNormalException) {
        this.isNormalException = isNormalException;
    }
}
