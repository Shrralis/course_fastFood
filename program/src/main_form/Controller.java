package main_form;

import base.AlertsBuilder;
import base.DataFormControllerInterface;
import base.OnMouseClickListener;
import javafx.beans.binding.Bindings;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;
import models.DateWorker;
import models.Owner;
import models.ServerQuery;
import models.ServerResult;
import server.DatabaseWorker;

import java.io.IOException;
import java.lang.reflect.Field;
import java.util.HashMap;

@SuppressWarnings("unchecked")
public class Controller {
    @FXML private TabPane tabs;
    @FXML private TableView<Result> tableResults;
    @FXML private TableView<Race> tableRaces;
    @FXML private TableView<Team> tableTeams;
    @FXML TableColumn<Team, String> columnBirthDateTableTeams;
    @FXML TableColumn<Team, String> columnFirstRaceDateTableTeams;
    @FXML private TableView<Pilot> tablePilots;
    @FXML TableColumn<Pilot, String> columnBirthDateTablePilots;
    @FXML TableColumn<Pilot, String> columnFirstRaceDateTablePilots;
    @FXML private TableView<Sponsor> tableSponsors;
    @FXML private TableView<Car> tableCars;

    @FXML
    public void initialize() {
        columnBirthDateTableTeams.setCellValueFactory(param -> {
            if (param.getValue() != null && param.getValue().getBirthdate() != null) {
                return new SimpleStringProperty(DateWorker.convertToString(param.getValue().getBirthdate()));
            } else {
                return new SimpleStringProperty("невідомо");
            }
        });
        columnFirstRaceDateTableTeams.setCellValueFactory(param -> {
            if (param.getValue() != null && param.getValue().getFirst_race_date() != null) {
                return new SimpleStringProperty(DateWorker.convertToString(param.getValue().getFirst_race_date()));
            } else {
                return new SimpleStringProperty("невідомо");
            }
        });
        columnBirthDateTablePilots.setCellValueFactory(param -> {
            if (param.getValue() != null && param.getValue().getBirthdate() != null) {
                return new SimpleStringProperty(DateWorker.convertToString(param.getValue().getBirthdate()));
            } else {
                return new SimpleStringProperty("невідомо");
            }
        });
        columnFirstRaceDateTablePilots.setCellValueFactory(param -> {
            if (param.getValue() != null && param.getValue().getFirst_race_date() != null) {
                return new SimpleStringProperty(DateWorker.convertToString(param.getValue().getFirst_race_date()));
            } else {
                return new SimpleStringProperty("невідомо");
            }
        });
        tableTeams.setRowFactory(param -> {
            final TableRow<Team> row = new TableRow<>();
            final ContextMenu contextMenu = new ContextMenu();
            final MenuItem addSponsor = new MenuItem("Додати спонсора");
            final MenuItem findSponsors = new MenuItem("Спонсори команди");

            addSponsor.setOnAction(event -> openAddSponsorForm());
            findSponsors.setOnAction(event -> {
                HashMap<String, Object> params = new HashMap<>();

                params.put("team", tableTeams.getSelectionModel().getSelectedItem().getId());
                tableSponsors.setItems(
                        FXCollections.observableArrayList(
                                DatabaseWorker.processQuery(
                                        ServerQuery.create(
                                                "teams_has_sponsors",
                                                "get",
                                                null,
                                                params
                                        )
                                ).getObjects()
                        )
                );
                tabs.getSelectionModel().select(4);
            });
            contextMenu.getItems().addAll(addSponsor, findSponsors);
            row.contextMenuProperty().bind(
                    Bindings.when(row.emptyProperty())
                            .then((ContextMenu) null)
                            .otherwise(contextMenu)
            );
            return row;
        });
        tableSponsors.setRowFactory(param -> {
            final TableRow<Sponsor> row = new TableRow<>();
            final ContextMenu contextMenu = new ContextMenu();
            final MenuItem addToTeam = new MenuItem("Додати до команди");
            final MenuItem findTeams = new MenuItem("Спонсоровані команди");

            addToTeam.setOnAction(event -> openAddToTeamForm());
            findTeams.setOnAction(event -> {
                HashMap<String, Object> params = new HashMap<>();

                params.put("sponsor", tableSponsors.getSelectionModel().getSelectedItem().getId());
                tableTeams.setItems(
                        FXCollections.observableArrayList(
                                DatabaseWorker.processQuery(
                                        ServerQuery.create(
                                                "teams_has_sponsors",
                                                "get",
                                                null,
                                                params
                                        )
                                ).getObjects()
                        )
                );
                tabs.getSelectionModel().select(2);
            });
            contextMenu.getItems().addAll(addToTeam, findTeams);
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

        if (sSelectedTab.equalsIgnoreCase("результати")) {
            deleteRecord(tableResults);
        } else if (tabs.getSelectionModel().getSelectedItem().getText().equalsIgnoreCase("гонки")) {
            deleteRecord(tableRaces);
        } else if (tabs.getSelectionModel().getSelectedItem().getText().equalsIgnoreCase("команди")) {
            deleteRecord(tableTeams);
        } else if (tabs.getSelectionModel().getSelectedItem().getText().equalsIgnoreCase("пілоти")) {
            deleteRecord(tablePilots);
        } else if (tabs.getSelectionModel().getSelectedItem().getText().equalsIgnoreCase("спонсори")) {
            deleteRecord(tableSponsors);
        } else {
            deleteRecord(tableCars);
        }
    }
    @FXML
    private void onButtonSearchClick() throws IOException {
        openDataForm(DataFormControllerInterface.Type.Search);
    }
    @FXML
    private void onButtonRefreshClick() {
        String sSelectedTab = tabs.getSelectionModel().getSelectedItem().getText();

        if (sSelectedTab.equalsIgnoreCase("результати")) {
            loadDataToTableFromDatabase(tableResults, null);
        } else if (tabs.getSelectionModel().getSelectedItem().getText().equalsIgnoreCase("гонки")) {
            loadDataToTableFromDatabase(tableRaces, null);
        } else if (tabs.getSelectionModel().getSelectedItem().getText().equalsIgnoreCase("команди")) {
            loadDataToTableFromDatabase(tableTeams, null);
        } else if (tabs.getSelectionModel().getSelectedItem().getText().equalsIgnoreCase("пілоти")) {
            loadDataToTableFromDatabase(tablePilots, null);
        } else if (tabs.getSelectionModel().getSelectedItem().getText().equalsIgnoreCase("спонсори")) {
            loadDataToTableFromDatabase(tableSponsors, null);
        } else {
            loadDataToTableFromDatabase(tableCars, null);
        }
    }

    private void openDataForm(DataFormControllerInterface.Type type) throws IOException {
        String sSelectedTab = tabs.getSelectionModel().getSelectedItem().getText();
        TableView tableView;
        FXMLLoader loader;

        if (sSelectedTab.equalsIgnoreCase("результати")) {
            tableView = tableResults;
            loader = new FXMLLoader(getClass().getResource("/results_data_form/data.fxml"));
        } else if (tabs.getSelectionModel().getSelectedItem().getText().equalsIgnoreCase("гонки")) {
            tableView = tableRaces;
            loader = new FXMLLoader(getClass().getResource("/races_data_form/data.fxml"));
        } else if (tabs.getSelectionModel().getSelectedItem().getText().equalsIgnoreCase("команди")) {
            tableView = tableTeams;
            loader = new FXMLLoader(getClass().getResource("/teams_data_form/data.fxml"));
        } else if (tabs.getSelectionModel().getSelectedItem().getText().equalsIgnoreCase("пілоти")) {
            tableView = tablePilots;
            loader = new FXMLLoader(getClass().getResource("/pilots_data_form/data.fxml"));
        } else if (tabs.getSelectionModel().getSelectedItem().getText().equalsIgnoreCase("спонсори")) {
            tableView = tableSponsors;
            loader = new FXMLLoader(getClass().getResource("/sponsors_data_form/data.fxml"));
        } else {
            tableView = tableCars;
            loader = new FXMLLoader(getClass().getResource("/cars_data_form/data.fxml"));
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

        if (sSelectedTab.equalsIgnoreCase("результати")) {
            tableView = tableResults;
        } else if (tabs.getSelectionModel().getSelectedItem().getText().equalsIgnoreCase("гонки")) {
            tableView = tableRaces;
        } else if (tabs.getSelectionModel().getSelectedItem().getText().equalsIgnoreCase("команди")) {
            tableView = tableTeams;
        } else if (tabs.getSelectionModel().getSelectedItem().getText().equalsIgnoreCase("пілоти")) {
            tableView = tablePilots;
        } else if (tabs.getSelectionModel().getSelectedItem().getText().equalsIgnoreCase("спонсори")) {
            tableView = tableSponsors;
        } else {
            tableView = tableCars;
        }

        switch (type) {
            case Add:
                okListener = () -> {
                    controller.addObjectToTableView(tableView);
                };
                break;
            case Edit:
                okListener = () -> {
                    controller.editObjectInTableView(tableView);
                };
                break;
            case Search:
                okListener = () -> {
                    System.out.println("doing search");
                    controller.search(this, tableView);
                    System.out.println("search finished");
                };
                break;
        }
        controller.setOnMouseOkClickListener(okListener);
    }

    void setupAllTables() {
        loadDataToTableFromDatabase(tableResults, null);
        loadDataToTableFromDatabase(tableRaces, null);
        loadDataToTableFromDatabase(tableTeams, null);
        loadDataToTableFromDatabase(tablePilots, null);
        loadDataToTableFromDatabase(tableSponsors, null);
        loadDataToTableFromDatabase(tableCars, null);
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

    private void openAddSponsorForm() {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/team_to_sponsor_data_form/data.fxml"));

        try {
            loader.load();
        } catch (IOException ignored) {}

        team_to_sponsor_data_form.Controller controller = loader.getController();
        Stage dataFormStage = new Stage();

        controller.setTeam(tableTeams.getSelectionModel().getSelectedItem());
        dataFormStage.setScene(new Scene(loader.getRoot()));
        controller.setPrimaryStage(dataFormStage);
        dataFormStage.showAndWait();
    }

    private void openAddToTeamForm() {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/sponsor_to_team_data_form/data.fxml"));

        try {
            loader.load();
        } catch (IOException ignored) {}

        sponsor_to_team_data_form.Controller controller = loader.getController();
        Stage dataFormStage = new Stage();

        controller.setSponsor(tableSponsors.getSelectionModel().getSelectedItem());
        dataFormStage.setScene(new Scene(loader.getRoot()));
        controller.setPrimaryStage(dataFormStage);
        dataFormStage.showAndWait();
    }
}
