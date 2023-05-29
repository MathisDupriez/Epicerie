package boucherie.vuetraiteur.vuetraiteurapp.DataBase;


import boucherie.common.commonressource.Modele.*;
import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class DataBaseController
{
    private final String SourcePath = "C:\\Users\\Dupriez Mathis\\ProjetJava\\Boucherie_Controle_commande\\Vue traiteur\\VueTraiteurApp\\src\\main\\java\\boucherie\\vuetraiteur\\vuetraiteurapp\\DataBase";
    private final File DataBaseFile = new File(SourcePath);

    public void AddArticle(Article article, String sectionName) {
        File ArticleFile = new File(SourcePath + "\\" + sectionName + "\\" + article.getName() + ".txt");
        if (ArticleFile.exists()) {
            System.out.println("Article already exist");
        } else {
            try {
                ArticleFile.createNewFile();
                BufferedWriter writer = new BufferedWriter(new FileWriter(ArticleFile));
                writer.write(article.getName());
                writer.newLine();
                writer.write(Double.toString(article.getArticlePrice()));
                writer.newLine();
                writer.write(Double.toString(article.getQuantity()));
                writer.newLine();
                writer.write(article.isPerKg().toString());
                writer.newLine();
                writer.write(article.getImagePath());
                writer.close();
            } catch (IOException e) {
                System.out.println("Error while creating article");
                e.printStackTrace();
            }
            System.out.println("Article created");
        }
    }

    public void AddSection(Section section) {
        File SectionFile = new File(SourcePath + "\\" + section.getName());
        if (SectionFile.exists()) {
            System.out.println("Section already exist");
        } else {
            SectionFile.mkdir();
            System.out.println("Section created");
        }
    }


    public List<Section> LoadDataBase() {
        List<Section> Shop = new ArrayList<>();
        for (File file : Objects.requireNonNull(DataBaseFile.listFiles())) {
            Section section = new Section(file.getName());
            if(file.listFiles()!=null)
            {
                for (File file1 : Objects.requireNonNull(file.listFiles())) {
                    String name = "";
                    String path = "";
                    double price = 0;
                    double quantity = 0;
                    boolean isPerKg = false;
                    try {
                        BufferedReader scanner = new BufferedReader(new FileReader(file1));
                        name = scanner.readLine();
                        System.out.println(name);
                        price = Double.parseDouble(scanner.readLine());
                        quantity = Double.parseDouble(scanner.readLine());
                        isPerKg = Boolean.parseBoolean(scanner.readLine());
                        path = scanner.readLine();
                    } catch (Exception e) {
                        System.out.println("Error while reading article");
                    }
                    if (isPerKg) {
                        section.addArticle(new ArticlePerKg(name, path, price, quantity));
                    } else {
                        section.addArticle(new ArticlePerPiece(name, path, price, quantity));


                    }
                    System.out.println(section.getArticles(section.section.size() - 1).getName());

                }
            }Shop.add(section);
            System.out.println(section.getName());
        }
        return Shop;
    }
}
