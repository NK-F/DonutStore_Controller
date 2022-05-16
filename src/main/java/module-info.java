module com.example.cs213_p4 {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.example.cs213_p4 to javafx.fxml;
    exports com.example.cs213_p4;
}