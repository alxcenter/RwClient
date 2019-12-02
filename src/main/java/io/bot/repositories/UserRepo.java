package io.bot.repositories;

import io.bot.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Transactional
public interface UserRepo extends JpaRepository<User, Long> {
    Optional<User> getUserByChatID(long chatID);
}
