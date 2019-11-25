package io.bot.service;

import io.bot.model.Monitoring;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Transactional
public interface MonitoringService {

    void createMonitoring(Monitoring monitoring);
    Monitoring getMonitoring(long id);
    List<Monitoring> getAllUserMonitorings(long id);
    void deleteMonitoring(long id);
    void disableMonitoring(Monitoring monitoring);
    void enableMonitoring(Monitoring monitoring);
}
