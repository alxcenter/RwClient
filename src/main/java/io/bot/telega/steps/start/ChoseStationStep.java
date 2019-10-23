package io.bot.telega.steps.start;

import io.bot.model.Station;
import io.bot.repositories.StationRepo;
import io.bot.telega.steps.Stepper;
import io.bot.uz.StationSearcher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardButton;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardRow;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.concurrent.TimeUnit;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static io.bot.telega.BenderStikers.WAWED_BENDER;
import static io.bot.telega.Emoji.*;
import static java.lang.Math.toIntExact;


@Component
@Scope("prototype")
public class ChoseStationStep extends Stepper {

    @Autowired
    StationRepo  stationRepository;
    @Autowired
    StationSearcher stationSearcher;

    private String fromStationSet = STATION + " *Станция отправления:* ";
    private String toStationSet = STATION + " *Станция прибытия: *";
    private String writeStationFrom = WRITING_HAND + " Напиши станцию оправления!\nНапример: Киев!";
    private String writeStationTo = WRITING_HAND + " Теперь напиши станцию прибытия!\nНапример: Днепр!";
    private String writeDate = WRITING_HAND + " Теперь укажи дату поездки в формате год-месяц-день (2019-07-30) или выбреите из списка ниже";
    private String noStationMessage = WOMAN_SHRUG + " Такой станции нет в списке.\nПопробуй ввести название заново или повтори поиск /start";
    private String wringDateMessage = WOMAN_SHRUG + " Дата указанна в неправильном формате, попробуй еще раз.";

    Map<Integer, String> map;
    int progress = 0;

//    public ChoseStationStep(Bot bot) {
//        super(bot);
//    }

    @Override
    protected void actionForMessage() {
            stepFlow();
    }

    @Override
    protected void actionForCallback() {

    }


    private void stepFlow() {
        messagesForRemove.add(toIntExact(message_id));
        switch (progress) {
            case 0:
                if (message_text.equals("/start")) {
                    sendSticker(WAWED_BENDER);
                    startMessage();
                    progress = 1;
                }
                break;
            case 1:
//                removeMessagesInList();
                progress = proposeToChoseStation(message_text, true) ? 2 : progress;
                break;
            case 2:
                progress = setStation(message_text, true) ? 3 : progress;
                break;
            case 3:
//                removeMessagesInList();
                progress = proposeToChoseStation(message_text, false) ? 4 : progress;
                break;
            case 4:
                progress = setStation(message_text, false) ? 5 : progress;
                break;
            case 5:
                progress = setData(message_text) ? 6 : progress;
                isFull = progress == 6;
                break;
            default:
                break;
        }
    }

    /*
     * Действие когда в месседже приходит /start
     * */
    private void startMessage() {
        map = null;
        progress = 1;
        messagesForRemove.add(sendMessage(writeStationFrom).getMessageId());
    }


    private boolean setStation(String messageBody, boolean isFrom) {
        if (map == null || !map.containsValue(messageBody)) {
            sendErrorMessage(noStationMessage);
            progress = isFrom ? 1 : 3;
            return false;
        }
        //засетить станцию мониторингу и занулить мапу
        map.entrySet().forEach(x -> {
            if (x.getValue().equals(messageBody)) {
                if (isFrom) {
                    monitoring.setFromStation(String.valueOf(x.getKey()));
                } else {
                    monitoring.setToStation(String.valueOf(x.getKey()));
                }
            }
        });
        map = null;

        StringBuilder messageData = new StringBuilder(isFrom ? fromStationSet : toStationSet)
                .append(messageBody)
                .append("\n\n")
                .append(writeStationTo);
        if (isFrom) {
            sendMessage(messageData.toString());
        } else {
            proposeToChoseDate();
        }
        return true;
    }

    private boolean setData(String messageBody) {
        Pattern pattern = Pattern.compile("20[\\d]{2}-[\\d]{2}-[\\d]{2}");
        Matcher matcher = pattern.matcher(messageBody.replaceAll(" ", ""));
        if (matcher.matches()) {
//            String date = messageBody.replaceAll("\\.", "-");
            try {
                monitoring.setDate(new SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH).parse(messageBody));
            } catch (ParseException e) {
                e.printStackTrace();
            }
            return true;
        } else {
            sendErrorMessage(wringDateMessage);
            return false;
        }
    }


    /*
     * отправляем юзеру клаву со списком станций.
     * если станций нет, отправляем сообщение что их нет, и давай типа заново ищи
     * */

    private boolean proposeToChoseStation(String stationName, boolean isFrom) {
        String template = "Выбери из списка станцию %s " +
                POINT_DOWN + "\nЕсли станции в списке нет - повтори поиск /start";
        String text = String.format(template, isFrom ? "отправления" : "прибытия");
        SendMessage sendMessage = new SendMessage(chat_id, text)
                .setReplyMarkup(getKeyboardOfStations(stationName));
        if (map == null || map.isEmpty()) {
            sendErrorMessage(noStationMessage);
            return false;
        } else {
            sendMessage(sendMessage);
            return true;
        }
    }

    private boolean proposeToChoseDate() {
        sendMessage(writeDate, getKeyboardOfDates());
        return true;
    }


    /*
     *вытягиваем из укрзализныци список станций.
     * */
    private ReplyKeyboardMarkup getKeyboardOfStations(String stationName) {
        map = stationSearcher.getStations(stationName);
        saveStationsToDb(map);
        List<KeyboardRow> rowList = new ArrayList<>();
        map.values().stream().sorted().forEach(x ->
        {
            KeyboardRow row = new KeyboardRow();
            row.add(new KeyboardButton().setText(x));
            rowList.add(row);
        });
        return new ReplyKeyboardMarkup()
                .setKeyboard(rowList)
                .setResizeKeyboard(true)
                .setOneTimeKeyboard(true);
    }

    private ReplyKeyboardMarkup getKeyboardOfDates() {
        List<KeyboardRow> rowList = new ArrayList<>();
        Date date = new Date();
        for (int i = 0; i < 30; i++) {
            String text = new SimpleDateFormat("yyyy-MM-dd").format(date);
            date.setTime(date.getTime() + TimeUnit.DAYS.toMillis(1));
            KeyboardRow row = new KeyboardRow();
            row.add(new KeyboardButton().setText(text));
            rowList.add(row);
        }
        return new ReplyKeyboardMarkup()
                .setOneTimeKeyboard(true)
                .setResizeKeyboard(true)
                .setSelective(true).setKeyboard(rowList);
    }

    /*
     * Отправляем ошибку когда пользователь ввел несуществующие названия станций
     * */

    private void sendErrorMessage(String errorMessage) {
        sendMessage(errorMessage);
    }

    private void saveStationsToDb(Map<Integer, String> map){
        for (Map.Entry<Integer, String> s : map.entrySet()) {
            stationRepository.saveStation(new Station(s.getKey(), s.getValue()));
        }
    }


}
