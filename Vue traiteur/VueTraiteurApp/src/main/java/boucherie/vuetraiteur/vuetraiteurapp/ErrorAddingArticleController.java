package boucherie.vuetraiteur.vuetraiteurapp;

import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.FlowPane;

import java.net.URL;
import java.util.ResourceBundle;

public class ErrorAddingArticleController implements Initializable {


    public Button ButtonOk;
    public FlowPane Image;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        ButtonOk.setOnMouseClicked(MouseEvent -> ButtonOk.getScene().getWindow().hide());
        Image image = new Image("C:\\Users\\Dupriez Mathis\\ProjetJava\\Boucherie_Controle_commande\\Vue traiteur\\VueTraiteurApp\\src\\main\\java\\boucherie\\vuetraiteur\\vuetraiteurapp\\Image\\Erreur.jpg");

    }
}
