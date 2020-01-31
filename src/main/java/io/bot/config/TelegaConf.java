package io.bot.config;

import io.bot.telega.Bot;
import io.bot.telega.MessagePool;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.telegram.telegrambots.ApiContextInitializer;
import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

@Configuration
@ComponentScan("io.bot.telega")
public class TelegaConf {

    @Value("${telega.name}")
    private String BOT_USER_NAME;

    @Value("${telega.apiToken}")
    private String BOT_TOKEN;

    @Bean
    public Bot getBot(){
        ApiContextInitializer.init();
        Bot bot = new Bot(BOT_USER_NAME, BOT_TOKEN);
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
