package io.bot.helper.proxy;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.context.annotation.SessionScope;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

@Component
@SessionScope
public class ProxyManager {

    @Autowired
    private ProxyStorage proxyStorage;
    private RwProxy proxy;

    @PostConstruct
    private RwProxy getNewProxy(){
        proxy = proxyStorage.getNewProxy();
        return proxy;
    }

    @PreDestroy
    private boolean putProxyBack(){
        return proxyStorage.putProxyBack(proxy);
    }

    public RwProxy getProxy() {
        return proxy;
    }
}
