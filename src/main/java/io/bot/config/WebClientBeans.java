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
import org.springframework.context.annotation.Profile;
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

    @Bean
    @Profile("default")
    @SessionScope
    @Primary
    public RestTemplate getRestTemplateForWeb(){
        RestTemplate restTemplate = new RestTemplate();
        restTemplate.getMessageConverters()
                .add(0, new StringHttpMessageConverter(Charset.forName("UTF-8")));
        return restTemplate;
    }

    @Bean
    @Profile("dev")
    @SessionScope
    @Primary
    public RestTemplate getDevRestTemplateForWeb(@Autowired ProxyManager proxyManager) {
        RestTemplate restTemplate = new RestTemplate(getRequestFactory(proxyManager));
        restTemplate.getMessageConverters()
                .add(0, new StringHttpMessageConverter(Charset.forName("UTF-8")));
        return restTemplate;
    }

    private ClientHttpRequestFactory getRequestFactory(ProxyManager proxyManager) {
        RwProxy p = proxyManager.getProxy();
        SimpleClientHttpRequestFactory requestFactory = new SimpleClientHttpRequestFactory();
        Proxy proxy = new Proxy(Proxy.Type.HTTP, new InetSocketAddress(p.getHost(), p.getPort()));
        requestFactory.setProxy(proxy);
        return requestFactory;
    }

    @Bean
    @SessionScope
    @Primary
    public RequestNtw getRequestNtwForWeb(@Autowired RestTemplate restTemplate) {
        return new RequestNtw(restTemplate);
    }

    @Bean
    @SessionScope
    @Primary
    public TrainSearch getTrainSearchForWeb(@Autowired RequestNtw requestNtw) {
        return new TrainSearch(requestNtw);
    }

    @Bean
    @SessionScope
    @Primary
    public StationSearcher getStationSearcherForWeb(@Autowired RequestNtw requestNtw) {
        return new StationSearcher(requestNtw);
    }
}
