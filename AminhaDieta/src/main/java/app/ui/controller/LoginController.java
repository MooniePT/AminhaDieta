package app.ui.controller;

import app.model.AppState;
import app.model.UserProfile;
import app.persistence.DataStore;
import app.ui.SceneManager;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;

/**
 * Controlador responsável pelo ecrã de login e seleção de perfil.
 */
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
            // Normalmente não acontece aqui se redirecionarmos estado vazio para Registo,
            // mas
            // seguro de lidar
            return;
        }

        for (UserProfile p : state.getProfiles()) {
            Button btn = new Button(p.getNome());
            btn.getStyleClass().add("button-item"); // Assume existência ou reutiliza estilo de botão
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
        sceneManager.showRegister(true, null); // Pode cancelar de volta ao login?
        // Lógica: se vier do Login, 'canCancel' significa "pode voltar ao login".
        // RegisterController existente 'onCancelar' vai para o Dashboard.
        // Podemos precisar de ajustar RegisterController/SceneManager para lidar com
        // "Cancelar para Login" vs "Cancelar para Dashboard".
        // Por agora, assumimos que 'true' vai para Dashboard, mas se não estivermos
        // logados,
        // Dashboard redireciona para Login?
        // Vamos verificar a lógica do SceneManager.
    }
}
