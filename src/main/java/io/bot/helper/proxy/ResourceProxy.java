package io.bot.helper.proxy;

import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;
import org.springframework.util.ResourceUtils;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

@Component
@Profile("dev")
public class ResourceProxy implements ProxyGrabber {

    @Override
    public List<String> getProxyList() {
        List<String> list = new ArrayList<>();
        Scanner scanner;
        try {
            File file = ResourceUtils.getFile("classpath:proxy.txt");
            scanner = new Scanner(file);
            while (scanner.hasNext()){
                list.add(scanner.nextLine());
            }
            scanner.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        return list;
    }
}
