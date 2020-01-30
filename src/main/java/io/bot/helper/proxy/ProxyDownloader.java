package io.bot.helper.proxy;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Primary;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

/*Временный класс. потом перенести на другой сервак*/
@Component
@Primary
public class ProxyDownloader implements ProxyGrabber{

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
        if (matcher.find()) {
            resultUrl += matcher.group(1);
        }
        return resultUrl;
    }

    private String getProxyString(String body) {
        Pattern pattern = Pattern.compile("id.*_\\w\\we\n(.*)</textarea>", Pattern.DOTALL);
        Matcher matcher = pattern.matcher(body);
        return matcher.find() ? matcher.group(1) : null;
    }

    private List<String> convertProxyStringToList(String proxy){
        List<String> temp = Arrays.asList(proxy.split("\n"));
        return temp.stream()
                .map(this::executeProxy)
                .collect(Collectors.toList());
    }

    private String executeProxy(String line) {
        return line.split(" ")[0];
    }

}
