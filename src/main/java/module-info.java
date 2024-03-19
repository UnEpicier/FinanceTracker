module fr.unepicier.financetracker {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;
    requires org.xerial.sqlitejdbc;
    requires org.slf4j;


    opens fr.unepicier.financetracker to javafx.fxml;
    exports fr.unepicier.financetracker;
    exports fr.unepicier.financetracker.model;
    exports fr.unepicier.financetracker.controller;
    opens fr.unepicier.financetracker.controller to javafx.fxml;
}