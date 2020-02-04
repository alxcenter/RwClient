package io.bot.uz.model;


import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonInclude;

import java.util.Date;

/**
 * Created by ALX on 06.03.2018.
 */
@JsonAutoDetect
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Station {

    private int code; //id станции
    private String station; // Имя станции
    private String stationTrain; //Имя начальной станции отправления
    private String date; //день недели + дата
    private String time; //время
    private Date srcDate; //дата форматированная
    private long sortTime; //timestamp

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getStation() {
        return station;
    }

    public void setStation(String station) {
        this.station = station;
    }

    public String getStationTrain() {
        return stationTrain;
    }

    public void setStationTrain(String stationTrain) {
        this.stationTrain = stationTrain;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public long getSortTime() {
        return sortTime;
    }

    public void setSortTime(long sortTime) {
        this.sortTime = sortTime;
    }

    public Date getSrcDate() {
        return srcDate;
    }

    public void setSrcDate(Date srcDate) {
        this.srcDate = srcDate;
    }

    @Override
    public String toString() {
        return "Station{" +
                "code=" + code +
                ", station='" + station + '\'' +
                ", stationTrain='" + stationTrain + '\'' +
                ", date='" + date + '\'' +
                ", time='" + time + '\'' +
                ", sortTime=" + sortTime +
                ", srcDate='" + srcDate + '\'' +
                '}';
    }
}
