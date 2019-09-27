package com.rowicka.gitartuner.collections.leaderboard;

import com.rowicka.gitartuner.collections.user.User;
import java.util.Map;
import java.util.Objects;

public class Leaderboard {
    private User user;
    private Map<String, Object> statistic;
    private String uid;


    public Leaderboard(String uid, Map<String, Object> statistic) {
        this.uid = uid;
        this.statistic = statistic;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public String getUid() {
        return uid;
    }

    public float getAllPoints(){

        return Float.parseFloat(Objects.requireNonNull(statistic.get("all")).toString());
    }

    public float getPointsByGroup(String group){
        return Float.parseFloat(Objects.requireNonNull(statistic.get(group)).toString());
    }
}
