package io.bot.telega.steps.info;

import io.bot.telega.BotHelper;
import io.bot.telega.steps.UpdateResolver;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.Update;

@Component
@Scope("prototype")
public class InfoAction extends BotHelper implements UpdateResolver {

    private String textForFaq = "text for info";

    @Override
    public void updateAction(Update update) {
        getUpdateData(update);
        actionForFaq();
    }

    private void actionForFaq(){
        if (message_text.equals("/info")){
            sendMessage(textForFaq);
        }
    }
}
