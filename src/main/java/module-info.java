module com.workshop {
    requires javafx.controls;
    requires javafx.fxml;

    opens com.workshop to javafx.fxml;
    exports com.workshop;
}
