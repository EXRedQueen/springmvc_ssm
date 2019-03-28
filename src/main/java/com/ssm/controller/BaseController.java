package com.ssm.controller;

import com.ssm.common.enums.MessageCodeEnum;
import com.ssm.common.response.BaseResponse;
import com.ssm.consts.Const;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;


/**
 * @description:
 * @auther: link(dingshengxiao)
 * @date: 2019/3/25 16:40
 */
public class BaseController {

    private static Logger logger = LogManager.getLogger(BaseResponse.class);

    /**
     * 处理未处理的异常
     * @param e
     * @return
     */
    @ExceptionHandler({Exception.class})
    @ResponseBody
    public BaseResponse excuteException(Exception e) {
        logger.error("", e);
        BaseResponse baseResponse = new BaseResponse();
        baseResponse.setStatus(Const.ERROR_SERVER);
        MessageCodeEnum systemError = MessageCodeEnum.SYSTEM_ERROR;
        baseResponse.setCode(systemError.getCode());
        baseResponse.setMessage(systemError.getMessage());
        return baseResponse;
    }
}
