module com.workshop {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;
    requires transitive javafx.base;
    requires transitive javafx.graphics;

    opens com.workshop to javafx.fxml;
    opens com.workshop.gui.controller to javafx.fxml;
    opens com.workshop.model.entities to javafx.base;

    exports com.workshop;
    exports com.workshop.model.services;
    exports com.workshop.gui.controller to javafx.fxml;
    exports com.workshop.model.entities;
}
