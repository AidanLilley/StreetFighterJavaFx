package com.example.gameproject;

import javafx.scene.image.Image;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Characters implements Serializable {
    private String modelURL;
    private int damage;
    private int health;
    private int speed;
    private String name;

    public Characters(String Name, int Speed, int Health, int Damage, String ModelURL) {
        this.name = Name;
        this.speed = Speed;
        this.health = Health;
        this.damage = Damage;
        this.modelURL = ModelURL;
    }

    public String getName() {
        return name;
    }

    public int getSpeed() {
        return speed;
    }

    public int getHealth() {
        return health;
    }

    public int getDamage() {
        return damage;
    }

    public String getModelURL() {
        return modelURL;
    }
}
