package io.bot.repositories;

import io.bot.model.User;

public interface UserRepo {
    User getUser(int chatID);
    void createUser(User user);
}
