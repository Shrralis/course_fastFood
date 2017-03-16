package fast_foods_data_form;

import base.AlertsBuilder;
import base.DataFormComboBoxController;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import models.Company;
import models.Fast_Food;

import java.util.Calendar;

/**
 * Created by shrralis on 3/13/17.
 */
@SuppressWarnings("unused")
public class Controller extends DataFormComboBoxController {
    {
        objectToProcess = new Fast_Food();
    }

    @FXML public TextField name;
    @FXML public TextField creation_year;
    @FXML public ComboBox<Company> company;
    @FXML public CheckBox bName;
    @FXML public CheckBox bCreation_year;
    @FXML public CheckBox bCompany;

    @Override
    protected boolean areTheFieldsValidForAdding() {
        if (isAnyTextFieldEmpty() || isAnyComboBoxEmpty()) {
            return false;
        }

        if (!creation_year.getText().trim().matches("^\\d+$")) {
            AlertsBuilder.start()
                    .setAlertType(Alert.AlertType.WARNING)
                    .setTitle("Помилка")
                    .setHeaderText("Помилка вводу")
                    .setContentText("Поле " + creation_year.getPromptText() + " повинно містити ціле число!")
                    .build()
                    .showAndWait();
            return false;
        } else {
            int iYear = Integer.parseInt(creation_year.getText().trim());

            if (iYear < 1970 || iYear > Calendar.getInstance().get(Calendar.YEAR)) {
                AlertsBuilder.start()
                        .setAlertType(Alert.AlertType.WARNING)
                        .setTitle("Помилка")
                        .setHeaderText("Помилка вводу")
                        .setContentText("Поле " + creation_year.getPromptText() + " повинно містити рік в межах від 1970–" +
                                Calendar.getInstance().get(Calendar.YEAR) + "!")
                        .build()
                        .showAndWait();
                return false;
            }
        }
        return true;
    }
    @Override
    protected void objectToProcessBasedOnFields() {
        if (bName.isSelected()) {
            ((Fast_Food) objectToProcess).setName(name.getText().trim().isEmpty() ? null : name.getText().trim());
        }

        if (bCreation_year.isSelected()) {
            ((Fast_Food) objectToProcess).setCreation_year(creation_year.getText().trim().isEmpty() ? null :
                    new Integer(creation_year.getText().trim()));
        }

        if (bCompany.isSelected()) {
            ((Fast_Food) objectToProcess).setCompany(company.getValue());
        }
    }
    @Override
    protected String getDatabaseTableName() {
        return "fast_foods";
    }
}
