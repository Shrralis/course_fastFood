package personal_data_form;

import base.DataFormComboBoxController;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import models.Filiation;
import models.Personal;

/**
 * Created by shrralis on 3/13/17.
 */
@SuppressWarnings("unused")
public class Controller extends DataFormComboBoxController {
    {
        objectToProcess = new Personal();
    }

    @FXML public TextField name;
    @FXML public TextField surname;
    @FXML public ComboBox<Personal.Work> work;
    @FXML public ComboBox<Filiation> filiation;
    @FXML public CheckBox bName;
    @FXML public CheckBox bSurname;
    @FXML public CheckBox bWork;
    @FXML public CheckBox bFiliation;

    @Override
    public void loadData() {
        super.loadData();
        setComboBox();
    }
    @Override
    protected boolean areTheFieldsValidForAdding() {
        return !isAnyTextFieldEmpty() && !isAnyComboBoxEmpty();
    }
    @Override
    protected void objectToProcessBasedOnFields() {
        if (bName.isSelected()) {
            ((Personal) objectToProcess).setName(name.getText().trim().isEmpty() ? null : name.getText().trim());
        }

        if (bSurname.isSelected()) {
            ((Personal) objectToProcess).setSurname(surname.getText().trim().isEmpty() ? null : surname.getText().trim());
        }

        if (bWork.isSelected()) {
            ((Personal) objectToProcess).setWork(work.getValue());
        }

        if (bFiliation.isSelected()) {
            ((Personal) objectToProcess).setFiliation(filiation.getValue());
        }
    }
    @Override
    protected String getDatabaseTableName() {
        return "personal";
    }

    private void setComboBox() {
        work.setItems(FXCollections.observableArrayList(Personal.Work.values()));
    }
}
