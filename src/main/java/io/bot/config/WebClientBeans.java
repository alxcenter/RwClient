package io.bot.config;

import io.bot.helper.proxy.ProxyManager;
import io.bot.helper.proxy.RwProxy;
import io.bot.uz.RequestNtw;
import io.bot.uz.StationSearcher;
import io.bot.uz.TrainSearch;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.http.client.ClientHttpRequestFactory;
import org.springframework.http.client.SimpleClientHttpRequestFactory;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.context.annotation.SessionScope;

import java.net.InetSocketAddress;
import java.net.Proxy;
import java.nio.charset.Charset;

@Configuration
public class WebClientBeans {

    @Autowired
    ProxyManager proxyManager;

    @Bean
    @SessionScope
    @Primary
    public RestTemplate getRestTemplateForWeb(){
        SimpleClientHttpRequestFactory requestFactory = new SimpleClientHttpRequestFactory();
        RestTemplate restTemplate = new RestTemplate(getRequestFactory());
        restTemplate.setRequestFactory(requestFactory);
        restTemplate.getMessageConverters()
                .add(0, new StringHttpMessageConverter(Charset.forName("UTF-8")));
        return restTemplate;
    }

    private ClientHttpRequestFactory getRequestFactory() {
        RwProxy p = proxyManager.getProxy();
        SimpleClientHttpRequestFactory requestFactory = new SimpleClientHttpRequestFactory();
        Proxy proxy = new Proxy(Proxy.Type.HTTP, new InetSocketAddress(p.getHost(), p.getPort()));
        requestFactory.setProxy(proxy);
        return requestFactory;
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
