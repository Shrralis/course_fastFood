package orders_data_form;

import base.AlertsBuilder;
import base.DataFormComboBoxController;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import models.DateWorker;
import models.Fast_Food;
import models.Filiation;
import models.Order;

/**
 * Created by shrralis on 3/13/17.
 */
@SuppressWarnings("unused")
public class Controller extends DataFormComboBoxController {
    {
        objectToProcess = new Order();
    }

    @FXML public DatePicker date;
    @FXML public TextField time;
    @FXML public ComboBox<Filiation> filiation;
    @FXML public CheckBox bDate;
    @FXML public CheckBox bTime;
    @FXML public CheckBox bFiliation;

    @Override
    protected boolean areTheFieldsValidForAdding() {
        if (isAnyTextFieldEmpty() || isAnyComboBoxEmpty()) {
            return false;
        }

        if (!time.getText().trim().matches("^\\d{2}:\\d{2}:\\d{2}$")) {
            AlertsBuilder.start()
                    .setAlertType(Alert.AlertType.WARNING)
                    .setTitle("Помилка")
                    .setHeaderText("Помилкка вводу")
                    .setContentText("Невірний формат поля час!\n" +
                            "Приклад: 00:12:47 (гг:хв:сс)")
                    .buildOnFront(primaryStage)
                    .showAndWait();
            return false;
        } else if (DateWorker.convertToTime(time) == null) {
            AlertsBuilder.start()
                    .setAlertType(Alert.AlertType.WARNING)
                    .setTitle("Помилка")
                    .setHeaderText("Помилкка вводу")
                    .setContentText("Невірний час!\n" +
                            "Максимум годин — 24, максимум хвилин — 60, максимум секунд — 60\n" +
                            "Приклад: 00:12:47 (гг:хв:сс)")
                    .buildOnFront(primaryStage)
                    .showAndWait();
            return false;
        }
        return true;
    }
    @Override
    protected void objectToProcessBasedOnFields() {
        if (bDate.isSelected()) {
            ((Order) objectToProcess).setDate(date.getValue() == null ? null : DateWorker.convertToDate(date));
        }

        if (bTime.isSelected()) {
            ((Order) objectToProcess).setTime(time.getText().trim().isEmpty() ? null : DateWorker.convertToTime(time));
        }

        if (bFiliation.isSelected()) {
            ((Order) objectToProcess).setFiliation(filiation.getValue());
        }
    }
    @Override
    protected String getDatabaseTableName() {
        return "orders";
    }
}
