package com.example.gameproject;

import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.effect.DropShadow;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.ArrayList;

public class SingleArenaSelect {
    public VBox mainBodySAS;
    public FlowPane arenaGrid;
    public Label SASTitle;
    public VBox arenaView;
    public HBox arenaSelectBody;
    public AnchorPane arenaPage;
    public Button selectArena;
    public Label apName;
    public AnchorPane StreetAttack;
    private Stage stage;
    private Scene scene;
    private Node clickedItem;
    private Arenas selectedArena;
    private ArrayList<AnchorPane> apList = new ArrayList<>();
    private ArrayList<Arenas> arenaList = new ArrayList<>();
    gameManager manager = gameManager.getInstance();

    public SingleArenaSelect() throws IOException {
    }

    public void initialize() throws IOException {
        sharedController controller = sharedController.getInstance();
        controller.dynamicDisplay(mainBodySAS, stage);
        if(!(scene==null)){
            arenaGrid.setPrefWidth(scene.getWidth()*0.6);
            arenaView.setPrefWidth(scene.getWidth()*0.3);
            arenaPage.setMaxWidth(arenaView.getPrefWidth()*0.9);
            arenaPage.setMinWidth(arenaView.getPrefWidth()*0.9);
            arenaPage.setMaxHeight(arenaView.getPrefWidth()*0.5);
            arenaPage.setMinHeight(arenaView.getPrefWidth()*0.5);
            VBox.setMargin(arenaPage,new Insets(0,0,20,0));
            arenaView.setPadding(new Insets(20,20,20,20));
            arenaGrid.setPadding(new Insets(20,20,20,20));
            arenaList = manager.getArenaList();
            StreetAttack = makeInteractive(StreetAttack, arenaList.get(0));
            apList.add(StreetAttack);
            apName.setFont(Font.font(null, null, null, scene.getWidth()*controller.uiScale*0.6));
            SASTitle.setFont(Font.font(null, null, null, scene.getWidth()*controller.uiScale*2));
        }

    }

    public void setStage(Stage stage){
        this.stage = stage;
    }
    public void setScene(Scene scene){
        this.scene = scene;
    }

    public void backButton() throws IOException, URISyntaxException {
        FXMLLoader fxmlLoader = new FXMLLoader(startGame.class.getResource("Fxml/SingleCharacterSelect.fxml"));
        Parent root = fxmlLoader.load();
        SingleCharacterSelect controller = fxmlLoader.getController();
        stage.getScene().setRoot(root);
        controller.setStage(stage);
        controller.setScene(scene);
        stage.show();
        controller.initialize();
    }
    private void itemClicked(AnchorPane red, Arenas arena) {
        if(this.clickedItem != null) {
            for(int i=0; i<apList.size();i++) {
                if(this.clickedItem == apList.get(i)) {
                    apList.get(i).setEffect(null);
                }
            }
        }
        this.clickedItem = red;
        this.selectedArena = arena;
        for(int i=0; i<apList.size();i++) {
            if(this.clickedItem == apList.get(i)) {
                int depth = 70;
                DropShadow borderGlow = new DropShadow();
                borderGlow.setOffsetY(0f);
                borderGlow.setOffsetX(0f);
                borderGlow.setColor(Color.RED);
                borderGlow.setWidth(depth);
                borderGlow.setHeight(depth);
                apList.get(i).setEffect(borderGlow);
            }
        }
    }
    private void mouseExit(Arenas arena) {
        if(this.clickedItem==null) {
            arenaPage.setBackground(null);
            apName.setText("Name: --");
        } else {
            arenaPage.setBackground(((AnchorPane) this.clickedItem).getBackground());
            apName.setText("Name: "+selectedArena.getName());
        }
    }
    private void mouseEnter(AnchorPane red, Arenas arena) {
        arenaPage.setBackground(red.getBackground());
        apName.setText("Name: "+arena.getName());
    }
    private AnchorPane makeInteractive(AnchorPane red, Arenas arena) throws IOException {
        red.setOnMouseEntered(event -> mouseEnter(red, arena));
        red.setOnMouseExited(event -> mouseExit(arena));
        red.setOnMouseClicked(event -> itemClicked(red, arena));
        String image = startGame.class.getResource(arena.getModelURL()).toExternalForm();
        red.setStyle("-fx-background-image: url('" + image + "');");
        red.setMinWidth(arenaGrid.getPrefWidth()*sharedController.getInstance().uiScale*7);
        red.setMaxWidth(arenaGrid.getPrefWidth()*sharedController.getInstance().uiScale*7);
        red.setMinHeight(arenaGrid.getPrefWidth()*sharedController.getInstance().uiScale*7);
        red.setMaxHeight(arenaGrid.getPrefWidth()*sharedController.getInstance().uiScale*7);
        return red;
    }

    public void toGameStage() throws IOException, URISyntaxException {
        if(selectedArena != null) {
            gameManager.getInstance().setGameArena(selectedArena);
            FXMLLoader fxmlLoad = new FXMLLoader(startGame.class.getResource("Fxml/GameStage.fxml"));
            Parent root = fxmlLoad.load();
            GameStageController control = fxmlLoad.getController();
            stage.getScene().setRoot(root);
            control.setStage(stage);
            control.setScene(scene);
            stage.show();
            control.initialize();
        } else {
            SASTitle.setFont(Font.font(null, null, null, selectArena.getFont().getSize()*1.7));
            SASTitle.setText("Choose An Arena Before Continuing!");

        }

    }

}
