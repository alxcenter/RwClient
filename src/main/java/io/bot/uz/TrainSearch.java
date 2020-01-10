package io.bot.uz;

import io.bot.uz.BotException.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Component;
import org.springframework.web.context.annotation.SessionScope;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;


/**
 * Created by ALX on 12.03.2018.
 */
@Component
@SessionScope
@Primary
public class TrainSearch {

    @Autowired
    private RequestNtw request;
    private static String seachBy = "train_search";
    private String post;
    private String from;
    private String to;
    private Date date;
    private String time;
    private TrainParser trainParser;

    public TrainSearch() {
    }

//    public TrainSearch(Train train) {
//        this.from = String.valueOf(train.getFrom().getCode());
//        this.to = String.valueOf(train.getTo().getCode());
//        this.date = train.getFrom().getSrcDate();
//        this.time = train.getFrom().getTime();
//    }

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
        if (captcha == null) {
            post = String.format("from=%s&to=%s&date=%s", from, to, dateFormat.format(date));
        } else {
            post = String.format("from=%s&to=%s&date=%s&captcha=%s", from, to, dateFormat.format(date), captcha);
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
}
