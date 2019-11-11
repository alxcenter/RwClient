package io.bot.model;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonFormat;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;

@Entity
@NamedQueries({
        @NamedQuery(
                name = "getUniqueActiveMonitoringList",
                query = "select distinct m.date, m.fromStation, m.toStation, m.trainNumber " +
                        "from Monitoring m where m.status = 0")
})
@JsonAutoDetect
public class Monitoring {
    @Id
    @GeneratedValue
    long id;
    @Temporal(TemporalType.DATE)
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd", timezone = "Europe/Kiev")
    Date date;
    @Column(name = "from_station")
    String fromStation;
    @Column(name = "to_station")
    String toStation;
    @Column(name = "train_number")
    String trainNumber;
    int status;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinTable(
            name = "monitoring_user_relates",
            joinColumns = @JoinColumn(name = "monitoring_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "user_id", referencedColumnName = "chat_id")
    )
    User relatesTo;


    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinTable(
            name = "passenger_monitoring_relate",
            joinColumns = @JoinColumn(name = "monitoring_id"),
            inverseJoinColumns = @JoinColumn(name = "passenger_id")
    )
            @Fetch(FetchMode.JOIN)
//    @JsonIgnore
    List<Passenger> list = new ArrayList<>();

    @OneToOne(cascade = CascadeType.ALL)
    @JoinTable(
            name = "monitoring_relates_place_filter",
            joinColumns = @JoinColumn(name = "monitoring_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "place_filter_id", referencedColumnName = "id")
    )
    PlaceFilter placeFilter;

    public User getRelatesTo() {
        return relatesTo;
    }

    public void setRelatesTo(User relatesTo) {
        this.relatesTo = relatesTo;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public PlaceFilter getPlaceFilter() {
        return placeFilter;
    }

    public void setPlaceFilter(PlaceFilter placeFilter) {
        this.placeFilter = placeFilter;
    }

    public List<Passenger> getPassengers() {
        return list;
    }

    public void setPassengers(List<Passenger> list) {
        this.list = list;
    }

    public long getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getFromStation() {
        return fromStation;
    }

    public void setFromStation(String fromStation) {
        this.fromStation = fromStation;
    }

    public String getToStation() {
        return toStation;
    }

    public void setToStation(String toStation) {
        this.toStation = toStation;
    }

    public String getTrainNumber() {
        return trainNumber;
    }

    public void setTrainNumber(String trainNumber) {
        this.trainNumber = trainNumber;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Monitoring that = (Monitoring) o;
        return Objects.equals(date, that.date) &&
                Objects.equals(toStation, that.toStation) &&
                Objects.equals(fromStation, that.fromStation) &&
                Objects.equals(trainNumber, that.trainNumber);
    }

    @Override
    public int hashCode() {
        return Objects.hash(date, toStation, fromStation, trainNumber);
    }

    @Override
    public String toString() {
        return "Monitoring{" +
                "id=" + id +
                ", date=" + date +
                ", toStation='" + toStation + '\'' +
                ", fromStation='" + fromStation + '\'' +
                ", trainNumber='" + trainNumber + '\'' +
                ", status=" + status +
                ", relatesTo=" + relatesTo +
                '}';
    }
}
