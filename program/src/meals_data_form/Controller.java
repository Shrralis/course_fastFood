package meals_data_form;

import base.AlertsBuilder;
import base.DataFormComboBoxController;
import base.DataFormController;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import models.Fast_Food;
import models.Filiation;
import models.Meal;

/**
 * Created by shrralis on 3/13/17.
 */
@SuppressWarnings("unused")
public class Controller extends DataFormController {
    {
        objectToProcess = new Meal();
    }

    @FXML public TextField name;
    @FXML public TextField calorie;
    @FXML public TextField weight;
    @FXML public CheckBox bName;
    @FXML public CheckBox bCalorie;
    @FXML public CheckBox bWeight;

    @Override
    protected boolean areTheFieldsValidForAdding() {
        if (isAnyTextFieldEmpty()) {
            return false;
        }

        if (!calorie.getText().trim().matches("^\\d+(\\.\\d+)?$")) {
            AlertsBuilder.start()
                    .setAlertType(Alert.AlertType.WARNING)
                    .setTitle("Помилка")
                    .setHeaderText("Помилка вводу")
                    .setContentText("Поле " + calorie.getPromptText() + " повинно містити ціле або дробове число!\n" +
                            "Приклад: 1.09")
                    .build()
                    .showAndWait();
            return false;
        }

        if (!weight.getText().trim().matches("^\\d+(\\.\\d+)?$")) {
            AlertsBuilder.start()
                    .setAlertType(Alert.AlertType.WARNING)
                    .setTitle("Помилка")
                    .setHeaderText("Помилка вводу")
                    .setContentText("Поле " + weight.getPromptText() + " повинно містити ціле або дробове число!\n" +
                            "Приклад: 1.09")
                    .build()
                    .showAndWait();
            return false;
        }
        return true;
    }
    @Override
    protected void objectToProcessBasedOnFields() {
        if (bName.isSelected()) {
            ((Meal) objectToProcess).setName(name.getText().trim().isEmpty() ? null : name.getText().trim());
        }

        if (bCalorie.isSelected()) {
            ((Meal) objectToProcess).setCalorie(calorie.getText().trim().isEmpty() ? null :
                    new Double(calorie.getText().trim()));
        }

        if (bWeight.isSelected()) {
            ((Meal) objectToProcess).setWeight(weight.getText().trim().isEmpty() ? null :
                    new Double(weight.getText().trim()));
        }
    }
    @Override
    protected String getDatabaseTableName() {
        return "meals";
    }
}
