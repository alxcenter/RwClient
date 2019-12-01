package io.bot.repositories;

import io.bot.model.Monitoring;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


@Transactional
public interface MonitoringRepo extends JpaRepository<Monitoring, Long> {
    List<Monitoring> findMonitoringsByRelatesToChatID(long id);
    Monitoring findMonitoringById(long id);
    void deleteMonitoringById(long id);
}
