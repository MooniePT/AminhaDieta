package app.ui.controller;

import app.model.AppState;
import app.model.MealEntry;
import app.model.UserProfile;
import app.persistence.DataStore;
import app.ui.SceneManager;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.fxml.FXML;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;

import java.time.format.DateTimeFormatter;
import java.util.Comparator;
import java.util.stream.Collectors;

public class HistoryController {

    @FXML
    private TableView<MealEntry> mealsTable;
    @FXML
    private TableColumn<MealEntry, String> dateCol;
    @FXML
    private TableColumn<MealEntry, String> descCol;
    @FXML
    private TableColumn<MealEntry, Number> calCol;

    @FXML
    private LineChart<String, Number> historyChart;

    private AppState state;

    public void init(SceneManager sceneManager, AppState state, DataStore store) {
        this.state = state;

        setupTable();
        loadData();
    }

    private void setupTable() {
        DateTimeFormatter fmt = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");
        dateCol.setCellValueFactory(cell -> new SimpleStringProperty(cell.getValue().getTimestamp().format(fmt)));
        descCol.setCellValueFactory(cell -> new SimpleStringProperty(cell.getValue().getDescription()));
        calCol.setCellValueFactory(cell -> new SimpleIntegerProperty(cell.getValue().getCalories()));
    }

    private void loadData() {
        UserProfile user = state.getActiveProfile();
        if (user == null)
            return;

        // Table
        mealsTable.getItems().setAll(user.getMeals().stream()
                .sorted(Comparator.comparing(MealEntry::getTimestamp).reversed())
                .collect(Collectors.toList()));

        // Chart - Let's show Weight history
        // If we had a WeightEntry list, we would use it.
        // For now, we only have one weight (current).
        // Let's populate with dummy or implemented Weight entries if available.
        // UserProfile has 'private final List<WeightEntry> weights = new
        // ArrayList<>();'
        // So we can use that. But currently we don't save history, just overwrite
        // current.
        // REGISTER update overrides current.
        // But implementing proper history means we should add to 'weights'.
        // For now, charts will be empty or just show current point.

        XYChart.Series<String, Number> series = new XYChart.Series<>();
        series.setName("Peso (kg)");
        // Add current
        series.getData().add(new XYChart.Data<>("Atual", user.getPesoKg()));

        historyChart.getData().clear();
        historyChart.getData().add(series);
    }
}
