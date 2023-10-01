package com.example.gameproject;

import javafx.animation.*;
import javafx.beans.binding.BooleanBinding;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.MouseButton;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.scene.text.TextBoundsType;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.io.EOFException;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.lang.reflect.Array;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Objects;
import java.util.Random;

public class GameStageController {
    public ImageView sprite;
    public Rectangle rec;
    public Rectangle barRec;
    public Rectangle healthRec;
    public Rectangle barContain;
    public Rectangle barContain2;
    public Rectangle barRec2;
    public Rectangle healthRec2;
    public AnchorPane heartIcon;
    public AnchorPane heartIcon2;
    public ImageView npc;
    public Label playerTitle;
    public Text npcTitle1;
    private Stage stage;
    private Scene scene;
    private Arenas gameArena;
    private Characters playerChar;
    private Characters npcChar;
    private ArrayList<Characters> charList = gameManager.getInstance().getCharacterList();
    private static GameStageController instance;
    public AnchorPane GameStageBody;
    @FXML
    static Rectangle r1,r1Back,r2,r2Back,r2Front;
    @FXML
    static Rectangle powerbar1,powerbar1back,powewrbar2,powerbar2back,powerbar1Border,powerbar2border,powerbar2transparent;
    private BooleanProperty wPressed = new SimpleBooleanProperty();
    private BooleanProperty aPressed = new SimpleBooleanProperty();
    private BooleanProperty sPressed = new SimpleBooleanProperty();
    private BooleanProperty dPressed = new SimpleBooleanProperty();
    private boolean spacePressed = false;
    private int movementVar = 4;
    private BooleanBinding keyPressed = wPressed.or(aPressed).or(sPressed).or(dPressed);
    private Image[] playerRunArr = {new Image((Objects.requireNonNull(startGame.class.getResource("Images/runningRight_0.png"))).openStream()),new Image((Objects.requireNonNull(startGame.class.getResource("Images/runningRight_1.png"))).openStream()),new Image((Objects.requireNonNull(startGame.class.getResource("Images/runningRight_2.png"))).openStream())};
    private Image[] playerPunchArr = {new Image((Objects.requireNonNull(startGame.class.getResource("Images/punchRight_0.png"))).openStream()),new Image((Objects.requireNonNull(startGame.class.getResource("Images/punchRight_1.png"))).openStream()),new Image((Objects.requireNonNull(startGame.class.getResource("Images/punchRight_2.png"))).openStream()),new Image((Objects.requireNonNull(startGame.class.getResource("Images/punchRight_2.png"))).openStream()),new Image((Objects.requireNonNull(startGame.class.getResource("Images/standingLeft.png"))).openStream())};
    Timeline animRun;
    Timeline animPunch;

    AnimationTimer timer = new AnimationTimer() {
        @Override
        public void handle(long l) {
            if (wPressed.get()) {
                if (sprite.getLayoutY() == (scene.getHeight() - (scene.getHeight() * 0.1) - sprite.getFitHeight())) {
                    sprite.setLayoutY(sprite.getLayoutY() - 100);
                }
            }
            if (aPressed.get()) {
                sprite.setLayoutX(sprite.getLayoutX() - movementVar);
            }
            if (sPressed.get()) {

            }
            if (dPressed.get()) {
                sprite.setLayoutX(sprite.getLayoutX() + movementVar);
            }


        }
    };
    private
    AnimationTimer gameLoop = new AnimationTimer() {
        @Override
        public void handle(long l) {
            try {
                if(sprite.getBoundsInParent().intersects(npc.getBoundsInParent())) {
                    System.out.println("touching");
                }
                if (sprite.getLayoutY() < (scene.getHeight() - (scene.getHeight() * 0.1) - sprite.getFitHeight()) - 5) {
                    sprite.setLayoutY(sprite.getLayoutY() + 2*(movementVar));
                } else {
                    sprite.setLayoutY(scene.getHeight() - (scene.getHeight() * 0.1) - sprite.getFitHeight());
                }
            } catch (Exception e) {
                System.out.println(e);
            }
        }
    };
    private void movementSetup() {
        if(!(scene==null)){
            scene.setOnKeyPressed(e -> {
                if(e.getCode() == KeyCode.W){
                    wPressed.set(true);
                }
                if(e.getCode() == KeyCode.A){
                    animRun.play();
                    aPressed.set(true);

                }
                if(e.getCode() == KeyCode.S){
                    sPressed.set(true);
                }
                if(e.getCode() == KeyCode.D){
                    animRun.play();
                    dPressed.set(true);
                }
            });
            scene.setOnMousePressed(e -> {
                if(e.getButton() == MouseButton.PRIMARY){
                    animPunch.play();
                }
            });
            scene.setOnKeyReleased(e -> {
                if(e.getCode() == KeyCode.W){
                    wPressed.set(false);
                }
                if(e.getCode() == KeyCode.A){
                    aPressed.set(false);
                    if(!(dPressed.get())){
                        animRun.stop();
                    }
                    try {
                        sprite.setImage(new Image(Objects.requireNonNull(Objects.requireNonNull(startGame.class.getResource(playerChar.getModelURL())).openStream())));
                    } catch (IOException ex) {
                        throw new RuntimeException(ex);
                    }
                }
                if(e.getCode() == KeyCode.S){
                    sPressed.set(false);
                }
                if(e.getCode() == KeyCode.D){
                    dPressed.set(false);
                    if(!(aPressed.get())){
                        animRun.stop();
                    }
                    try {
                        sprite.setImage(new Image(Objects.requireNonNull(Objects.requireNonNull(startGame.class.getResource(playerChar.getModelURL())).openStream())));
                    } catch (IOException ex) {
                        throw new RuntimeException(ex);
                    }
                }
                if(e.getCode() == KeyCode.SPACE){
                    spacePressed = false;
                }
            });
        }

    }

    public GameStageController() throws IOException {
    }

    public void initialize() throws IOException {
        sharedController controller = sharedController.getInstance();
        controller.dynamicDisplay(GameStageBody, stage);
        gameArena = gameManager.getInstance().getGameArena();
        playerChar = gameManager.getInstance().getPlayerChar();
        for(int i=0; i<charList.size();i++) {
            if(charList.get(i)==playerChar) {
                charList.remove(i);
            }
        }
        Random rand = new Random();
        int int_random = rand.nextInt(charList.size());
        npcChar = charList.get(int_random);
        if(!(scene==null)){
            String image = Objects.requireNonNull(startGame.class.getResource(gameArena.getModelURL())).toExternalForm();
            GameStageBody.setStyle("-fx-background-image: url('" + image + "'); -fx-background-size: stretch;");
            sprite.setImage(new Image(Objects.requireNonNull(Objects.requireNonNull(startGame.class.getResource(playerChar.getModelURL())).openStream())));
            sprite.setFitHeight(scene.getHeight()*0.3);
            sprite.setFitWidth(scene.getHeight()*0.1);
            sprite.setLayoutY(scene.getHeight()-(scene.getHeight()*0.1)-sprite.getFitHeight());
            sprite.setLayoutX(scene.getWidth()*0.1);
            npc.setImage(new Image(Objects.requireNonNull(Objects.requireNonNull(startGame.class.getResource(npcChar.getModelURL())).openStream())));
            npc.setFitHeight(sprite.getFitHeight());
            npc.setFitWidth(sprite.getFitWidth());
            npc.setLayoutY(scene.getHeight()-(scene.getHeight()*0.1)-sprite.getFitHeight());
            npc.setLayoutX((scene.getWidth()*0.9)-npc.getFitWidth());
            rec.setHeight(scene.getHeight()*0.1);
            rec.setWidth(scene.getWidth());
            rec.setLayoutY(scene.getHeight()-(scene.getHeight()*0.1));
            rec.setFill(Color.RED);
            barRec.setHeight(scene.getHeight()*0.03);
            barRec.setWidth(scene.getWidth()*0.2);
            barRec.setLayoutY(scene.getHeight()*0.1);
            barRec.setLayoutX(scene.getWidth()*0.1);
            barRec.setFill(Color.WHITE);
            healthRec.setHeight(barRec.getHeight());
            healthRec.setWidth(barRec.getWidth());
            healthRec.setLayoutY(barRec.getLayoutY());
            healthRec.setLayoutX(barRec.getLayoutX());
            healthRec.setFill(Color.RED);
            barContain.setHeight(barRec.getHeight()+(barRec.getHeight()*0.15));
            barContain.setWidth(barRec.getWidth()+(barRec.getHeight()*0.15));
            barContain.setLayoutY(barRec.getBoundsInParent().getCenterY()-(barContain.getHeight()/2));
            barContain.setLayoutX(barRec.getBoundsInParent().getCenterX()-(barContain.getWidth()/2));
            barContain.setFill(Color.BLACK);
            heartIcon.setMinSize(barContain.getHeight(),barContain.getHeight());
            heartIcon.setMaxSize(barContain.getHeight(),barContain.getHeight());
            heartIcon.setLayoutY(barContain.getLayoutY());
            heartIcon.setLayoutX(barContain.getBoundsInParent().getMinX()-(barContain.getHeight()*1.1));
            heartIcon.setStyle("-fx-background-image: url('" + Objects.requireNonNull(startGame.class.getResource("Images/heartIcon.png")).toExternalForm() + "'); -fx-background-size: stretch;");
            barRec2.setHeight(scene.getHeight()*0.03);
            barRec2.setWidth(scene.getWidth()*0.2);
            barRec2.setLayoutY(scene.getHeight()*0.1);
            barRec2.setLayoutX(scene.getWidth()*0.7);
            barRec2.setFill(Color.WHITE);
            healthRec2.setHeight(barRec2.getHeight());
            healthRec2.setWidth(barRec2.getWidth());
            healthRec2.setLayoutY(barRec2.getLayoutY());
            healthRec2.setLayoutX(barRec2.getLayoutX());
            healthRec2.setFill(Color.RED);
            barContain2.setHeight(barRec2.getHeight()+(barRec2.getHeight()*0.15));
            barContain2.setWidth(barRec2.getWidth()+(barRec2.getHeight()*0.15));
            barContain2.setLayoutY(barRec2.getBoundsInParent().getCenterY()-(barContain2.getHeight()/2));
            barContain2.setLayoutX(barRec2.getBoundsInParent().getCenterX()-(barContain2.getWidth()/2));
            barContain2.setFill(Color.BLACK);
            heartIcon2.setMinSize(barContain2.getHeight(),barContain2.getHeight());
            heartIcon2.setMaxSize(barContain2.getHeight(),barContain2.getHeight());
            heartIcon2.setLayoutY(barContain2.getLayoutY());
            heartIcon2.setLayoutX(barContain2.getBoundsInParent().getMaxX()+(barContain2.getHeight()*0.1));
            heartIcon2.setStyle("-fx-background-image: url('" + startGame.class.getResource("Images/heartIcon.png").toExternalForm() + "'); -fx-background-size: stretch;");
            playerTitle.setText(playerChar.getName());
            playerTitle.setLayoutY(barContain.getLayoutY()-barContain.getHeight());
            playerTitle.setLayoutX(barContain.getLayoutX());
            playerTitle.setFont(Font.font(null, FontWeight.BOLD, null, barContain.getHeight()*0.8));
            npcTitle1.setText(npcChar.getName());
            npcTitle1.setFont(Font.font(null, FontWeight.BOLD, null, barContain2.getHeight()*0.8));
            npcTitle1.setBoundsType(TextBoundsType.VISUAL);
            npcTitle1.setLayoutX(barContain2.getBoundsInParent().getMaxX()-npcTitle1.getBoundsInParent().getWidth());
            npcTitle1.setLayoutY(playerTitle.getLayoutY()+npcTitle1.getBaselineOffset());
            animRun = (new AnimationController(100,sprite,playerRunArr)).getAnimation();
            animPunch = (new AnimationController(50,sprite,playerPunchArr)).getAnimation();
            animPunch.setCycleCount(playerPunchArr.length);
            keyPressed.addListener(((observableValue, aBoolean, t1) -> {
                if(!aBoolean){
                    timer.start();
                } else {
                    timer.stop();
                }
            }));
            movementSetup();
            gameLoop.start();
        }


    }
    int currentFrame = 0;
    public void setStage(Stage stage){
        this.stage = stage;
    }
    public void setScene(Scene scene){
        this.scene = scene;
    }
}
