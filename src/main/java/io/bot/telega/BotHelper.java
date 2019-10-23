package io.bot.telega;

import org.springframework.beans.factory.annotation.Autowired;
import org.telegram.telegrambots.meta.api.methods.BotApiMethod;
import org.telegram.telegrambots.meta.api.methods.ParseMode;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.methods.send.SendSticker;
import org.telegram.telegrambots.meta.api.methods.updatingmessages.DeleteMessage;
import org.telegram.telegrambots.meta.api.methods.updatingmessages.EditMessageText;
import org.telegram.telegrambots.meta.api.objects.CallbackQuery;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboard;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardRemove;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.util.ArrayList;
import java.util.List;

import static java.lang.Math.toIntExact;

public class BotHelper {

    @Autowired
    protected Bot bot;
    protected Update update;
    protected boolean isFull = false;
    protected long message_id;
    protected long chat_id;
    protected String call_text;
    protected String call_data;
    protected String message_text;
    protected List<Integer> messagesForRemove = new ArrayList<>();




    public Message sendMessage(String messageBody, ReplyKeyboard replyKeyboard) {
        Message execute = null;
        SendMessage message = new SendMessage(chat_id, messageBody); // Create a message object object
        message.enableMarkdown(true).setParseMode(ParseMode.MARKDOWN).setReplyMarkup(replyKeyboard);
        try {
            execute = bot.execute(message);
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
        return execute;
    }

    public Message sendMessage(String messageBody) {
        return sendMessage(messageBody, new ReplyKeyboardRemove());
    }

    public void sendMessage(BotApiMethod new_message) {
        try {
            bot.execute(new_message);
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }

    public Message sendSticker(String stickerName) {
        Message execute = null;
        SendSticker sticker = new SendSticker();
        sticker.setChatId(chat_id).setSticker(stickerName);
        try {
            execute = bot.execute(sticker);
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
        return execute;
    }

    public void removeMessage(int messageId){
        DeleteMessage deleteMessage = new DeleteMessage();
        deleteMessage.setChatId(chat_id)
                .setMessageId(messageId);
        try {
            bot.execute(deleteMessage);
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }

    public void removeMessagesInList(){
        for (Integer x :messagesForRemove) {
            removeMessage(x);
        }
        messagesForRemove.clear();
    }



    public void editMessage(String newMessageText, InlineKeyboardMarkup markup) {
        EditMessageText editMessageText = new EditMessageText(); // Create a message object object
        editMessageText.setChatId(chat_id)
                .setMessageId(toIntExact(message_id))
                .setText(newMessageText)
                .enableMarkdown(markup!=null)
                .setReplyMarkup(markup);
        try {
            bot.execute(editMessageText);
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }



    protected void getUpdateData(Update update) {
        if (update.hasCallbackQuery()) {
            CallbackQuery query = update.getCallbackQuery();
            this.call_data = query.getData();
            this.call_text = query.getMessage().getText();
            this.message_id = query.getMessage().getMessageId();
            this.chat_id = query.getMessage().getChatId();
        } else if (update.hasMessage()) {
            this.message_text = update.getMessage().getText();
            this.message_id = update.getMessage().getMessageId();
            this.chat_id = update.getMessage().getChatId();
        }
    }

    private void rmData() {
        this.call_data = null;
        this.call_text = null;
        this.message_text = null;
        this.message_id = 0;
    }

}
