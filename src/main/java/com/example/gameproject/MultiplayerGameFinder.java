package com.example.gameproject;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ListView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.net.URISyntaxException;
import java.util.ArrayList;

public class MultiplayerGameFinder {
    public VBox GameFinderMainBody;
    public ListView<Integer> GameFinderGamesView;
    public HBox GameFinderOptionsBar;
    private Stage stage;
    private Scene scene;
    private Socket clientSocket;
    private ArrayList<Integer> threadList = new ArrayList<>();
    public void initialize() throws IOException, ClassNotFoundException {
        sharedController controller = sharedController.getInstance();
        controller.dynamicDisplay(GameFinderMainBody, stage);
        if(!(scene==null)){
            clientSocket = new Socket("127.0.0.1", 9994);
            GameFinderGamesView.setMinSize(scene.getWidth()*0.6,scene.getHeight()*0.7);
            GameFinderGamesView.setMaxSize(scene.getWidth()*0.6,scene.getHeight()*0.7);
            ObjectOutputStream request = new ObjectOutputStream(clientSocket.getOutputStream());
            request.writeObject(new Request(null,null,"Thread List",null));
            ObjectInputStream requestOutcome = new ObjectInputStream(clientSocket.getInputStream());
            threadList = (ArrayList<Integer>) requestOutcome.readObject();
            GameFinderGamesView.getItems().addAll(threadList);
        }
    }
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
    public void setStage(Stage stage){
        this.stage = stage;
    }
    public void setScene(Scene scene){
        this.scene = scene;
    }

    public void createGame() throws IOException, ClassNotFoundException {
        FXMLLoader fxmlLoader = new FXMLLoader(startGame.class.getResource("Fxml/MultiplayerGameSelect.fxml"));
        Parent root = fxmlLoader.load();
        MultiplayerGameSelect controller = fxmlLoader.getController();
        stage.getScene().setRoot(root);
        controller.setStage(stage);
        controller.setScene(scene);
        stage.show();
        controller.initialize();
        controller.createGame();
    }

    public void joinGame() throws IOException, ClassNotFoundException {
        FXMLLoader fxmlLoader = new FXMLLoader(startGame.class.getResource("Fxml/MultiplayerGameSelect.fxml"));
        Parent root = fxmlLoader.load();
        MultiplayerGameSelect controller = fxmlLoader.getController();
        stage.getScene().setRoot(root);
        controller.setStage(stage);
        controller.setScene(scene);
        stage.show();
        controller.initialize();
        controller.joinGame(GameFinderGamesView.getSelectionModel().getSelectedItem());
    }

    public void refreshList() throws IOException, ClassNotFoundException {
        System.out.println("Refreshing");
        clientSocket = new Socket("127.0.0.1", 9994);
        GameFinderGamesView.getItems().clear();
        ObjectOutputStream request = new ObjectOutputStream(clientSocket.getOutputStream());
        request.writeObject(new Request(null,null,"Thread List",null));
        ObjectInputStream requestOutcome = new ObjectInputStream(clientSocket.getInputStream());
        threadList = (ArrayList<Integer>) requestOutcome.readObject();
        System.out.println(threadList);
        GameFinderGamesView.getItems().addAll(threadList);
    }
}
