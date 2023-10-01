package com.example.gameproject;

public class settings {
    private static settings instance;
    private double musicVolume;
    private double sfxVolume;
    private String uiScale;
    private settings() {
        if (instance != null) {
            throw new IllegalStateException("Cannot create multiple instances of GameWithSoundExample");
        }


    }
    public static settings getInstance() {
        if (instance == null) {
            instance = new settings();
        }
        return instance;
    }

    public double getMusicVolume() {
        return musicVolume;
    }

    public double getSfxVolume() {
        return sfxVolume;
    }

    public String getUiScale() {
        return uiScale;
    }

    public void setMusicVolume(double musicVolume) {
        this.musicVolume = musicVolume;
        musicController player = musicController.getInstance();
        player.changeMusicVolume(musicVolume);

    }

    public void setSfxVolume(double sfxVolume) {
        this.sfxVolume = sfxVolume;
        sfxController player = sfxController.getInstance();
        player.changeSfxVolume(sfxVolume);
    }

    public void setUiScale(String uiScale) {
        this.uiScale = uiScale;
        sharedController shared = sharedController.getInstance();
        shared.setUiScale(uiScale);
    }
}
