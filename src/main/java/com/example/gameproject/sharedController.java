package com.example.gameproject;

import javafx.beans.value.ChangeListener;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.transform.Scale;
import javafx.stage.Stage;



public class sharedController {
    @FXML
    private Stage stage;
    @FXML
    private Scene scene;
    private static sharedController instance;
    public double uiScale = 0.02;
    private sharedController() {
        if (instance != null) {
            throw new IllegalStateException("Cannot create multiple instances of GameWithSoundExample");
        }
    }
    public static sharedController getInstance() {
        if (instance == null) {
            instance = new sharedController();
        }
        return instance;
    }
    public void dynamicDisplay(Node root, Stage stage) {
        if (stage != null) {
            this.stage = stage;
            this.scene = stage.getScene();
            root.setStyle("-fx-font-size: "+stage.getWidth()*uiScale+";");
            if (root instanceof GridPane) {
                root.setStyle("-fx-font-size: "+stage.getWidth()*uiScale+";"+" -fx-vgap: "+stage.getHeight()*(uiScale+0.01)+";");
            }
            ChangeListener<Number> widthListener = (obs, oldWidth, newWidth) -> update(root);
            ChangeListener<Number> heightListener = (obs, oldHeight, newHeight) -> update(root);
            stage.widthProperty().addListener(widthListener);
            stage.heightProperty().addListener(heightListener);
            stage.widthProperty().removeListener(widthListener);
            stage.heightProperty().removeListener(heightListener);
            stage.setWidth(stage.getWidth());
            stage.setHeight(stage.getHeight());
            stage.setFullScreen(true);
        }
    }

    private void update(Node root) {
        double newWidth  = stage.getWidth();
        double newHeight = stage.getHeight();
        root.setStyle("-fx-font-size: "+newWidth*uiScale+";");
        if(root instanceof GridPane) {
            root.setStyle("-fx-font-size: "+newWidth*uiScale+";"+" -fx-vgap: "+newHeight*(uiScale+0.01)+"; -fx-pref-width: "+newWidth*uiScale*10+";");
        }
    }

    public void setUiScale(String uiScale) {
        switch (uiScale) {
            case "Huge" -> {
                this.uiScale = 0.03;
            }
            case "Large" -> {
                this.uiScale = 0.02;
            }
            case "Medium" -> {
                this.uiScale = 0.01;
            }
            case "Small" -> {
                this.uiScale = 0.005;
            }
        }
    }
}
