package io.bot.helper.proxy;

import com.google.common.util.concurrent.ThreadFactoryBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.http.client.ClientHttpRequestFactory;
import org.springframework.http.client.SimpleClientHttpRequestFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.net.InetSocketAddress;
import java.net.Proxy;
import java.util.*;
import java.util.concurrent.*;
import java.util.stream.Collectors;

@Component
public class ProxyParser {

    private Logger log = LoggerFactory.getLogger(ProxyParser.class);
    private final String URL = "https://booking.uz.gov.ua/ru/train_search/";

    @Autowired
    ProxyGrabber grabber;

    private static List<String> proxyList;
    private static List<RwProxy> successProxy = Collections.synchronizedList(new LinkedList<>());
    private static CountDownLatch latch;

    public List<RwProxy> getValidProxy() {
        proxyList = grabber.getProxyList();
        log.info("Input list for parsing has " + proxyList.size() + " proxies");
        ExecutorService service = Executors.newFixedThreadPool(10, new ThreadFactoryBuilder().setNameFormat("Parse-proxy-thread-%d").build());
        latch = new CountDownLatch(proxyList.size());
        for (String x : proxyList) {
            String[] stringProxy = x.split(":");
            RwProxy proxyObject = new RwProxy();
            proxyObject.setHost(stringProxy[0]);
            proxyObject.setPort(Integer.valueOf(stringProxy[1]));
            service.submit(() -> checkProxy(proxyObject));
        }
        while (latch.getCount() > 0){
            try {
                Thread.sleep(5000);
                System.out.println("Threads left " + latch.getCount() + " Successful list size: " + successProxy.size());
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        try {
            latch.await();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("Success proxy size " + successProxy.size());
        for (RwProxy x : successProxy) {
//            log.debug(x.getHost() + ":" + x.getPort() + " : " + x.getResponseTime() + " sec.");
            System.out.println(x.getHost() + ":" + x.getPort());
        }
        service.shutdown();

        return successProxy.stream()
                .filter(x-> x.getResponseTime() < 5)
                .collect(Collectors.toList());
    }

    private void checkProxy(RwProxy proxy) {
        ExecutorService checkOneProxyExecutor = Executors.newSingleThreadExecutor(new ThreadFactoryBuilder()
                .setNameFormat("Single-parser-thread-%d")
                .setDaemon(true).build());
        Future<?> submit = checkOneProxyExecutor.submit(() ->
                {
                    try {
                        actionForProxy(proxy);
                    } catch (NoSuchElementException e) {
                        log.error(e.getMessage());
                    }
                }
        );

        try {
            submit.get(5, TimeUnit.SECONDS);
        } catch (InterruptedException e) {
            submit.cancel(true);
        } catch (ExecutionException e) {
            submit.cancel(true);
        } catch (TimeoutException e) {
            submit.cancel(true);
        } finally {
            latch.countDown();
        }
        checkOneProxyExecutor.shutdown();
    }

    private ClientHttpRequestFactory getRequestFactory(RwProxy pr) {
        SimpleClientHttpRequestFactory requestFactory = new SimpleClientHttpRequestFactory();
        Proxy proxy = new Proxy(Proxy.Type.HTTP, new InetSocketAddress(pr.getHost(), pr.getPort()));
        requestFactory.setProxy(proxy);
        return requestFactory;
    }


    private void actionForProxy(RwProxy x) {
        Thread counterThread = new Thread(() -> {
            try {
                Thread.sleep(1_000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });
        counterThread.start();
        boolean is2xx = getRequest(x);
        if (is2xx) {
            successProxy.add(x);
        }
    }


    private boolean getRequest(RwProxy proxyObject) {
        Date date1 = new Date();
        RestTemplate restTemplate = new RestTemplate(getRequestFactory(proxyObject));
        ResponseEntity<String> response = restTemplate.postForEntity(URL, new HttpEntity<>("from=2200001&to=2218000&date=2020-02-30"), String.class);
        response.getStatusCode().is2xxSuccessful();
        Date date2 = new Date();
        proxyObject.setResponseTime((int) ((date2.getTime() - date1.getTime()) / 1000));
        return response.getStatusCode().is2xxSuccessful();
    }


}
