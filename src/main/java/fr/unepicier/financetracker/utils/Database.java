package fr.unepicier.financetracker.utils;

import fr.unepicier.financetracker.FinanceTrackerApplication;
import org.sqlite.JDBC;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.logging.Level;
import org.slf4j.Logger;

import static org.slf4j.LoggerFactory.getLogger;

public class Database {
    private static final Logger log = getLogger(FinanceTrackerApplication.class);

    /**
     * Location of database
     */
    private static final String location = OSValidator.IS_WINDOWS ? System.getenv("APPDATA") + "/FinanceTracker/database.db" : OSValidator.IS_MAC ? System.getProperty("user.home") + "/Library/Application Support/FinanceTracker/database.db" : System.getProperty("user.home") + "/FinanceTracker/database.db";

    /**
     * Currently only table needed
     */
    private static final String requiredTable = "Expense";

    public static boolean isOK() {
        if (!checkDrivers()) return false; //driver errors

        try {
            Files.createDirectories(Paths.get(location.replace("/database.db", "")));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        if (!checkConnection()) return false; //can't connect to db

        return createTableIfNotExists(); //tables didn't exist
    }

    private static boolean checkDrivers() {
        try {
            Class.forName("org.sqlite.JDBC");
            DriverManager.registerDriver(new JDBC());
            return true;
        } catch (ClassNotFoundException | SQLException classNotFoundException) {
            log.error("Could not start SQLite Drivers", classNotFoundException);
            return false;
        }
    }

    private static boolean checkConnection() {
        try (Connection connection = connect()) {
            return connection != null;
        } catch (SQLException e) {
            log.error("Could not connect to SQLite DB");
            return false;
        }
    }

    private static boolean createTableIfNotExists() {
        System.out.println("HAAAA");
        String createExpense = """
                     CREATE TABLE IF NOT EXISTS expense(
                             date TEXT NOT NULL,
                             housing REAL NOT NULL,
                             food REAL NOT NULL,
                             goingOut REAL NOT NULL,
                             transportation REAL NOT NULL,
                             travel REAL NOT NULL,
                             tax REAL NOT NULL,
                             other REAL NOT NULL
                        );
                """;
        String createIncome =
                """
                        CREATE TABLE IF NOT EXISTS income(
                            date TEXT NOT NULL,
                            salary REAL NOT NULL,
                            helps REAL NOT NULL,
                            autoBusiness REAL NOT NULL,
                            passives REAL NOT NULL,
                            other REAL NOT NULL
                        );
                       
                   """;

        try (Connection connection = Database.connect()) {
            assert connection != null;
            PreparedStatement statement = connection.prepareStatement(createIncome);
            statement.executeUpdate();
            statement = connection.prepareStatement(createExpense);
            statement.executeUpdate();
            return true;
        } catch (SQLException exception) {
            log.error("Could not create tables in database", exception);
            return false;
        }
    }

    protected static Connection connect() {
        String dbPrefix = "jdbc:sqlite:";
        Connection connection;
        try {
            connection = DriverManager.getConnection(dbPrefix + location);
        } catch (SQLException exception) {
            log.error("Could not connect to SQLite DB" + location, exception);
            return null;
        }
        return connection;
    }

}
