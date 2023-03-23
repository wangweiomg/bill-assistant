package com.tencent.wxcloudrun.config;

import com.theokanning.openai.service.OpenAiService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.util.StringUtils;

@Slf4j
@Configuration
public class OpenAiServiceConfig {


    @Value("${third.openai.token}")
    private String token;

    @Bean
    public OpenAiService openAiService() {


        log.info("<--openai token has-->{}, length-->{}", StringUtils.hasText(token), StringUtils.hasLength(token));

        return new OpenAiService(token);

    }


}
