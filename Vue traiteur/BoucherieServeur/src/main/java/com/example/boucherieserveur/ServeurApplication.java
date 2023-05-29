package com.example.boucherieserveur;

import boucherie.common.commonressource.Modele.Article;
import boucherie.common.commonressource.Modele.Bag;
import javafx.application.Application;
import javafx.stage.Stage;
import boucherie.common.commonressource.Network.SocketObject;
import java.io.IOException;

import java.io.*;
import java.net.ServerSocket;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class ServeurApplication extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        startServer();
    }

    private List<SocketObject> clients = new ArrayList<>();
    private Bag bag = new Bag();

    public void startServer() {
        try {


            ServerSocket serverSocket = new ServerSocket(8888);
            while(true) {
                System.out.println("En attente d'un client ...");
                SocketObject socketObject = new SocketObject(serverSocket.accept());
                clients.add(socketObject);
                Thread thread = new Thread(() -> {
                    try {
                        socketObject.write(bag);
                        while (true) {
                            Bag BagToSend = null;
                            System.out.println("En attente d'un panier...");
                            BagToSend = socketObject.read();
                            System.out.println("Panier reçu : " + BagToSend.getTotalPrice());
                            for (Article article : BagToSend.getBag()) {
                                if (article!=null){
                                    System.out.println(article.getName());
                                    System.out.println(article.getQuantity());
                                    System.out.println(article.getPrice());
                                }
                            }
                            this.broadcast(BagToSend);
                        }

                    } catch (IOException e) {
                        System.err.println("Erreur : " + e.getMessage());
                        clients.remove(socketObject); // Supprimer le client de la liste en cas d'erreur
                    } catch (ClassNotFoundException e) {
                        System.err.println("Classe non trouvée : " + e.getMessage());
                        clients.remove(socketObject); // Supprimer le client de la liste en cas d'erreur
                    }
                });
                thread.start();
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }



    private void broadcast(Object object){
        synchronized(clients) {
            List<SocketObject> copyClients = new ArrayList<>(clients); // pour bien gérer le remove
            for (SocketObject client : copyClients) {
                try {
                    client.write(object);
                } catch (IOException e) {
                    clients.remove(client);
                }
            }
        }
    }
}