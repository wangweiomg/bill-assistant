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



        String key1 = System.getenv("OPENAI_TOKEN");
        String key2 = System.getProperty("OPENAI_TOKEN");

        log.info(" openai token has-->{}, env-->{}, prop-->{}", StringUtils.hasText(token), StringUtils.hasText(key1), StringUtils.hasText(key2));

        if (!StringUtils.hasText(token)) {

            if (StringUtils.hasText(key1)) {
                token = key1;
            } else {
                if (StringUtils.hasText(key2)) {
                    token = key2;
                }
            }


        }

        return new OpenAiService(token);

    }


}
