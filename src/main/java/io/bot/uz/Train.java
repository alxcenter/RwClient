package io.bot.uz;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;

import java.util.List;

/**
 * Created by ALX on 06.03.2018.
 */
@JsonAutoDetect
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Train {

    private String num;
    private int category;
    private int isTransformer;
    private String travelTime;
    private Station from;
    private Station to;
    private String proxy;

    public void setMain(Station from, Station to) {
        this.from = from;
        this.to = to;
    }

    public int getIsTransformer() {
        return isTransformer;
    }

    public void setIsTransformer(int isTransformer) {
        this.isTransformer = isTransformer;
    }

    public String getNum() {
        return num;
    }

    public void setNum(String num) {
        this.num = num;
    }

    public int getCategory() {
        return category;
    }

    public void setCategory(int category) {
        this.category = category;
    }

    public String getTravelTime() {
        return travelTime;
    }

    public void setTravelTime(String travelTime) {
        this.travelTime = travelTime;
    }

    public Station getFrom() {
        return from;
    }

    public void setFrom(Station from) {
        this.from = from;
    }

    public Station getTo() {
        return to;
    }

    public void setTo(Station to) {
        this.to = to;
    }

    public String getProxy() {
        return proxy;
    }

    public void setProxy(String proxy) {
        this.proxy = proxy;
    }

    @Override
    public String toString() {
        return "Train{" +
                "num='" + num + '\'' +
                ", category=" + category +
                ", travelTime='" + travelTime + '\'' +
                ", from=" + from +
                ", to=" + to +
                '}';
    }
}
