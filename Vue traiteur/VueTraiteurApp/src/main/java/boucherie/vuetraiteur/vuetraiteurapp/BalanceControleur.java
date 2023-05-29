package boucherie.vuetraiteur.vuetraiteurapp;

import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

import java.net.URL;
import java.util.ResourceBundle;

public class BalanceControleur implements Initializable {
    public Label labelbalance;
    public TextField TextfieldPoid;
    public VueBlancelistener listener;



    private void onBalanceChanged() {
        if(listener != null)
        {
            listener.onBalanceChanged();
        }
    }
    public String getBalance(){
        return TextfieldPoid.getText();
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
            TextfieldPoid.setOnKeyPressed(keyEvent -> {onBalanceChanged();});
        }

    public void setListener(VueBlancelistener listener) {
        this.listener = listener;
    }
    public interface VueBlancelistener{
        void onBalanceChanged();
    }

}
