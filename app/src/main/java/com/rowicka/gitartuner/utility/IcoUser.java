package com.rowicka.gitartuner.utility;

public class IcoUser {
    private String url, name;
    private int resourceDisplay;

    public IcoUser(String name, String url, int resourceDisplay){
        this.name = name;
        this.url = url;
        this.resourceDisplay = resourceDisplay;
    }

    public String getImgUrl() {
        return this.url;
    }
}
