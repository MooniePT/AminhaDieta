package app.ui.controller;

import app.model.AppState;
import app.model.UserProfile;
import app.persistence.DataStore;
import app.ui.SceneManager;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;

public class LoginController {

    @FXML
    private VBox profilesContainer;

    private SceneManager sceneManager;
    private AppState state;
    private DataStore store;

    public void init(SceneManager sceneManager, AppState state, DataStore store) {
        this.sceneManager = sceneManager;
        this.state = state;
        this.store = store;
        loadProfiles();
    }

    private void loadProfiles() {
        profilesContainer.getChildren().clear();

        if (state.getProfiles().isEmpty()) {
            // Should usually not happen here if we redirect empty state to Register, but
            // safe to handle
            return;
        }

        for (UserProfile p : state.getProfiles()) {
            Button btn = new Button(p.getNome());
            btn.getStyleClass().add("button-item"); // Assume existence or reuse button style
            btn.setMaxWidth(Double.MAX_VALUE);
            btn.setPrefHeight(50);
            btn.setOnAction(e -> selectProfile(p));
            profilesContainer.getChildren().add(btn);
        }
    }

    private void selectProfile(UserProfile p) {
        state.setActiveProfile(p.getId());
        store.save(state);
        sceneManager.showDashboard();
    }

    @FXML
    private void onNewProfile() {
        sceneManager.showRegister(true, null); // Can cancel back to login? Actually if no profiles, can't cancel.
        // Logic: if coming from Login, 'canCancel' means "can go back to login".
        // Existing RegisterController 'onCancelar' goes to Dashboard.
        // We might need to adjust RegisterController/SceneManager to handle "Cancel to
        // Login" vs "Cancel to Dashboard".
        // For now, let's assume 'true' goes to Dashboard, but if we are not logged in,
        // Dashboard redirects to Login?
        // Let's check SceneManager logic.
    }
}
