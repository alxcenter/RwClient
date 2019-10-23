package io.bot.telega;

import io.bot.telega.steps.UpdateResolver;
import io.bot.telega.steps.info.InfoAction;
import io.bot.telega.steps.list.MonitoringList;
import io.bot.telega.steps.start.StartAction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.Update;

@Component
@Scope("prototype")
public class UpdateManager {


    @Autowired
    ApplicationContext context;
    @Autowired
    @Qualifier("startAction")
    UpdateResolver action;

    StartAction startAction;
    InfoAction infoAction;
    MonitoringList listAction;

    public void updateAction(Update update) {
        resolveCommand(update);
        action.updateAction(update);
    }

    private void resolveCommand(Update update) {
        if (update.hasMessage() && update.getMessage().hasText()) {
            String text = update.getMessage().getText();
            switch (text) {
                case "/start":
                    action = startAction==null?startAction=context.getBean(StartAction.class):startAction;
                    break;
                case "/info":
                    action = infoAction==null?infoAction=context.getBean(InfoAction.class):infoAction;
                    break;
                case "/list":
                    action = listAction==null?listAction=context.getBean(MonitoringList.class):listAction;
                    break;
                default:
                    break;
            }
        }
    }


}
