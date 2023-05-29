package boucherie.tpe.tpe;

import boucherie.common.commonressource.Modele.Article;
import boucherie.common.commonressource.Modele.Bag;
import boucherie.common.commonressource.Network.SocketObject;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.Socket;

public class TPE extends Application {
    double totalPrice = 0;
    SocketObject finalSocketToServerBag;
    SocketObject finalSocketToServerTPE;
    String mode = "En attente de paiement ...";
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader TPELoader = new FXMLLoader(TPE.class.getResource("TPE.fxml"));
        Scene scene = new Scene(TPELoader.load(), 450, 300);
        stage.setResizable(false);
        stage.setTitle("TPE");
        stage.setScene(scene);
        stage.show();

        TPEcontroller controller = TPELoader.getController();
        controller.SetDefaultMode(mode);
        controller.setListener(new TPEcontroller.TPElistener() {
            @Override
            public void onButtonOuiClicked() {
                try {
                    controller.SetDefaultMode(mode);
                    finalSocketToServerTPE.write(true);

                }catch (Exception e){
                    System.out.println(e.getMessage());
                    controller.SetDefaultMode("Erreur de connexion au serveur, relancez le serveur et le client");
                }

            }

            @Override
            public void onButtonNonClicked() {
                try {
                    controller.SetDefaultMode(mode);
                    finalSocketToServerTPE.write(false);

                }catch (Exception e){
                    System.out.println(e.getMessage());
                    controller.SetDefaultMode("Erreur de connexion au serveur, relancez le serveur et le client");
                }
            }
        });
        SocketObject socketToServer;
        try {
            Socket socket = new Socket("localhost", 4445);
            socketToServer = new SocketObject(socket);
            finalSocketToServerTPE = socketToServer;
            Thread thread = new Thread(() -> {
                while (true) {
                    try {
                        Boolean Confirmation = null;
                        Confirmation = finalSocketToServerTPE.read();
                        Boolean finalConfirmation = Confirmation;
                        if (finalConfirmation != null) {
                            Platform.runLater(() -> {
                                controller.setConfirmation(finalConfirmation,totalPrice);
                            });
                        }

                    } catch (Exception e) {
                        System.out.println(e.getMessage());
                    }
                }
            });

            thread.setDaemon(true);
            thread.start();
        } catch (Exception e) {
            System.out.println(e.getMessage());
            System.out.println("Erreur de connexion au serveur, relancez le serveur et le client");
            controller.ServeurDeconnecte();
        }
        try
        {
            Socket socket = new Socket("localhost", 8888);
            socketToServer = new SocketObject(socket);
            finalSocketToServerBag = socketToServer;
            Thread thread = new Thread(() -> {
                while (true){
                    try {
                        Bag bag = new Bag();
                        bag = finalSocketToServerBag.read();
                        Bag finalBag = bag;
                        Platform.runLater(() -> {
                            totalPrice = finalBag.getTotalPrice();
                        });

                    }catch (Exception e){
                        System.out.println(e.getMessage());
                    }
                }
            });

            thread.setDaemon(true);
            thread.start();
        }
        catch (Exception e){
            System.out.println(e.getMessage());
            System.out.println("Erreur de connexion au serveur, relancez le serveur et le client");
            controller.ServeurDeconnecte();
        }

    }
}