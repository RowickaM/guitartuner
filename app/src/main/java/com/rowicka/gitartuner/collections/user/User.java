package com.rowicka.gitartuner.collections.user;

import android.util.Log;

public class User {

    private String email, nick, imgUrl;

    public User(String email, String nick, String imgUrl) {
        this.email = email;
        this.nick = nick;
        this.imgUrl = imgUrl;
    }

    public String getEmail() {
        return this.email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getNick() {
        return nick;
    }

    public void setNick(String nick) {
        this.nick = nick;
    }

    public String getImgUrl() {
        return imgUrl;
    }

    public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
    }
}
