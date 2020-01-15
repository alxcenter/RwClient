package io.bot.uz;

import io.bot.uz.BotException.*;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;


/**
 * Created by ALX on 12.03.2018.
 */

public class TrainSearch {

    private RequestNtw request;
    private String seachBy = "train_search";
    private String post;
    private TrainParser trainParser;

    public List<Train> getTrains(String from, String to, Date date) throws OtherException, WrongDateException, TrainNotFoundException, ServiceTemporarilyUnavailableException, CaptchaException {
        List<Train> trains = null;
        try {
            trains = getTrains(from, to, date, null);
        } catch (CaptchaException e) {
            throw new CaptchaException(e.getMessage());
        }
        return trains;
    }

    public List<Train> getTrains(String from, String to, Date date, String captcha) throws OtherException, WrongDateException, TrainNotFoundException, ServiceTemporarilyUnavailableException, CaptchaException {
        List<Train> trainList = null;
        trainParser = new TrainParser();
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH);
        post = String.format("from=%s&to=%s&date=%s", from, to, dateFormat.format(date));
        if (captcha != null) {
            post = post.concat("&captcha=").concat(captcha);
        }
        String[] response = request.sendPost(seachBy, post);
        String json = response[0];
        try {
            trainList = trainParser.getTrainList(json);
        } catch (CaptchaException e) {
            throw new CaptchaException(request.getSessionCookies());
        }
        return trainList;
    }

    public RequestNtw getRequest() {
        return request;
    }

    public void setRequest(RequestNtw request) {
        this.request = request;
    }
}
