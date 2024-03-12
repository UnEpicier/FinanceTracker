package fr.unepicier.financetracker.utils;

import fr.unepicier.financetracker.model.Expense;

import javax.xml.transform.Result;
import java.sql.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class ExpenseDAO {
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
}
