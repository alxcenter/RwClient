package io.bot.config;

import io.bot.telega.Bot;
import io.bot.telega.MessagePool;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;
import org.telegram.telegrambots.ApiContextInitializer;
import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

@Configuration
@ComponentScan("io.bot.telega")
public class TelegaConf {
    @Bean
    @Scope("singleton")
    public Bot getBot(){
        ApiContextInitializer.init();
        Bot bot = new Bot();
        TelegramBotsApi telegramBotsApi = new TelegramBotsApi();
        try {
            telegramBotsApi.registerBot(bot);
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
        return bot;
    }

    @Bean
    public MessagePool getMessagePool(){
        MessagePool messagePool = new MessagePool(getBot());
        messagePool.startSending();
        return messagePool;
    }
}
