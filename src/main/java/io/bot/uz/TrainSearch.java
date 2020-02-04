package io.bot.uz;

import io.bot.uz.BotException.CaptchaException;
import io.bot.uz.BotException.RailWayException;
import io.bot.uz.model.Train;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;


/**
 * Created by ALX on 12.03.2018.
 */

public class TrainSearch {

    private RequestNtw request;
    private String searchBy = "train_search";
    private String post;

    public TrainSearch(RequestNtw request) {
        this.request = request;
    }

    public List<Train> getTrains(String from, String to, Date date) throws RailWayException {
        try {
            return getTrains(from, to, date, null);
        } catch (CaptchaException e) {
            throw new CaptchaException(e.getMessage());
        }
    }

    public List<Train> getTrains(String from, String to, Date date, String captcha) throws RailWayException {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH);
        post = String.format("from=%s&to=%s&date=%s", from, to, dateFormat.format(date));
        if (captcha != null) {
            post = post.concat("&captcha=").concat(captcha);
        }
        String[] response = request.sendPost(searchBy, post);
        String json = response[0];
        try {
            return new TrainParser().getTrainList(json);
        } catch (CaptchaException e) {
            throw new CaptchaException(request.getSessionCookies());
        }
    }

    public RequestNtw getRequest() {
        return request;
    }

    public void setRequest(RequestNtw request) {
        this.request = request;
    }
}
