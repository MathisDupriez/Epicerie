package boucherie.common.commonressource.Modele;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Bag implements Serializable {
    List<Article> Bag ;
    public Bag(){
        Bag = new ArrayList<Article>();
    }
    public void addArticle(Article item){
        Bag.add(item);
    }
    public void removeItem(Article item){
        Bag.remove(item);
    }
    public double getTotalPrice(){
        double total = 0;
        for (Article item : Bag) {
            total += item.getPrice();
        }
        return total;
    }
    public List<Article> getBag(){
        return Bag;
    }

    public void clearBag() {
        Bag.clear();
    }
}
