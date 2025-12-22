package app.ui.controller;

import app.model.AppState;
import app.model.UserProfile;
import app.model.WaterEntry;
import app.persistence.DataStore;
import app.ui.SceneManager;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.TextField;

/**
 * Controlador responsável pela gestão do consumo diário de água.
 */
public class HydrationController {

    @FXML
    private Label mainLabel;
    @FXML
    private ProgressBar waterBar;
    @FXML
    private TextField customField;
    @FXML
    private Label statusLabel;

    private AppState state;
    private DataStore store;

    public void init(SceneManager sceneManager, AppState state, DataStore store) {
        this.state = state;
        this.store = store;
        updateView();
    }

    private void updateView() {
        UserProfile user = state.getActiveProfile();
        if (user == null)
            return;

        double consumedL = user.getWaterConsumedToday() / 1000.0;
        double goalL = user.getDailyWaterGoalMl() / 1000.0;

        mainLabel.setText(String.format("%.1f L / %.1f L", consumedL, goalL));
        waterBar.setProgress(goalL > 0 ? consumedL / goalL : 0);
    }

    @FXML
    private void add250() {
        addWater(250);
    }

    @FXML
    private void add500() {
        addWater(500);
    }

    @FXML
    private void addCustom() {
        try {
            double amount = Double.parseDouble(customField.getText().trim().replace(",", "."));
            if (amount <= 0)
                throw new NumberFormatException();
            addWater(amount);
            customField.clear();
            statusLabel.setText("");
        } catch (NumberFormatException e) {
            statusLabel.setText("Valor inválido.");
        }
    }

    @FXML
    private void removeLast() {
        UserProfile user = state.getActiveProfile();
        if (user != null && !user.getWaters().isEmpty()) {
            int size = user.getWaters().size();
            user.getWaters().remove(size - 1);
            store.save(state);
            updateView();
            statusLabel.setText("Último registo removido.");
        }
    }

    private void addWater(double ml) {
        UserProfile user = state.getActiveProfile();
        if (user != null) {
            user.getWaters().add(new WaterEntry(ml));
            store.save(state);
            updateView();
        }
    }
}
