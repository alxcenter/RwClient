package io.bot.uz.BotException;

/**
 * Created by ALX on 04.05.2018.
 */
public class CaptchaException extends RailWayException{

    /**
     *
     * Если на добавление билетов в корзину пришла каптча
     * {"data":{"error":null,"analytics":[],"cartHtml":""},"captcha":1}
     *
     */

    public CaptchaException(String message) {
        super(message);
    }

    public CaptchaException() {
    }
}
