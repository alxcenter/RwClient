package io.bot.repositories;

import io.bot.model.Monitoring;

import java.util.List;

public interface MonitoringRepo {

    void addMonitoring(Monitoring monitoring);
    List<Monitoring> getUserMonitors(long id);
    Monitoring getMonitor(long id);
    void disableMonitoring(Monitoring monitoring);
    void enableMonitoring(Monitoring monitoring);
    void deleteMonitoring(long id);

}
