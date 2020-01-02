package com.rowicka.gitartuner.collections.user;

public class User {

    private String email, nick, imgUrl;

    /**
     * KOnsruktor klasy User
     * @param email string z emailem użytkownika
     * @param nick string z nazwą użytka
     * @param imgUrl string przetrzymujący informacje o przypisanym, avatarze
     */
    public User(String email, String nick, String imgUrl) {
        this.email = email;
        this.nick = nick;
        this.imgUrl = imgUrl;
    }

    /**
     *
     * @return zwraca email użytkownika
     */
    public String getEmail() {
        return this.email;
    }

    /**
     *
     * @return zwraca nick użytkownika
     */
    public String getNick() {
        return nick;
    }

    /**
     *
     * @return zwraca url avatara użytkownika
     */
    public String getImgUrl() {
        return imgUrl;
    }
}
