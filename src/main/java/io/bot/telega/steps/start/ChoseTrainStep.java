package io.bot.telega.steps.start;

import io.bot.telega.Emoji;
import io.bot.telega.markup.BotMarkup;
import io.bot.telega.steps.Stepper;
import io.bot.uz.BotException.*;
import io.bot.uz.RequestNtw;
import io.bot.uz.Train;
import io.bot.uz.TrainSearch;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendPhoto;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static io.bot.telega.BenderStikers.CRYING_BENDER;
import static io.bot.telega.Emoji.*;

@Component
@Scope("prototype")
public class ChoseTrainStep extends Stepper {

    @Autowired
    TrainSearch trainSearch;
    int progress = 1;
    boolean wasSearchSuccess = false;
    private InlineKeyboardMarkup inlineKeyboardMarkup;
    private String tempTrainNumber = null;
    private String trainPropose = "Посмотри список поездов в расписании " + POINT_UP + ".\nВыбери номер поезда для поиска из списка ниже" + POINT_DOWN;
    private String noTrainMessage = "По заданному направлению поезда не курсируют.\nПопробуйте поиск с другими параметрами /start";
    private List<Train> trainList;
    private int trainListMessageId;

    @Autowired
    RequestNtw request;

//    public ChoseTrainStep(Bot bot) {
//        super(bot);
//    }

    @Override
    protected void actionForMessage() {
        stepFlow();
    }

    @Override
    protected void actionForCallback() {
        callBackFlow();
    }


    private void callBackFlow() {
        if (call_data.equals("accept")){
            if (tempTrainNumber!=null){
                actionForAccept();
                progress = 3;
                isFull = true;
            }
        }else {
            modifyMarkUp(call_data);
            String answer = "Выбран поезд: *" + call_data + "*.\nНажми \"Подтвердить\" или выбери другой поезд.";
            editMessage(answer, inlineKeyboardMarkup);
        }
    }

    private void stepFlow() {
        switch (progress) {
            case 0:
                wasSearchSuccess = startSearching(message_text);
                progress = wasSearchSuccess ? 2 : progress;
                break;
            case 1:
                wasSearchSuccess = startSearching(null);
                progress = wasSearchSuccess ? 2 : 0;
                break;
            default:
                break;
        }
    }

    //if success return true
    private boolean startSearching(String captcha) {
//        trainSearch = trainSearch == null ? new TrainSearch() : trainSearch;
        List<Train> trains = null;
        try {
            if (captcha == null) {
                trains = trainSearch.getTrains(monitoring.getFromStation(), monitoring.getToStation(), monitoring.getDate());
            } else {
                trains = trainSearch.getTrains(monitoring.getFromStation(), monitoring.getToStation(), monitoring.getDate(), captcha);
            }
        } catch (OtherException e) {
            e.printStackTrace();
        } catch (WrongDateException e) {
            e.printStackTrace();
        } catch (TrainNotFoundException e) {
            sendMessage(noTrainMessage);
        } catch (ServiceTemporarilyUnavailableException e) {
            e.printStackTrace();
        } catch (CaptchaException e) {
            sendCaptchaMessage(e.getMessage());
            return false;
        }
        if (trains != null) {
            this.trainList = trains;
            sendTrainList(trains);
        }
        return true;
    }

    private void sendCaptchaMessage(String cookies) {
        sendSticker(CRYING_BENDER);
        ooopsCaptcha();
        SendPhoto photoMessage = new SendPhoto();
        photoMessage.setChatId(update.getMessage().getChatId());
//        photoMessage.setPhoto("captcha", new GetSender(cookies).getCaptchaFromUrl("captcha/"));
        photoMessage.setPhoto("captcha", request.getCaptcha("captcha/", cookies));
        try {
            bot.execute(photoMessage);
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }




    private void sendTrainList(List<Train> trains) {
        StringBuilder builder = new StringBuilder();
        trains.stream().forEach(x -> builder.append(getFormattedTrain(x)));
        builder.append(getDividedLine());
        trainListMessageId = sendMessage(builder.toString()).getMessageId();
        sentProposeToChoseStationMessage(trains);
    }

    private String getDividedLine() {
        StringBuilder stringBuilder = new StringBuilder();
        for (int i = 0; i < 8; i++) {
            stringBuilder.append(Emoji.WAVY_DASH);
        }
        return stringBuilder.toString();
    }

    private String getFormattedTrain(Train train) {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(getDividedLine() + "\n\n")
                .append(Emoji.LOCOMOTIVE).append(" *" + train.getNum())
                .append(" " + train.getFrom().getStationTrain())
                .append(" — " + train.getTo().getStationTrain() + "*\n")
                .append(Emoji.CLOCK12).append("отправление: ").append(train.getFrom().getTime() + " ")
                .append(new SimpleDateFormat("dd-MM-yyyy").format(train.getFrom().getSrcDate()) + "\n")
                .append(Emoji.CLOCK830).append("прибытие: ").append(train.getTo().getTime() + " ")
                .append(train.getTo().getDate().split(" ")[1].replaceAll("\\.", "-") + "\n")
                .append(Emoji.WATCH).append("время в пути: ").append(train.getTravelTime() + "\n\n");
        return stringBuilder.toString();
    }

    private InlineKeyboardMarkup getTrainsInlineKeyboard(List<Train> trains) {
        Map<String, String> map = new HashMap<>();
        trains.forEach(x -> map.put(x.getNum(), x.getNum()));
        InlineKeyboardMarkup inlineKeyBoard = BotMarkup.getInlineKeyBoard(map);
        //добавляем кнопку подтверждения.
        this.inlineKeyboardMarkup = inlineKeyBoard;
        return inlineKeyBoard;
    }

    private void sentProposeToChoseStationMessage(List<Train> trains) {
        sendMessage(trainPropose, getTrainsInlineKeyboard(trains));
    }

    private void ooopsCaptcha() {
        sendMessage("Упс, каптча. Введи символы на картинке");
    }

    private void modifyMarkUp(String trainNum) {
        this.tempTrainNumber = trainNum;
        boolean hasAccept = false;
        List<List<InlineKeyboardButton>> keyboard = inlineKeyboardMarkup.getKeyboard();
        for (int i = 0; i < keyboard.size(); i++) {
            for (int j = 0; j < keyboard.get(i).size(); j++) {
                InlineKeyboardButton inlineKeyboardButton = keyboard.get(i).get(j);
                if (inlineKeyboardButton.getCallbackData().equals("accept")){
                    hasAccept = true;
                }
                if (inlineKeyboardButton.getCallbackData().equals(trainNum)) {
                    inlineKeyboardButton.setText(inlineKeyboardButton.getText().replaceAll(WHITE_CIRCLE, ""));
                    inlineKeyboardButton.setText(inlineKeyboardButton.getText().replaceAll(RADIO_BUTTON, ""));
                    inlineKeyboardButton.setText(RADIO_BUTTON + inlineKeyboardButton.getText());
                }else if (inlineKeyboardButton.getText().contains(RADIO_BUTTON)) {
                    inlineKeyboardButton.setText(inlineKeyboardButton.getText().replaceAll(RADIO_BUTTON, WHITE_CIRCLE));
                }
            }
        }
        if (!hasAccept){
            addAcceptButton();
        }
    }

    private void addAcceptButton(){
        ArrayList<InlineKeyboardButton> acceptButton = new ArrayList<>();
        acceptButton.add(new InlineKeyboardButton().setCallbackData("accept").setText("Подтвердить"));
        inlineKeyboardMarkup.getKeyboard().add(acceptButton);
    }

    private void actionForAccept(){
        monitoring.setTrainNumber(tempTrainNumber);
        modifyToSuccess();
    }

    private void modifyToSuccess(){
        removeMessage(trainListMessageId);
        editMessage(LOCOMOTIVE + "*Выбран поезд " + getFormattedMonitoring(), new InlineKeyboardMarkup());
    }

    private String getFormattedMonitoring(){
        Train train = null;
        for (Train x: trainList) {
            if (x.getNum().equals(monitoring.getTrainNumber())){
                train = x;
            }
        }
        StringBuilder builder = new StringBuilder();
        builder.append(monitoring.getTrainNumber() + ":\n")
                .append(train.getFrom().getStation() + " - ")
                .append(train.getTo().getStation() + "*")
                .append("\n")
                .append(Emoji.CLOCK12).append("отправление:\n     ").append(train.getFrom().getTime() + " " + train.getFrom().getDate() + "\n")
                .append(Emoji.CLOCK830).append("прибытие:\n     ").append(train.getTo().getTime() + " " + train.getTo().getDate());
        return builder.toString();
    }

}