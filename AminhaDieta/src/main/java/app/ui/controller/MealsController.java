package app.ui.controller;

import app.model.AppState;
import app.model.Food;
import app.model.MealEntry;
import app.model.UserProfile;
import app.persistence.DataStore;
import app.ui.SceneManager;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.util.StringConverter;

import java.time.format.DateTimeFormatter;

public class MealsController {

    @FXML
    private ComboBox<Food> foodSelector;
    @FXML
    private TextField descriptionField;
    @FXML
    private TextField caloriesField;
    @FXML
    private TextField protField;
    @FXML
    private TextField carbField;
    @FXML
    private TextField fatField;

    @FXML
    private Label statusLabel;
    @FXML
    private ListView<String> mealsList;

    // Embedded Controller
    @FXML
    private FoodDatabaseController foodDatabaseController;

    private AppState state;
    private DataStore store;

    public void init(SceneManager sceneManager, AppState state, DataStore store) {
        this.state = state;
        this.store = store;

        // Init embedded controller if present (fx:include injects it + "Controller"
        // suffix naming convention often used,
        // but wait, standard FXML loader injection is id+"Controller". My id was
        // fx:id="foodDatabase".
        // So field should be foodDatabaseController? No, it injects the controller of
        // the included file.
        // Actually, let's pass data to the embedded controller manually if needed.
        if (foodDatabaseController != null) {
            foodDatabaseController.init(sceneManager, state, store);
            foodDatabaseController.setOnFoodAddedListener(this::updateList);
        }

        setupFoodSelector();
        updateList();
    }

    private void setupFoodSelector() {
        UserProfile user = state.getActiveProfile();
        if (user == null)
            return;

        foodSelector.setConverter(new StringConverter<Food>() {
            @Override
            public String toString(Food object) {
                return object == null ? "" : object.getName();
            }

            @Override
            public Food fromString(String string) {
                return null; // Not needed
            }
        });

        // Populate
        foodSelector.getItems().setAll(user.getFoods());

        // Refresh items when list changes?
        // Ideally we would use ObservableList, but for now simple init is okay.
        // User adds food in other tab -> might not update here immediately unless we
        // refresh.
        // Hook for tab switch? For now simple.
    }

    @FXML
    public void onFoodSelected() {
        Food f = foodSelector.getValue();
        if (f != null) {
            descriptionField.setText(f.getName());
            caloriesField.setText(String.valueOf(f.getCaloriesPer100g()));
            protField.setText(String.valueOf(f.getProteinPer100g()));
            carbField.setText(String.valueOf(f.getCarbsPer100g()));
            fatField.setText(String.valueOf(f.getFatPer100g()));
        }
    }

    @FXML
    private void onAddMeal() {
        try {
            String desc = descriptionField.getText().trim();
            String calStr = caloriesField.getText().trim();

            // Macros optional/default 0
            double p = parseDoubleOrZero(protField.getText());
            double c = parseDoubleOrZero(carbField.getText());
            double f = parseDoubleOrZero(fatField.getText());

            if (desc.isEmpty() || calStr.isEmpty()) {
                statusLabel.setText("A descrição e as calorias são obrigatórias.");
                return;
            }

            int cal = Integer.parseInt(calStr);
            if (cal <= 0)
                throw new NumberFormatException();

            UserProfile user = state.getActiveProfile();
            if (user != null) {
                user.getMeals().add(new MealEntry(desc, cal, p, c, f));
                store.save(state);

                statusLabel.setText("");
                descriptionField.clear();
                caloriesField.clear();
                protField.clear();
                carbField.clear();
                fatField.clear();
                foodSelector.getSelectionModel().clearSelection();

                updateList();
            }
        } catch (NumberFormatException e) {
            statusLabel.setText("Valores numéricos inválidos.");
        }
    }

    private double parseDoubleOrZero(String s) {
        if (s == null || s.trim().isEmpty())
            return 0;
        try {
            return Double.parseDouble(s.trim().replace(",", "."));
        } catch (Exception e) {
            return 0;
        }
    }

    private void updateList() {
        mealsList.getItems().clear();
        UserProfile user = state.getActiveProfile();
        if (user == null)
            return;

        // Refresh selector just in case new foods were added
        foodSelector.getItems().setAll(user.getFoods());

        java.time.LocalDate today = java.time.LocalDate.now();
        DateTimeFormatter timeFmt = DateTimeFormatter.ofPattern("HH:mm");

        for (MealEntry m : user.getMeals()) {
            if (m.getTimestamp().toLocalDate().equals(today)) {
                String line = String.format("[%s] %s - %d kcal (P:%.1f C:%.1f G:%.1f)",
                        m.getTimestamp().format(timeFmt),
                        m.getDescription(),
                        m.getCalories(),
                        m.getProtein(), m.getCarbs(), m.getFat());
                mealsList.getItems().add(0, line);
            }
        }
    }
}
