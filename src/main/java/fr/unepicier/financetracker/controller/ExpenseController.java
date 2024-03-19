package fr.unepicier.financetracker.controller;

import fr.unepicier.financetracker.FinanceTrackerApplication;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.layout.VBox;
import org.slf4j.Logger;

import java.io.IOException;
import java.net.URL;
import java.util.Objects;
import java.util.ResourceBundle;

import static org.slf4j.LoggerFactory.getLogger;

public class ExpenseController implements Initializable {
    Logger log = getLogger(ExpenseController.class);

    @FXML
    private VBox root;

    @FXML
    private HeaderController headerController;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        headerController.getViewValue().addListener((observable, oldValue, newValue) -> {
            try {
                Node component = FXMLLoader.load(Objects.requireNonNull(FinanceTrackerApplication.class.getResource("expense-" + newValue.toLowerCase() + ".fxml")));
                root.getChildren().removeLast();
                root.getChildren().add(component);
            } catch (IOException e) {
                log.error("Unable to load expense-" + newValue.toLowerCase() + ".fxml");
            }

        });
    }
}
