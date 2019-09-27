package com.rowicka.gitartuner.collections.user;

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

    public String getNick() {
        return nick;
    }

    public String getImgUrl() {
        return imgUrl;
    }
}
