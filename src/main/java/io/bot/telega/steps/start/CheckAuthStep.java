package io.bot.telega.steps.start;

import io.bot.model.User;
import io.bot.repositories.UserRepo;
import io.bot.telega.Bot;
import io.bot.telega.steps.Stepper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.Contact;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardButton;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardRow;

import java.util.ArrayList;
import java.util.List;

import static io.bot.telega.Emoji.IPHONE;
import static io.bot.telega.Emoji.POINT_DOWN;
import static java.lang.Math.toIntExact;

@Component
@Scope(value = "prototype"/*, proxyMode = ScopedProxyMode.TARGET_CLASS*/)
public class CheckAuthStep extends Stepper {


    private boolean wasChecked;
    private boolean userExist;


    @Autowired
    UserRepo userRepository;

    @Autowired
    Bot bot;
    int progress = 0;


    @Override
    protected void actionForMessage() {
        if (!wasChecked){
            checkUserForExist();
            if (userExist){
                isFull = true;
            }
        }else if (wasChecked&&!userExist){
            stepFlow();
        }
    }

    @Override
    protected void actionForCallback() {

    }

    void stepFlow() {
        switch (progress) {
            case 0:
                actionForContact();
                progress = userExist?1:progress;
                if (progress!=1) {
                    break;
                }
            case 2:
                sendWelcomeMessage();
                isFull = progress == 1;
                break;
            default:
                break;
        }

    }

    private void sendPhoneRequest() {
        sendMessage("Для использования сервиса необходимо отправить свой номер телефона." + POINT_DOWN, getPhoneRequestKeyboard().setResizeKeyboard(true));
    }

    private void actionForContact() {
        if (update.getMessage().hasContact()) {
            Contact contact = update.getMessage().getContact();
            if (contact.getUserID() != null && contact.getUserID() == chat_id) {
                User user = new User();
                user.setChatID(toIntExact(update.getMessage().getChatId()));
                user.setChatID(contact.getUserID());
                user.setPhoneNumber(contact.getPhoneNumber());
                userRepository.createUser(user);
                userExist = true;
                monitoring.setRelatesTo(user);
            }else {
                sendMessage("Нужно отправить свой контакт" + POINT_DOWN, getPhoneRequestKeyboard().setResizeKeyboard(true));
            }
        }else {
            sendPhoneRequest();
        }
    }

    private ReplyKeyboardMarkup getPhoneRequestKeyboard() {
        ReplyKeyboardMarkup keyboardMarkup = new ReplyKeyboardMarkup();
        List<KeyboardRow> keyboardRows = new ArrayList<>();
        KeyboardRow keyboardButtons = new KeyboardRow();
        keyboardButtons.add(new KeyboardButton(IPHONE + " Share your number").setRequestContact(true));
        keyboardRows.add(keyboardButtons);
        keyboardMarkup.setKeyboard(keyboardRows);
        return keyboardMarkup;
    }


    private void checkUserForExist() {
        this.wasChecked = true;
        User user = userRepository.getUser(toIntExact(chat_id));
        userExist = user!=null;
        if (!userExist) {
            sendPhoneRequest();
        }else {
            monitoring.setRelatesTo(user);
        }
    }

    private void  sendWelcomeMessage(){
        sendMessage("Данные успешно добавленны.\nТеперь ты можешь использовать сервис.\n" +
                "Жми /start для создания мониторинга.");
    }

}
