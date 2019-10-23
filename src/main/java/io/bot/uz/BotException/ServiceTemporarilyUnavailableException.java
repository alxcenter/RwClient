package io.bot.uz.BotException;

public class ServiceTemporarilyUnavailableException extends RailWayException {

    /**
     *
     *  Сервис временно недоступен.
     *  Выпадет при неверной дате. Если дата поезда меньше текущей.
     *  {"error":1,"data":"Сервис временно недоступен. Приносим извинения за доставленные неудобства."}
     *
     *
     *
     */

    public ServiceTemporarilyUnavailableException(String message) {
        super(message);
    }

    public ServiceTemporarilyUnavailableException() {
    }
}
