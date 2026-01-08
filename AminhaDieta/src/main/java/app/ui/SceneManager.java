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
            Scene scene = new Scene(loader.load(), 1280, 800);
            scene.getStylesheets().add(getClass().getResource("/css/styles.css").toExternalForm());

            RegisterController controller = loader.getController();
            controller.init(this, state, store, canCancel, profileToEdit);

            stage.setScene(scene);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public void showLogin() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/LoginView.fxml"));
            Scene scene = new Scene(loader.load(), 1280, 800);
            scene.getStylesheets().add(getClass().getResource("/css/styles.css").toExternalForm());

            app.ui.controller.LoginController controller = loader.getController();
            controller.init(this, state, store);

            stage.setScene(scene);
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
            Scene scene = new Scene(loader.load(), 1280, 800);
            scene.getStylesheets().add(getClass().getResource("/css/styles.css").toExternalForm());

            DashboardController controller = loader.getController();
            controller.init(this, state, store);

            stage.setScene(scene);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public void showInitialScene() {
        if (state.getProfiles().isEmpty())
            showRegister(false, null);
        else
            showLogin();
    }

}
