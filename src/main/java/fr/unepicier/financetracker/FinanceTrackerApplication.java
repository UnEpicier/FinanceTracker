package fr.unepicier.financetracker;

import fr.unepicier.financetracker.utils.Database;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

import java.io.IOException;

public class FinanceTrackerApplication extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage stage) throws IOException {
        if (!Database.isOK()) {
            System.exit(1);
        }

        FXMLLoader fxmlLoader = new FXMLLoader(FinanceTrackerApplication.class.getResource("expense-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load());

        stage.setTitle("Finance Tracker");
        stage.setResizable(false);
        stage.getIcons().add(new Image(String.valueOf(FinanceTrackerApplication.class.getResource("assets/FinanceTracker.png"))));
        stage.setScene(scene);
        stage.show();
    }
}
