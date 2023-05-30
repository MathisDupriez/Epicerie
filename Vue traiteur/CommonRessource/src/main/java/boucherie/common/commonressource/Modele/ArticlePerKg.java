package boucherie.common.commonressource.Modele;

import java.io.Serializable;

public class ArticlePerKg extends Article implements Serializable {
    private final double pricePerKg;
    public ArticlePerKg(String name, String path,double pricePerKilo,double Quantity) {
        super(name, path,Quantity);
        this.pricePerKg = pricePerKilo;
    }
    public ArticlePerKg(String name,double pricePerKilo,double Quantity) {
        super(name,Quantity);
        this.pricePerKg = pricePerKilo;
    }
    @Override
    public double getPrice() {return pricePerKg*quantity;}
    @Override
    public double getArticlePrice() {
        return pricePerKg;
    }

    @Override
    public Boolean isPerKg() {
        return true;
    }
}
