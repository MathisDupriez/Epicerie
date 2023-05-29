module boucherie.tpe.tpe {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.controlsfx.controls;
    requires com.dlsc.formsfx;
    requires org.kordamp.bootstrapfx.core;

    opens boucherie.tpe.tpe to javafx.fxml;
    exports boucherie.tpe.tpe;
}