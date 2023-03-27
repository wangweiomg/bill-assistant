package com.tencent.wxcloudrun.controller;

import com.tencent.wxcloudrun.config.ApiResponse;
import com.tencent.wxcloudrun.dto.WxRequestHeader;
import com.tencent.wxcloudrun.model.User;
import com.tencent.wxcloudrun.service.CardService;
import com.tencent.wxcloudrun.service.UserService;
import com.tencent.wxcloudrun.utils.WxHeaderUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;


@RestController
public class UserController {

    final UserService userService;
    final Logger logger;
    final CardService cardService;

    public UserController(@Autowired UserService userService, @Autowired CardService cardService) {
        this.userService = userService;
        this.logger = LoggerFactory.getLogger(UserController.class);
        this.cardService = cardService;
    }

    @PostMapping(value = "/api/user/upsert")
    ApiResponse upsert(@RequestHeader HttpHeaders headers) {

        logger.info("/api/user/upsert post request");

        logger.debug("/api/user/upsert post request headers-->{}", headers);

        WxRequestHeader header = WxHeaderUtil.covertToWxRequest(headers);

        User user = new User();
        user.setWxOpenId(header.getOpenId());
        user.setWxAppId(header.getAppId());
        user.setWxUnionId(header.getUnionId());


        Optional<User> userOptional = userService.getByOpenId(header.getOpenId());

        // 先upsert 了再说
        userService.upsert(user);

        if (!userOptional.isPresent()) {
            // 不存在user， 就生成测试card
            cardService.initSample(user.getId());
        }

        return ApiResponse.ok(user);

    }


}
