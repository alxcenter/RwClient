package io.bot.config;

import io.bot.uz.RequestNtw;
import io.bot.uz.StationSearcher;
import io.bot.uz.TrainSearch;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.http.client.ClientHttpRequestFactory;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.context.annotation.SessionScope;

import java.nio.charset.Charset;
import java.util.concurrent.TimeUnit;

@Configuration
public class WebClientBeans {

    private final int TIMEOUT = (int) TimeUnit.SECONDS.toMillis(10);


    @Bean
    @SessionScope
    @Primary
    public RestTemplate getRestTemplateForWeb(){
        RestTemplate restTemplate = new RestTemplate(getRequestFactory());
        restTemplate.getMessageConverters()
                .add(0, new StringHttpMessageConverter(Charset.forName("UTF-8")));
        return restTemplate;
    }

    private ClientHttpRequestFactory getRequestFactory() {
        HttpComponentsClientHttpRequestFactory factory =
                new HttpComponentsClientHttpRequestFactory();

        factory.setReadTimeout(TIMEOUT);
        factory.setConnectTimeout(TIMEOUT);
        factory.setConnectionRequestTimeout(TIMEOUT);
        return factory;
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
