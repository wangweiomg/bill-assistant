package com.tencent.wxcloudrun.controller;

import com.tencent.wxcloudrun.config.ApiResponse;
import com.tencent.wxcloudrun.constants.WxOpenUrlConstant;
import com.tencent.wxcloudrun.constants.WxRequestHeaderNamesConstant;
import com.tencent.wxcloudrun.dto.wx.WxPhoneNumberResponse;
import com.tencent.wxcloudrun.model.User;
import com.tencent.wxcloudrun.service.UserService;
import com.theokanning.openai.completion.CompletionRequest;
import com.theokanning.openai.service.OpenAiService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.Random;

@Slf4j
@RestController
public class WxController {

    final RestTemplate restTemplate;
    final UserService userService;

    public WxController(@Autowired RestTemplate restTemplate, @Autowired UserService userService) {
        this.restTemplate = restTemplate;
        this.userService = userService;
    }


    @Value("${third.chatbot.url}")
    private String chatUrl;


    @GetMapping(value ="/api/wx/getuserphonenumber/{code}")
    ApiResponse getMobile(@PathVariable String code, @RequestHeader HttpHeaders requestHeaders) {

        log.info("<-- /api/wx/getuserphonenumber  get request! -->");

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        Map<String, Object> body = new HashMap<>(1);
        body.put("code", code);
        HttpEntity<Map<String, Object>> entity = new HttpEntity<>(body, headers);


        HttpEntity<WxPhoneNumberResponse> res = restTemplate.postForEntity(WxOpenUrlConstant.GET_PHONE_NUMBER_URL, entity, WxPhoneNumberResponse.class);


        log.debug("<-- getphonenumber res.body-->{}", res.getBody());

        try {

            String [] prompts = new String[] {
                    "springboot为什么这么流行",
                    "nodejs koa 和 springboot比较",
                    "go语言很流行吗",
                    "为什么Rust 的包管理这么好用",
                    "微信小程序是什么",
                    "Why Chinese people like living in apartment buildings",
                    "How to be a Java coder",
                    "What's the most popular web browser in the world",
                    "Who's the most beautiful girl in the world",
                    "How can I learn English"
            };

            String prompt = prompts[new Random().nextInt(10)];

            String result = restTemplate.getForObject(chatUrl +"?msg=" + prompt, String.class);

            log.info("<--openai result-->{}", result);



        } catch (Exception e) {
            log.error("<--test request hello world error-->", e);
        }


        try {

            WxPhoneNumberResponse phone = res.getBody();

            if (Objects.nonNull(phone) && phone.getErrcode() == 0) {

                // 保存手机号到用户表
                User user = new User();
                user.setWxOpenId(requestHeaders.getFirst(WxRequestHeaderNamesConstant.OPEN_ID));
                user.setMobile(phone.getPhone_info().getPhoneNumber());


                userService.upsert(user);


                return ApiResponse.ok(phone.getPhone_info());

            }

        } catch (RuntimeException e) {
            log.error("<--invalid response data!-->", e);

        }

        return ApiResponse.ok();



    }
}
