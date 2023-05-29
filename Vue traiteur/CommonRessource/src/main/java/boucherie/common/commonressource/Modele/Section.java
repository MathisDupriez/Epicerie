package boucherie.common.commonressource.Modele;

import java.util.ArrayList;
import java.util.List;

public class Section {
    public List<Article> section;
    String name;
    public Section(String name){
        this.name = name;
        section = new ArrayList<>();
    }
    public void addArticle(Article article){
        section.add(article);
    }
    public void removeArticle(Article article){
        section.remove(article);
    }
    public void printSection(){
        for (Article article : section) {
            System.out.println(article.getName());
        }
    }
    public Article getArticles(int index) {
        return section.get(index);
    }
    public String getName() {
        return name;
    }
    // this method is gonna be remake for a better use
    public Article getArticle(String name){
        for (Article article : section) {
            if(article.getName().equals(name)){
                return article;
            }
        }
        return null;
    }
}
