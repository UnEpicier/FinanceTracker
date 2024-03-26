package fr.unepicier.financetracker;

import fr.unepicier.financetracker.utils.Database;
import fr.unepicier.financetracker.utils.OSValidator;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import org.slf4j.Logger;
import static org.slf4j.LoggerFactory.getLogger;

import java.io.IOException;


public class FinanceTrackerApplication extends Application {
    static {
        System.setProperty("app.root", OSValidator.IS_WINDOWS ? System.getenv("APPDATA") + "/FinanceTracker" : OSValidator.IS_MAC ? System.getProperty("user.home") + "/Library/Application Support/FinanceTracker" : System.getProperty("user.home") + "/FinanceTracker");
    }

    private static final Logger log = getLogger(FinanceTrackerApplication.class);

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage stage) throws IOException {
        if (!Database.isOK()) {
            log.error("Could not load database");
            System.exit(1);
        }
        log.info("Loading Finance Tracker...");
        FXMLLoader fxmlLoader = new FXMLLoader(FinanceTrackerApplication.class.getResource("expense-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load());

        stage.setTitle("Finance Tracker");
        stage.setResizable(false);
        stage.getIcons().add(new Image(String.valueOf(FinanceTrackerApplication.class.getResource("assets/FinanceTracker.png"))));
        stage.setScene(scene);
        stage.show();
        log.info("Successfully loaded Finance Tracker");
    }
}
