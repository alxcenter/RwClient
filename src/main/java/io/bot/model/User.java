package io.bot.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import java.util.Objects;

@Entity
public class User {


    @Id
    @Column(name = "chat_id")
    int chatID;
    @Column(name = "phone_number")
    String phoneNumber;

    public int getChatID() {
        return chatID;
    }

    public void setChatID(int chatID) {
        this.chatID = chatID;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return chatID == user.chatID &&
                phoneNumber == user.phoneNumber;
    }

    @Override
    public int hashCode() {
        return Objects.hash(chatID, phoneNumber);
    }
}
