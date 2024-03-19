package fr.unepicier.financetracker.controller;

import fr.unepicier.financetracker.model.Expense;
import fr.unepicier.financetracker.utils.ExpenseDAO;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Dialog;
import javafx.scene.control.TableView;
import org.slf4j.Logger;

import java.util.Optional;

import static org.slf4j.LoggerFactory.getLogger;

public class ExpenseTableController {
    private static final Logger log = getLogger(ExpenseTableController.class);

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

        log.debug(result.toString());

        event.consume();
    }
}
