package io.bot.telega;

import org.springframework.beans.factory.annotation.Autowired;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.objects.Update;

import java.util.HashMap;
import java.util.Map;


/**
 * Created by ALX on 06.03.2018.
 */
public class Bot extends TelegramLongPollingBot {

    private Map<Long, UpdateManager> updates = new HashMap<>();
    @Autowired
    private UpdateManager um;

    private String BOT_USER_NAME;
    private String BOT_TOKEN;

    public Bot(String BOT_USER_NAME, String BOT_TOKEN) {
        this.BOT_USER_NAME = BOT_USER_NAME;
        this.BOT_TOKEN = BOT_TOKEN;
    }

    @Override
    public void onUpdateReceived(Update update) {
        getUpdateManagerForUpdate(update).updateAction(update);
    }


    @Override
    public String getBotUsername() {
        return BOT_USER_NAME;
    }

    @Override
    public String getBotToken() {
        return BOT_TOKEN;
    }

    UpdateManager getUpdateManagerForUpdate(Update update) {
        UpdateManager updateManager = null;
        Long chatId = 0L;
        try {
            if (update.hasMessage()) {
                chatId = update.getMessage().getChatId();
                updateManager = getUpdate(chatId);
            }else if (update.hasCallbackQuery()){
                chatId = update.getCallbackQuery().getMessage().getChatId();
                updateManager = getUpdate(chatId);
            }
        }catch (NullPointerException e){
            updateManager = um;
            updates.put(chatId, updateManager);
        }

        return updateManager;
    }

    private UpdateManager getUpdate(Long chatId){
        UpdateManager updateManager = updates.get(chatId);
        if (updateManager == null) throw new NullPointerException();
        return updateManager;
    }
}
