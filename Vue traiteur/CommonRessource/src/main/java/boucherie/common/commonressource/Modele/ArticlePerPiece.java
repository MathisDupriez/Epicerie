package boucherie.common.commonressource.Modele;

import java.io.Serializable;

public class ArticlePerPiece extends Article implements Serializable {
    private final double pricePerPiece;
    public ArticlePerPiece(String name, String path,double pricePerPiece,double quantity) {
        super( name, path,quantity);
        this.pricePerPiece = pricePerPiece;
    }
    @Override
    public double getPrice() {
        return quantity*pricePerPiece;
    }
    @Override
    public double getArticlePrice() {return pricePerPiece;}
    @Override
    public Boolean isPerKg() {
        return false;
    }
}
