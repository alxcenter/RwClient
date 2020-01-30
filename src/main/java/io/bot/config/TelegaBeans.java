package io.bot.config;

import io.bot.service.StationService;
import io.bot.uz.RequestNtw;
import io.bot.uz.StationSearcher;
import io.bot.uz.TrainSearch;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.web.client.RestTemplate;

import java.nio.charset.Charset;

@Configuration
public class TelegaBeans {

    @Bean
    @Qualifier("telega")
    public RestTemplate getRestTemplateTelega(){
        RestTemplate restTemplate = new RestTemplate();
        restTemplate.getMessageConverters()
                .add(0, new StringHttpMessageConverter(Charset.forName("UTF-8")));
        return restTemplate;
    }

    @Bean
    @Qualifier("telega")
    @Scope("prototype")
    public RequestNtw getRequestNtw(){
        return new RequestNtw(getRestTemplateTelega());
    }

    @Bean
    @Qualifier("telega")
    @Scope("prototype")
    public TrainSearch getTrainSearch(){
        return new TrainSearch(getRequestNtw());
    }

    @Bean
    @Qualifier("telega")
    @Scope("prototype")
    public StationService getStationService(){
        return new StationService(getStationSearcher());
    }

    @Bean
    @Qualifier("telega")
    @Scope("prototype")
    public StationSearcher getStationSearcher(){
        return new StationSearcher(getRequestNtw());
    }

}
