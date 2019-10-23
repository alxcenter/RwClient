package io.bot.uz.BotException;

/**
 * Created by ALX on 31.05.2018.
 */
public class CartServerUnavailableException extends RailWayException{

    /**
     *
     * Если при добавлении в корзину Серевер не может обслужить Нас в Данный момент.
     *{
     *  "error": 1,
     *  "data": {
     *      "ignoredPlaces": [0],
     *      "error": ["Повторите, пожалуйста, свой запрос. Сервер не может обслужить Вас в данный момент"],
     *      "analytics": [],
     *      "cartHtml": ""
     *}
     *
     */


    public CartServerUnavailableException(String message) {
        super(message);
    }

    public CartServerUnavailableException(){}
}
