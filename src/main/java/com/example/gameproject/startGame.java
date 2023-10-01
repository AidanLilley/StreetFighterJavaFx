package com.example.gameproject;

import javafx.application.Application;
import javafx.beans.property.ReadOnlyBooleanProperty;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyCombination;
import javafx.scene.input.KeyEvent;
import javafx.stage.Stage;
import javafx.scene.media.*;
import java.io.*;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class startGame extends Application {
    public double stageWidth;
    public double stageHeight;

    @Override
    public void start(Stage stage) throws IOException, InterruptedException, URISyntaxException {
        getSettings();
        FXMLLoader fxmlLoader = new FXMLLoader(startGame.class.getResource("Fxml/MainPage.fxml"));
        Parent root = fxmlLoader.load();
        mainPageController controller = fxmlLoader.getController();
        Scene scene = new Scene(root, 1280, 720);
        stage.setScene(scene);
        controller.setStage(stage);
        stage.setFullScreenExitHint("");
        stage.setResizable(false);
        controller.setScene(scene);
        stage.show();
        //stage.setFullScreen(true);
        stage.getIcons().add(new Image(Objects.requireNonNull(startGame.class.getResource("Images/BackgroundScreen.jpg")).openStream()));
        controller.initialize();
        stage.setTitle("Generic Fighting Game!");
        /*scene.setOnKeyPressed(event -> {
            if (event.getCode() == KeyCode.ESCAPE) {
                event.consume();
                stage.setFullScreen(true);
            }
        });*/


    }
    @Override
    public void stop(){
        settings sett = settings.getInstance();
        try {
            FileWriter Fw = new FileWriter(String.valueOf(getClass().getResource("/Images/Settings.txt")));
            BufferedWriter writer = new BufferedWriter(Fw);
            writer.write("Ui Scale: "+ sett.getUiScale());
            writer.newLine();
            writer.write("Music Volume: "+ sett.getMusicVolume());
            writer.newLine();
            writer.write("Sfx Volume: "+ sett.getSfxVolume());
            writer.flush();
            Fw.close();
            writer.close();
        } catch (IOException e) {e.printStackTrace();}
    }

    public static void main(String[] args) {
        launch();
    }
    private void getSettings() {
        try {
            FileReader Fr = new FileReader(String.valueOf(getClass().getResource("/Images/Settings.txt")));
            BufferedReader reader = new BufferedReader(Fr);
            List<String> sa = new ArrayList<>();
            String line;
            while((line = reader.readLine())!=null) {
                String[] splitElements = line.split(": ");
                sa.add(splitElements[splitElements.length - 1]);
            }
            Fr.close();
            reader.close();
            settings sett = settings.getInstance();
            sett.setUiScale(sa.get(0));
            sett.setMusicVolume(Double.parseDouble(sa.get(1)));
            sett.setSfxVolume(Double.parseDouble(sa.get(2)));

        } catch (IOException e) {e.printStackTrace();}
    }
}