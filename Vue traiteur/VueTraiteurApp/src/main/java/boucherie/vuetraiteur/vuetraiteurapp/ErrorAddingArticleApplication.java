package boucherie.vuetraiteur.vuetraiteurapp;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class ErrorAddingArticleApplication extends Application
{
    FXMLLoader ErrorLoarder;
    Stage Error = new Stage();
    @Override
    public void start(Stage Error) throws Exception {

        ErrorLoarder = new FXMLLoader(boucherie.vuetraiteur.vuetraiteurapp.BalanceApplication.class.getResource("Error.fxml"));
        Scene BalanceScene = new Scene(ErrorLoarder.load(), 450, 210);
        Error.setMaxWidth(500);
        Error.setMaxHeight(350);
        Error.setMinWidth(430);
        Error.setMinHeight(250);
        Error.setTitle("Erreur");
        Error.setScene(BalanceScene);
        this.Error = Error;
    }

    public void show() {
        Error.show();
    }
}
