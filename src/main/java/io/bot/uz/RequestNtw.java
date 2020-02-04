package io.bot.uz;

import io.bot.uz.requsts.HeadersStorage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.List;
import java.util.Optional;

public class RequestNtw {

    private Logger log = LoggerFactory.getLogger(RequestNtw.class);
    private RestTemplate restTemplate;
    private final String url = "https://booking.uz.gov.ua/ru/";
    private String sessionCookies;

    public RequestNtw(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public String[] sendPost(String searchBy, String post) {
        return sendPost(searchBy, post, null);
    }

    public String[] sendPost(String searchBy, String post, String cookies) {
        ResponseEntity<String> responseEntity = getResponseEntity(searchBy, post, cookies);
        List<String> list = responseEntity.getHeaders().get("set-cookie");
        String coo = this.sessionCookies == null ? parseCookies(list) : this.sessionCookies;
        log.debug("POST cookies is " + coo);
        return new String[]{responseEntity.getBody(), coo};
    }

    public ResponseEntity<String> getResponseEntity(String searchBy, String post, String cookies) {
        MultiValueMap<String, String> headers = HeadersStorage.getPostHeaders();
        attachCookies(headers, cookies);
        HttpEntity<String> entity = new HttpEntity<>(post, headers);
        return restTemplate.postForEntity(url + searchBy + "/", entity, String.class);
    }

    public String sendGet(String searchBy) {
        return sendGet(searchBy, null);
    }

    public String sendGet(String searchBy, String cookies) {
        MultiValueMap<String, String> headers = HeadersStorage.geHeadersForGet();
        attachCookies(headers, cookies);
        HttpEntity<String> httpEntity = new HttpEntity<>(headers);
        ResponseEntity<String> entity = restTemplate.getForEntity(url + searchBy, String.class, httpEntity);
        return entity.getBody();
    }

    public InputStream getCaptcha(String searchBy, String cookies) {
        MultiValueMap<String, String> headers = HeadersStorage.getCaptchaHeaders();
        attachCookies(headers, cookies);
        HttpEntity httpEntity = new HttpEntity(headers);
        ResponseEntity<byte[]> entity = restTemplate.exchange(url + searchBy, HttpMethod.GET, httpEntity, byte[].class);
        log.debug(entity.getHeaders().toString());
        return new ByteArrayInputStream(entity.getBody());
    }

    public void attachCookies(MultiValueMap<String, String> headers, String cookies) {
        Optional.ofNullable(cookies).ifPresent(this::setSessionCookies);
        Optional.ofNullable(this.sessionCookies).ifPresent(x -> headers.add("Cookie", x));
    }

    private String parseCookies(List<String> cookiesHeaders) {
        StringBuilder cookies = new StringBuilder();
        boolean isFind = false;
        for (String x : cookiesHeaders) {
            if (hasSession(x) && isFind) {
                cookies.append(x.split(" ")[0] + " ");
            } else if (!hasSession(x)) {
                cookies.append(x.split(" ")[0] + " ");
            } else if (hasSession(x)) {
                isFind = true;
            }
        }
        log.debug("Cookies is = " + cookies.toString());

        this.sessionCookies = Optional.ofNullable(this.sessionCookies)
                .orElse(cookies.toString());
        return cookies.toString();
    }

    private boolean hasSession(String header){
        return header.startsWith("_gv_sessid");
    }

    public RequestNtw setSessionCookies(String sessionCookies) {
        this.sessionCookies = sessionCookies;
        return this;
    }

    public String getSessionCookies() {
        return sessionCookies;
    }
}
