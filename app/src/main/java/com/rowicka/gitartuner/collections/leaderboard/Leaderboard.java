package com.rowicka.gitartuner.collections.leaderboard;

import com.rowicka.gitartuner.collections.user.User;
import java.util.Map;
import java.util.Objects;

public class Leaderboard {
    /**
     * Klasa zawierająca informacje o statystyce gracza
     * user obiekt typu User
     * statistic obiekt typu HasMap zawierająca informacje o statystyce użytkownika
     * uid pole przechowujące infromacje o idywidualnym oznaczeniu użytkownika
     */
    private User user;
    private Map<String, Object> statistic;
    private String uid;

    /**
     * Konstruktor klasy Leaderboard
     * @param uid pole z informacją o UID użytkownika
     * @param statistic pole przechowujące dane statystyczne o użytkowniku
     */
    Leaderboard(String uid, Map<String, Object> statistic) {
        this.uid = uid;
        this.statistic = statistic;
    }

    /**
     * Funkcja do uzyskania pola typu User
     * @return zwraca pole user
     */
    public User getUser() {
        return user;
    }

    /**
     * Funkcja do ustawienia pola user
     * @param user oniekt typu User
     */
    void setUser(User user) {
        this.user = user;
    }

    /**
     * Funkcja do uzyskania  UID użytkownika
     * @return zwraca UID użytkownika
     */
    String getUid() {
        return uid;
    }

    /**
     * Funkcja zwracająca wszystkie punkty użytkownika
     * @return zwraca wszystkie punkty
     */
    public float getAllPoints(){
        return Float.parseFloat(Objects.requireNonNull(statistic.get("all")).toString());
    }

    /**
     * Funkcja zwraca punkty użytkownika uzyskanbe w danej grupie
     * @param group nazwa grupy
     * @return zwraca punkty użytkownika z danej grupy
     */
    public float getPointsByGroup(String group){
        return Float.parseFloat(Objects.requireNonNull(statistic.get(group)).toString());
    }
}
