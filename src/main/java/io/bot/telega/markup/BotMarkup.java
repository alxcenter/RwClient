package io.bot.telega.markup;

import io.bot.model.Passenger;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import static io.bot.telega.Emoji.*;

public class BotMarkup {

    public static InlineKeyboardMarkup getInlineKeyBoard(Map<String, String> map) {
        return getInlineKeyBoard(map, 4);
    }

    public static InlineKeyboardMarkup getInlineKeyBoard(Map<String, String> map, int elementsInRow) {
        return getInlineKeyBoard(map, elementsInRow, WHITE_CIRCLE);
    }

    public synchronized static InlineKeyboardMarkup getInlineKeyBoard(Map<String, String> map, int elementsInRow, String checkButton) {
        InlineKeyboardMarkup inlineKeyboardMarkup = new InlineKeyboardMarkup();
        List<List<InlineKeyboardButton>> inlineButtons = new ArrayList<>();
        List<InlineKeyboardButton> rows = new ArrayList<>();
        Iterator<Map.Entry<String, String>> iterator = map.entrySet().iterator();
        for (int i = 0; i < map.size(); ) {
            for (int j = 0; j < elementsInRow && i < map.size(); j++) {
                Map.Entry<String, String> next = iterator.next();
                rows.add(new InlineKeyboardButton(checkButton + next.getKey()).setCallbackData(next.getValue()));
                i++;
            }
            inlineButtons.add(rows);
            rows = new ArrayList<>();
        }
        inlineKeyboardMarkup.setKeyboard(inlineButtons);
        return inlineKeyboardMarkup;
    }

    public synchronized static InlineKeyboardMarkup getPassengerButtons(List<Passenger> passengers) {
        InlineKeyboardMarkup inlineKeyboardMarkup = new InlineKeyboardMarkup();
        List<List<InlineKeyboardButton>> inlineButtons = new ArrayList<>();
        List<InlineKeyboardButton> rows = new ArrayList<>();
        for (int i = 0; i < passengers.size(); i++) {
            Passenger p = passengers.get(i);
//            rows.add(new InlineKeyboardButton(PENCIL2).setCallbackData("edit"));
//            rows.add(new InlineKeyboardButton(REMOVE).setCallbackData("remove"));
            rows.add(new InlineKeyboardButton(PERSON_SINGLE + p.getName() + " " + p.getSurname()).setCallbackData("passId_" + i));
            inlineButtons.add(rows);
            rows = new ArrayList<>();
        }
        inlineKeyboardMarkup.setKeyboard(inlineButtons);
        return inlineKeyboardMarkup;
    }

    public synchronized static InlineKeyboardMarkup getYesNoKeyboard() {
        InlineKeyboardMarkup inlineKeyboardMarkup = new InlineKeyboardMarkup();
        List<List<InlineKeyboardButton>> inlineButtons = new ArrayList<>();
        List<InlineKeyboardButton> rows = new ArrayList<>();
            rows.add(new InlineKeyboardButton(SAVE).setCallbackData("save"));
            rows.add(new InlineKeyboardButton(REMOVE).setCallbackData("restart"));
            inlineButtons.add(rows);
        inlineKeyboardMarkup.setKeyboard(inlineButtons);
        return inlineKeyboardMarkup;
    }

}
