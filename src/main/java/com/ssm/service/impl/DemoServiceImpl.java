package com.ssm.service.impl;

import com.ssm.common.enums.MessageCodeEnum;
import com.ssm.common.exception.MyException;
import com.ssm.common.response.BaseResponse;
import com.ssm.service.DemoService;
import org.springframework.stereotype.Service;

/**
 * @description:
 * @auther: link(dingshengxiao)
 * @date: 2019/3/25 15:59
 */
@Service
public class DemoServiceImpl implements DemoService {

    @Override
    public BaseResponse demo() {
        throw new MyException(MessageCodeEnum.YOU_PLAY_SEKIRO_LIKE_CAI);
    }
}
