package io.bot.repositories;

import io.bot.model.Monitoring;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
@Transactional
public class MonitoringRepoImpl implements MonitoringRepo {

    @Autowired
    SessionFactory sessionFactory;

    @Override
    public void addMonitoring(Monitoring monitoring) {
        Session session = sessionFactory.getCurrentSession();
        session.save(monitoring);
    }

    @Override
    public List<Monitoring> getUserMonitors(long id) {
        Session currentSession = sessionFactory.getCurrentSession();
        Query query = currentSession.createQuery("from Monitoring m where m.relatesTo.chatID = :id", Monitoring.class);
        query.setParameter("id", (int)id);
        List<Monitoring> resultList = query.getResultList();
        return resultList;
    }

    @Override
    public Monitoring getMonitor(long id) {
        Session currentSession = sessionFactory.getCurrentSession();
        Query query = currentSession.createQuery("from Monitoring m where m.id= :id", Monitoring.class);
        query.setParameter("id", id);
        return (Monitoring) query.getSingleResult();
    }

    @Override
    public void disableMonitoring(Monitoring monitoring) {
        monitoring.setStatus(1);
        sessionFactory.getCurrentSession().update(monitoring);
    }

    @Override
    public void enableMonitoring(Monitoring monitoring) {
        monitoring.setStatus(0);
        sessionFactory.getCurrentSession().update(monitoring);
    }

    @Override
    public void deleteMonitoring(long id) {
        Session currentSession = sessionFactory.getCurrentSession();
        Query query = currentSession.createQuery("delete from Monitoring m where m.id= :id", Monitoring.class);
        query.setParameter("id", id);
        query.executeUpdate();
    }

}
