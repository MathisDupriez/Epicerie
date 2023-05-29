package boucherie.vueclient.vueclient;

import boucherie.common.commonressource.Modele.*;
import boucherie.common.commonressource.Network.SocketObject;
import boucherie.common.commonressource.View.VueTraiteurPart;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.Socket;

public class VueClientApplication extends Application {
    VueTraiteurPart vueTraiteurPart = new VueTraiteurPart();
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(VueClientApplication.class.getResource("VueClient.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 276, 500);
        stage.setMinWidth(276);
        stage.setMinHeight(400);
        stage.setTitle("Vue Client");
        stage.setScene(scene);
        stage.show();
        VueClientController vueClientController = fxmlLoader.getController();
        SocketObject socketToServer;
        try
        {
            Socket socket = new Socket("localhost", 8888);
            socketToServer = new SocketObject(socket);
            Thread thread = new Thread(() -> {
                while (true){
                    try {
                        Bag bag = new Bag();
                        bag = socketToServer.read();
                        Bag finalBag = bag;
                        Platform.runLater(() -> {
                            vueClientController.Clear();
                            for (Article article : finalBag.getBag()) {
                                if (article != null) {
                                    vueClientController.AjouterArticle(article);
                                    vueClientController.setPrixTotal(finalBag.getTotalPrice());
                                }
                            }
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
            vueClientController.ServeurDeconnecte();
        }







    }

    public static void main(String[] args) {
        launch();
    }
}