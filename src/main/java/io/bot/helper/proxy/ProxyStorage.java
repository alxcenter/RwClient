package io.bot.helper.proxy;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Component
public class ProxyStorage {
    @Autowired
    ProxyParser proxyParser;

    private Queue<RwProxy> freshProxy = new LinkedList<>();
    private List<RwProxy> usedProxy = new LinkedList<>();

    @PostConstruct
    public void init(){
        ExecutorService executorService = Executors.newSingleThreadExecutor();
        executorService.submit(() -> {
            List<RwProxy> validProxy = proxyParser.getValidProxy();
            validProxy.stream().filter(this::timeLimit5Sec)
                    .forEach(freshProxy::add);
        });
        executorService.shutdown();
    }

    public boolean timeLimit5Sec(RwProxy rwProxy) {
        return rwProxy.getResponseTime() < 5;
    }

    public RwProxy getNewProxy(){
        RwProxy rwProxy = freshProxy.poll();
        usedProxy.add(rwProxy);
        return rwProxy;
    }

    public boolean putProxyBack(RwProxy rwProxy){
        usedProxy.remove(rwProxy);
        return freshProxy.add(rwProxy);
    }
}
