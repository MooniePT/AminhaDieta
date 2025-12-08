package app.ui.controller;

import app.model.AppState;
import app.model.Food;
import app.model.UserProfile;
import app.persistence.DataStore;
import app.ui.SceneManager;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;

import java.util.Comparator;

public class FoodDatabaseController {

    @FXML
    private TextField nameField;
    @FXML
    private TextField calField;
    @FXML
    private TextField protField;
    @FXML
    private TextField carbField;
    @FXML
    private TextField fatField;

    @FXML
    private ListView<String> foodList;
    @FXML
    private Label statusLabel;

    private AppState state;
    private DataStore store;

    public void init(SceneManager sceneManager, AppState state, DataStore store) {
        this.state = state;
        this.store = store;
        updateList();
    }

    @FXML
    private void onAddFood() {
        try {
            String name = nameField.getText().trim();
            if (name.isEmpty())
                throw new IllegalArgumentException("O nome é obrigatório");

            int cal = Integer.parseInt(calField.getText().trim());
            double prot = Double.parseDouble(protField.getText().trim().replace(",", "."));
            double carb = Double.parseDouble(carbField.getText().trim().replace(",", "."));
            double fat = Double.parseDouble(fatField.getText().trim().replace(",", "."));

            if (cal < 0 || prot < 0 || carb < 0 || fat < 0)
                throw new IllegalArgumentException("Os valores não podem ser negativos");

            UserProfile user = state.getActiveProfile();
            if (user != null) {
                user.getFoods().add(new Food(name, cal, prot, carb, fat));
                store.save(state);

                clearFields();
                updateList();
            }
        } catch (NumberFormatException e) {
            statusLabel.setText("Verifique os números.");
        } catch (IllegalArgumentException e) {
            statusLabel.setText(e.getMessage());
        }
    }

    private void clearFields() {
        nameField.clear();
        calField.clear();
        protField.clear();
        carbField.clear();
        fatField.clear();
        statusLabel.setText("");
        nameField.requestFocus();
    }

    private void updateList() {
        foodList.getItems().clear();
        UserProfile user = state.getActiveProfile();
        if (user == null)
            return;

        user.getFoods().stream()
                .sorted(Comparator.comparing(Food::getName))
                .forEach(f -> {
                    String label = String.format("%s (%dkcal, P:%.1f, C:%.1f, G:%.1f)",
                            f.getName(), f.getCaloriesPer100g(),
                            f.getProteinPer100g(), f.getCarbsPer100g(), f.getFatPer100g());
                    foodList.getItems().add(label);
                });
    }
}
