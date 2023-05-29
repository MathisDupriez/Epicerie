module boucherie.vueclient.vueclient {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.controlsfx.controls;
    requires com.dlsc.formsfx;
    requires org.kordamp.bootstrapfx.core;
    requires boucherie.common.commonressource;

    opens boucherie.vueclient.vueclient to javafx.fxml;
    exports boucherie.vueclient.vueclient;
}