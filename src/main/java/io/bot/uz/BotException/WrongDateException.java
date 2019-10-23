package io.bot.uz.BotException;

/**
 * Created by ALX on 24.04.2018.
 */
public class WrongDateException extends RailWayException {

    /**
     *  Эта ошибка появляется если введенная дата меньше текущей.
     *  {"error":1,"data":"Введена неверная дата"}
     *
     */

    public WrongDateException(String message) {
    super(message);
    }

    public WrongDateException() {
    }
}
