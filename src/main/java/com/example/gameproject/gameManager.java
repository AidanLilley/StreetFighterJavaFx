package com.example.gameproject;

import java.io.IOException;
import java.util.ArrayList;

public class gameManager {
    private static gameManager instance;
    private Arenas gameArena;
    private Characters playerChar;
    private ArrayList<Characters> charList = new ArrayList<>();
    private ArrayList<Arenas> arenaList = new ArrayList<>();
    private void addCharacter(Characters character) {
        for(int i=0; i<charList.size();i++) {
            if(character == charList.get(i)) {
                return;
            }
        }
        charList.add(character);
    }
    private void addArena(Arenas arena) {
        for(int i=0; i<arenaList.size();i++) {
            if(arena == arenaList.get(i)) {
                return;
            }
        }
        arenaList.add(arena);
    }
    Characters Josh = new Characters("Josh Lloyd",3,8,6,"Images/standingLeft.png");
    Characters Nathan = new Characters("Nathan Blenkinsop",1,1,1,"Images/logo.jpg");
    Characters Beth = new Characters("Beth Dean",9,2,3,"Images/logo.jpg");
    Characters DanF = new Characters("Dan Fox",5,5,8, "Images/logo.jpg");
    Characters Liz = new Characters("Liz Whileman",5,1,3,"Images/BackgroundScreen.jpg");
    Characters DanM = new Characters("Dan Menzer",7,6,4,"Images/logo.jpg");
    Characters James = new Characters("James Stanley",6,6,6,"Images/logo.jpg");
    Characters Samiro = new Characters("Samiro",2,9,4,"Images/BackgroundScreen.jpg");
    Characters Realyn = new Characters("Stupid Person",3,4,1,"Images/logo.jpg");
    Arenas Arena1 = new Arenas("Street Attack","Images/StreetAttack.jpg");
    private gameManager() throws IOException {
        if (instance != null) {
            throw new IllegalStateException("Cannot create multiple instances of GameWithSoundExample");
        }
    }
    public static gameManager getInstance() throws IOException {
        if (instance == null) {
            instance = new gameManager();
        }

        return instance;
    }

    public ArrayList<Characters> getCharacterList() {
        addCharacter(Josh);
        addCharacter(DanM);
        addCharacter(Nathan);
        addCharacter(Beth);
        addCharacter(DanF);
        addCharacter(Liz);
        addCharacter(James);
        addCharacter(Samiro);
        addCharacter(Realyn);
        return charList;
    }
    public ArrayList<Arenas> getArenaList() {
        addArena(Arena1);
        return arenaList;
    }
    public void setGameArena(Arenas arena) {this.gameArena = arena;}
    public Arenas getGameArena() {return this.gameArena;}
    public void setPlayerChar(Characters player) {this.playerChar = player;}
    public Characters getPlayerChar() {return this.playerChar;}
}
