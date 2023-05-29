module com.example.boucherieserveur {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.controlsfx.controls;
    requires com.dlsc.formsfx;
    requires org.kordamp.bootstrapfx.core;

    requires boucherie.common.commonressource;
    opens com.example.boucherieserveur to javafx.fxml;
    exports com.example.boucherieserveur;
}