package com.example.gameproject;

import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;

import java.io.File;

public class musicController {
    private static musicController instance;
    private MediaPlayer player;
    private boolean soundLoaded;
    private boolean soundPlaying;
    private boolean repeating;
    private double volume;
    private musicController() {
        if (instance != null) {
            throw new IllegalStateException("Cannot create multiple instances of GameWithSoundExample");
        }


    }
    public static musicController getInstance() {
        if (instance == null) {
            instance = new musicController();
        }
        return instance;
    }

    public void loadSound(Media sound) {
        if (!soundLoaded) {
            player = new MediaPlayer(sound);
            soundLoaded = true;

        }
    }
    public void unloadSound() {
        if (!soundLoaded) {
            player.stop();
            soundLoaded = false;
            repeating = false;
        }
    }
    public void playSound() {
        if (soundLoaded && !soundPlaying) {
            player.play();
            soundPlaying = true;
            settings sett = settings.getInstance();
            player.setVolume(volume);
        }
    }
    public void pauseSound() {
        if (soundLoaded && soundPlaying) {
            player.pause();
            soundPlaying = false;
        }
    }
    public void repeatSound() {
        if (!repeating) {
            player.setCycleCount(MediaPlayer.INDEFINITE);
            repeating = true;
        } else {
            player.setCycleCount(0);
            repeating = false;
        }

    }
    public void changeMusicVolume(double volume){
        this.volume = volume;
        if(soundLoaded && soundPlaying) {
            player.setVolume(volume);
        }
    }
}
