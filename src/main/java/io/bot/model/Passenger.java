package io.bot.model;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;

@Entity
public class Passenger {

    public Passenger() {
    }

    public Passenger(String name, String surname) {
        this.name = name;
        this.surname = surname;
    }

    @Id
    @GeneratedValue
    private int id;
    private String name;
    private String surname;
    private int status;
    private String studentCard = "";
    private String bedding = "1";
    private String child = "";
    @OneToOne(cascade = CascadeType.ALL)
    @JoinTable(
            name = "passenger_relates_place_filter",
            joinColumns = @JoinColumn(name = "pass_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "place_filter_id", referencedColumnName = "id")
    )
    @JsonIgnore
    private PlaceFilter placeFilter;

    @ManyToOne(cascade = CascadeType.ALL)
    @JsonIgnore
    private Monitoring monitoring;

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public Monitoring getMonitoring() {
        return monitoring;
    }

    public void setMonitoring(Monitoring monitoring) {
        this.monitoring = monitoring;
    }

    public PlaceFilter getPlaceFilter() {
        return placeFilter;
    }

    public void setPlaceFilter(PlaceFilter placeFilter) {
        this.placeFilter = placeFilter;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getStudentCard() {
        return studentCard;
    }

    public void setStudentCard(String studentCard) {
        this.studentCard = studentCard;
    }

    public String getBedding() {
        return bedding;
    }

    public void setBedding(String bedding) {
        this.bedding = bedding;
    }

    public String getChild() {
        return child;
    }

    public void setChild(String child) {
        this.child = child;
    }

    @Override
    public String toString() {
        return "Passenger{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", surname='" + surname + '\'' +
                ", status=" + status +
                ", placeFilter=" + placeFilter +
                ", monitoring=" + monitoring +
                '}';
    }
}
