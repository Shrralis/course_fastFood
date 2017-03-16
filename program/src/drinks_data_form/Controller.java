package drinks_data_form;

import base.AlertsBuilder;
import base.DataFormController;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.CheckBox;
import javafx.scene.control.TextField;
import models.Drink;
import models.Meal;

/**
 * Created by shrralis on 3/13/17.
 */
@SuppressWarnings("unused")
public class Controller extends DataFormController {
    {
        objectToProcess = new Drink();
    }

    @FXML public TextField name;
    @FXML public TextField calorie;
    @FXML public TextField volume;
    @FXML public CheckBox bName;
    @FXML public CheckBox bCalorie;
    @FXML public CheckBox bVolume;

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

        if (!volume.getText().trim().matches("^\\d+?$")) {
            AlertsBuilder.start()
                    .setAlertType(Alert.AlertType.WARNING)
                    .setTitle("Помилка")
                    .setHeaderText("Помилка вводу")
                    .setContentText("Поле " + volume.getPromptText() + " повинно містити ціле число!")
                    .build()
                    .showAndWait();
            return false;
        }
        return true;
    }
    @Override
    protected void objectToProcessBasedOnFields() {
        if (bName.isSelected()) {
            ((Drink) objectToProcess).setName(name.getText().trim().isEmpty() ? null : name.getText().trim());
        }

        if (bCalorie.isSelected()) {
            ((Drink) objectToProcess).setCalorie(calorie.getText().trim().isEmpty() ? null :
                    new Double(calorie.getText().trim()));
        }

        if (bVolume.isSelected()) {
            ((Drink) objectToProcess).setVolume(volume.getText().trim().isEmpty() ? null :
                    new Integer(volume.getText().trim()));
        }
    }
    @Override
    protected String getDatabaseTableName() {
        return "drinks";
    }
}
