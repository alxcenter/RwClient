package io.bot.service;

import io.bot.exceptions.MonitoringNotFoundException;
import io.bot.model.Monitoring;
import io.bot.model.Status;
import io.bot.repositories.MonitoringRepo;
import io.bot.repositories.StationRepo;
import io.bot.repositories.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MonitoringServiceImpl implements MonitoringService {

    @Autowired
    private MonitoringRepo monitoringRepo;
    @Autowired
    private StationRepo stationRepo;
    @Autowired
    private UserRepo userRepo;

    @Override
    public Monitoring createMonitoring(Monitoring monitoring) {
        userRepo.getUserByChatID(monitoring.getRelatesTo().getChatID())
                .ifPresent(monitoring::setRelatesTo);
        stationRepo.findById(monitoring.getFromStation().getStationCode())
                .ifPresent(monitoring::setFromStation);
        stationRepo.findById(monitoring.getToStation().getStationCode())
                .ifPresent(monitoring::setToStation);
        monitoring.setStatus(Status.ACTIVE);
        return monitoringRepo.save(monitoring);
    }

    @Override
    public Monitoring getMonitoring(long id) {
        return monitoringRepo.findMonitoringById(id)
                .orElseThrow(() -> new MonitoringNotFoundException(id));
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
    public Status disableMonitoring(long id) {
        monitoringRepo.updateMonitoringStatus(Status.PAUSED, id);
        return Status.PAUSED;
    }

    @Override
    public Status enableMonitoring(long id) {
        monitoringRepo.updateMonitoringStatus(Status.ACTIVE, id);
        return Status.ACTIVE;
    }
}
