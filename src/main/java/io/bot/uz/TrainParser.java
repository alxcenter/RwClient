package io.bot.uz;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.bot.uz.BotException.*;
import io.bot.uz.model.Train;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.IntStream;

/**
 * Created by ALX on 06.03.2018.
 */
public class TrainParser {

    public List<Train> getTrainList(String jsonText) throws OtherException, WrongDateException, TrainNotFoundException, ServiceTemporarilyUnavailableException, CaptchaException {
        List<Train> list = new ArrayList<>();
        errorChecker(jsonText);
        JSONArray trainArrayList = new JSONObject(jsonText).getJSONObject("data").getJSONArray("list");
        IntStream.range(0, trainArrayList.length())
                .forEach(index ->{
                    Train train = getTrain(trainArrayList.getJSONObject(index).toString());
                    list.add(train);
                });
        return list;
    }

    /**
     *
     * @param json - input object for parsing to Train.class
     * @return object instance of Train.class
     */
    private Train getTrain(String json) {
        Train train = null;
        try {
            train = new ObjectMapper().readerFor(Train.class).readValue(json);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return train;
    }

    /**
     *
     * @param jsonText - input json for find exceptions
     * @throws ServiceTemporarilyUnavailableException
     * @throws WrongDateException
     * @throws OtherException
     * @throws TrainNotFoundException
     */
    private void errorChecker(String jsonText) throws ServiceTemporarilyUnavailableException, WrongDateException, OtherException, TrainNotFoundException, CaptchaException {
        JSONObject jsonObject = new JSONObject(jsonText);
        if (jsonObject.has("error")) {
            if (jsonObject.has("captcha")){
                throw new CaptchaException("Необходимо ввести капчу");
            }else if (jsonObject.get("data").toString().contains("Сервис временно недоступен")) {
                throw new ServiceTemporarilyUnavailableException(jsonText);
            } else if (jsonObject.get("data").toString().contains("Введена неверная дата")) {
                throw new WrongDateException(jsonText);
            } else {
                throw new OtherException(jsonText);
            }
        } else if (jsonObject.getJSONObject("data").has("warning")) {
            if(jsonObject.getJSONObject("data").getJSONArray("list").length()==0) {
                String warningMessage = jsonObject.getJSONObject("data").getString("warning");
                throw new TrainNotFoundException(warningMessage + "\n" + jsonText);
            }
        }

    }
}


