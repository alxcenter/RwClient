package io.bot.telega.steps.start;

import io.bot.model.PlaceFilter;
import io.bot.telega.markup.BotMarkup;
import io.bot.telega.steps.Stepper;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.updatingmessages.EditMessageReplyMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static io.bot.telega.Emoji.*;
import static java.lang.Math.toIntExact;

@Component
@Scope("prototype")
public class ChosePlaceStep extends Stepper {

    private String messageWagonType = "Теперь выбери типы вагонов которые нужно отслеживать.";
    int progress = 0;

//    public ChosePlaceStep(Bot bot) {
//        super(bot);
//    }

    @Override
    protected void actionForMessage() {
    }

    @Override
    protected void actionForCallback() {
        String call_data = update.getCallbackQuery().getData();
        switch (progress) {
            case 0:
                sendInlineKeyboardToChoseWagonTypes();
                progress = 1;
                break;
            case 1:
                progress = actionForWagonTypeFilters(call_data)?2:progress;
                isFull = progress == 2;
                break;
            default:
                break;
        }
    }


    private boolean actionForWagonTypeFilters(String call_data){
        if (call_data.equals("accept")){
            actionForAccept();
            return true;
        }else {
            modifyMarkUp(call_data);
            setWagonTypeResolver(call_data);
            editReplyMarkup();
            return false;
        }
    }

    private void actionForAccept(){
        editMessage(getPlaceFilterFinishMessage(), new InlineKeyboardMarkup());
    }

    private void editReplyMarkup(){
        EditMessageReplyMarkup new_reply_markup = new EditMessageReplyMarkup();
        new_reply_markup
                .setChatId(chat_id)
                .setMessageId(toIntExact(message_id))
                .setReplyMarkup(update.getCallbackQuery().getMessage().getReplyMarkup());
        try {
            bot.execute(new_reply_markup);
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }

    }

    private void setWagonTypeResolver(String call_data){
        PlaceFilter placeFilter = monitoring.getPlaceFilter();
        placeFilter = placeFilter == null ? new PlaceFilter() : placeFilter;
        monitoring.setPlaceFilter(placeFilter);
        switch (call_data){
            case "L":
                placeFilter.setWagon_l_type(!placeFilter.isWagon_l_type());
                break;
            case "K":
                placeFilter.setWagon_k_type(!placeFilter.isWagon_k_type());
                break;
            case "P":
                placeFilter.setWagon_p_type(!placeFilter.isWagon_p_type());
                break;
            case "C1":
                placeFilter.setWagon_c1_type(!placeFilter.isWagon_c1_type());
                break;
            case "C2":
                placeFilter.setWagon_c2_type(!placeFilter.isWagon_c2_type());
                break;
        }
    }

    private void modifyMarkUp(String wagonType) {
        boolean hasAccept = false;
        boolean isSomethingChecked = false;
        List<List<InlineKeyboardButton>> keyboard = update.getCallbackQuery().getMessage().getReplyMarkup().getKeyboard();
        for (int i = 0; i < keyboard.size(); i++) {
            for (int j = 0; j < keyboard.get(i).size(); j++) {
                InlineKeyboardButton inlineButton = keyboard.get(i).get(j);
                if (inlineButton.getCallbackData().equals("accept")){
                    hasAccept = true;
                }
                if (inlineButton.getCallbackData().equals(wagonType)) {
                    setRevertCheckbox(inlineButton);
                }
                if (inlineButton.getText().contains(CHECK_BOX)) {
                    isSomethingChecked = true;
                }
            }
        }
        if (!hasAccept){
            addAcceptButton();
        }else if (!isSomethingChecked){
            removeAcceptButton();
        }
    }

    private ArrayList<InlineKeyboardButton> getAcceptButton(){
        ArrayList<InlineKeyboardButton> acceptButton = new ArrayList<>();
        acceptButton.add(new InlineKeyboardButton().setCallbackData("accept").setText("Подтвердить"));
        return acceptButton;
    }

    private void addAcceptButton(){
        update.getCallbackQuery().getMessage().getReplyMarkup().getKeyboard().add(getAcceptButton());
    }

    private void removeAcceptButton(){
        update.getCallbackQuery().getMessage().getReplyMarkup().getKeyboard().remove(getAcceptButton());
    }

    private void setRevertCheckbox(InlineKeyboardButton inlineButton){
        String inlineText = inlineButton.getText();
        if (inlineText.contains(CHECK_BOX_EMPTY)){
            inlineText = inlineText.replaceAll(CHECK_BOX_EMPTY, CHECK_BOX);
        }else if (inlineText.contains(CHECK_BOX)){
            inlineText = inlineText.replaceAll(CHECK_BOX, CHECK_BOX_EMPTY);
        }
        inlineButton.setText(inlineText);
    }

    private void sendInlineKeyboardToChoseWagonTypes(){
        Map<String, String> map = new HashMap<>();
        map.put("Люкс", "L");
        map.put("Купе", "K");
        map.put("Плацкарт", "P");
        map.put("С1", "C1");
        map.put("С2", "C2");
        InlineKeyboardMarkup inlineKeyBoard = BotMarkup.getInlineKeyBoard(map, 3, CHECK_BOX_EMPTY);
        sendMessage(messageWagonType, inlineKeyBoard);
    }

    private String getPlaceFilterFinishMessage(){
        PlaceFilter placeFilter = monitoring.getPlaceFilter();
        StringBuilder builder = new StringBuilder();
        builder.append(TRAIN +" *Выбранные типы вагонов:*\n");
        if (placeFilter.isWagon_c1_type()) builder.append(SMALL_BLUE_DIAMOND).append(" Сидячий первого класса\n");
        if (placeFilter.isWagon_c2_type()) builder.append(SMALL_BLUE_DIAMOND).append("Сидячий второго класса\n");
        if (placeFilter.isWagon_l_type()) builder.append(SMALL_BLUE_DIAMOND).append("Люкс\n");
        if (placeFilter.isWagon_k_type()) builder.append(SMALL_BLUE_DIAMOND).append("Купе\n");
        if (placeFilter.isWagon_p_type()) builder.append(SMALL_BLUE_DIAMOND).append("Плацкарт\n");
        builder.replace(builder.length() - 1, builder.length(), "");
        return builder.toString();
    }
}
