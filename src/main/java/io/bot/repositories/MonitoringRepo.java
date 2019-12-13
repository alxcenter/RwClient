package io.bot.repositories;

import io.bot.model.Monitoring;
import io.bot.model.Status;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;


@Transactional
public interface MonitoringRepo extends JpaRepository<Monitoring, Long> {
    List<Monitoring> findMonitoringsByRelatesToChatID(long id);
    Optional<Monitoring> findMonitoringById(long id);
    void deleteMonitoringById(long id);
    @Modifying
    @Query("update Monitoring m set m.status = :status where m.id = :id")
    void updateMonitoringStatus(@Param("status") Status status,  @Param("id")long id);
}
