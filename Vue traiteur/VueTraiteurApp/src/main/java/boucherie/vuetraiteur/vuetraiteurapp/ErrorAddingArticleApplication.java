package boucherie.vuetraiteur.vuetraiteurapp;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class ErrorAddingArticleApplication extends Application
{
    FXMLLoader ErrorLoarder;
    @Override
    public void start(Stage Error) throws Exception {
        ErrorLoarder = new FXMLLoader(boucherie.vuetraiteur.vuetraiteurapp.BalanceApplication.class.getResource("Error.fxml"));
        Scene BalanceScene = new Scene(ErrorLoarder.load(), 450, 190);
        Error.setTitle("Erreur");
        Error.setScene(BalanceScene);
        Error.show();
    }

}
