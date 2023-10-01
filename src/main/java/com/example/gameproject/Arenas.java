package com.example.gameproject;

import java.io.Serializable;

public class Arenas implements Serializable {
    private String modelURL;
    private int damage;
    private int health;
    private int speed;
    private String name;

    public Arenas(String Name, String ModelURL) {
        this.name = Name;
        this.modelURL = ModelURL;
    }

    public String getName() {
        return name;
    }

    public String getModelURL() {
        return modelURL;
    }
}
