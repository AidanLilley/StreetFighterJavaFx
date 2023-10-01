package com.example.gameproject;

import javafx.animation.*;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.scene.media.Media;
import javafx.stage.Stage;
import javafx.event.ActionEvent;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.util.Duration;

import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.util.Objects;

public class mainPageController {
    @FXML
    public Button newGame;
    @FXML
    public Button loadGame;
    @FXML
    public Button settings;
    @FXML
    public Button exitGame;
    @FXML
    public Label welcomeText;
    @FXML
    private VBox mainBody;
    @FXML
    private Stage stage;
    @FXML
    private Scene scene;
    public boolean firstPlay = false;


    public void bounce(Node element) {
        TranslateTransition translateTransition = new TranslateTransition(Duration.seconds(1), element);
        translateTransition.setFromY(0);
        translateTransition.setToY(-10);
        translateTransition.setCycleCount(TranslateTransition.INDEFINITE);
        translateTransition.setAutoReverse(true);
        translateTransition.setInterpolator(Interpolator.LINEAR);
        translateTransition.play();
    }


    @FXML
    protected void SinglePlayerButton() throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(startGame.class.getResource("Fxml/SingleCharacterSelect.fxml"));
        Parent root = fxmlLoader.load();
        SingleCharacterSelect controller = fxmlLoader.getController();
        stage.getScene().setRoot(root);
        controller.setStage(stage);
        controller.setScene(scene);
        stage.show();
        controller.initialize();
    }
    @FXML
    protected void MultiplayerButton() throws IOException, ClassNotFoundException {
        FXMLLoader fxmlLoader = new FXMLLoader(startGame.class.getResource("Fxml/MultiplayerGameFinder.fxml"));
        Parent root = fxmlLoader.load();
        MultiplayerGameFinder controller = fxmlLoader.getController();
        stage.getScene().setRoot(root);
        controller.setStage(stage);
        controller.setScene(scene);
        stage.show();
        controller.initialize();
    }
    @FXML
    protected void settingsButton(ActionEvent event) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(startGame.class.getResource("Fxml/Settings.fxml"));
        Parent root = fxmlLoader.load();
        SettingsController controller = fxmlLoader.getController();
        stage.getScene().setRoot(root);
        controller.setStage(stage);
        controller.setScene(scene);
        stage.show();
        controller.initialize();
    }
    @FXML
    protected void exitButton() {
        Platform.exit();
    }
    @FXML
    public void initialize() throws URISyntaxException {
        VBox.setMargin(welcomeText, new Insets(0, 0, 10, 0));
        VBox.setMargin(newGame, new Insets(0, 0, 20, 0));
        VBox.setMargin(loadGame, new Insets(0, 0, 20, 0));
        VBox.setMargin(settings, new Insets(0, 0, 20, 0));
        bounce(welcomeText);
        sharedController controller = sharedController.getInstance();
        controller.dynamicDisplay(mainBody, stage);
        exitGame.setFocusTraversable(false);
        settings.setFocusTraversable(false);
        loadGame.setFocusTraversable(false);
        newGame.setFocusTraversable(false);
        Media sound = new Media(new File(Objects.requireNonNull(startGame.class.getResource("Sounds/reflected-light-147979.mp3")).toURI()).toURI().toString());
        musicController player1 = musicController.getInstance();
        player1.loadSound(sound);
        player1.playSound();
        player1.repeatSound();

    }

    private void updateElementWidth(double size) {
        mainBody.setStyle("-fx-font-size: "+size*0.02+";");

    }

    public void setStage(Stage stage){
        this.stage = stage;
    }
    public void setScene(Scene scene){
        this.scene = scene;
    }


}