package boucherie.common.commonressource.Modele;

import javafx.scene.image.Image;

import java.io.ByteArrayOutputStream;
import java.io.Serializable;

public abstract class  Article implements Serializable {
    private final String name;
    private transient Image myPicture;
    private String ImagePath;
    protected double quantity;
    public boolean isPerKg = false;
    public Article(String name, String path, double quantity){
        this.quantity = quantity;
        ImagePath = path;

        try{
            myPicture = new Image(path);
        }catch (Exception e){
             myPicture = new Image("C:\\Users\\Dupriez Mathis\\Desktop\\banane.jpg");
            ImagePath = "C:\\Users\\Dupriez Mathis\\Desktop\\banane.jpg";
        }

        this.name = name;
    }
    public Article(String name, double quantity){
        this.quantity = quantity;
        myPicture = new Image("C:\\Users\\Dupriez Mathis\\Desktop\\banane.jpg");
        this.name = name;
    }
    public String getImagePath() {return ImagePath;}
    public double getPrice() {return 0;}
    public double getArticlePrice() {return 0;};
    public String getName() {return name;}
    public double getQuantity() {return quantity;}
    public Boolean isPerKg() {return isPerKg;}
    public Image getPicture() {return myPicture;}
    public void setQuantity(double quantity) {this.quantity = quantity;}

}
