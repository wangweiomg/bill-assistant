package com.tencent.wxcloudrun.controller;

import com.tencent.wxcloudrun.config.ApiResponse;
import com.tencent.wxcloudrun.constants.WxOpenUrlConstant;
import com.tencent.wxcloudrun.dto.wx.WxPhoneNumberResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.Map;

@Slf4j
@RestController
public class WxController {

    final RestTemplate restTemplate;

    public WxController(@Autowired RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }



    @GetMapping(value ="/api/wx/getuserphonenumber/{code}")
    ApiResponse getMobile(@PathVariable String code) {

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);



        Map<String, Object> body = new HashMap<>(1);
        body.put("code", code);
        HttpEntity<Map<String, Object>> entity = new HttpEntity<>(body, headers);
        HttpEntity<WxPhoneNumberResponse> res = restTemplate.postForEntity(WxOpenUrlConstant.GET_PHONE_NUMBER_URL, entity, WxPhoneNumberResponse.class);


        log.debug("res.body-->{}", res.getBody());



        return ApiResponse.ok(res.getBody().getPhone_info());

    }
}
