module boucherie.common.commonressource {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.controlsfx.controls;
    requires com.dlsc.formsfx;
    requires org.kordamp.bootstrapfx.core;
    requires junit;

    opens boucherie.common.commonressource to javafx.fxml;
    exports boucherie.common.commonressource.Modele;
    exports boucherie.common.commonressource.Network;
    exports boucherie.common.commonressource.View;
}