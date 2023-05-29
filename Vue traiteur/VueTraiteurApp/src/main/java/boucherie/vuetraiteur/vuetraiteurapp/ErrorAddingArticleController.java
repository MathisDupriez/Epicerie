package boucherie.vuetraiteur.vuetraiteurapp;

import javafx.fxml.Initializable;
import javafx.scene.control.Button;

import java.net.URL;
import java.util.ResourceBundle;

public class ErrorAddingArticleController implements Initializable {


    public Button ButtonOk;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        ButtonOk.setOnMouseClicked(MouseEvent -> ButtonOk.getScene().getWindow().hide());
    }
}
