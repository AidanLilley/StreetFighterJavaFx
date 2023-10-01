package com.example.gameproject;

import javafx.animation.AnimationTimer;
import javafx.event.ActionEvent;
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

import java.io.*;
import java.net.Socket;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.Objects;

public class MultiplayerGameSelect {
    public FlowPane MultiplayerCharGrid;
    public AnchorPane Josh;
    public AnchorPane DanM;
    public AnchorPane Nathan;
    public AnchorPane Beth;
    public AnchorPane DanF;
    public AnchorPane Liz;
    public AnchorPane James;
    public AnchorPane Samiro;
    public AnchorPane Realyn;
    public Label mcpName;
    public Label mcpHealth;
    public Label mcpDamage;
    public Label mcpSpeed;
    public AnchorPane MPcharacterPage;
    public VBox ownCharView;
    public AnchorPane MPcharacterPageEnemy;
    public Label mcpNameEnemy;
    public Label mcpHealthEnemy;
    public Label mcpDamageEnemy;
    public Label mcpSpeedEnemy;
    public VBox enemyCharView;
    public HBox arenaSelector;
    public AnchorPane arenaView;
    public Button carouselBack;
    public Button carouselForward;
    public HBox bottomScreen;
    public Label warning;
    private Socket clientSocket;
    public VBox MultiplayerSelectMainBody;
    private Stage stage;
    private Scene scene;
    private int threadID;
    private String role = "";
    private Node clickedItem;
    private Characters selectedChar;
    private ArrayList<AnchorPane> apList = new ArrayList<>();
    private ArrayList<Characters> charList = new ArrayList<>();
    private ArrayList<Arenas> arenaList = new ArrayList<>();
    private Request enemyPlayerData = new Request(null,null,null,null);
    private Request ownPlayerData = new Request(null,null,null,null);
    private boolean gameCreated = false;
    private int selectedArenaNum = 0;
    gameManager manager = gameManager.getInstance();
    private
    AnimationTimer gameLoop = new AnimationTimer() {
        @Override
        public void handle(long l) {
            try {
                if(gameCreated) {
                    update();
                }

            } catch (InterruptedException | IOException | ClassNotFoundException e) {
                System.out.println(e);
            }
        }
    };

    public MultiplayerGameSelect() throws IOException {
    }

    private void update() throws InterruptedException, IOException, ClassNotFoundException {
            try {
                gameCreated = false;
                clientSocket = new Socket("127.0.0.1", 9994);
                ObjectOutputStream sendData = new ObjectOutputStream(clientSocket.getOutputStream());
                sendData.writeObject(ownPlayerData);
                ObjectInputStream receiveData = new ObjectInputStream(clientSocket.getInputStream());
                enemyPlayerData = (Request) receiveData.readObject();
                clientSocket.close();
                if (enemyPlayerData.getType()==null) {
                    MPcharacterPageEnemy.setBackground(null);
                    mcpNameEnemy.setText("Waiting for second player!");
                    mcpHealthEnemy.setText("");
                    mcpDamageEnemy.setText("");
                    mcpSpeedEnemy.setText("");
                }
                else if(enemyPlayerData.getPlayer()==null) {
                    MPcharacterPageEnemy.setBackground(null);
                    mcpNameEnemy.setText("Name: --");
                    mcpHealthEnemy.setText("Health: --");
                    mcpDamageEnemy.setText("Damage: --");
                    mcpSpeedEnemy.setText("Speed: --");
                } else {
                    String image = startGame.class.getResource(enemyPlayerData.getPlayer().getModelURL()).toExternalForm();
                    MPcharacterPageEnemy.setStyle("-fx-background-image: url('" + image + "'); -fx-background-size: stretch;");
                    mcpNameEnemy.setText("Name: "+enemyPlayerData.getPlayer().getName());
                    mcpHealthEnemy.setText("Health: "+enemyPlayerData.getPlayer().getHealth());
                    mcpDamageEnemy.setText("Damage: "+enemyPlayerData.getPlayer().getDamage());
                    mcpSpeedEnemy.setText("Speed: "+enemyPlayerData.getPlayer().getSpeed());
                }
                if(enemyPlayerData.getMessage() != null) {
                    if(Objects.equals(enemyPlayerData.getMessage(), "Start Game For Joined")) {
                        gameLoop.stop();
                        FXMLLoader fxmlLoad = new FXMLLoader(startGame.class.getResource("Fxml/MPGameStage.fxml"));
                        Parent root = fxmlLoad.load();
                        MPGameStageController control = fxmlLoad.getController();
                        stage.getScene().setRoot(root);
                        control.setStage(stage);
                        control.setScene(scene);
                        control.setOwnData(ownPlayerData);
                        control.setEnemyData(enemyPlayerData);
                        stage.show();
                        control.initialize();
                    }
                }
                gameCreated = true;
            } catch (EOFException ex){
            }
    }

    public void initialize() throws IOException {
        sharedController controller = sharedController.getInstance();
        controller.dynamicDisplay(MultiplayerSelectMainBody, stage);
        if(!(scene==null)){
            MultiplayerCharGrid.setPrefWidth(scene.getWidth()*0.9);
            MultiplayerCharGrid.setPadding(new Insets(20,20,20,20));

            ownCharView.setPrefWidth(scene.getWidth()*0.3);
            MPcharacterPage.setMaxWidth(ownCharView.getPrefWidth()*0.9);
            MPcharacterPage.setMinWidth(ownCharView.getPrefWidth()*0.9);
            MPcharacterPage.setMaxHeight(ownCharView.getPrefWidth()*0.5);
            MPcharacterPage.setMinHeight(ownCharView.getPrefWidth()*0.5);
            mcpName.setFont(Font.font(null, null, null, scene.getWidth()*controller.uiScale*0.6));
            mcpSpeed.setFont(Font.font(null, null, null, scene.getWidth()*controller.uiScale*0.6));
            mcpDamage.setFont(Font.font(null, null, null, scene.getWidth()*controller.uiScale*0.6));
            mcpHealth.setFont(Font.font(null, null, null, scene.getWidth()*controller.uiScale*0.6));

            enemyCharView.setPrefWidth(scene.getWidth()*0.3);
            MPcharacterPageEnemy.setMaxWidth(enemyCharView.getPrefWidth()*0.9);
            MPcharacterPageEnemy.setMinWidth(enemyCharView.getPrefWidth()*0.9);
            MPcharacterPageEnemy.setMaxHeight(enemyCharView.getPrefWidth()*0.5);
            MPcharacterPageEnemy.setMinHeight(enemyCharView.getPrefWidth()*0.5);
            mcpNameEnemy.setFont(Font.font(null, null, null, scene.getWidth()*controller.uiScale*0.6));
            mcpSpeedEnemy.setFont(Font.font(null, null, null, scene.getWidth()*controller.uiScale*0.6));
            mcpDamageEnemy.setFont(Font.font(null, null, null, scene.getWidth()*controller.uiScale*0.6));
            mcpHealthEnemy.setFont(Font.font(null, null, null, scene.getWidth()*controller.uiScale*0.6));

            arenaSelector.setPrefWidth(scene.getWidth()*0.3);
            arenaList = manager.getArenaList();
            arenaView.setStyle("-fx-background-image: url('" + startGame.class.getResource(arenaList.get(selectedArenaNum).getModelURL()).toExternalForm() + "');");
            arenaView.setPrefWidth(arenaSelector.getPrefWidth()*0.7);
            arenaView.setPadding(new Insets(20,20,20,20));

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

            gameLoop.start();
        }
    }
    public void createGame() throws IOException, ClassNotFoundException {
        clientSocket= new Socket("127.0.0.1", 9994);
        role = "Host";
        ObjectOutputStream request = new ObjectOutputStream(clientSocket.getOutputStream());
        request.writeObject(new Request(null,null,"New Game",null));
        ObjectInputStream requestOutcome = new ObjectInputStream(clientSocket.getInputStream());
        Request outcome = (Request) requestOutcome.readObject();
        threadID = outcome.getThreadID();
        System.out.println("Client: game created with threadID "+threadID);
        gameCreated = true;
        ownPlayerData.setThreadID(threadID);
        ownPlayerData.setType(role);
        clientSocket.close();
    }
    public void joinGame(Integer threadID) throws IOException, ClassNotFoundException {
        clientSocket= new Socket("127.0.0.1", 9994);
        role = "Joined";
        gameCreated = true;
        ObjectOutputStream request = new ObjectOutputStream(clientSocket.getOutputStream());
        request.writeObject(new Request(null,null,"Join Game",threadID,null));
        System.out.println("Client: joined game with threadID "+threadID);
        ownPlayerData.setThreadID(threadID);
        ownPlayerData.setType(role);
        clientSocket.close();
    }
    public void backButton() throws IOException, URISyntaxException, ClassNotFoundException {
        gameLoop.stop();
        if(role == "Host") {
            clientSocket= new Socket("127.0.0.1", 9994);
            ObjectOutputStream request = new ObjectOutputStream(clientSocket.getOutputStream());
            request.writeObject(new Request(null,null,"End Game",threadID,null));
        }else {
            clientSocket= new Socket("127.0.0.1", 9994);
            ObjectOutputStream request = new ObjectOutputStream(clientSocket.getOutputStream());
            request.writeObject(new Request(null,null,null,threadID,null));
        }
        FXMLLoader fxmlLoader = new FXMLLoader(startGame.class.getResource("Fxml/MultiplayerGameFinder.fxml"));
        Parent root = fxmlLoader.load();
        MultiplayerGameFinder controller = fxmlLoader.getController();
        stage.getScene().setRoot(root);
        controller.setStage(stage);
        controller.setScene(scene);
        stage.show();
        controller.initialize();
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
        ownPlayerData.setPlayer(character);
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
            MPcharacterPage.setBackground(null);
            mcpName.setText("Name: --");
            mcpHealth.setText("Health: --");
            mcpDamage.setText("Damage: --");
            mcpSpeed.setText("Speed: --");
        } else {
            MPcharacterPage.setBackground(((AnchorPane) this.clickedItem).getBackground());
            mcpName.setText("Name: "+ownPlayerData.getPlayer().getName());
            mcpHealth.setText("Health: "+ownPlayerData.getPlayer().getHealth());
            mcpDamage.setText("Damage: "+ownPlayerData.getPlayer().getDamage());
            mcpSpeed.setText("Speed: "+ownPlayerData.getPlayer().getSpeed());
        }
    }
    private void mouseEnter(AnchorPane red, Characters character) {
        MPcharacterPage.setBackground(red.getBackground());
        mcpName.setText("Name: "+character.getName());
        mcpHealth.setText("Health: "+character.getHealth());
        mcpDamage.setText("Damage: "+character.getDamage());
        mcpSpeed.setText("Speed: "+character.getSpeed());
    }
    private AnchorPane makeInteractive(AnchorPane red, Characters character) throws IOException {
        red.setOnMouseEntered(event -> mouseEnter(red, character));
        red.setOnMouseExited(event -> mouseExit(character));
        red.setOnMouseClicked(event -> itemClicked(red, character));
        String image = startGame.class.getResource(character.getModelURL()).toExternalForm();
        red.setStyle("-fx-background-image: url('" + image + "');");
        red.setMinWidth(MultiplayerCharGrid.getPrefWidth()*sharedController.getInstance().uiScale*7);
        red.setMaxWidth(MultiplayerCharGrid.getPrefWidth()*sharedController.getInstance().uiScale*7);
        red.setMinHeight(MultiplayerCharGrid.getPrefWidth()*sharedController.getInstance().uiScale*7);
        red.setMaxHeight(MultiplayerCharGrid.getPrefWidth()*sharedController.getInstance().uiScale*7);
        return red;
    }

    public void setStage(Stage stage){
        this.stage = stage;
    }
    public void setScene(Scene scene){
        this.scene = scene;
    }

    public void carouselPrev() {
        selectedArenaNum = (selectedArenaNum-1)%arenaList.size();
        arenaView.setStyle("-fx-background-image: url('" + startGame.class.getResource(arenaList.get(selectedArenaNum).getModelURL()).toExternalForm() + "');");
        System.out.println(selectedArenaNum);
    }

    public void carouselNext() {
        selectedArenaNum = (selectedArenaNum+1)%arenaList.size();
        arenaView.setStyle("-fx-background-image: url('" + startGame.class.getResource(arenaList.get(selectedArenaNum).getModelURL()).toExternalForm() + "');");
        System.out.println(selectedArenaNum);
    }

    public void startBattle() throws IOException, ClassNotFoundException {
        gameLoop.stop();
        if((ownPlayerData.getPlayer() != null)&&(enemyPlayerData.getPlayer() != null)) {
            if(Objects.equals(role, "Host")) {
                clientSocket = new Socket("127.0.0.1", 9994);
                ObjectOutputStream request = new ObjectOutputStream(clientSocket.getOutputStream());
                request.writeObject(new Request(null,null,"Start Game",threadID,null,arenaList.get(selectedArenaNum)));
                ownPlayerData.setMessage("Start Game For Joined");
                clientSocket = new Socket("127.0.0.1", 9994);
                ObjectOutputStream request2 = new ObjectOutputStream(clientSocket.getOutputStream());
                request2.writeObject(ownPlayerData);
            }
            FXMLLoader fxmlLoad = new FXMLLoader(startGame.class.getResource("Fxml/MPGameStage.fxml"));
            Parent root = fxmlLoad.load();
            MPGameStageController control = fxmlLoad.getController();
            stage.getScene().setRoot(root);
            control.setStage(stage);
            control.setScene(scene);
            control.setOwnData(ownPlayerData);
            control.setEnemyData(enemyPlayerData);
            stage.show();
            control.initialize();
        } else {
            warning.setText("wait until both players have selected a character");
        }
    }
}
