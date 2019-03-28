package com.ssm.aop;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.ssm.common.enums.MessageCodeEnum;
import com.ssm.common.exception.MyException;
import com.ssm.common.response.BaseResponse;
import com.ssm.consts.Const;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.aspectj.lang.ProceedingJoinPoint;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * @description:
 * @auther: link(dingshengxiao)
 * @date: 2019/3/25 17:25
 */
@Component
public class RequestAspect {

    private static Logger logger = LogManager.getLogger(RequestAspect.class);

    public Object around(ProceedingJoinPoint proceedingJoinPoint, String body, HttpServletRequest request,
                               HttpServletResponse response) throws Throwable {
        //Object object = null;
        BaseResponse baseResponse = new BaseResponse();

        try {
            Object[] args = proceedingJoinPoint.getArgs();
            // 代理执行方法
            proceedingJoinPoint.proceed(args);
        } catch (MyException e) {
            e.printStackTrace();
            baseResponse = getMyExceptionInfo(e);
        }

        String content = JSON.toJSONString(baseResponse, SerializerFeature.DisableCircularReferenceDetect);
        writeResponse(response, content);
        return null;
    }

    private void writeResponse(HttpServletResponse response, String content) {
        PrintWriter out = null;
        try {
            response.setCharacterEncoding("UTF-8");
            out = response.getWriter();
            out.write(content);
            out.flush();
        } catch (IOException e) {
            logger.error("流写出异常", e);
        } finally {
            if (out != null) {
                out.close();
            }
        }
    }

    /**
     * 处理自定义异常
     * @param myException
     * @return
     */
    private static BaseResponse getMyExceptionInfo(MyException myException) {
        BaseResponse response = myException.getBaseResponse();
        if (response == null) {
            response = new BaseResponse();
        }
        response.setStatus(myException.getStatus());
        String message;
        String code;
        MessageCodeEnum msgCodeEnum = myException.getMessageCodeEnum();
        if (msgCodeEnum != null) {
            message = msgCodeEnum.getMessage();
            code = msgCodeEnum.getCode();
        } else if (myException.getBaseResponse() != null) {
            message = response.getMessage();
            code = response.getCode();
        } else {
            message = myException.getMessage();
            code = myException.getCode();
        }

        if (message == null ||message.trim().equals("")) {
            message = MessageCodeEnum.SYSTEM_ERROR.getMessage();
            code = MessageCodeEnum.SYSTEM_ERROR.getCode();
        }
        response.setMessage(message);
        response.setCode(code);
        return response;
    }

}
