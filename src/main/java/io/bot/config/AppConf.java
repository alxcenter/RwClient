package io.bot.config;

import org.springframework.context.annotation.*;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

import java.nio.charset.Charset;

@Configuration
//@ComponentScan({"io.bot.controllers", "io.bot.repositories", "io.bot.service", "io.bot.uz"})
//@Import(TelegaConf.class)
//@Import(DbConfig.class)
//@EnableWebMvc
public class AppConf {

    @Bean
    @Scope("prototype")
    public RestTemplate getRestTemplate(){
        RestTemplate restTemplate = new RestTemplate();
        restTemplate.getMessageConverters()
                .add(0, new StringHttpMessageConverter(Charset.forName("UTF-8")));
        return restTemplate;
    }

}
