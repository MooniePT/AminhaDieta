package app.ui.controller;

import app.model.AppState;
import app.persistence.DataStore;
import app.ui.SceneManager;
import javafx.fxml.FXML;
import javafx.scene.layout.StackPane;

public class DashboardController {

    @FXML
    private StackPane contentArea;

    private SceneManager sceneManager;
    private AppState state;
    private DataStore store;

    public void init(SceneManager sceneManager, AppState state, DataStore store) {
        this.sceneManager = sceneManager;
        this.state = state;
        this.store = store;

        onHome(); // Load home by default
    }

    @FXML
    private void onHome() {
        loadView("/fxml/HomeView.fxml");
    }

    @FXML
    private void onMeals() {
        loadView("/fxml/MealsView.fxml");
    }

    @FXML
    private void onHydration() {
        loadView("/fxml/HydrationView.fxml");
    }

    @FXML
    private void onHistory() {
        loadView("/fxml/HistoryView.fxml");
    }

    @FXML
    private void onTrocarPerfil() {
        state.setActiveProfile(null);
        store.save(state);
        sceneManager.showLogin();
    }

    private void loadView(String fxmlPath) {
        try {
            javafx.fxml.FXMLLoader loader = new javafx.fxml.FXMLLoader(getClass().getResource(fxmlPath));
            javafx.scene.Parent view = loader.load();

            // Inject dependencies manually based on controller type
            Object controller = loader.getController();
            if (controller instanceof HomeController) {
                ((HomeController) controller).init(sceneManager, state, store);
            } else if (controller instanceof MealsController) {
                ((MealsController) controller).init(sceneManager, state, store);
            } else if (controller instanceof HydrationController) {
                ((HydrationController) controller).init(sceneManager, state, store);
            } else if (controller instanceof HistoryController) {
                ((HistoryController) controller).init(sceneManager, state, store);
            }

            contentArea.getChildren().setAll(view);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
