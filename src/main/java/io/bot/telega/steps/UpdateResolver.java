package io.bot.telega.steps;

import org.telegram.telegrambots.meta.api.objects.Update;

public interface UpdateResolver {
    void updateAction(Update update);
}
