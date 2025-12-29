package app;

import app.model.AppState;
import app.persistence.DataStore;
import app.ui.SceneManager;
import javafx.application.Application;
import javafx.stage.Stage;

import java.nio.file.Path;

/**
 * Classe principal da aplicação JavaFX.
 * Configura o armazenamento de dados e inicia a interface gráfica.
 */
public class Main extends Application {

    @Override
    public void start(Stage stage) {
        // Ficheiro de dados (localizado na pasta AppData do utilizador para evitar
        // problemas de permissões)
        String appData = System.getenv("APPDATA");
        if (appData == null) {
            appData = System.getProperty("user.home");
        }
        Path dataPath = Path.of(appData, "AMinhaDieta", "data", "appstate.dat");

        DataStore store = new DataStore(dataPath);
        AppState state = store.load();

        SceneManager sceneManager = new SceneManager(stage, state, store);
        sceneManager.showInitialScene();

        stage.setTitle("A Minha Dieta");
        stage.getIcons().add(new javafx.scene.image.Image(getClass().getResourceAsStream("/images/icon.png")));
        stage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
