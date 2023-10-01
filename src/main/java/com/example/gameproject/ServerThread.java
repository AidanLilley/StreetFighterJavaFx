package com.example.gameproject;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.net.Socket;

public class ServerThread extends Thread implements Serializable {
    public int threadID;
    public Socket serviceSocketHost = null;
    public volatile Socket serviceSocketJoined = null;
    public Request hostData = new Request(null,null,null,null);
    public Request joinedData = new Request(null,null,null,null);
    private boolean exit = false;
    private Arenas gameArena;
    public ServerThread(Socket aSocket, int id) {
        this.serviceSocketHost = aSocket;
        this.threadID = id;
    }
    public void setGameArena(Arenas arena) {
        gameArena = arena;
    }
    public Arenas getGameArena() {
        return gameArena;
    }
    public void setServiceSocketJoined(Socket aSocket, Request request) throws IOException, ClassNotFoundException {
        this.serviceSocketJoined = aSocket;
        joinedData = request;
        ObjectOutputStream dataToJoined = new ObjectOutputStream(serviceSocketJoined.getOutputStream());
        dataToJoined.writeObject(hostData);
        System.out.println("data sent to joined");
    }
    public void setServiceSocketJoined(Socket aSocket) {
        this.serviceSocketJoined = aSocket;
    }
    public void setServiceSocketHost(Socket aSocket, Request request) throws IOException, ClassNotFoundException {
        this.serviceSocketHost = aSocket;
        hostData = request;
        ObjectOutputStream dataToHost = new ObjectOutputStream(serviceSocketHost.getOutputStream());
        dataToHost.writeObject(joinedData);
        System.out.println("data sent to host");
    }
    public void endThread() {
        exit = true;
    }

    public void handleRequests() {
        try {

        } catch (Exception e) {
            System.out.println("Service thread " + this.getId() + ": " + e+"handling a request");
        }
    }

    public void run() {
        try {
            System.out.println("Thread "+threadID+": Waiting for second player!");
            while ((serviceSocketJoined == null)&&(!exit)) {
                Thread.onSpinWait();
            }
            System.out.println("Thread "+threadID+": Player Joined, Sending Data!");
            while(!exit) {
                handleRequests();
            }
        } catch (Exception e) {
            System.out.println("Service thread " + this.getId() + ": " + e+"run method");
            System.exit(1);
        }
    }
    public int getThreadID() {
        return threadID;
    }
}
