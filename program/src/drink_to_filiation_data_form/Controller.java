package drink_to_filiation_data_form;

import base.AlertsBuilder;
import base.DataFormComboBoxControllerAdditional;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import models.Drink;
import models.DrinkToFiliation;

/**
 * Created by shrralis on 3/14/17.
 */
public class Controller extends DataFormComboBoxControllerAdditional {
    {
        objectToProcess = new DrinkToFiliation();
    }

    @FXML public ComboBox<Drink> drink;
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
        drink.getValue().setPrice(price.getText().isEmpty() ? null : new Double(price.getText()));
        ((DrinkToFiliation) objectToProcess).setDrink(drink.getValue());
    }
    @Override
    protected String getDatabaseTableName() {
        return "filiations_has_drinks";
    }
}
