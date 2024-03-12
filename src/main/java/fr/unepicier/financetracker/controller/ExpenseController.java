package fr.unepicier.financetracker.controller;

import fr.unepicier.financetracker.model.Expense;
import fr.unepicier.financetracker.utils.ExpenseDAO;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Dialog;
import javafx.scene.control.TableView;

import java.time.LocalDate;
import java.util.Optional;

public class ExpenseController {

    @FXML
    private TableView<Expense> expenseTable;

    private final ObservableList<Expense> items = FXCollections.observableArrayList();

    public void initialize() {
        items.addAll(ExpenseDAO.getExpenses());
        expenseTable.setItems(items);
    }



    public void addExpense(ActionEvent event) {
        Dialog<Expense> addPersonDialog = new ExpenseDialog();
        Optional<Expense> result = addPersonDialog.showAndWait();
        result.ifPresent(items::add);

        System.out.println(result);
        event.consume();
    }
}
