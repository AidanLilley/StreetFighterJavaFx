package com.example.gameproject;

import javafx.animation.AnimationTimer;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.scene.text.TextBoundsType;
import javafx.stage.Stage;

import java.io.EOFException;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.Objects;

public class MPGameStageController {
    public AnchorPane MPGameStageBody;
    public Label playerTitleHost;
    public Rectangle barContainHost;
    public Rectangle barRecHost;
    public Rectangle healthRecHost;
    public AnchorPane heartIconHost;
    public Text playerTitleJoined;
    public Rectangle barContainJoined;
    public Rectangle barRecJoined;
    public Rectangle healthRecJoined;
    public AnchorPane heartIconJoined;
    public Rectangle rec;
    public ImageView hostSprite;
    public ImageView joinedSprite;
    private Characters hostChar;
    private Characters joinedChar;
    private Arenas gameArena;
    private Stage stage;
    private Scene scene;
    private boolean gameCreated = true;

    private
    AnimationTimer gameLoopHost = new AnimationTimer() {
        @Override
        public void handle(long l) {
            try {
                if(gameCreated) {
                    updateHost();
                }

            } catch (InterruptedException | IOException | ClassNotFoundException e) {
                System.out.println(e);
            }
        }
    };
    AnimationTimer gameLoopJoined = new AnimationTimer() {
        @Override
        public void handle(long l) {
            try {
                if(gameCreated) {
                    updateJoined();
                }

            } catch (InterruptedException | IOException | ClassNotFoundException e) {
                System.out.println(e);
            }
        }
    };
    private Request ownPlayerData;
    private Socket clientSocket;
    private Request enemyPlayerData;
    private String role = "";

    private void updateHost() throws InterruptedException, IOException, ClassNotFoundException {
        try {
            gameCreated = false;
            clientSocket = new Socket("127.0.0.1", 9994);
            ObjectOutputStream sendData = new ObjectOutputStream(clientSocket.getOutputStream());
            sendData.writeObject(ownPlayerData);
            System.out.println(ownPlayerData);
            ObjectInputStream receiveData = new ObjectInputStream(clientSocket.getInputStream());
            enemyPlayerData = (Request) receiveData.readObject();
            clientSocket.close();
            joinedSprite = enemyPlayerData.getView();
            joinedChar = enemyPlayerData.getPlayer();
            gameCreated = true;
        } catch (Exception e){
        }
    }

    private void updateJoined() throws InterruptedException, IOException, ClassNotFoundException {
        try {
            gameCreated = false;
            clientSocket = new Socket("127.0.0.1", 9994);
            ObjectOutputStream sendData = new ObjectOutputStream(clientSocket.getOutputStream());
            sendData.writeObject(ownPlayerData);
            System.out.println(ownPlayerData);
            ObjectInputStream receiveData = new ObjectInputStream(clientSocket.getInputStream());
            enemyPlayerData = (Request) receiveData.readObject();
            clientSocket.close();
            hostSprite = enemyPlayerData.getView();
            hostChar = enemyPlayerData.getPlayer();
            gameCreated = true;
        } catch (Exception e){
        }
    }

    public void initialize() throws IOException, ClassNotFoundException {
        sharedController controller = sharedController.getInstance();
        controller.dynamicDisplay(MPGameStageBody, stage);
        if(!(scene==null)){
            clientSocket = new Socket("127.0.0.1", 9994);
            ObjectOutputStream request = new ObjectOutputStream(clientSocket.getOutputStream());
            ownPlayerData.setMessage("Get Arena");
            request.writeObject(ownPlayerData);
            ObjectInputStream requestOutcome = new ObjectInputStream(clientSocket.getInputStream());
            Arenas outcome = (Arenas) requestOutcome.readObject();
            gameArena = outcome;
            ownPlayerData.setMessage(null);
            role = ownPlayerData.getType();
            if(Objects.equals(role, "Host")) {
                hostChar = ownPlayerData.getPlayer();
                joinedChar = enemyPlayerData.getPlayer();
            } else {
                joinedChar = ownPlayerData.getPlayer();
                hostChar = enemyPlayerData.getPlayer();
            }
            MPGameStageBody.setStyle("-fx-background-image: url('" + Objects.requireNonNull(startGame.class.getResource(gameArena.getModelURL())).toExternalForm() + "'); -fx-background-size: stretch;");
            hostSprite.setImage(new Image(Objects.requireNonNull(Objects.requireNonNull(startGame.class.getResource(hostChar.getModelURL())).openStream())));
            hostSprite.setFitHeight(scene.getHeight()*0.3);
            hostSprite.setFitWidth(scene.getHeight()*0.1);
            hostSprite.setLayoutY(scene.getHeight()-(scene.getHeight()*0.1)-hostSprite.getFitHeight());
            hostSprite.setLayoutX(scene.getWidth()*0.1);
            joinedSprite.setImage(new Image(Objects.requireNonNull(Objects.requireNonNull(startGame.class.getResource(joinedChar.getModelURL())).openStream())));
            joinedSprite.setFitHeight(hostSprite.getFitHeight());
            joinedSprite.setFitWidth(hostSprite.getFitWidth());
            joinedSprite.setLayoutY(scene.getHeight()-(scene.getHeight()*0.1)-joinedSprite.getFitHeight());
            joinedSprite.setLayoutX((scene.getWidth()*0.9)-joinedSprite.getFitWidth());
            rec.setHeight(scene.getHeight()*0.1);
            rec.setWidth(scene.getWidth());
            rec.setLayoutY(scene.getHeight()-(scene.getHeight()*0.1));
            rec.setFill(Color.TRANSPARENT);
            barRecHost.setHeight(scene.getHeight()*0.03);
            barRecHost.setWidth(scene.getWidth()*0.2);
            barRecHost.setLayoutY(scene.getHeight()*0.1);
            barRecHost.setLayoutX(scene.getWidth()*0.1);
            barRecHost.setFill(Color.WHITE);
            healthRecHost.setHeight(barRecHost.getHeight());
            healthRecHost.setWidth(barRecHost.getWidth());
            healthRecHost.setLayoutY(barRecHost.getLayoutY());
            healthRecHost.setLayoutX(barRecHost.getLayoutX());
            healthRecHost.setFill(Color.RED);
            barContainHost.setHeight(barRecHost.getHeight()+(barRecHost.getHeight()*0.15));
            barContainHost.setWidth(barRecHost.getWidth()+(barRecHost.getHeight()*0.15));
            barContainHost.setLayoutY(barRecHost.getBoundsInParent().getCenterY()-(barContainHost.getHeight()/2));
            barContainHost.setLayoutX(barRecHost.getBoundsInParent().getCenterX()-(barContainHost.getWidth()/2));
            barContainHost.setFill(Color.BLACK);
            heartIconHost.setMinSize(barContainHost.getHeight(),barContainHost.getHeight());
            heartIconHost.setMaxSize(barContainHost.getHeight(),barContainHost.getHeight());
            heartIconHost.setLayoutY(barContainHost.getLayoutY());
            heartIconHost.setLayoutX(barContainHost.getBoundsInParent().getMinX()-(barContainHost.getHeight()*1.1));
            heartIconHost.setStyle("-fx-background-image: url('" + Objects.requireNonNull(startGame.class.getResource("Images/heartIcon.png")).toExternalForm() + "'); -fx-background-size: stretch;");
            barRecJoined.setHeight(scene.getHeight()*0.03);
            barRecJoined.setWidth(scene.getWidth()*0.2);
            barRecJoined.setLayoutY(scene.getHeight()*0.1);
            barRecJoined.setLayoutX(scene.getWidth()*0.7);
            barRecJoined.setFill(Color.WHITE);
            healthRecJoined.setHeight(barRecJoined.getHeight());
            healthRecJoined.setWidth(barRecJoined.getWidth());
            healthRecJoined.setLayoutY(barRecJoined.getLayoutY());
            healthRecJoined.setLayoutX(barRecJoined.getLayoutX());
            healthRecJoined.setFill(Color.RED);
            barContainJoined.setHeight(barRecJoined.getHeight()+(barRecJoined.getHeight()*0.15));
            barContainJoined.setWidth(barRecJoined.getWidth()+(barRecJoined.getHeight()*0.15));
            barContainJoined.setLayoutY(barRecJoined.getBoundsInParent().getCenterY()-(barContainJoined.getHeight()/2));
            barContainJoined.setLayoutX(barRecJoined.getBoundsInParent().getCenterX()-(barContainJoined.getWidth()/2));
            barContainJoined.setFill(Color.BLACK);
            heartIconJoined.setMinSize(barContainJoined.getHeight(),barContainJoined.getHeight());
            heartIconJoined.setMaxSize(barContainJoined.getHeight(),barContainJoined.getHeight());
            heartIconJoined.setLayoutY(barContainJoined.getLayoutY());
            heartIconJoined.setLayoutX(barContainJoined.getBoundsInParent().getMaxX()+(barContainJoined.getHeight()*0.1));
            heartIconJoined.setStyle("-fx-background-image: url('" + Objects.requireNonNull(startGame.class.getResource("Images/heartIcon.png")).toExternalForm() + "'); -fx-background-size: stretch;");
            playerTitleHost.setText(hostChar.getName());
            playerTitleHost.setLayoutY(barContainHost.getLayoutY()-barContainHost.getHeight());
            playerTitleHost.setLayoutX(barContainHost.getLayoutX());
            playerTitleHost.setFont(Font.font(null, FontWeight.BOLD, null, barContainHost.getHeight()*0.8));
            playerTitleJoined.setText(joinedChar.getName());
            playerTitleJoined.setFont(Font.font(null, FontWeight.BOLD, null, barContainJoined.getHeight()*0.8));
            playerTitleJoined.setBoundsType(TextBoundsType.VISUAL);
            playerTitleJoined.setLayoutX(barContainJoined.getBoundsInParent().getMaxX()-playerTitleJoined.getBoundsInParent().getWidth());
            playerTitleJoined.setLayoutY(playerTitleHost.getLayoutY()+playerTitleJoined.getBaselineOffset());
            if(Objects.equals(role, "Host")) {
                gameLoopHost.start();
            } else {
                gameLoopJoined.start();
            }
        }
    }


    public void setStage(Stage stage){
        this.stage = stage;
    }
    public void setScene(Scene scene){
        this.scene = scene;
    }

    public void setOwnData(Request ownPlayerData) {
        this.ownPlayerData = ownPlayerData;
    }

    public void setEnemyData(Request enemyPlayerData) {
        this.enemyPlayerData = enemyPlayerData;
    }
}
