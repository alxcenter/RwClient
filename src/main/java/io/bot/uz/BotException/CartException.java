package io.bot.uz.BotException;

/**
 * Created by a.zaitsev on 14.05.2018.
 */
public class CartException extends RailWayException {

    /**
     *
     * Если места которые мы кинули в корзину уже заняты
     * {"error":1, "data":{"ignoredPlaces":[0],"error":["Выбранное вами место 050 в вагоне 1 уже занято.
     * Пожалуйста, выберите другое место."],"analytics":[],"cartHtml":""}}
     *
     */

    public CartException(String message) {
        super(message);
    }

    public CartException() {
    }
}
