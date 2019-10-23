package io.bot.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.util.Objects;

@Entity
public class PlaceFilter {
    @Id
    @GeneratedValue
    private int id;
    private boolean exclude_side_places; // исключить боковушки
    private boolean exclude_vip_zone; //исключить места возле туалета
    private boolean exclude_top_places; //исключить верхние места
    private boolean exclude_top_side_places; //исключить верхние боковые места
    private boolean wagon_p_type;
    private boolean wagon_k_type;
    private boolean wagon_l_type;
    private boolean wagon_c1_type;
    private boolean wagon_c2_type;

    public int getId() {
        return id;
    }

    public PlaceFilter setId(int id) {
        this.id = id;
        return this;
    }

    public boolean isExclude_side_places() {
        return exclude_side_places;
    }

    public PlaceFilter setExclude_side_places(boolean exclude_side_places) {
        this.exclude_side_places = exclude_side_places;
        return this;
    }

    public boolean isExclude_vip_zone() {
        return exclude_vip_zone;
    }

    public PlaceFilter setExclude_vip_zone(boolean exclude_vip_zone) {
        this.exclude_vip_zone = exclude_vip_zone;
        return this;
    }

    public boolean isExclude_top_places() {
        return exclude_top_places;
    }

    public PlaceFilter setExclude_top_places(boolean exclude_top_places) {
        this.exclude_top_places = exclude_top_places;
        return this;
    }

    public boolean isExclude_top_side_places() {
        return exclude_top_side_places;
    }

    public PlaceFilter setExclude_top_side_places(boolean exclude_top_side_places) {
        this.exclude_top_side_places = exclude_top_side_places;
        return this;
    }

    public boolean isWagon_p_type() {
        return wagon_p_type;
    }

    public PlaceFilter setWagon_p_type(boolean wagon_p_type) {
        this.wagon_p_type = wagon_p_type;
        return this;
    }

    public boolean isWagon_k_type() {
        return wagon_k_type;
    }

    public PlaceFilter setWagon_k_type(boolean wagon_k_type) {
        this.wagon_k_type = wagon_k_type;
        return this;
    }

    public boolean isWagon_l_type() {
        return wagon_l_type;
    }

    public PlaceFilter setWagon_l_type(boolean wagon_l_type) {
        this.wagon_l_type = wagon_l_type;
        return this;
    }

    public boolean isWagon_c1_type() {
        return wagon_c1_type;
    }

    public PlaceFilter setWagon_c1_type(boolean wagon_c1_type) {
        this.wagon_c1_type = wagon_c1_type;
        return this;
    }

    public boolean isWagon_c2_type() {
        return wagon_c2_type;
    }

    public PlaceFilter setWagon_c2_type(boolean wagon_c2_type) {
        this.wagon_c2_type = wagon_c2_type;
        return this;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        PlaceFilter that = (PlaceFilter) o;
        return exclude_side_places == that.exclude_side_places &&
                exclude_vip_zone == that.exclude_vip_zone &&
                exclude_top_places == that.exclude_top_places &&
                exclude_top_side_places == that.exclude_top_side_places &&
                wagon_p_type == that.wagon_p_type &&
                wagon_k_type == that.wagon_k_type &&
                wagon_l_type == that.wagon_l_type &&
                wagon_c1_type == that.wagon_c1_type &&
                wagon_c2_type == that.wagon_c2_type;
    }

    @Override
    public int hashCode() {
        return Objects.hash(exclude_side_places, exclude_vip_zone, exclude_top_places, exclude_top_side_places, wagon_p_type, wagon_k_type, wagon_l_type, wagon_c1_type, wagon_c2_type);
    }

    @Override
    public String toString() {
        return "PlaceFilter{" +
                "id=" + id +
                ", exclude_side_places=" + exclude_side_places +
                ", exclude_vip_zone=" + exclude_vip_zone +
                ", exclude_top_places=" + exclude_top_places +
                ", exclude_top_side_places=" + exclude_top_side_places +
                ", wagon_p_type=" + wagon_p_type +
                ", wagon_k_type=" + wagon_k_type +
                ", wagon_l_type=" + wagon_l_type +
                ", wagon_c1_type=" + wagon_c1_type +
                ", wagon_c2_type=" + wagon_c2_type +
                '}';
    }
}
