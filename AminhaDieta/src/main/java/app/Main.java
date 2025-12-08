package app;

import app.model.AppState;
import app.persistence.DataStore;
import app.ui.SceneManager;
import javafx.application.Application;
import javafx.stage.Stage;

import java.nio.file.Path;

public class Main extends Application {

    @Override
    public void start(Stage stage) {
        // ficheiro de dados (fica dentro do teu projeto / pasta do utilizador)
        Path dataPath = Path.of("data", "appstate.dat");

        DataStore store = new DataStore(dataPath);
        AppState state = store.load();

        SceneManager sceneManager = new SceneManager(stage, state, store);
        sceneManager.showInitialScene();

        stage.setTitle("A Minha Dieta");
        stage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
