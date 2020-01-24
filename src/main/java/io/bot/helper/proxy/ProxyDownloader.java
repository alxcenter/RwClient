package io.bot.helper.proxy;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/*Временный класс. потом перенести на другой сервак*/
@Component
public class ProxyDownloader {

    @Autowired
    @Qualifier("telega")
    private RestTemplate restTemplate;

    @Value("${rw_proxy}")
    private String proxyUrl;

    public List<String> getProxyList(){
        String response = getResponse(proxyUrl);
        String lastProxyPage = getLastProxyPage(response);
        String proxyResponse = getResponse(lastProxyPage);
        String proxyString = getProxyString(proxyResponse);
        return convertProxyStringToList(proxyString);
    }

    private String getResponse(String url) {
        ResponseEntity<String> response = restTemplate.getForEntity(url, String.class);
        return response.getBody();
    }

    private String getLastProxyPage(String body) {
        String[] temp = proxyUrl.split("/u/"); //секретик
        String resultUrl = temp[0];
        String source = temp[1].replaceAll("\\d", "");
                Pattern pattern = Pattern.compile("<a href=\"(/.*)\">" + source + "_proxy");
        Matcher matcher = pattern.matcher(body);
        while (matcher.find()) {
            String group = matcher.group(1);
            resultUrl = resultUrl + group;
            break;
        }
        return resultUrl;
    }

    private String getProxyString(String body) {
        String resultProxy = new String();
        Pattern pattern = Pattern.compile("id.*_\\w\\we\n(.*)</textarea>", Pattern.DOTALL);
        Matcher matcher = pattern.matcher(body);
        while (matcher.find()) {
            String group = matcher.group(1);
            resultProxy = group;
            break;
        }
        return resultProxy;
    }

    private List<String> convertProxyStringToList(String proxy){
        List<String> temp = Arrays.asList(proxy.split("\n"));
        List<String> list = new ArrayList<>();
        temp.forEach(x -> {
            if (validateAnonymousProxy(x)){
                list.add(x.split(" ")[0]);
            }
        });
        return list;
    }

    private boolean validateAnonymousProxy(String pr) {
        if (pr.contains("-N")) return false;
        return true;
    }

}
