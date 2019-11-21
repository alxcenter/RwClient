package io.bot.service;

import io.bot.model.Monitoring;

import java.util.List;

public interface MonitoringService {

    void createMonitoring(Monitoring monitoring);
    Monitoring getMonitoring(long id);
    List<Monitoring> getAllUserMonitorings(long id);
    void deleteMonitoring(long id);
    void disableMonitoring(Monitoring monitoring);
    void enableMonitoring(Monitoring monitoring);
}
