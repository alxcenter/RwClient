package io.bot.telega;

import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.util.PriorityQueue;
import java.util.Queue;

/**
 * Created by a.zaitsev on 14.05.2018.
 */

//@Component
public class MessagePool{
    Bot bot;
    long chatId = 167944354L;

    public MessagePool(Bot bot) {
        this.bot = bot;
    }

    private static Queue<String> messages = new PriorityQueue<String>();
    public void startSending(){
        messages.add("Test");
        SendMessage sendMessage = new SendMessage(chatId, "StartSearching");
        MonitorMessage monitorMessage = new MonitorMessage();
        monitorMessage.start();
        try {
            bot.execute(sendMessage);
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }

    public static void add(String e){
        messages.add(e);
    }

    public static String getMessage(){
        return messages.poll();
    }


    class MonitorMessage extends  Thread{
        @Override
        public void run() {
            while (true){
                if (!messages.isEmpty()){
                    SendMessage sendMessage = new SendMessage(chatId, messages.poll());
                    try {
                        bot.execute(sendMessage);
                    } catch (TelegramApiException e) {
                        e.printStackTrace();
                    }
                }
                try {
                    Thread.sleep(5000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
