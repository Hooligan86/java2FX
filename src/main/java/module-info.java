module com.hooligan.homeworksfx {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.web;

    requires org.controlsfx.controls;
    requires com.dlsc.formsfx;
    requires validatorfx;
    requires org.kordamp.ikonli.javafx;
    requires org.kordamp.bootstrapfx.core;
    requires eu.hansolo.tilesfx;

    opens com.hooligan.homeworksfx to javafx.fxml;
    exports com.hooligan.homeworksfx;
    exports com.hooligan.homeworksfx.controllers;
    opens com.hooligan.homeworksfx.controllers to javafx.fxml;
}