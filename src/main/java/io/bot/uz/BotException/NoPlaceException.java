package io.bot.uz.BotException;

public class NoPlaceException extends RailWayException {

    /**
     *
     * Выпадет когда в вагоне нету мест. WagonSearch
     * {"error":1,"data":"Нет свободных мест в вагонах данного типа"}
     *
     * Тоесть метод getWagonTypes();  нашел что в вагоне есть места.
     * Через несколько милисекунд кто-то выкупил места и в вагоне не оказалось мест.
     * Но наш метод getWagon() этого не знает и пытается что-то найти.
     * Соответсвенно получает ошибку.
     *
     *
     * Так же если искать по конектрным вагонам PlaceSearch
     * {"error":1,"data":"В 7 вагоне недостаточно свободных мест"}
     *
     */
    public NoPlaceException(String message) {
        super(message);
    }

    public NoPlaceException() {
    }
}
