package io.bot.service;

import io.bot.model.Monitoring;
import io.bot.model.User;
import io.bot.repositories.MonitoringRepo;
import io.bot.repositories.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MonitoringServiceImpl implements MonitoringService {

    @Autowired
    MonitoringRepo monitoringRepo;

    @Autowired
    UserRepo userRepo;

    @Override
    public void createMonitoring(Monitoring monitoring) {
        User user = userRepo.getUserByChatID(monitoring.getRelatesTo().getChatID());
        if (user!=null) monitoring.setRelatesTo(user);
        monitoringRepo.save(monitoring);
    }

    @Override
    public Monitoring getMonitoring(long id) {
        return monitoringRepo.findMonitoringById(id);
    }

    @Override
    public List<Monitoring> getAllUserMonitorings(long id) {
        return monitoringRepo.findMonitoringsByRelatesToChatID(id);
    }

    @Override
    public void deleteMonitoring(long id) {
        monitoringRepo.deleteMonitoringById(id);
    }

    @Override
    public void disableMonitoring(Monitoring monitoring) {
        monitoring.setStatus(1);
        monitoringRepo.save(monitoring);
    }

    @Override
    public void enableMonitoring(Monitoring monitoring) {
        monitoring.setStatus(0);
        monitoringRepo.save(monitoring);
    }
}
