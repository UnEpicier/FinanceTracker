package fr.unepicier.financetracker.utils;

import fr.unepicier.financetracker.model.Expense;
import fr.unepicier.financetracker.model.Income;
import org.slf4j.Logger;

import java.sql.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import static org.slf4j.LoggerFactory.getLogger;

public class ExpenseDAO {
    private static final Logger log = getLogger(ExpenseDAO.class);
    private static final DateTimeFormatter DATE_FORMAT = DateTimeFormatter.ofPattern("yyyy-MM-dd");

    public static void addExpense(String date, Float housing, Float food, Float goingOut, Float transportation, Float travel, Float tax, Float other) {
        String query = "INSERT INTO expense(date, housing, food, goingOut, transportation, travel, tax, other) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";

        try (Connection connection = Database.connect()) {
            assert connection != null;
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setString(1, date);
            statement.setFloat(2, housing);
            statement.setFloat(3, food);
            statement.setFloat(4, goingOut);
            statement.setFloat(5, transportation);
            statement.setFloat(6, travel);
            statement.setFloat(7, tax);
            statement.setFloat(8, other);

            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    public static void addIncome(String date, Float salary, Float helps, Float autoBusiness, Float passives, Float other) {
        String query = "INSERT INTO income(date, salary, helps, autoBusiness, passives, other) VALUES (?, ?, ?, ?, ?, ?)";

        try (Connection connection = Database.connect()) {
            assert connection != null;
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setString(1, date);
            statement.setFloat(2, salary);
            statement.setFloat(3, helps);
            statement.setFloat(4, autoBusiness);
            statement.setFloat(5, passives);
            statement.setFloat(6, other);

            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static List<Expense> getExpenses() {
        String query = "SELECT * FROM expense";

        List<Expense> expenseArray = new ArrayList<>();

        try (Connection connection = Database.connect()){
            PreparedStatement statement = connection.prepareStatement(query);
            ResultSet rs = statement.executeQuery();

            while(rs.next()) {
                expenseArray.add(new Expense(
                        LocalDate.parse(rs.getString("date"), DateTimeFormatter.ofPattern("yyyy-MM-dd")),
                        rs.getFloat("housing"),
                        rs.getFloat("food"),
                        rs.getFloat("goingOut"),
                        rs.getFloat("transportation"),
                        rs.getFloat("travel"),
                        rs.getFloat("tax"),
                        rs.getFloat("other")
                ));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return expenseArray;
    }

    public static List<Income> getIncomes() {
        String query = "SELECT * FROM income";

        List<Income> incomeArray = new ArrayList<>();

        try (Connection connection = Database.connect()){
            PreparedStatement statement = connection.prepareStatement(query);
            ResultSet rs = statement.executeQuery();

            while(rs.next()) {
                incomeArray.add(new Income(
                        LocalDate.parse(rs.getString("date"), DateTimeFormatter.ofPattern("yyyy-MM-dd")),
                        rs.getFloat("salary"),
                        rs.getFloat("helps"),
                        rs.getFloat("autoBusiness"),
                        rs.getFloat("passives"),
                        rs.getFloat("other")
                ));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return incomeArray;
    }

    public static List<Expense> findLastExpensesEndingAtCurrentMonth(int numberOfLine, LocalDate currentMonth) {
        String query = "SELECT * FROM expense WHERE date <= '" + currentMonth.format(DATE_FORMAT)
                + "' ORDER BY date DESC LIMIT " + numberOfLine;

        List<Expense> lastExpenses = new ArrayList<>();

        try (Connection connection = Database.connect()) {
            PreparedStatement statement = connection.prepareStatement(query);
            ResultSet rs = statement.executeQuery();
            while (rs.next()) {
                lastExpenses.add(new Expense(
                        LocalDate.parse(rs.getString("date"), DATE_FORMAT),
                        rs.getFloat("housing"),
                        rs.getFloat("food"),
                        rs.getFloat("goingOut"),
                        rs.getFloat("transportation"),
                        rs.getFloat("travel"),
                        rs.getFloat("tax"),
                        rs.getFloat("other")));
            }
        } catch (SQLException e) {
            log.error("Could not load Expenses from database", e);
        }
        return lastExpenses;

    }

    public static List<Income> findLastIncomesEndingAtCurrentMonth(int numberOfLine, LocalDate currentMonth) {
        String query = "SELECT * FROM income WHERE date <= '" + currentMonth.format(DATE_FORMAT)
                + "' ORDER BY date DESC LIMIT " + numberOfLine;

        List<Income> lastIncomes = new ArrayList<>();

        try (Connection connection = Database.connect()) {
            PreparedStatement statement = connection.prepareStatement(query);
            ResultSet rs = statement.executeQuery();
            while (rs.next()) {
                lastIncomes.add(new Income(
                        LocalDate.parse(rs.getString("date"), DATE_FORMAT),
                        rs.getFloat("salary"),
                        rs.getFloat("helps"),
                        rs.getFloat("autoBusiness"),
                        rs.getFloat("passives"),
                        rs.getFloat("other")));
            }
        } catch (SQLException e) {
            log.error("Could not load Incomes from database", e);
        }
        return lastIncomes;

    }
}
