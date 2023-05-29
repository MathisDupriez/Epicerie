package boucherie.tpe.tpe;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;

import java.net.URL;
import java.text.DecimalFormat;
import java.text.Format;
import java.util.ResourceBundle;

public class TPEcontroller implements Initializable {

    public Button ButtonOui;
    public Button ButtonNon;
    public Label LabelText;
    public Label LabelPrix;
    private TPElistener listener;
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        ButtonOui.setOnMouseClicked(MouseEvent -> listener.onButtonOuiClicked());
        ButtonNon.setOnMouseClicked(MouseEvent -> listener.onButtonNonClicked());
    }
    public void setListener(TPElistener listener) {
        this.listener = listener;
    }
    public void setLabelText(String text) {
        LabelText.setText(text);
    }
    public void setLabelPrix(String text) {
        LabelPrix.setText(text);
    }
    public void onButtonOuiClicked() {
        if(listener != null){
            listener.onButtonOuiClicked();
        }

    }
    public void onButtonNonClicked() {
        if(listener != null){
            listener.onButtonNonClicked();
        }
    }

    public void setConfirmation(Boolean finalConfirmation,double totalPrice) {
        if(finalConfirmation){
            ButtonOui.setVisible(true);
            ButtonNon.setVisible(true);
            setLabelText("Voulez vous confirmer le paiement de :");
            Format format = new DecimalFormat("#.##");
            setLabelPrix(format.format(totalPrice));
        }else{
            setLabelText("Voulez vous confirmer le paiement de :");
            setLabelPrix("");
            ButtonOui.setVisible(false);
            ButtonNon.setVisible(false);
        }
    }

    public void ServeurDeconnecte() {
        setLabelText("Serveur déconnecté");
        LabelText.setStyle("-fx-text-fill: red");
        setLabelPrix("");
        ButtonOui.setVisible(false);
        ButtonNon.setVisible(false);

    }
    public void SetDefaultMode(String text  ){
        setLabelText(text);
        LabelText.setStyle("-fx-text-fill: black");
        setLabelPrix("");
        ButtonOui.setVisible(false);
        ButtonNon.setVisible(false);
    }


    public interface TPElistener {
        void onButtonOuiClicked();
        void onButtonNonClicked();
    }
}