package app.ui.controller;

import app.model.AppState;
import app.model.ExerciseEntry;
import app.persistence.DataStore;
import app.ui.SceneManager;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.*;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Controlador responsável pela gestão e registo de exercícios físicos.
 */
public class ExerciseController {

    @FXML
    private ComboBox<String> typeCombo;
    @FXML
    private TextField durationField;
    @FXML
    private TextField caloriesField;
    @FXML
    private Label statusLabel;
    @FXML
    private ListView<String> exerciseList;

    private AppState state;
    private DataStore store;

    public void init(SceneManager sceneManager, AppState state, DataStore store) {

        this.state = state;
        this.store = store;

        typeCombo.getItems().addAll("Caminhada", "Corrida", "Ciclismo", "Natação", "Musculação", "Yoga");
        refreshList();
    }

    @FXML
    private void onAddExercise() {
        try {
            String type = typeCombo.getValue();
            if (type == null || type.trim().isEmpty()) {
                type = typeCombo.getEditor().getText();
            }
            if (type == null || type.trim().isEmpty()) {
                throw new IllegalArgumentException("Selecione ou digite o tipo de atividade.");
            }

            int duration = Integer.parseInt(durationField.getText().trim());
            int calories = Integer.parseInt(caloriesField.getText().trim());

            if (duration <= 0)
                throw new IllegalArgumentException("Duração inválida.");
            if (calories <= 0)
                throw new IllegalArgumentException("Calorias inválidas.");

            ExerciseEntry entry = new ExerciseEntry(LocalDateTime.now(), type, duration, calories);
            state.getActiveProfile().getExercises().add(entry);
            store.save(state);

            statusLabel.setText("");
            typeCombo.setValue(null);
            durationField.clear();
            caloriesField.clear();

            refreshList();

            javafx.scene.control.Alert alert = new javafx.scene.control.Alert(
                    javafx.scene.control.Alert.AlertType.INFORMATION);
            alert.setTitle("Bom Trabalho!");
            alert.setHeaderText("Exercício Registado!");
            alert.setContentText("Continua assim! O teu corpo agradece.");
            alert.showAndWait();
        } catch (NumberFormatException e) {
            statusLabel.setText("Duração e Calorias devem ser números inteiros.");
        } catch (IllegalArgumentException e) {
            statusLabel.setText(e.getMessage());
        } catch (Exception e) {
            statusLabel.setText("Erro ao adicionar atividade.");
        }
    }

    private void refreshList() {
        if (state.getActiveProfile() == null)
            return;

        java.time.LocalDate today = java.time.LocalDate.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm");

        List<String> items = state.getActiveProfile().getExercises().stream()
                .filter(e -> e.getTimestamp().toLocalDate().equals(today))
                .map(e -> String.format("[%s] %s - %d min - %d kcal",
                        e.getTimestamp().format(formatter), e.getType(), e.getDurationMinutes(), e.getCaloriesBurned()))
                .collect(Collectors.toList());

        exerciseList.setItems(FXCollections.observableArrayList(items));
    }
}
