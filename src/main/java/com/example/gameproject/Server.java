package com.example.gameproject;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Objects;

public class Server {
    private int thePort = 0;
    private int threadID = 0;
    private String theIPAddress = null;
    private ServerSocket serverSocket = null;
    private ArrayList<ServerThread> threadList = new ArrayList<>();
    public ArrayList<Integer> transferableThreadList = new ArrayList<>();

    public Server() {

        try {

            this.serverSocket = new ServerSocket(9994);
        } catch (Exception e) {
            System.out.println(e);
            System.exit(1);
        }
    }

    public void createNewThread(Socket ServiceSocket, int id) {
        try {
            ServerThread newInstance = new ServerThread(ServiceSocket, id);
            newInstance.start();
            threadList.add(newInstance);
            ObjectOutputStream data = new ObjectOutputStream(ServiceSocket.getOutputStream());
            data.writeObject(new Request(null,null,null,id,null));
        } catch (Exception e) {
            System.out.println(e+"creation");
            System.exit(1);
        }
    }
    public void executeServiceLoop() {
        try {
            while (true) {
                Socket ServiceSocket = serverSocket.accept();
                ObjectInputStream request = new ObjectInputStream(ServiceSocket.getInputStream());
                Request requestObj = (Request) request.readObject();
                if(requestObj.getMessage()!= null) {
                    System.out.println("Server: "+requestObj.getMessage());
                    if (Objects.equals(requestObj.getMessage(), "Thread List")) {
                        transferableThreadList.clear();
                        for (ServerThread serverThread : threadList) {
                            if (serverThread.isAlive()) {
                                transferableThreadList.add(serverThread.getThreadID());
                            }
                        }
                        ObjectOutputStream outcome = new ObjectOutputStream(ServiceSocket.getOutputStream());
                        outcome.writeObject(transferableThreadList);
                    } else if(Objects.equals(requestObj.getMessage(), "New Game")) {
                        createNewThread(ServiceSocket, threadID);
                        System.out.println("Server: game created with threadID "+threadID);
                        threadID++;
                    } else if (Objects.equals(requestObj.getMessage(), "Join Game")) {
                        for (ServerThread serverThread : threadList) {
                            if (Objects.equals(serverThread.getThreadID(), requestObj.getThreadID())) {
                                serverThread.setServiceSocketJoined(ServiceSocket);
                            }
                        }
                    } else if (Objects.equals(requestObj.getMessage(), "End Game")) {
                        for (ServerThread serverThread : threadList) {
                            if (Objects.equals(serverThread.getThreadID(), requestObj.getThreadID())) {
                                serverThread.endThread();
                            }
                        }
                    } else if (Objects.equals(requestObj.getMessage(), "Start Game")) {
                        for (ServerThread serverThread : threadList) {
                            if (Objects.equals(serverThread.getThreadID(), requestObj.getThreadID())) {
                                serverThread.setGameArena(requestObj.getArena());
                            }
                        }
                    } else if (Objects.equals(requestObj.getMessage(), "Start Game For Joined")) {
                        for (ServerThread serverThread : threadList) {
                            if (Objects.equals(serverThread.getThreadID(), requestObj.getThreadID())) {
                                serverThread.setServiceSocketHost(ServiceSocket, requestObj);
                            }
                        }
                    } else if (Objects.equals(requestObj.getMessage(), "Get Arena")) {
                        for (ServerThread serverThread : threadList) {
                            if (Objects.equals(serverThread.getThreadID(), requestObj.getThreadID())) {
                                ObjectOutputStream outcome = new ObjectOutputStream(ServiceSocket.getOutputStream());
                                outcome.writeObject(serverThread.getGameArena());
                            }
                        }
                    }
                } else {
                    for (ServerThread serverThread : threadList) {
                        if (Objects.equals(serverThread.getThreadID(), requestObj.getThreadID())) {
                            if(Objects.equals(requestObj.getType(), "Host")) {
                                serverThread.setServiceSocketHost(ServiceSocket, requestObj);
                            } else {
                                serverThread.setServiceSocketJoined(ServiceSocket, requestObj);
                            }
                        }
                    }
                }
                request.close();
            }
        } catch (Exception e) {
            System.out.println(e+"not creation");
        }
    }

    public static void main(String[] args) {
        Server server = new Server();
        System.out.println("Server: Server is up and running!");
        server.executeServiceLoop();
        System.exit(0);
    }
}
