package io.bot.telega.steps.list;

import io.bot.model.Monitoring;
import io.bot.repositories.StationRepo;
import io.bot.service.MonitoringService;
import io.bot.telega.BotHelper;
import io.bot.telega.steps.UpdateResolver;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.Update;

import java.util.List;

import static io.bot.telega.Emoji.LOCOMOTIVE;

@Component
@Scope("prototype")
public class MonitoringList  extends BotHelper implements UpdateResolver {

    @Autowired
    MonitoringService monitoringService;

    @Autowired
    StationRepo stationRepo;


    @Override
    public void updateAction(Update update) {
        List<Monitoring> userMonitors = monitoringService.getAllUserMonitorings(update.getMessage().getChatId());
        getUpdateData(update);
        StringBuilder builder = new StringBuilder();
        userMonitors.forEach(x -> builder.append(LOCOMOTIVE + getFormattedMessage(x) + "\n"));
        sendMessage(builder.toString());
    }


    //запилил по быстрому для теста
    private String getFormattedMessage(Monitoring monitoring){
        StringBuilder builder = new StringBuilder();
        builder.append(monitoring.getTrainNumber());
        builder.append(" ");
        builder.append(stationRepo.findByStationCode(Integer.parseInt(monitoring.getFromStation())).getStationName());
        builder.append("-");
        builder.append(stationRepo.findByStationCode(Integer.parseInt(monitoring.getToStation())).getStationName());
        builder.append(" ");
        builder.append(monitoring.getDate());
        return builder.toString();
    }



}
