module boucherie.vuetraiteur.vuetraiteurapp {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.controlsfx.controls;
    requires com.dlsc.formsfx;
    requires org.kordamp.bootstrapfx.core;
    requires java.desktop;
    requires boucherie.common.commonressource;

    opens boucherie.vuetraiteur.vuetraiteurapp to javafx.fxml;
    exports boucherie.vuetraiteur.vuetraiteurapp;
    exports boucherie.vuetraiteur.vuetraiteurapp.DataBase;
    opens boucherie.vuetraiteur.vuetraiteurapp.DataBase to javafx.fxml;
}