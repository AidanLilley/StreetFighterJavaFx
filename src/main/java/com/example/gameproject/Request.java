package com.example.gameproject;

import javafx.scene.image.ImageView;

import java.io.Serializable;

public class Request implements Serializable {
    private ImageView View;
    private Characters Player;
    private String Message;
    private int ThreadID;
    private String Type;
    private Arenas Arena;
    public Request(ImageView View, Characters Player, String Message, int ThreadID, String Type, Arenas Arena) {
        this.View = View;
        this.Player = Player;
        this.Message = Message;
        this.ThreadID = ThreadID;
        this.Type = Type;
        this.Arena = Arena;
    }
    public Request(ImageView View, Characters Player, String Message, int ThreadID, String Type) {
        this.View = View;
        this.Player = Player;
        this.Message = Message;
        this.ThreadID = ThreadID;
        this.Type = Type;
    }
    public Request(ImageView View, Characters Player, String Message, String Type) {
        this.View = View;
        this.Player = Player;
        this.Message = Message;
        this.Type = Type;
    }

    public ImageView getView() {
        return View;
    }
    public Characters getPlayer() {
        return Player;
    }
    public String getMessage() {
        return Message;
    }
    public int getThreadID() {
        return ThreadID;
    }
    public String getType() {
        return Type;
    }
    public Arenas getArena() {
        return Arena;
    }

    public void setMessage(String message) {
        Message = message;
    }
    public void setPlayer(Characters player) {
        Player = player;
    }
    public void setThreadID(int threadID) {
        this.ThreadID = threadID;
    }
    public void setType(String type) {
        this.Type = type;
    }
    public void setView(ImageView view) {
        View = view;
    }
    public void setArena(Arenas arena) {
        Arena = arena;
    }


}
