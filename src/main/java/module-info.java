module com.example.se233casestudy4 {
    requires javafx.controls;
    requires javafx.fxml;
    requires org.apache.logging.log4j;
    requires javafx.swing;


    opens com.example.se233casestudy4 to javafx.fxml;
    exports com.example.se233casestudy4;
}