module com.workshop {
    requires javafx.controls;
    requires javafx.fxml;

    opens com.workshop to javafx.fxml;
    opens com.workshop.gui.controller to javafx.fxml;
    exports com.workshop;
    exports com.workshop.gui.controller to javafx.fxml;
}
