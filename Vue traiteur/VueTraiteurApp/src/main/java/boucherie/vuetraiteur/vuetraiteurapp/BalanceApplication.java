package boucherie.vuetraiteur.vuetraiteurapp;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import static javafx.application.Application.launch;


public class BalanceApplication extends Application {

    FXMLLoader Balanceloarder;
    @Override
    public void start(Stage Balance) throws Exception {
        Balanceloarder = new FXMLLoader(BalanceApplication.class.getResource("BalanceTraiteur.fxml"));
        Scene BalanceScene = new Scene(Balanceloarder.load(), 300, 200);
        Balance.setTitle("Balance");
        Balance.setMinWidth(300);
        Balance.setMinHeight(300);
        Balance.setScene(BalanceScene);
        Balance.show();
    }
    public BalanceControleur getController (){
        return Balanceloarder.getController();
    }

    public static void main(String[] args) {
        launch();
    }
}
