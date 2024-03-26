package fr.unepicier.financetracker.controller;

import fr.unepicier.financetracker.model.Expense;
import fr.unepicier.financetracker.model.Income;
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

public class IncomeTableController {
    private static final Logger log = getLogger(ExpenseTableController.class);

    @FXML
    private TableView<Income> incomeTable;

    private final ObservableList<Income> items = FXCollections.observableArrayList();

    public void initialize() {
        items.addAll(ExpenseDAO.getIncomes());
        incomeTable.setItems(items);
    }

    public void addIncome(ActionEvent event) {
        Dialog<Income> addPersonDialog = new IncomeDialog();
        Optional<Income> result = addPersonDialog.showAndWait();
        result.ifPresent(items::add);

        log.debug(result.toString());

        event.consume();
    }

}
