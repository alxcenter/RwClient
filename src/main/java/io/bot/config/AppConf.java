package io.bot.config;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.HashMap;
import java.util.Map;

@Configuration
public class AppConf {

    @Bean
    @Qualifier("sess")
    public Map<String, Object> storage(){
        return new HashMap<>();
    }
}
