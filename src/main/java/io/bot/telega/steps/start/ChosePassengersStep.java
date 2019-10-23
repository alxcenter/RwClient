package io.bot.telega.steps.start;

import io.bot.model.Passenger;
import io.bot.telega.markup.BotMarkup;
import io.bot.telega.steps.Stepper;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static io.bot.telega.BenderStikers.FIRE_ASS_BENDER;
import static io.bot.telega.BenderStikers.OK_BENDER;
import static io.bot.telega.Emoji.*;

@Component
@Scope("prototype")
public class ChosePassengersStep extends Stepper {


    private List<Passenger> passengers;
    private String messageForRequestPassengers = "Отлично! Теперь введи имя и фамилию пассажиров через запятую. Например:\nОлег Кот, Иван Якорь\n" +
            "Лимит - 8 пассажиров.";
    private String confirmCreationMonitoring = "\n" + DIALOG + " У тебя получилось!\n\n*Сохранить мониторинг?*";
    private String monitoringCreated = TADA + TADA +TADA + "Мониторинг успешно создан, запускаю в работу!";

    int progress = 0;

//    public ChosePassengersStep(Bot bot) {
//        super(bot);
//    }

    @Override
    protected void actionForMessage() {
        stepFlow();
    }

    @Override
    protected void actionForCallback() {
        stepFlow();
    }


    private void stepFlow() {
        switch (progress) {
            case 0:
                messagesForRemove.add(sendSticker(OK_BENDER).getMessageId());
                messagesForRemove.add(sendMessage(messageForRequestPassengers).getMessageId());
                progress = 1;
                break;
            case 1:
                this.passengers = parsePassenger(message_text);
                InlineKeyboardMarkup passengerButtons = BotMarkup.getPassengerButtons(this.passengers);
                removeMessagesInList();
                removeMessage(update.getMessage().getMessageId());
                sendMessage(PERSON_GROUP + " *Список пассажиров:*", passengerButtons);
                finishMonitoring();
                progress = 2;
                break;
            case 2:
                if (call_data.equals("save")) {
                    monitoringActionsBeforeFinish();
                    monitoring.setPassengers(passengers);
                    monitoringCreatedSuccessfully();
                    isFull = progress == 2;
                }
                break;
            default:
                break;
        }
    }

    private List<Passenger> parsePassenger(String passengerString) {
        String[] split = passengerString.split(",");
        List<String> listWithTrim = new ArrayList<>();
        Arrays.asList(split).forEach(x -> listWithTrim.add(x.trim()));
        List<Passenger> passengers = new ArrayList<>();
        listWithTrim.forEach(x -> {
            String[] s = x.replaceAll("  ", " ").split(" ");
            passengers.add(new Passenger(s[0], s[1]));
        });
        return passengers;
    }

    private void finishMonitoring() {
        Integer messageId = sendMessage(confirmCreationMonitoring, BotMarkup.getYesNoKeyboard()).getMessageId();
        messagesForRemove.add(messageId);
    }

    private void monitoringCreatedSuccessfully(){
        removeMessagesInList();
        sendMessage(monitoringCreated);
        sendSticker(FIRE_ASS_BENDER);
    }

    private void monitoringActionsBeforeFinish(){
        passengers.forEach(x -> {
            x.setPlaceFilter(monitoring.getPlaceFilter());
            x.setMonitoring(monitoring);
        });
    }

}
