package filiations_data_form;

import base.DataFormComboBoxController;
import javafx.fxml.FXML;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import models.Fast_Food;
import models.Filiation;

/**
 * Created by shrralis on 3/13/17.
 */
@SuppressWarnings("unused")
public class Controller extends DataFormComboBoxController {
    {
        objectToProcess = new Filiation();
    }

    @FXML public TextField country;
    @FXML public TextField city;
    @FXML public TextField address;
    @FXML public CheckBox general;
    @FXML public ComboBox<Fast_Food> fast_food;
    @FXML public CheckBox bCountry;
    @FXML public CheckBox bCity;
    @FXML public CheckBox bAddress;
    @FXML public CheckBox bGeneral;
    @FXML public CheckBox bFast_food;

    @Override
    protected boolean areTheFieldsValidForAdding() {
        return !isAnyTextFieldEmpty() && !isAnyComboBoxEmpty();
    }
    @Override
    protected void objectToProcessBasedOnFields() {
        if (bCountry.isSelected()) {
            ((Filiation) objectToProcess).setCountry(country.getText().trim().isEmpty() ? null : country.getText().trim());
        }

        if (bCity.isSelected()) {
            ((Filiation) objectToProcess).setCity(city.getText().trim().isEmpty() ? null : city.getText().trim());
        }

        if (bAddress.isSelected()) {
            ((Filiation) objectToProcess).setAddress(address.getText().trim().isEmpty() ? null : address.getText().trim());
        }

        if (bGeneral.isSelected()) {
            ((Filiation) objectToProcess).setGeneral(general.isSelected());
        }

        if (bFast_food.isSelected()) {
            ((Filiation) objectToProcess).setFast_food(fast_food.getValue());
        }
    }
    @Override
    protected String getDatabaseTableName() {
        return "filiations";
    }
}
