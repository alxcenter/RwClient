package io.bot.telega.steps;

import io.bot.model.Monitoring;
import io.bot.telega.BotHelper;
import org.telegram.telegrambots.meta.api.objects.Update;


public abstract class Stepper extends BotHelper {

    protected Monitoring monitoring;

    public Monitoring getMonitoring() {
        return monitoring;
    }

    public Stepper setMonitoring(Monitoring monitoring) {
        this.monitoring = monitoring;
        return this;
    }

    public boolean updateAction(Update update) {
        this.update = update;
        getUpdateData(update);
        if (update.hasCallbackQuery()) {
            actionForCallback();
        } else if (update.hasMessage() && (update.getMessage().hasText() || update.getMessage().hasContact())) {
            actionForMessage();
        }
        rmData();
        return isFull;
    }


    private void rmData() {
        this.call_data = null;
        this.call_text = null;
        this.message_text = null;
        this.message_id = 0;
    }

    protected abstract void actionForMessage();

    protected abstract void actionForCallback();


}
