package boucherie.vuetraiteur.vuetraiteurapp;


import boucherie.common.commonressource.View.VueTraiteurPart;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.VBox;
import boucherie.common.commonressource.Modele.*;

import java.net.URL;
import java.text.DecimalFormat;
import java.util.ResourceBundle;

public class VueTraiteurController implements Initializable {
    public ScrollPane ScrollpaneVboxScetion;
    public VBox VboxPanier;
    public Button buttonAjouter;
    public Button buttonEncaisser;
    public Label labelPrixTotal;
    public Label labelPrixArticle;
    public Label labelPoid;
    public Label labelDernierArticle;
    public FlowPane FlowFruitEtLégume;
    public VBox VboxSection;


    private Article SelectedArticle;
    private Section SelectedSection;
    private Boolean ispressed = false;
    private double prixTotal = 0;


    private VueTraiteurPart vueTraiteurPart = new VueTraiteurPart();
    private Vuetraiterlistener listener ;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle)
    {
        ScrollpaneVboxScetion.setFitToHeight(true);
        ScrollpaneVboxScetion.setVbarPolicy(ScrollPane.ScrollBarPolicy.ALWAYS);
        ScrollpaneVboxScetion.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
        buttonAjouter.setOnMouseClicked(MouseEvent -> { ajouterAuPanier();});
        buttonEncaisser.setOnMouseClicked(MouseEvent -> {Encaisser();});
    }


    //This part of the code concern Adding of article in the bag
    public void ajouterAuPanier ()
    {
        if(listener != null)
        {
            listener.ajouterAuPanier();
        }
    }
    public void ViewAjouterAuPanier(Article article){

        BorderPane borderPane = vueTraiteurPart.createBagArticle(article);
        borderPane.setUserData(article);
        borderPane.setOnMouseClicked(MouseEvent -> {delArticle(borderPane);});
        VboxPanier.getChildren().add(borderPane);
        prixTotal+=article.getPrice();

        DecimalFormat decimalFormat = new DecimalFormat("#.###");
        labelPrixTotal.setText(decimalFormat.format(prixTotal)+"€");
    }
    //this part of code concern the supression of article in the bag
    public void delArticle(BorderPane borderPane){
        if(listener != null){
            DelthisArticle((Article) borderPane.getUserData());
            Article article = (Article) borderPane.getUserData();
            listener.delArticle(article);
        }
    }
    public void DelthisArticle(Article userData){
            BorderPane borderPaneToRemove = null;
            // Parcourir les enfants de la VBox pour trouver le BorderPane avec userData ARTICLE
            for (Node node : VboxPanier.getChildren()) {
                if (node instanceof BorderPane) {
                    Article nodeUserData = (Article) ((BorderPane) node).getUserData();

                    if (nodeUserData != null && nodeUserData.equals(userData)&& userData.getQuantity() ==nodeUserData.getQuantity()) {
                        borderPaneToRemove = (BorderPane) node;
                        System.out.println(nodeUserData.getName());
                        System.out.println(userData.getName());
                        System.out.println(nodeUserData.getQuantity());
                        break;
                    }
                }
            }
        // Supprimer le BorderPane de la VBox
        if (borderPaneToRemove != null) {
            VboxPanier.getChildren().remove(borderPaneToRemove);
        }
    }
    //this part of code if for the "encaisserButton"
    public void ViewEncaisser()
    {
        VboxPanier.getChildren().clear();
        labelPrixTotal.setText("0€");
    }
    public void Encaisser(){

        if(listener != null)
        {
            listener.Encaisser();
        }
    }
    //this part of code is for the "AjouterArticleButton" that add article to a section
    public void ajouterArticle(Section section)
    {
        if(listener != null)
        {
            setSelecteSection(section);
            listener.ajouterArticle();
        }
    }
    public void setSelecteSection(Section section)
    {
        SelectedSection = section;
    }
    public void addSection(Section section){

        FlowPane flowPane = new FlowPane();
        flowPane.setUserData(section.getName());
        System.out.println(section.getName());
        Button ButtonAjouterArticleSection = vueTraiteurPart.CreateAddArticleButton(section,flowPane);
        ButtonAjouterArticleSection.setUserData(section.getName());
        FlowPane.setMargin(ButtonAjouterArticleSection, new Insets(5.0));
        ButtonAjouterArticleSection.setOnMouseClicked(MouseEvent->{ajouterArticle(section);});
        flowPane.getChildren().add(ButtonAjouterArticleSection);
        TitledPane titledPane = new TitledPane(section.getName(),flowPane);
        VboxSection.getChildren().add(titledPane);

    }
    public void ViewAjouterArticle(Article article)
    {
        for (Node titledPane : VboxSection.getChildren()) {
            if (titledPane instanceof TitledPane&& SelectedSection!= null) {
                Object nodeUserData = ((TitledPane) titledPane).getContent().getUserData();
                System.out.println(nodeUserData);
                if (nodeUserData != null && nodeUserData.equals(SelectedSection.getName())) {
                    Button buttonArticle = vueTraiteurPart.createArticle(article);
                    buttonArticle.setOnMouseClicked(Event -> {articleClicked(buttonArticle);});
                    ((FlowPane) ((TitledPane) titledPane).getContent()).getChildren().add(0,buttonArticle);
                    break;
                }
            }
        }
    }

    public Section getSelectedSection() {
        return SelectedSection;
    }
    public Article getSelectedArticle() {
        return SelectedArticle;
    }

    public void SetSelecedArticle(Article article)
    {
        SelectedArticle = article;
    }


    //this part of code if for what is done when an Article is clicked
    public void articleClicked(Button buttonArticle){
        if(listener != null)
        {
            SetSelecedArticle((Article) buttonArticle.getUserData());
            listener.articleClicked();

        }
    }
    public void ViewArticleClicked(Article article)
    {

        if(article.isPerKg()){
            labelPoid.setText(article.getQuantity() + " kg");
        }
        else{
            labelPoid.setText(article.getQuantity() + " p");
        }
        labelDernierArticle.setText(article.getName());
        labelPrixArticle.setText(article.getArticlePrice()+ "€");

        ispressed = true;
    }
    public void resetStatut(){
        labelPoid.setText("0");
        labelPrixArticle.setText("");
        ispressed = false;
    }
    public void changePoid(String poid)
    {
        labelPoid.setText(poid);
    }
    public void resetTotalPrice()
    {
        prixTotal = 0;
        labelPrixTotal.setText("0€");
    }





    //this part of code is for the Listener of the view
    public interface Vuetraiterlistener
    {
        void ajouterAuPanier();
        void Encaisser();
        void ajouterArticle();
        void articleClicked();
        void delArticle(Article article);

    }
    public void setListener(Vuetraiterlistener listener)
    {
        this.listener = listener;
    }
}