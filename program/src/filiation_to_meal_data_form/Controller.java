package filiation_to_meal_data_form;

import base.AlertsBuilder;
import base.DataFormComboBoxControllerAdditional;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import models.Filiation;
import models.MealToFiliation;

/**
 * Created by shrralis on 3/14/17.
 */
public class Controller extends DataFormComboBoxControllerAdditional {
    {
        objectToProcess = new MealToFiliation();
    }

    @FXML public ComboBox<Filiation> filiation;
    @FXML public TextField price;

    @Override
    protected boolean areTheFieldsValidForAdding() {
        if (isAnyTextFieldEmpty() || isAnyComboBoxEmpty()) {
            return false;
        }


        if (!price.getText().trim().matches("^\\d+(\\.\\d+)?$")) {
            AlertsBuilder.start()
                    .setAlertType(Alert.AlertType.WARNING)
                    .setTitle("Помилка")
                    .setHeaderText("Помилка вводу")
                    .setContentText("Поле " + price.getPromptText() + " повинно містити ціле або дробове число!\n" +
                            "Приклад: 1.09")
                    .build()
                    .showAndWait();
            return false;
        }
        return true;
    }
    @Override
    protected void objectToProcessBasedOnFields() {
        ((MealToFiliation) objectToProcess).setFiliation(filiation.getValue());
        ((MealToFiliation) objectToProcess).getMeal().setPrice(price.getText().isEmpty() ? null : new Double(price.getText()));
    }
    @Override
    protected String getDatabaseTableName() {
        return "filiations_has_meals";
    }
}
