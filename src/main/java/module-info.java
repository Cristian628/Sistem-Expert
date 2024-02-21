module com.example.sistemexpert {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.example.sistemexpert to javafx.fxml;
    exports com.example.sistemexpert;
}