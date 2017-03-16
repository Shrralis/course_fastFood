package companies_data_form;

import base.AlertsBuilder;
import base.DataFormController;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.CheckBox;
import javafx.scene.control.TextField;
import models.Company;

import java.util.Calendar;

/**
 * Created by shrralis on 3/13/17.
 */
@SuppressWarnings("unused")
public class Controller extends DataFormController {
    {
        objectToProcess = new Company();
    }

    @FXML public TextField name;
    @FXML public TextField country;
    @FXML public TextField ceo_full_name;
    @FXML public TextField creation_year;
    @FXML public TextField office_address;
    @FXML public CheckBox bName;
    @FXML public CheckBox bCountry;
    @FXML public CheckBox bCeo_full_name;
    @FXML public CheckBox bCreation_year;
    @FXML public CheckBox bOffice_address;

    @Override
    protected boolean areTheFieldsValidForAdding() {
        if (isAnyTextFieldEmpty()) {
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
            ((Company) objectToProcess).setName(name.getText().trim().isEmpty() ? null : name.getText().trim());
        }

        if (bCountry.isSelected()) {
            ((Company) objectToProcess).setCountry(country.getText().trim().isEmpty() ? null : country.getText().trim());
        }

        if (bCeo_full_name.isSelected()) {
            ((Company) objectToProcess).setCeo_full_name(ceo_full_name.getText().trim().isEmpty() ? null : ceo_full_name.getText().trim());
        }

        if (bCreation_year.isSelected()) {
            ((Company) objectToProcess).setCreation_year(creation_year.getText().trim().isEmpty() ? null :
                    new Integer(creation_year.getText().trim()));
        }

        if (bOffice_address.isSelected()) {
            ((Company) objectToProcess).setOffice_address(office_address.getText().trim().isEmpty() ? null : office_address.getText().trim());
        }
    }
    @Override
    protected String getDatabaseTableName() {
        return "companies";
    }
}
