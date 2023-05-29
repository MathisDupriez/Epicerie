package boucherie.vuetraiteur.vuetraiteurapp;

import javafx.fxml.Initializable;
import javafx.scene.control.*;

import java.net.URL;
import java.util.ResourceBundle;

public class AjouterArticleController implements Initializable {


    public TextField TextFieldName;
    public TextField TextFieldPrice;
    public RadioButton RadioKilo;
    public RadioButton RadioPiece;
    public Button ButtonAdd;
    public Button ButtonCancel;
    public CheckBox AddSection;
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle)
    {
        ButtonAdd.setOnAction(actionEvent -> {onAddArticle();});
        ButtonCancel.setOnAction(actionEvent -> {onCancel();});
        AddSection.setOnAction(actionEvent -> {AddSection();});
    }
    public AfficherArticleControllerListener listener;
    public void setListener(AfficherArticleControllerListener listener) {
        this.listener = listener;
    }
    public void AddSection(){
        if (listener!=null){
            listener.AddSection();
        }
    }
    private void onAddArticle() {
        if(listener != null)
        {
            listener.onAddArticle();
        }
    }
    private void onCancel() {
        if(listener != null)
        {
            listener.onCancel();
        }
    }
    public Boolean TextFieldIsDouble(){
        try{
            Double.parseDouble(TextFieldPrice.getText());
            return true;
        }catch (NumberFormatException e){
            return false;
        }
    }
    public String getArticleName(){
        return TextFieldName.getText();
    }
    public String getArticlePrice(){

        System.out.println(TextFieldPrice.getText());return TextFieldPrice.getText();
    }
    public boolean isPerKilo(){
        return RadioKilo.isSelected();
    }
    public boolean getValidation(){
            boolean isRadioButtonSelected = RadioKilo.isSelected() || RadioPiece.isSelected();
            boolean areTextFieldsFilled = !TextFieldName.getText().isEmpty() && !TextFieldPrice.getText().isEmpty();
            return isRadioButtonSelected && areTextFieldsFilled;
    }
    public boolean GetAddSectionSatut(){
        return AddSection.isSelected();
    }

    public void resetUI(){
        TextFieldPrice.setDisable(false);
        RadioKilo.setDisable(false);
        RadioPiece.setDisable(false);
        TextFieldName.setText("");
        TextFieldPrice.setText("");
        RadioKilo.setSelected(false);
        RadioPiece.setSelected(false);
        AddSection.setSelected(false);
    }
    public void SetUIForAddSection(){
        TextFieldPrice.setDisable(true);
        RadioKilo.setDisable(true);
        RadioPiece.setDisable(true);
    }
    public interface AfficherArticleControllerListener{
        void onAddArticle();
        void onCancel();
        void AddSection();
    }
}
