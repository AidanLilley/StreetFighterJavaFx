package com.example.gameproject;

import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.effect.DropShadow;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.ArrayList;

public class SingleCharacterSelect {
    public VBox mainBodySCS;
    public FlowPane characterGrid;
    public Label SCSTitle;
    public VBox characterView;
    public HBox characterSelectBody;
    public AnchorPane characterPage;
    public Button selectChar;
    public Label cpName;
    public Label cpHealth;
    public Label cpDamage;
    public Label cpSpeed;
    public AnchorPane Josh;
    public AnchorPane DanM;
    public AnchorPane Nathan;
    public AnchorPane Beth;
    public AnchorPane DanF;
    public AnchorPane Liz;
    public AnchorPane James;
    public AnchorPane Samiro;
    public AnchorPane Realyn;
    private Stage stage;
    private Scene scene;
    private Node clickedItem;
    private Characters selectedChar;
    private ArrayList<AnchorPane> apList = new ArrayList<>();
    private ArrayList<Characters> charList = new ArrayList<>();
    gameManager manager = gameManager.getInstance();

    public SingleCharacterSelect() throws IOException {
    }

    public void initialize() throws IOException {
        sharedController controller = sharedController.getInstance();
        controller.dynamicDisplay(mainBodySCS, stage);
        if(!(scene==null)){
            characterGrid.setPrefWidth(scene.getWidth()*0.6);
            characterView.setPrefWidth(scene.getWidth()*0.3);
            characterPage.setMaxWidth(characterView.getPrefWidth()*0.9);
            characterPage.setMinWidth(characterView.getPrefWidth()*0.9);
            characterPage.setMaxHeight(characterView.getPrefWidth()*0.5);
            characterPage.setMinHeight(characterView.getPrefWidth()*0.5);
            VBox.setMargin(characterPage,new Insets(0,0,20,0));
            characterView.setPadding(new Insets(20,20,20,20));
            characterGrid.setPadding(new Insets(20,20,20,20));
            charList = manager.getCharacterList();
            Josh = makeInteractive(Josh, charList.get(0));
            DanM = makeInteractive(DanM, charList.get(1));
            Nathan = makeInteractive(Nathan, charList.get(2));
            Beth = makeInteractive(Beth, charList.get(3));
            DanF = makeInteractive(DanF, charList.get(4));
            Liz = makeInteractive(Liz, charList.get(5));
            James = makeInteractive(James, charList.get(6));
            Samiro = makeInteractive(Samiro, charList.get(7));
            Realyn = makeInteractive(Realyn, charList.get(8));
            apList.add(Josh);
            apList.add(DanM);
            apList.add(Nathan);
            apList.add(Beth);
            apList.add(DanF);
            apList.add(Liz);
            apList.add(James);
            apList.add(Samiro);
            apList.add(Realyn);
            cpName.setFont(Font.font(null, null, null, scene.getWidth()*controller.uiScale*0.6));
            cpSpeed.setFont(Font.font(null, null, null, scene.getWidth()*controller.uiScale*0.6));
            cpDamage.setFont(Font.font(null, null, null, scene.getWidth()*controller.uiScale*0.6));
            cpHealth.setFont(Font.font(null, null, null, scene.getWidth()*controller.uiScale*0.6));
            SCSTitle.setFont(Font.font(null, null, null, scene.getWidth()*controller.uiScale*2));
        }

    }

    public void setStage(Stage stage){
        this.stage = stage;
    }
    public void setScene(Scene scene){
        this.scene = scene;
    }

    public void backButton() throws IOException, URISyntaxException {
        FXMLLoader fxmlLoad = new FXMLLoader(startGame.class.getResource("Fxml/MainPage.fxml"));
        Parent root = fxmlLoad.load();
        mainPageController control = fxmlLoad.getController();
        stage.getScene().setRoot(root);
        control.setStage(stage);
        control.setScene(scene);
        stage.show();
        control.initialize();
    }
    private void itemClicked(AnchorPane red, Characters character) {
        if(this.clickedItem != null) {
            for(int i=0; i<apList.size();i++) {
                if(this.clickedItem == apList.get(i)) {
                    apList.get(i).setEffect(null);
                }
            }
        }
        this.clickedItem = red;
        this.selectedChar = character;
        for(int i=0; i<apList.size();i++) {
            if(this.clickedItem == apList.get(i)) {
                int depth = 70;
                DropShadow borderGlow= new DropShadow();
                borderGlow.setOffsetY(0f);
                borderGlow.setOffsetX(0f);
                borderGlow.setColor(Color.RED);
                borderGlow.setWidth(depth);
                borderGlow.setHeight(depth);
                apList.get(i).setEffect(borderGlow);
            }
        }
    }
    private void mouseExit(Characters character) {
        if(this.clickedItem==null) {
            characterPage.setBackground(null);
            cpName.setText("Name: --");
            cpHealth.setText("Health: --");
            cpDamage.setText("Damage: --");
            cpSpeed.setText("Speed: --");
        } else {
            characterPage.setBackground(((AnchorPane) this.clickedItem).getBackground());
            cpName.setText("Name: "+selectedChar.getName());
            cpHealth.setText("Health: "+selectedChar.getHealth());
            cpDamage.setText("Damage: "+selectedChar.getDamage());
            cpSpeed.setText("Speed: "+selectedChar.getSpeed());
        }
    }
    private void mouseEnter(AnchorPane red, Characters character) {
        characterPage.setBackground(red.getBackground());
        cpName.setText("Name: "+character.getName());
        cpHealth.setText("Health: "+character.getHealth());
        cpDamage.setText("Damage: "+character.getDamage());
        cpSpeed.setText("Speed: "+character.getSpeed());
    }
    private AnchorPane makeInteractive(AnchorPane red, Characters character) throws IOException {
        red.setOnMouseEntered(event -> mouseEnter(red, character));
        red.setOnMouseExited(event -> mouseExit(character));
        red.setOnMouseClicked(event -> itemClicked(red, character));
        String image = startGame.class.getResource(character.getModelURL()).toExternalForm();
        red.setStyle("-fx-background-image: url('" + image + "');");
        red.setMinWidth(characterGrid.getPrefWidth()*sharedController.getInstance().uiScale*7);
        red.setMaxWidth(characterGrid.getPrefWidth()*sharedController.getInstance().uiScale*7);
        red.setMinHeight(characterGrid.getPrefWidth()*sharedController.getInstance().uiScale*7);
        red.setMaxHeight(characterGrid.getPrefWidth()*sharedController.getInstance().uiScale*7);
        return red;
    }

    public void toArenaSelect() throws IOException, URISyntaxException {
        if(selectedChar != null) {
            gameManager.getInstance().setPlayerChar(selectedChar);
            FXMLLoader fxmlLoad = new FXMLLoader(startGame.class.getResource("Fxml/SingleArenaSelect.fxml"));
            Parent root = fxmlLoad.load();
            SingleArenaSelect control = fxmlLoad.getController();
            stage.getScene().setRoot(root);
            control.setStage(stage);
            control.setScene(scene);
            stage.show();
            control.initialize();
        } else {
            SCSTitle.setFont(Font.font(null, null, null, selectChar.getFont().getSize()*1.7));
            SCSTitle.setText("Choose A Character Before Continuing!");

        }

    }
}
