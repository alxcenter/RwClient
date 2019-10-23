package io.bot.uz;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.List;

@Component
public class RequestNtw {

    @Autowired
    RestTemplate restTemplate;

    private final String AGENT = "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/65.0.3325.181 Safari/537.36";
    private final String url = "https://booking.uz.gov.ua/ru/";
    private String cookies;

    public String[] sendPost(String searchBy, String post) {
        return sendPost(searchBy, post, null);
    }

    public String[] sendPost(String searchBy, String post, String cookies) {
        ResponseEntity<String> responseEntity = getResponseEntity(searchBy, post, cookies);
        List<String> list = responseEntity.getHeaders().get("set-cookie");
        String coo = this.cookies == null ? parseCookies(list) : this.cookies;
        String[] result = new String[2];
        result[0] = responseEntity.getBody();
        result[1] = coo;
        return result;
    }

    public ResponseEntity<String> getResponseEntity(String searchBy, String post, String cookies) {
        MultiValueMap<String, String> headers = getHeaders();
        if (cookies != null) {
            headers.add("Cookie", cookies);
            this.cookies = cookies;
        } else if (this.cookies != null) {
            headers.add("Cookie", this.cookies);
        }
        headers.add("Accept", "*/*");
        headers.add("Content-Type", "application/x-www-form-urlencoded; charset=UTF-8");
        headers.add("Origin", "https://booking.uz.gov.ua");
        HttpEntity<String> entity = new HttpEntity<>(post, headers);
        ResponseEntity<String> responseEntity = restTemplate.postForEntity(url + searchBy + "/", entity, String.class);
        return responseEntity;
    }

    public String sendGet(String searchBy) {
        return sendGet(searchBy, null);
    }

    public String sendGet(String searchBy, boolean isImage) {
        return sendGet(searchBy, null, false);
    }

    public String sendGet(String searchBy, String cookies) {
        return sendGet(searchBy, cookies, false);
    }

    public String sendGet(String searchBy, String cookies, boolean isImage) {
        MultiValueMap<String, String> headers = getHeaders();
        if (cookies != null) {
            headers.add("Cookie", cookies);
            this.cookies = cookies;
        } else if (this.cookies != null) {
            headers.add("Cookie", this.cookies);
        }
        if (isImage) {
            headers.add("Accept", "image/webp,image/apng,image/*,*/*;q=0.8");
        } else {
            headers.add("Accept", "application/json, text/javascript, */*; q=0.01");
        }
        HttpEntity<String> httpEntity = new HttpEntity<>(headers);
        ResponseEntity<String> entity = restTemplate.getForEntity(url + searchBy, String.class, httpEntity);
        return entity.getBody();
    }

    public InputStream getCaptcha(String searchBy, String cookies) {
        MultiValueMap<String, String> headers = getHeaders();
        if (cookies != null) {
            headers.add("Cookie", cookies);
            this.cookies = cookies;
        } else if (this.cookies != null) {
            headers.add("Cookie", this.cookies);
        }
        headers.add("Accept", "image/webp,image/apng,image/*,*/*;q=0.8");


        HttpEntity headers1 = new HttpEntity(headers);

        ResponseEntity<byte[]> entity = restTemplate.exchange(url + searchBy, HttpMethod.GET, headers1, byte[].class);
//        ResponseEntity<byte[]> entity = restTemplate.getForr(url + searchBy, byte[].class, headers1);
        System.out.println(entity.getHeaders());
        return new ByteArrayInputStream(entity.getBody());
    }

    public RequestNtw setCookies(String cookies) {
        this.cookies = cookies;
        return this;
    }

    private String parseCookies(List<String> cookiesHeaders) {
        StringBuilder cookies = new StringBuilder();
        boolean isFind = false;
        for (String x : cookiesHeaders) {
            if (x.startsWith("_gv_sessid") && isFind) {
                cookies.append(x.split(" ")[0] + " ");
            } else if (!x.startsWith("_gv_sessid")) {
                cookies.append(x.split(" ")[0] + " ");
            } else if (x.startsWith("_gv_sessid")) {
                isFind = true;
            }
        }
        System.out.print("\nCookies is = " + cookies.toString() + "\n");

        if (this.cookies == null) {
            this.cookies = cookies.toString();
        }
        return cookies.toString();

    }

    private MultiValueMap<String, String> getHeaders() {
        MultiValueMap<String, String> headers = new LinkedMultiValueMap<>();
        headers.add("Accept-Encoding", "gzip, deflate, br");
        headers.add("Accept-Language", "ru-RU,ru;q=0.9,en-US;q=0.8,en;q=0.7");
        headers.add("User-Agent", AGENT);
        headers.add("X-Requested-With", "XMLHttpRequest");
        headers.add("Connection", "keep-alive");
        headers.add("Host", "booking.uz.gov.ua");
        return headers;
    }


    public String getCookies() {
        return cookies;
    }

    public RestTemplate getRestTemplate() {
        return restTemplate;
    }

    public RequestNtw setRestTemplate(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
        return this;
    }
}
