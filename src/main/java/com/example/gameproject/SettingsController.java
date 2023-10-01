package com.example.gameproject;

import javafx.animation.Interpolator;
import javafx.animation.TranslateTransition;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.geometry.Rectangle2D;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.stage.Screen;
import javafx.stage.Stage;
import javafx.util.Duration;
import javafx.scene.media.*;
import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.util.Objects;



public class SettingsController {
    @FXML
    public Label settingsText;
    public double stageWidth;
    public double stageHeight;
    @FXML
    public GridPane mainBodySettings;
    public Button fsButton;
    public Button backButton;
    public ComboBox uiButton;
    public Slider volumeSlider;
    public Label volumeLabel;
    public Label sfxLabel;
    public Slider sfxSlider;
    @FXML
    private Stage stage;
    @FXML
    private Scene scene;


    @FXML
    public void backButton() throws IOException, URISyntaxException {
        FXMLLoader fxmlLoader = new FXMLLoader(startGame.class.getResource("Fxml/MainPage.fxml"));
        Parent root = fxmlLoader.load();
        mainPageController controller = fxmlLoader.getController();
        stage.getScene().setRoot(root);
        controller.setStage(stage);
        controller.setScene(scene);
        stage.show();
        controller.initialize();
    }


    public void initialize() {
        mainPageController bounce = new mainPageController();
        bounce.bounce(settingsText);
        sharedController controller = sharedController.getInstance();
        backButton.setFocusTraversable(false);
        settings sett = settings.getInstance();
        volumeSlider.valueProperty().addListener((observable, oldValue, newValue) -> {sett.setMusicVolume(newValue.doubleValue());});
        sfxSlider.valueProperty().addListener((observable, oldValue, newValue) -> {sett.setSfxVolume(newValue.doubleValue());});
        volumeSlider.setValue(sett.getMusicVolume());
        sfxSlider.setValue(sett.getSfxVolume());
        switch (sett.getUiScale()) {
            case "Huge" -> {
                uiButton.setValue("Huge");
                uiButton.setStyle("-fx-pref-width: 8em");
            }
            case "Large" -> {
                uiButton.setValue("Large");
                uiButton.setStyle("-fx-pref-width: 8em");
            }
            case "Medium" -> {
                uiButton.setValue("Medium");
                uiButton.setStyle("-fx-pref-width: 8em");
            }
            case "Small" -> {
                uiButton.setValue("Small");
                uiButton.setStyle("-fx-pref-width: 8em");
            }
            default -> uiButton.setValue("Medium");
        }
        controller.dynamicDisplay(mainBodySettings, stage);
    }

    public void uiChange(ActionEvent actionEvent) {
        String uiScale = (String) uiButton.getValue();
        settings sett = settings.getInstance();
        switch (uiScale) {
            case "Huge", "Large", "Medium", "Small" -> {
                sett.setUiScale(uiScale);
                sharedController controller = sharedController.getInstance();
                controller.dynamicDisplay(mainBodySettings, stage);
                uiButton.setStyle("-fx-pref-width: 8em");
            }
        }
    }
    public void setStage(Stage stage){
        this.stage = stage;
    }
    public void setScene(Scene scene){
        this.scene = scene;
    }

}
