package io.bot.service;

import io.bot.model.Monitoring;
import io.bot.model.Status;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Transactional
public interface MonitoringService {
    Monitoring createMonitoring(Monitoring monitoring);
    Monitoring getMonitoring(long id);
    List<Monitoring> getAllUserMonitorings(long id);
    void deleteMonitoring(long id);
    Status disableMonitoring(long id);
    Status enableMonitoring(long id);
}
