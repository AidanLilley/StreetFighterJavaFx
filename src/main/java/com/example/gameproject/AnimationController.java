package com.example.gameproject;

import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.util.Duration;

public class AnimationController {
    int currentFrame = 0;
    int duration;
    ImageView sprite;
    Image[] frames;
    Timeline animation;
    public AnimationController(int duration, ImageView sprite, Image[] frames) {
        this.sprite = sprite;
        this.frames = frames;
        this.duration = duration;
        animation = new Timeline(
                new KeyFrame(Duration.millis(duration), event -> {
                    // Update the image view with the next frame
                    sprite.setImage(frames[currentFrame]);
                    currentFrame = (currentFrame + 1) % frames.length;
                })
        );
        animation.setDelay(Duration.millis(0));
        animation.setCycleCount(Animation.INDEFINITE);
    }
    public Timeline getAnimation() {
        return animation;
    }
}
