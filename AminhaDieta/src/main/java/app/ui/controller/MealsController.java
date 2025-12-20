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
import javafx.scene.control.TextInputDialog;
import javafx.util.StringConverter;

import java.time.format.DateTimeFormatter;

/**
 * Controlador responsável pelo registo e gestão de refeições.
 * Permite adicionar refeições manualmente ou através de atalhos rápidos.
 */
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

    // Controlador Embutido
    @FXML
    private FoodDatabaseController foodDatabaseController;

    private AppState state;
    private DataStore store;

    public void init(SceneManager sceneManager, AppState state, DataStore store) {
        this.state = state;
        this.store = store;

        // Inicializar controlador embutido se presente
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

        // Preencher
        foodSelector.getItems().setAll(user.getFoods());

        // Atualizar itens quando a lista muda?
        // Idealmente usaríamos ObservableList, mas por agora inicialização simples é
        // ok.
        // Utilizador adiciona comida noutra aba -> pode não atualizar aqui
        // imediatamente a menos que
        // atualizemos.
        // Hook para troca de aba? Por agora simples.
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

            // Macros opcionais/padrão 0
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

    private void askQuantityAndAdd(String name, double kcal100, double p100, double c100, double f100,
            boolean isLiquid) {
        TextInputDialog dialog = new TextInputDialog();
        dialog.setTitle("Adicionar " + name);
        dialog.setHeaderText("Quantidade de " + name);
        dialog.setContentText(isLiquid ? "Quantidade (ml):" : "Quantidade (g):");

        dialog.showAndWait().ifPresent(result -> {
            try {
                double qty = Double.parseDouble(result.trim());
                if (qty <= 0)
                    return;

                double ratio = qty / 100.0;
                int cal = (int) (kcal100 * ratio);
                double p = p100 * ratio;
                double c = c100 * ratio;
                double f = f100 * ratio;

                UserProfile user = state.getActiveProfile();
                if (user != null) {
                    user.getMeals()
                            .add(new MealEntry(name + " (" + (int) qty + (isLiquid ? "ml" : "g") + ")", cal, p, c, f));
                    store.save(state);
                    updateList();
                }
            } catch (NumberFormatException e) {
                // Ignore
            }
        });
    }

    @FXML
    private void onAddRice() {
        askQuantityAndAdd("Arroz", 130, 2.7, 28, 0.3, false);
    }

    @FXML
    private void onAddPasta() {
        askQuantityAndAdd("Massa", 131, 5, 25, 1.1, false);
    }

    @FXML
    private void onAddPotato() {
        askQuantityAndAdd("Batata", 87, 1.9, 20, 0.1, false);
    }

    @FXML
    private void onAddMilk() {
        askQuantityAndAdd("Leite", 47, 3.4, 4.9, 1.5, true);
    }

    @FXML
    private void onAddEggs() {
        // Ovos (aprox. 155 kcal/100g)
        askQuantityAndAdd("Ovos", 155, 13, 1.1, 11, false);
    }

    @FXML
    private void onAddBread() {
        // Pão (aprox. 265 kcal/100g)
        askQuantityAndAdd("Pão", 265, 9, 49, 3.2, false);
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

        // Atualizar seletor caso novos alimentos tenham sido adicionados
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
