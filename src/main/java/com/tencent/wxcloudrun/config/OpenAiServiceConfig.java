package com.tencent.wxcloudrun.config;

import com.theokanning.openai.service.OpenAiService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenAiServiceConfig {


    @Value("${third.openai.token}")
    private String token;

    @Bean
    public OpenAiService init() {

        return new OpenAiService(token);

    }


}
