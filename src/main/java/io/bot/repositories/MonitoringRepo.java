package io.bot.repositories;

import io.bot.model.Monitoring;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface MonitoringRepo extends JpaRepository<Monitoring, Long> {



    List<Monitoring> findMonitoringsByRelatesToChatID(long id);
    Monitoring findMonitoringById(long id);
    void deleteMonitoringById(long id);
















////    void addMonitoring(Monitoring monitoring);
//    @Query("from Monitoring m where m.relatesTo.chatID = :id")
//    List<Monitoring> getUserMonitors(@Param("id") long id);
//    @Modifying
//    @Query("from Monitoring m where m.id = :id")
//    Monitoring getMonitor(@Param("id")long id);
////    void disableMonitoring(Monitoring monitoring);
////    void enableMonitoring(Monitoring monitoring);
//    @Modifying
//    @Query("delete from Monitoring m where m.id= :id")
//    void deleteMonitoring(@Param("id") long id);
////    void deleteMonitoringById(long id);

}
