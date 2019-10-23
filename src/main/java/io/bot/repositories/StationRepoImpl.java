package io.bot.repositories;

import io.bot.model.Station;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
@Transactional
public class StationRepoImpl implements StationRepo {

    @Autowired
    SessionFactory sessionFactory;

    @Override
    public void saveStation(Station station) {
        Session session = sessionFactory.getCurrentSession();
        session.saveOrUpdate(station);
    }

    @Override
    public Station getStation(int stationCode) {
        Session session = sessionFactory.getCurrentSession();
        Query<Station> query = session.createQuery("from Station s where s.stationCode = :stationCode", Station.class);
        query.setParameter("stationCode", stationCode);
        Station station = query.uniqueResult();
        return station;
    }
}
