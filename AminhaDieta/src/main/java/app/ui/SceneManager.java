package app.ui;

import app.model.AppState;
import app.persistence.DataStore;
import app.ui.controller.DashboardController;
import app.ui.controller.RegisterController;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class SceneManager {

    private final Stage stage;
    private final AppState state;
    private final DataStore store;

    public SceneManager(Stage stage, AppState state, DataStore store) {
        this.stage = stage;
        this.state = state;
        this.store = store;
    }

    public void showRegister(boolean canCancel, app.model.UserProfile profileToEdit) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/RegisterView.fxml"));
            javafx.scene.Parent root = loader.load(); // Load root first

            RegisterController controller = loader.getController();
            controller.init(this, state, store, canCancel, profileToEdit);

            setSceneRoot(root);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public void showLogin() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/LoginView.fxml"));
            javafx.scene.Parent root = loader.load();

            app.ui.controller.LoginController controller = loader.getController();
            controller.init(this, state, store);

            setSceneRoot(root);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public void showDashboard() {
        if (state.getActiveProfile() == null) {
            showLogin();
            return;
        }
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/DashboardView.fxml"));
            javafx.scene.Parent root = loader.load();

            DashboardController controller = loader.getController();
            controller.init(this, state, store);

            setSceneRoot(root);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public void showInitialScene() {
        if (state.getProfiles().isEmpty())
            showRegister(false, null);
        else
            showLogin(); // Always show login if profiles exist, don't auto-login
    }

    private void setSceneRoot(javafx.scene.Parent root) {
        Scene scene = stage.getScene();
        if (scene == null) {
            scene = new Scene(root);
            scene.getStylesheets().add(getClass().getResource("/css/styles.css").toExternalForm());
            stage.setScene(scene);
        } else {
            scene.setRoot(root);
        }
    }

}
