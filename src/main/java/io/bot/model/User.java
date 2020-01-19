package io.bot.model;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Collection;
import java.util.Objects;

@Entity
@Table(name = "users")
public class User implements UserDetails {


    @Id
    @Column(name = "chat_id")
    private long chatID;
    @Column(name = "phone_number")
    private String phoneNumber;
    private String telegaUsername;
    private String firstName;
    private String lastName;
    private String photoUrl;

    public void setChatID(long chatID) {
        this.chatID = chatID;
    }

    public long getChatID() {
        return chatID;
    }

    public String getTelegaUsername() {
        return telegaUsername;
    }

    public void setTelegaUsername(String telegaUsername) {
        this.telegaUsername = telegaUsername;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getPhotoUrl() {
        return photoUrl;
    }

    public void setPhotoUrl(String photoUrl) {
        this.photoUrl = photoUrl;
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

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return null;
    }

    @Override
    public String getPassword() {
        return String.valueOf(chatID);
    }

    @Override
    public String getUsername() {
        return String.valueOf(chatID);
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
