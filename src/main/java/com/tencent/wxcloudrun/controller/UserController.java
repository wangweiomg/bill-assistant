package com.tencent.wxcloudrun.controller;

import com.tencent.wxcloudrun.config.ApiResponse;
import com.tencent.wxcloudrun.dto.WxRequestHeader;
import com.tencent.wxcloudrun.model.User;
import com.tencent.wxcloudrun.service.UserService;
import com.tencent.wxcloudrun.utils.WxHeaderUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;



@RestController
public class UserController {

    final UserService userService;
    final Logger logger;

    public UserController(@Autowired UserService userService) {
        this.userService = userService;
        this.logger = LoggerFactory.getLogger(UserController.class);
    }

    @PostMapping(value = "/api/user/upsert")
    ApiResponse upsert(@RequestHeader HttpHeaders headers) {

        logger.info("/api/user/upsert post request");

        logger.debug("/api/user/upsert post request headers-->{}", headers);

        WxRequestHeader header = WxHeaderUtil.covertToWxRequest(headers);

        //todo 通过openId 获取用户公开信息

        User user = new User();
        user.setWxOpenId(header.getOpenId());
        user.setWxAppId(header.getAppId());
        user.setWxUnionId(header.getUnionId());


        userService.upsert(user);

        return ApiResponse.ok(user);

    }


}
