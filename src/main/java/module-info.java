module com.example.chapter4debugging {
    requires javafx.controls;
    requires javafx.fxml;
    requires org.apache.logging.log4j;


    opens com.example.chapter4debugging to javafx.fxml;
    exports com.example.chapter4debugging;
}