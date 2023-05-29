package boucherie.vuetraiteur.vuetraiteurapp;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class AjouterArticleApplication extends Application {
    Stage stage = new Stage();
    FXMLLoader VueTraiteurloarder;
    @Override
    public void start(Stage stage) throws Exception{
        this.stage = stage;
        VueTraiteurloarder = new FXMLLoader(AjouterArticleApplication.class.getResource("AjouterArticle.fxml"));
        Scene VueTraiteurScene = new Scene(VueTraiteurloarder.load(), 510, 180);
        this.stage.setTitle("Ajouter un article");
        this.stage.setMinWidth(510);
        this.stage.setMinHeight(180);
        this.stage.setScene(VueTraiteurScene);

    }
    public AjouterArticleController getController (){
        return VueTraiteurloarder.getController();
    }
    public static void main(String[] args) {
        launch();
    }
    public void hide(){
        stage.hide();
    }
    public void show(){
        stage.show();
    }

}
