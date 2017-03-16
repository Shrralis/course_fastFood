package main_form;

import base.*;
import javafx.beans.binding.Bindings;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;
import models.*;
import server.DatabaseWorker;

import java.io.IOException;
import java.lang.reflect.Field;
import java.util.HashMap;

@SuppressWarnings("unchecked")
public class Controller {
    @FXML private TabPane tabs;
    @FXML private TableView<Fast_Food> tableFast_foods;
    @FXML TableColumn<Fast_Food, String> columnCompanyTableFast_foods;
    @FXML private TableView<Company> tableCompanies;
    @FXML private TableView<Filiation> tableFiliations;
    @FXML TableColumn<Filiation, String> columnGeneralTableFiliations;
    @FXML TableColumn<Filiation, String> columnFast_foodTableFiliations;
    @FXML private TableView<Meal> tableMeals;
    @FXML private TableView<Drink> tableDrinks;
    @FXML private TableView<Order> tableOrders;
    @FXML TableColumn<Order, String> columnDateTableOrders;
    @FXML TableColumn<Order, String> columnTimeTableOrders;
    @FXML TableColumn<Order, String> columnFiliationTableOrders;

    @FXML
    public void initialize() {
        columnCompanyTableFast_foods.setCellValueFactory(param -> {
            if (param.getValue() != null && param.getValue().getCompany().getName() != null) {
                return new SimpleStringProperty(param.getValue().getCompany().toString());
            } else {
                return new SimpleStringProperty("невідомо");
            }
        });
        columnGeneralTableFiliations.setCellValueFactory(param -> {
            if (param.getValue() != null && param.getValue().getGeneral() != null) {
                return new SimpleStringProperty(param.getValue().getGeneral() ? "є головною" : "не головна");
            } else {
                return new SimpleStringProperty("невідомо");
            }
        });
        columnFast_foodTableFiliations.setCellValueFactory(param -> {
            if (param.getValue() != null && param.getValue().getFast_food() != null) {
                return new SimpleStringProperty(param.getValue().getFast_food().toString());
            } else {
                return new SimpleStringProperty("невідомо");
            }
        });
        columnDateTableOrders.setCellValueFactory(param -> {
            if (param.getValue() != null && param.getValue().getDate() != null) {
                return new SimpleStringProperty(DateWorker.convertDateToString(param.getValue().getDate()));
            } else {
                return new SimpleStringProperty("невідомо");
            }
        });
        columnFiliationTableOrders.setCellValueFactory(param -> {
            if (param.getValue() != null && param.getValue().getFiliation() != null) {
                return new SimpleStringProperty(param.getValue().getFiliation().toString());
            } else {
                return new SimpleStringProperty("невідомо");
            }
        });
        tableFiliations.setRowFactory(param -> {
            final TableRow<Filiation> row = new TableRow<>();
            final ContextMenu contextMenu = new ContextMenu();
            final MenuItem addMeal = new MenuItem("Додати страву");
            final MenuItem addDrink = new MenuItem("Додати напій");
            final MenuItem findMeals = new MenuItem("Страви, які тут подаються");
            final MenuItem findDrinks = new MenuItem("Напої, які тут подаються");

            addMeal.setOnAction(event -> openAddMealForm(tableFiliations.getSelectionModel().getSelectedItem()));
            addDrink.setOnAction(event -> openAddDrinkForm(tableFiliations.getSelectionModel().getSelectedItem()));
            findMeals.setOnAction(event -> {
                HashMap<String, Object> params = new HashMap<>();

                params.put("filiation", tableFiliations.getSelectionModel().getSelectedItem().getId());
                tableMeals.setItems(
                        FXCollections.observableArrayList(
                                DatabaseWorker.processQuery(
                                        ServerQuery.create(
                                                "filiations_has_meals",
                                                "get",
                                                null,
                                                params
                                        )
                                ).getObjects()
                        )
                );
                tabs.getSelectionModel().select(3);
            });
            findDrinks.setOnAction(event -> {
                HashMap<String, Object> params = new HashMap<>();

                params.put("filiation", tableDrinks.getSelectionModel().getSelectedItem().getId());
                tableDrinks.setItems(
                        FXCollections.observableArrayList(
                                DatabaseWorker.processQuery(
                                        ServerQuery.create(
                                                "filiations_has_drinks",
                                                "get",
                                                null,
                                                params
                                        )
                                ).getObjects()
                        )
                );
                tabs.getSelectionModel().select(4);
            });
            contextMenu.getItems().addAll(addMeal, addDrink, findMeals, findDrinks);
            row.contextMenuProperty().bind(
                    Bindings.when(row.emptyProperty())
                            .then((ContextMenu) null)
                            .otherwise(contextMenu)
            );
            return row;
        });
        tableMeals.setRowFactory(param -> {
            final TableRow<Meal> row = new TableRow<>();
            final ContextMenu contextMenu = new ContextMenu();
            final MenuItem addToFiliation = new MenuItem("Додати до філії");
            final MenuItem addToOrder = new MenuItem("Додати до замовлення");
            final MenuItem findFiliations = new MenuItem("Філії, які подають цю страву");
            final MenuItem findOrders = new MenuItem("Замовлення, в яких є ця страва");

            addToFiliation.setOnAction(event -> openAddToFiliationForm(tableMeals.getSelectionModel().getSelectedItem()));
            addToOrder.setOnAction(event -> openAddToOrderForm(tableMeals.getSelectionModel().getSelectedItem()));
            findFiliations.setOnAction(event -> {
                HashMap<String, Object> params = new HashMap<>();

                params.put("meal", tableMeals.getSelectionModel().getSelectedItem().getId());
                tableFiliations.setItems(
                        FXCollections.observableArrayList(
                                DatabaseWorker.processQuery(
                                        ServerQuery.create(
                                                "filiations_has_meals",
                                                "get",
                                                null,
                                                params
                                        )
                                ).getObjects()
                        )
                );
                tabs.getSelectionModel().select(2);
            });
            findOrders.setOnAction(event -> {
                HashMap<String, Object> params = new HashMap<>();

                params.put("meal", tableMeals.getSelectionModel().getSelectedItem().getId());
                tableOrders.setItems(
                        FXCollections.observableArrayList(
                                DatabaseWorker.processQuery(
                                        ServerQuery.create(
                                                "orders_has_meals",
                                                "get",
                                                null,
                                                params
                                        )
                                ).getObjects()
                        )
                );
                tabs.getSelectionModel().select(4);
            });
            contextMenu.getItems().addAll(addToFiliation, addToOrder, findFiliations, findOrders);
            row.contextMenuProperty().bind(
                    Bindings.when(row.emptyProperty())
                            .then((ContextMenu) null)
                            .otherwise(contextMenu)
            );
            return row;
        });
        tableDrinks.setRowFactory(param -> {
            final TableRow<Drink> row = new TableRow<>();
            final ContextMenu contextMenu = new ContextMenu();
            final MenuItem addToFiliation = new MenuItem("Додати до філії");
            final MenuItem addToOrder = new MenuItem("Додати до замовлення");
            final MenuItem findFiliations = new MenuItem("Філії, які подають цей напій");
            final MenuItem findOrders = new MenuItem("Замовлення, в яких є цей напій");

            addToFiliation.setOnAction(event -> openAddToFiliationForm(tableDrinks.getSelectionModel().getSelectedItem()));
            addToOrder.setOnAction(event -> openAddToOrderForm(tableDrinks.getSelectionModel().getSelectedItem()));
            findFiliations.setOnAction(event -> {
                HashMap<String, Object> params = new HashMap<>();

                params.put("drink", tableDrinks.getSelectionModel().getSelectedItem().getId());
                tableFiliations.setItems(
                        FXCollections.observableArrayList(
                                DatabaseWorker.processQuery(
                                        ServerQuery.create(
                                                "filiations_has_drinks",
                                                "get",
                                                null,
                                                params
                                        )
                                ).getObjects()
                        )
                );
                tabs.getSelectionModel().select(4);
            });
            findOrders.setOnAction(event -> {
                HashMap<String, Object> params = new HashMap<>();

                params.put("drink", tableDrinks.getSelectionModel().getSelectedItem().getId());
                tableOrders.setItems(
                        FXCollections.observableArrayList(
                                DatabaseWorker.processQuery(
                                        ServerQuery.create(
                                                "orders_has_drinks",
                                                "get",
                                                null,
                                                params
                                        )
                                ).getObjects()
                        )
                );
                tabs.getSelectionModel().select(4);
            });
            contextMenu.getItems().addAll(addToFiliation, addToOrder, findFiliations, findOrders);
            row.contextMenuProperty().bind(
                    Bindings.when(row.emptyProperty())
                            .then((ContextMenu) null)
                            .otherwise(contextMenu)
            );
            return row;
        });
        tableOrders.setRowFactory(param -> {
            final TableRow<Order> row = new TableRow<>();
            final ContextMenu contextMenu = new ContextMenu();
            final MenuItem addMeal = new MenuItem("Додати страву");
            final MenuItem addDrink = new MenuItem("Додати напій");
            final MenuItem findMeals = new MenuItem("Страви, які є в замовленні");
            final MenuItem findDrinks = new MenuItem("Напої, які є в замовленні");

            addMeal.setOnAction(event -> openAddMealForm(tableOrders.getSelectionModel().getSelectedItem()));
            addDrink.setOnAction(event -> openAddDrinkForm(tableOrders.getSelectionModel().getSelectedItem()));
            findMeals.setOnAction(event -> {
                HashMap<String, Object> params = new HashMap<>();

                params.put("order", tableOrders.getSelectionModel().getSelectedItem().getId());
                tableMeals.setItems(
                        FXCollections.observableArrayList(
                                DatabaseWorker.processQuery(
                                        ServerQuery.create(
                                                "orders_has_meals",
                                                "get",
                                                null,
                                                params
                                        )
                                ).getObjects()
                        )
                );
                tabs.getSelectionModel().select(3);
            });
            findDrinks.setOnAction(event -> {
                HashMap<String, Object> params = new HashMap<>();

                params.put("order", tableOrders.getSelectionModel().getSelectedItem().getId());
                tableDrinks.setItems(
                        FXCollections.observableArrayList(
                                DatabaseWorker.processQuery(
                                        ServerQuery.create(
                                                "orders_has_drinks",
                                                "get",
                                                null,
                                                params
                                        )
                                ).getObjects()
                        )
                );
                tabs.getSelectionModel().select(4);
            });
            contextMenu.getItems().addAll(addMeal, addDrink, findMeals, findDrinks);
            row.contextMenuProperty().bind(
                    Bindings.when(row.emptyProperty())
                            .then((ContextMenu) null)
                            .otherwise(contextMenu)
            );
            return row;
        });
    }
    @FXML
    private void onButtonAddClick() throws IOException {
        openDataForm(DataFormControllerInterface.Type.Add);
    }
    @FXML
    private void onButtonEditClick() throws IOException {
        openDataForm(DataFormControllerInterface.Type.Edit);
    }
    @FXML
    private void onButtonDeleteClick() {
        String sSelectedTab = tabs.getSelectionModel().getSelectedItem().getText();

        if (sSelectedTab.equalsIgnoreCase("заклади")) {
            deleteRecord(tableFast_foods);
        } else if (tabs.getSelectionModel().getSelectedItem().getText().equalsIgnoreCase("компанії")) {
            deleteRecord(tableCompanies);
        } else if (tabs.getSelectionModel().getSelectedItem().getText().equalsIgnoreCase("філії")) {
            deleteRecord(tableFiliations);
        } else if (tabs.getSelectionModel().getSelectedItem().getText().equalsIgnoreCase("страви")) {
            deleteRecord(tableMeals);
        } else if (tabs.getSelectionModel().getSelectedItem().getText().equalsIgnoreCase("напої")) {
            deleteRecord(tableDrinks);
        } else {
            deleteRecord(tableOrders);
        }
    }
    @FXML
    private void onButtonSearchClick() throws IOException {
        openDataForm(DataFormControllerInterface.Type.Search);
    }
    @FXML
    private void onButtonRefreshClick() {
        String sSelectedTab = tabs.getSelectionModel().getSelectedItem().getText();

        if (sSelectedTab.equalsIgnoreCase("заклади")) {
            loadDataToTableFromDatabase(tableFast_foods, null);
        } else if (tabs.getSelectionModel().getSelectedItem().getText().equalsIgnoreCase("компанії")) {
            loadDataToTableFromDatabase(tableCompanies, null);
        } else if (tabs.getSelectionModel().getSelectedItem().getText().equalsIgnoreCase("філії")) {
            loadDataToTableFromDatabase(tableFiliations, null);
        } else if (tabs.getSelectionModel().getSelectedItem().getText().equalsIgnoreCase("страви")) {
            loadDataToTableFromDatabase(tableMeals, null);
        } else if (tabs.getSelectionModel().getSelectedItem().getText().equalsIgnoreCase("напої")) {
            loadDataToTableFromDatabase(tableDrinks, null);
        } else {
            loadDataToTableFromDatabase(tableOrders, null);
        }
    }

    private void openDataForm(DataFormControllerInterface.Type type) throws IOException {
        String sSelectedTab = tabs.getSelectionModel().getSelectedItem().getText();
        TableView tableView;
        FXMLLoader loader;

        if (sSelectedTab.equalsIgnoreCase("заклади")) {
            tableView = tableFast_foods;
            loader = new FXMLLoader(getClass().getResource("/fast_foods_data_form/data.fxml"));
        } else if (tabs.getSelectionModel().getSelectedItem().getText().equalsIgnoreCase("компанії")) {
            tableView = tableCompanies;
            loader = new FXMLLoader(getClass().getResource("/companies_data_form/data.fxml"));
        } else if (tabs.getSelectionModel().getSelectedItem().getText().equalsIgnoreCase("філії")) {
            tableView = tableFiliations;
            loader = new FXMLLoader(getClass().getResource("/filiations_data_form/data.fxml"));
        } else if (tabs.getSelectionModel().getSelectedItem().getText().equalsIgnoreCase("страви")) {
            tableView = tableMeals;
            loader = new FXMLLoader(getClass().getResource("/meals_data_form/data.fxml"));
        } else if (tabs.getSelectionModel().getSelectedItem().getText().equalsIgnoreCase("напої")) {
            tableView = tableDrinks;
            loader = new FXMLLoader(getClass().getResource("/drinks_data_form/data.fxml"));
        } else {
            tableView = tableOrders;
            loader = new FXMLLoader(getClass().getResource("/orders_data_form/data.fxml"));
        }
        loader.load();

        DataFormControllerInterface controller = loader.getController();
        Stage dataFormStage = new Stage();

        if (type == DataFormControllerInterface.Type.Edit) {
            if (tableView.getSelectionModel().getSelectedItem() == null) {
                AlertsBuilder.start()
                        .setAlertType(Alert.AlertType.WARNING)
                        .setTitle("Помилка")
                        .setHeaderText("Помилка вибору")
                        .setContentText("Для редагування не було вибрано жодного значення у таблиці зверху!")
                        .build()
                        .showAndWait();
                return;
            }
            controller.setObjectToProcess((Owner) tableView.getSelectionModel().getSelectedItem());
        }
        setDataFormClickListeners(controller, type);
        dataFormStage.setScene(new Scene(loader.getRoot()));
        controller.setType(type);
        controller.setPrimaryStage(dataFormStage);
        dataFormStage.show();
    }

    private void setDataFormClickListeners(DataFormControllerInterface controller, DataFormControllerInterface.Type type) {
        OnMouseClickListener okListener = null;
        String sSelectedTab = tabs.getSelectionModel().getSelectedItem().getText();
        TableView tableView;

        if (sSelectedTab.equalsIgnoreCase("заклади")) {
            tableView = tableFast_foods;
        } else if (tabs.getSelectionModel().getSelectedItem().getText().equalsIgnoreCase("компанії")) {
            tableView = tableCompanies;
        } else if (tabs.getSelectionModel().getSelectedItem().getText().equalsIgnoreCase("філії")) {
            tableView = tableFiliations;
        } else if (tabs.getSelectionModel().getSelectedItem().getText().equalsIgnoreCase("страви")) {
            tableView = tableMeals;
        } else if (tabs.getSelectionModel().getSelectedItem().getText().equalsIgnoreCase("напої")) {
            tableView = tableDrinks;
        } else {
            tableView = tableOrders;
        }

        switch (type) {
            case Add:
                okListener = () -> controller.addObjectToTableView(tableView);
                break;
            case Edit:
                okListener = () -> controller.editObjectInTableView(tableView);
                break;
            case Search:
                okListener = () -> controller.search(this, tableView);
                break;
        }
        controller.setOnMouseOkClickListener(okListener);
    }

    void setupAllTables() {
        loadDataToTableFromDatabase(tableFast_foods, null);
        loadDataToTableFromDatabase(tableCompanies, null);
        loadDataToTableFromDatabase(tableFiliations, null);
        loadDataToTableFromDatabase(tableMeals, null);
        loadDataToTableFromDatabase(tableDrinks, null);
        loadDataToTableFromDatabase(tableOrders, null);
    }
    @SuppressWarnings("unchecked")
    public void loadDataToTableFromDatabase(TableView tableView, HashMap<String, Object> params) {
        for (Field field : getClass().getDeclaredFields()) {
            try {
                if (field.getType() == TableView.class && field.get(this) == tableView) {
                    tableView.setItems(
                            FXCollections.observableArrayList(
                                    DatabaseWorker.processQuery(
                                            ServerQuery.create(
                                                    field.getName().substring("table".length()).toLowerCase(),
                                                    "get", null, params
                                            )
                                    ).getObjects()
                            )
                    );
                    break;
                }
            } catch (IllegalAccessException ignored) {}
        }
    }

    private void deleteRecord(TableView tableView) {
        String tableName = null;

        for (Field field : getClass().getDeclaredFields()) {
            try {
                if (field.getType() == TableView.class && field.get(this) == tableView) {
                    tableName = field.getName().substring("table".length()).toLowerCase();

                    break;
                }
            } catch (IllegalAccessException ignored) {}
        }

        ServerResult result = DatabaseWorker.processQuery(
                ServerQuery.create(
                        tableName,
                        "delete",
                        (Owner) tableView.getSelectionModel().getSelectedItem(),
                        null
                )
        );

        if (result != null && result.getResult() == 0) {
            tableView.getItems().remove(tableView.getSelectionModel().getSelectedItem());
        } else {
            AlertsBuilder.start()
                    .setAlertType(Alert.AlertType.WARNING)
                    .setTitle("Видалення")
                    .setHeaderText("Помилка видалення")
                    .build()
                    .showAndWait();
        }
    }

    private <T extends Owner, E extends DataFormComboBoxControllerAdditional> void openAddMealForm(T filiationOrOrder) {
        FXMLLoader loader;

        if (filiationOrOrder instanceof Filiation) {
            loader = new FXMLLoader(getClass().getResource("/meal_to_filiation_data_form/data.fxml"));
        } else {
            loader = new FXMLLoader(getClass().getResource("/meal_to_order_data_form/data.fxml"));
        }

        try {
            loader.load();
        } catch (IOException ignored) {}

        E controller = loader.getController();
        Stage dataFormStage = new Stage();

        controller.setObjectToSearch(filiationOrOrder);
        dataFormStage.setScene(new Scene(loader.getRoot()));
        controller.setPrimaryStage(dataFormStage);
        dataFormStage.showAndWait();
    }

    private <T extends Owner, E extends DataFormComboBoxControllerAdditional> void openAddDrinkForm(T filiationOrOrder) {
        FXMLLoader loader;

        if (filiationOrOrder instanceof Filiation) {
            loader = new FXMLLoader(getClass().getResource("/drink_to_filiation_data_form/data.fxml"));
        } else {
            loader = new FXMLLoader(getClass().getResource("/drink_to_order_data_form/data.fxml"));
        }

        try {
            loader.load();
        } catch (IOException ignored) {}

        E controller = loader.getController();
        Stage dataFormStage = new Stage();

        controller.setObjectToSearch(filiationOrOrder);
        dataFormStage.setScene(new Scene(loader.getRoot()));
        controller.setPrimaryStage(dataFormStage);
        dataFormStage.showAndWait();
    }

    private <T extends Owner, E extends DataFormComboBoxControllerAdditional> void openAddToFiliationForm(T mealOrDrink) {
        FXMLLoader loader;

        if (mealOrDrink instanceof Meal) {
            loader = new FXMLLoader(getClass().getResource("/filiation_to_meal_data_form/data.fxml"));
        } else {
            loader = new FXMLLoader(getClass().getResource("/filiation_to_drink_data_form/data.fxml"));
        }

        try {
            loader.load();
        } catch (IOException ignored) {}

        E controller = loader.getController();
        Stage dataFormStage = new Stage();

        controller.setObjectToSearch(mealOrDrink);
        dataFormStage.setScene(new Scene(loader.getRoot()));
        controller.setPrimaryStage(dataFormStage);
        dataFormStage.showAndWait();
    }

    private <T extends Owner, E extends DataFormComboBoxControllerAdditional> void openAddToOrderForm(T mealOrDrink) {
        FXMLLoader loader;

        if (mealOrDrink instanceof Meal) {
            loader = new FXMLLoader(getClass().getResource("/order_to_meal_data_form/data.fxml"));
        } else {
            loader = new FXMLLoader(getClass().getResource("/order_to_drink_data_form/data.fxml"));
        }

        try {
            loader.load();
        } catch (IOException ignored) {}

        E controller = loader.getController();
        Stage dataFormStage = new Stage();

        controller.setObjectToSearch(mealOrDrink);
        dataFormStage.setScene(new Scene(loader.getRoot()));
        controller.setPrimaryStage(dataFormStage);
        dataFormStage.showAndWait();
    }
}
