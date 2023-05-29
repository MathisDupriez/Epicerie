package boucherie.vuetraiteur.vuetraiteurapp;

import boucherie.common.commonressource.Modele.*;
import boucherie.common.commonressource.Network.SocketObject;
import boucherie.vuetraiteur.vuetraiteurapp.DataBase.DataBaseController;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/// TODO: 20-05-23
/// 1. Faire l'event quand on clique sur un article du panier
/// 2. mettre une section dans chaque ButtonAjouterArticle
/// 3. ajouter un scroolPane autour de la Vbox du panier
/// 4 Commencer le Réseaux
public class VueTraiteurApplication extends Application  {
    //variable that concern the bag that will be sent in the network
    Bag bag = new Bag();
    //variable that concern the list of section that making the shop
    List<Section> Shop = new ArrayList<>();
    //variable that concern the used article in the code
    private final String defaultArticleImage = "C:\\Users\\Dupriez Mathis\\Desktop\\banane.jpg";
    private Article selectedArticle;
    private Article addedArticle;
    private double quantity=0;
    //variable that concern the VueTraiteur
    private VueTraiteurController vueTraiteurController;
    //variable that concern the DataBase
    private final DataBaseController dataBaseController = new DataBaseController();


    //variable that concern the AddArticle
    AjouterArticleApplication ajouterArticleApplication = new AjouterArticleApplication();
    AjouterArticleController ajouterArticleController;
    //variable that concern the Balance
    BalanceApplication balanceApplication = new BalanceApplication();
    //variable that concern the Network
    SocketObject socketToServer;
    @Override
    public void start(Stage stage) throws Exception {

        FXMLLoader VueTraiteurloarder = new FXMLLoader(VueTraiteurApplication.class.getResource("VuetraiteurControleur.fxml"));
        Scene scene = new Scene(VueTraiteurloarder.load(), 1000, 740);
        stage.setMinWidth(700);
        stage.setMinHeight(300);
        stage.setTitle("Vue Traiteur");
        stage.setScene(scene);
        stage.show();

        vueTraiteurController = VueTraiteurloarder.getController();
        vueTraiteurController.setListener(VueTraiteurSetListener());

        Initialization();

        BalanceControleur balanceControleur = balanceApplication.getController();
        balanceControleur.setListener(BalanceSetListener());
        ajouterArticleController = ajouterArticleApplication.getController();
        ajouterArticleController.setListener(AjouterArticleSetListener());

    }
    public static void main(String[] args) {
        launch();
    }
    public void Initialization() throws Exception {
        System.out.println("initialisation");

        ajouterArticleApplication.start(new Stage());

        balanceApplication.start(new Stage());
        System.out.println("connection au serveur");
        Socket socket = new Socket("localhost", 8888);
        socketToServer = new SocketObject(socket);
        System.out.println("connection au serveur terminé");

        Shop = dataBaseController.LoadDataBase();
        for (Section section : Shop) {
            if(!Objects.equals(section.getName(), "DataBaseController.java")){
                vueTraiteurController.addSection(section);
                for (Article article : section.section) {
                    vueTraiteurController.setSelecteSection(section);
                    vueTraiteurController.ViewAjouterArticle(article);
                }
            }
        }
        System.out.println("initialisation terminé");
    }
    public VueTraiteurController.Vuetraiterlistener VueTraiteurSetListener(){
        return new VueTraiteurController.Vuetraiterlistener()
            {
                @Override
                public void ajouterAuPanier() {
                if(selectedArticle != null){
                    bag.addArticle(selectedArticle);
                    try{
                        socketToServer.write(bag);
                    }catch (Exception e){
                        System.out.println(e.getMessage());
                    }

                    vueTraiteurController.ViewAjouterAuPanier(selectedArticle);
                    quantity=0;
                    vueTraiteurController.resetStatut();
                }
            }
                @Override
                public void Encaisser() {
                vueTraiteurController.ViewEncaisser();
                vueTraiteurController.resetTotalPrice();
                bag.clearBag();
                try{
                    socketToServer.write(bag);
                }catch (Exception e) {
                    System.out.println(e.getMessage());
                }
            }
                @Override
                public void ajouterArticle() {
                ajouterArticleApplication.show();
            }
                @Override
                public void articleClicked() {
                if(selectedArticle!= vueTraiteurController.getSelectedArticle()){
                    selectedArticle = vueTraiteurController.getSelectedArticle();
                    quantity=0;
                }
                if(!selectedArticle.isPerKg){quantity++;}
                selectedArticle.setQuantity(quantity);
                vueTraiteurController.ViewArticleClicked(selectedArticle);
            }
                @Override
                public void delArticle(Article article){
                bag.removeItem(article);
                try{
                    socketToServer.write(bag);
                }catch (Exception e){
                    System.out.println(e.getMessage());
                }
            }
        };
    }
    public BalanceControleur.VueBlancelistener BalanceSetListener(){
        return new BalanceControleur.VueBlancelistener()
        {
            @Override
            public void onBalanceChanged() {
                Platform.runLater(() -> {
                    if(selectedArticle.isPerKg) {
                        selectedArticle.setQuantity(Double.parseDouble(balanceApplication.getController().getBalance()) / 1000);
                        vueTraiteurController.changePoid(balanceApplication.getController().getBalance());
                    }
                });
            }
        };
    }
    public AjouterArticleController.AfficherArticleControllerListener AjouterArticleSetListener() {
        return new AjouterArticleController.AfficherArticleControllerListener() {
            @Override
            public void onAddArticle() {
                if (ajouterArticleController.GetAddSectionSatut()) {
                    Section addedSection = new Section(ajouterArticleController.getArticleName());
                    dataBaseController.AddSection(addedSection);
                    Shop.add(addedSection);
                    vueTraiteurController.addSection(addedSection);
                } else if (ajouterArticleController.getValidation()) {
                    Article addedArticle;
                    if (ajouterArticleController.isPerKilo()) {
                        addedArticle = new ArticlePerKg(ajouterArticleController.getArticleName(), defaultArticleImage, Double.parseDouble(ajouterArticleController.getArticlePrice()), 0);
                    } else {
                        addedArticle = new ArticlePerPiece(ajouterArticleController.getArticleName(), defaultArticleImage, Double.parseDouble(ajouterArticleController.getArticlePrice()), 0);
                    }
                    dataBaseController.AddArticle(addedArticle, vueTraiteurController.getSelectedSection().getName());
                    vueTraiteurController.ViewAjouterArticle(addedArticle);
                } else {
                    return; // Ne rien faire si aucune condition n'est satisfaite
                }

                ajouterArticleApplication.hide();
                ajouterArticleController.resetUI();
            }

            @Override
            public void onCancel() {
                ajouterArticleApplication.hide();
                ajouterArticleController.resetUI();
            }

            @Override
            public void AddSection() {
                ajouterArticleController.SetUIForAddSection();
            }
        };
    }


}