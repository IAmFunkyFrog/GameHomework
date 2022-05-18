module com.homework.nastyahomework {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.homework.nastyahomework to javafx.fxml;
    exports com.homework.nastyahomework;
    exports com.homework.nastyahomework.controller;
    opens com.homework.nastyahomework.controller to javafx.fxml;
}