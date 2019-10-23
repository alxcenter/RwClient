package io.bot.uz.BotException;

/**
 * Created by ALX on 24.04.2018.
 */
public class TrainNotFoundException extends RailWayException {

    /**
     *
     * Эта ошибка выпадает когда нет мест ни на один поезд.
     * {"data":{"warning":"По заданному Вами направлению мест нет","list":
     * [{"num":"104Д","category":0,"travelTime":"7:23","from":
     * {"code":"2210700","station":"Днепр-Главный","stationTrain":"Мариуполь",
     * "date":"вторник, 01.05.2018","time":"23:55","sortTime":1525208100,
     * "srcDate":"2018-05-01"}, "to":{"code":"2200001","station":"Киев-Пассажирский",
     * "stationTrain":"Киев-Пассажирский","date":"среда, 02.05.2018","time":"07:18",
     * "sortTime":1525234680},"types":[],"child":{"minDate":"2004-05-02","maxDate":"2018-04-30"},
     * "allowStudent":1,"allowBooking":1,"allowRoundtrip":1,"isCis":0,"isEurope":0,"allowPrivilege":0}]}}
     *
     * Или же выбратнная дата больше чем 45 дней до отправления поезда.
     * {"data":{"warning":"По заданному Вами направлению мест нет","list":[]}}
     *
     */

    public TrainNotFoundException() {
    }

    public TrainNotFoundException(String message) {
        super(message);
    }
}
