package boucherie.common.commonressource.View;

import boucherie.common.commonressource.Modele.*;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.ContentDisplay;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.Text;

public class VueTraiteurPart {
    public VueTraiteurPart()
    {

    }
    public Button createArticle(Article Monarticle)
    {// Création du ArticleName pour les deux lignes de texte
        Label ArticleName = new Label(Monarticle.getName());
        Label ArticlePrice;
        ArticleName.setAlignment(Pos.BOTTOM_CENTER);
        if(Monarticle.isPerKg())
        {
            ArticlePrice = new Label(Monarticle.getArticlePrice()+"€/Kg");
        }
        else
        {
            ArticlePrice = new Label(Monarticle.getArticlePrice()+"€/p");
        }

        ArticlePrice.setAlignment(Pos.BOTTOM_CENTER);

        // Création de l'ImageView
        ImageView imageView = new ImageView();
        imageView.setFitHeight(116);
        Text tempText = new Text(Monarticle.getName());
        tempText.setFont(ArticleName.getFont());
        double width = tempText.getLayoutBounds().getWidth();


        imageView.setFitWidth(103);
        imageView.setPickOnBounds(true);
        imageView.setSmooth(false);

        Image image = Monarticle.getPicture();
        imageView.setImage(image);

        // Création du VBox pour empiler l'image et le ArticleName
        VBox vbox = new VBox(imageView, ArticleName, ArticlePrice);
        vbox.setAlignment(Pos.CENTER);

        // Création du bouton
        Button button = new Button();
        button.setPrefHeight(150);
        button.setMinWidth(width + 4); // Largeur du label + 2 pixels de chaque côté
        button.setMaxWidth(Region.USE_PREF_SIZE);
        button.setPadding(new Insets(0, 5, 0, 5)); // 2 pixels de padding à gauche et à droite
        button.setContentDisplay(ContentDisplay.TOP);
        button.setGraphic(vbox);

        // Configuration du bouton
        button.setId(Monarticle.getName());
        System.out.println(Monarticle.isPerKg());
        button.setUserData(Monarticle);
        System.out.println(((Article) button.getUserData()).isPerKg());
        return button;
    }
    public BorderPane createBagArticle(Article item){
        BorderPane borderPane = new BorderPane();
        borderPane.setMaxHeight(Double.MAX_VALUE);
        borderPane.setMaxWidth(Double.NEGATIVE_INFINITY);
        borderPane.setMinHeight(Double.NEGATIVE_INFINITY);
        borderPane.setMinWidth(Double.NEGATIVE_INFINITY);
        borderPane.setPrefHeight(40.0);
        borderPane.setPrefWidth(280.0);
        borderPane.setMaxWidth(Double.MAX_VALUE);

        BorderPane innerBorderPane = new BorderPane();
        innerBorderPane.setMinHeight(25.0);
        innerBorderPane.setPrefHeight(25.0);
        innerBorderPane.setPrefWidth(280.0);
        BorderPane.setAlignment(innerBorderPane, Pos.CENTER);

        FlowPane topFlowPane = new FlowPane();
        topFlowPane.setAlignment(Pos.CENTER_LEFT);
        topFlowPane.setPrefHeight(0.0);
        topFlowPane.setPrefWidth(427.0);
        BorderPane.setAlignment(topFlowPane, Pos.CENTER);

        Label labelNomProduit = new Label(item.getName());
        labelNomProduit.setPrefHeight(17.0);
        labelNomProduit.setPrefWidth(195.0);
        labelNomProduit.setPadding(new Insets(0, 0, 0, 5.0));

        topFlowPane.getChildren().add(labelNomProduit);
        innerBorderPane.setTop(topFlowPane);

        FlowPane bottomFlowPane = new FlowPane();
        bottomFlowPane.setAlignment(Pos.CENTER_LEFT);
        bottomFlowPane.setPrefHeight(0.0);
        bottomFlowPane.setPrefWidth(427.0);
        BorderPane.setAlignment(bottomFlowPane, Pos.CENTER);

        Label labelPrixPoid = new Label();
        if(item.isPerKg()){
            labelPrixPoid.setText(item.getQuantity() + " kg à " + item.getArticlePrice() + "€");
        }
        else{
            labelPrixPoid.setText(item.getQuantity() + " p à " + item.getArticlePrice() + "€");
        }

        labelPrixPoid.setPrefHeight(17.0);
        labelPrixPoid.setPrefWidth(158.0);
        labelPrixPoid.setPadding(new Insets(0, 0, 0, 5.0));


        bottomFlowPane.getChildren().add(labelPrixPoid);
        innerBorderPane.setBottom(bottomFlowPane);

        borderPane.setLeft(innerBorderPane);

        FlowPane rightFlowPane = new FlowPane();
        rightFlowPane.setAlignment(Pos.CENTER_RIGHT);
        rightFlowPane.setPrefHeight(200.0);
        rightFlowPane.setPrefWidth(200.0);
        BorderPane.setAlignment(rightFlowPane, Pos.CENTER);

        Label labelPrix = new Label(item.getPrice() + "");
        labelPrix.setAlignment(Pos.CENTER_RIGHT);
        labelPrix.setContentDisplay(ContentDisplay.RIGHT);
        labelPrix.setPrefHeight(28.0);
        labelPrix.setPrefWidth(83.0);
        labelPrix.setFont(new Font(19.0));
        labelPrix.setPadding(new Insets(0, 5.0, 0, 0));
        labelPrix.setText(item.getPrice() + "");
        rightFlowPane.getChildren().add(labelPrix);
        borderPane.setRight(rightFlowPane);
        return borderPane;
    }

    public Button CreateAddArticleButton(Section section, FlowPane flowPane){
        Button buttonAjouterArticle= new Button("Ajouter un Article");
        Label labelPlus = new Label("+");
        labelPlus.setAlignment(Pos.CENTER);
        labelPlus.setFont(new Font(96.0));
        buttonAjouterArticle.setGraphic(labelPlus);
        buttonAjouterArticle.setAlignment(Pos.BOTTOM_CENTER);
        buttonAjouterArticle.setContentDisplay(ContentDisplay.TOP);
        buttonAjouterArticle.setMinHeight(Region.USE_PREF_SIZE);
        buttonAjouterArticle.setMinWidth(Region.USE_PREF_SIZE);
        buttonAjouterArticle.setMnemonicParsing(false);
        buttonAjouterArticle.setPrefHeight(179.0);
        buttonAjouterArticle.setPrefWidth(119.0);
        buttonAjouterArticle.setSnapToPixel(false);
        buttonAjouterArticle.setUserData(flowPane);
        return buttonAjouterArticle;
    }
}
