package com.tencent.wxcloudrun.config;

import com.theokanning.openai.service.OpenAiService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenAiServiceConfig {



    @Bean
    public OpenAiService init() {

        String token = System.getProperty("OPENAI_TOKEN");

        return new OpenAiService(token);

    }


}
