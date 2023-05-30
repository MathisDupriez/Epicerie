package boucherie.vuetraiteur.vuetraiteurapp;

import boucherie.common.commonressource.Modele.*;
import boucherie.common.commonressource.Network.SocketObject;
import boucherie.vuetraiteur.vuetraiteurapp.DataBase.DataBaseController;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/// TODo:
/// faire les test unitaire du modele
/// faire la javaDoc
/// renomer les méthodes et les variables en anglais et correctement

public class VueTraiteurApplication extends Application  {
    //variable that concern the bag that will be sent in the network
    private final Bag bag = new Bag();
    //variable that concern the list of section that making the shop
    private List<Section> Shop = new ArrayList<>();
    //variable that concern the used article in the code
    private final String defaultArticleImage = "C:\\Users\\Dupriez Mathis\\ProjetJava\\Boucherie_Controle_commande\\Vue traiteur\\VueTraiteurApp\\src\\main\\java\\boucherie\\vuetraiteur\\vuetraiteurapp\\Image\\NoImage.png";
    private Article selectedArticle;
    private double quantity=0;
    //variable that concern the VueTraiteur
    private VueTraiteurController vueTraiteurController;
    //variable that concern the DataBase
    private final DataBaseController dataBaseController = new DataBaseController();


    //variable that concern the AddArticle
    private final AjouterArticleApplication ajouterArticleApplication = new AjouterArticleApplication();
    private AjouterArticleController ajouterArticleController;
    //variable that concern the Balance
    private final BalanceApplication balanceApplication = new BalanceApplication();
    //variable that concern the Network
    SocketObject socketToServerArticle;
    SocketObject socketToServerTpe;
    // variable that concern the ErrorAddingArticle
    private final ErrorAddingArticleApplication errorAddingArticleApplication = new ErrorAddingArticleApplication();

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
        errorAddingArticleApplication.start(new Stage());
        balanceApplication.start(new Stage());

        System.out.println("connection au serveur");
        try {
            Socket socket = new Socket("localhost", 8888);
            socketToServerArticle = new SocketObject(socket);
            System.out.println("connection au serveur terminé : 8888");
        }catch (Exception e){
            vueTraiteurController.ErrorConnection();
            System.out.println("Connexion échoué : 8888");
        }
        try {
            Socket socket = new Socket("localhost", 4445);
            socketToServerTpe = new SocketObject(socket);
            System.out.println("connection au serveur terminé : 4445");
        }catch (Exception e){
            vueTraiteurController.ErrorConnection();
            System.out.println("Connexion échoué : 4445");
        }


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
                            socketToServerArticle.write(bag);
                        }catch (Exception e){
                            System.out.println(e.getMessage());
                        }

                        vueTraiteurController.ViewAjouterAuPanier(selectedArticle);
                        quantity=0;
                        vueTraiteurController.resetStatut();
                        balanceApplication.getController().reset();
                    }
                }
                @Override
                public void Encaisser() {
                    try {
                        Boolean tpe = true;
                        socketToServerTpe.write(tpe);
                        socketToServerTpe.close();
                        socketToServerTpe = new SocketObject(new Socket("localhost", 4445));
                    }
                    catch (Exception e){
                        System.out.println(e.getMessage());
                    }
                    Boolean answer = null;
                    try {
                        socketToServerTpe.write(null);
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                    while(answer == null) {
                        try {
                            System.out.println("attente de la réponse du tpe");
                            answer = socketToServerTpe.read();
                            System.out.println(answer);
                        } catch (Exception e) {
                            System.out.println(e.getMessage());
                        }
                    }
                    if(answer){
                        vueTraiteurController.ViewEncaisser();
                        vueTraiteurController.resetTotalPrice(0);
                        bag.clearBag();
                        try{
                            socketToServerArticle.write(bag);
                        }catch (Exception e) {
                            System.out.println(e.getMessage());
                        }
                    }
                    else{
                        return;
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
                        System.out.println(selectedArticle.isPerKg);
                        quantity=0;
                    }
                    if(!selectedArticle.isPerKg)
                    {quantity++;
                        selectedArticle.setQuantity(quantity);
                        vueTraiteurController.ViewArticleClicked(selectedArticle);
                    }

                }
                @Override
                public void delArticle(Article article){
                    bag.removeItem(article);
                    vueTraiteurController.resetTotalPrice(bag.getTotalPrice());

                    try{
                        socketToServerArticle.write(bag);
                    }catch (Exception e){
                        System.out.println(e.getMessage());
                    }
                }

                @Override
                public void OnComboBoxQuantiteChanged() {
                    quantity = vueTraiteurController.getQuantite();
                    selectedArticle.setQuantity(quantity);
                    vueTraiteurController.ViewArticleClicked(selectedArticle);
                }


            };
    }
    public BalanceControleur.VueBlancelistener BalanceSetListener(){
        return () -> Platform.runLater(() -> {
                if(selectedArticle != null&& !Objects.equals(balanceApplication.getController().getBalance(), "")){
                    selectedArticle.setQuantity(Double.parseDouble(balanceApplication.getController().getBalance())/1000);
                    vueTraiteurController.ViewArticleClicked(selectedArticle);
                }
        });
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
                    if(ajouterArticleController.TextFieldIsDouble()){
                        Article addedArticle;
                        if (ajouterArticleController.isPerKilo()) {
                            addedArticle = new ArticlePerKg(ajouterArticleController.getArticleName(), defaultArticleImage, Double.parseDouble(ajouterArticleController.getArticlePrice()), 0);
                        } else {
                            addedArticle = new ArticlePerPiece(ajouterArticleController.getArticleName(), defaultArticleImage, Double.parseDouble(ajouterArticleController.getArticlePrice()), 0);
                        }
                        dataBaseController.AddArticle(addedArticle, vueTraiteurController.getSelectedSection().getName());
                        vueTraiteurController.ViewAjouterArticle(addedArticle);
                    }
                    else{
                        ajouterArticleApplication.hide();
                        errorAddingArticleApplication.show();
                    }

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