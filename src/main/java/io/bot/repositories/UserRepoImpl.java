package io.bot.repositories;

import io.bot.model.User;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
@Transactional
public class UserRepoImpl implements UserRepo {
    @Autowired
    SessionFactory sessionFactory;

    public User getUser(int chatID){
        Session session = sessionFactory.getCurrentSession();
        return session.get(User.class, chatID);
    }

    public void createUser(User user){
        Session session = sessionFactory.getCurrentSession();
        session.save(user);
    }
}
