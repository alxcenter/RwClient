package io.bot.config;

import io.bot.uz.RequestNtw;
import io.bot.uz.StationSearcher;
import io.bot.uz.TrainSearch;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.context.annotation.SessionScope;

import java.nio.charset.Charset;

@Configuration
public class WebClientBeans {

    @Bean
    @SessionScope
    @Primary
    public RestTemplate getRestTemplateForWeb(){
        RestTemplate restTemplate = new RestTemplate();
        restTemplate.getMessageConverters()
                .add(0, new StringHttpMessageConverter(Charset.forName("UTF-8")));
        return restTemplate;
    }

    @Bean
    @SessionScope
    @Primary
    public RequestNtw getRequestNtwForWeb(){
        return new RequestNtw(getRestTemplateForWeb());
    }

    @Bean
    @SessionScope
    @Primary
    public TrainSearch getTrainSearchForWeb(){
        TrainSearch trainSearch = new TrainSearch();
        trainSearch.setRequest(getRequestNtwForWeb());
        return trainSearch;
    }

    @Bean
    @SessionScope
    @Primary
    public StationSearcher getStationSearcherForWeb(RequestNtw requestNtw){
        StationSearcher stationSearcher = new StationSearcher();
        stationSearcher.setRequest(requestNtw);
        return stationSearcher;
    }
}
