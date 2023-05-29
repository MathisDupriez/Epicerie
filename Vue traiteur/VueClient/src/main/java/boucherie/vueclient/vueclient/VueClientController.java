package boucherie.vueclient.vueclient;

import boucherie.common.commonressource.Modele.Article;
import boucherie.common.commonressource.View.VueTraiteurPart;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;

import java.text.DecimalFormat;

public class VueClientController {

    public Label labelPrixTotal;
    public VBox VboxPanier;
    public Label LabelCommande;
    VueTraiteurPart vueTraiteurPart = new VueTraiteurPart();
    public void AjouterArticle(Article article){
        VboxPanier.getChildren().add(vueTraiteurPart.createBagArticle(article));
    }
    public void setPrixTotal(double prixTotal){
        DecimalFormat decimalFormat = new DecimalFormat("#.###");
        labelPrixTotal.setText(decimalFormat.format(prixTotal)+"€");
    }
    public void Clear(){
        VboxPanier.getChildren().clear();
        setPrixTotal(0);
    }
    public void ServeurDeconnecte(){
        LabelCommande.setText("Serveur déconnecté veuillez relancer l'application");
        LabelCommande.setStyle("-fx-text-fill: red");
    }

}