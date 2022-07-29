module com.example.assigment2 {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.web;

    requires org.controlsfx.controls;
    requires com.dlsc.formsfx;
    requires validatorfx;
    requires org.kordamp.ikonli.javafx;
    requires org.kordamp.bootstrapfx.core;
    requires eu.hansolo.tilesfx;

    opens com.example.assigment2 to javafx.fxml;
    exports com.example.assigment2;

    exports com.example.assigment2.Controller;
    opens com.example.assigment2.Controller to javafx.fxml;

}