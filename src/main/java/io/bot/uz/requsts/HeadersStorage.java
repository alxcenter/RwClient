package io.bot.uz.requsts;

import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

public class HeadersStorage {

    private final static String AGENT = "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/65.0.3325.181 Safari/537.36";

    private static MultiValueMap<String, String> getHeaders() {
        MultiValueMap<String, String> headers = new LinkedMultiValueMap<>();
        headers.add("Accept-Language", "ru-RU,ru;q=0.9,en-US;q=0.8,en;q=0.7");
        headers.add("Accept-Encoding", "gzip, deflate, br");
        headers.add("X-Requested-With", "XMLHttpRequest");
        headers.add("Connection", "keep-alive");
        headers.add("Host", "booking.uz.gov.ua");
        headers.add("User-Agent", AGENT);
        return headers;
    }

    public static MultiValueMap<String, String> getCaptchaHeaders() {
        MultiValueMap<String, String> headers = getHeaders();
        headers.add("Accept", "image/webp,image/apng,image/*,*/*;q=0.8");
        return headers;
    }

    public static MultiValueMap<String, String> getPostHeaders() {
        MultiValueMap<String, String> headers = getHeaders();
        headers.add("Content-Type", "application/x-www-form-urlencoded; charset=UTF-8");
        headers.add("Origin", "https://booking.uz.gov.ua");
        headers.add("Accept", "*/*");
        return headers;
    }

    public static MultiValueMap<String, String> geHeadersForGet() {
        MultiValueMap<String, String> headers = getHeaders();
        headers.add("Accept", "application/json, text/javascript, */*; q=0.01");
        return headers;
    }


}
