package com.ssm.controller;

import com.ssm.common.response.BaseResponse;
import com.ssm.service.DemoService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @Auther: Link(dingshengxiao)
 * @Date: 2019/3/6 17:29
 * @Description:
 */
@Controller
@RequestMapping({"/demo"})
public class DemoController extends BaseController {

    @Autowired
    private DemoService demoService;

    Logger logger = LogManager.getLogger(DemoController.class.getName());

    @RequestMapping("/demo.do")
    @ResponseBody
    public BaseResponse getMainInfoAspect(@RequestBody String body, HttpServletRequest request,
                                          HttpServletResponse response) {
        return demoService.demo();
    }
}
