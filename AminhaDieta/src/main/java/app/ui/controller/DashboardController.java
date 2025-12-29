package app.ui.controller;

import app.model.AppState;
import app.persistence.DataStore;
import app.ui.SceneManager;
import javafx.fxml.FXML;
import javafx.scene.layout.StackPane;
import app.model.UserProfile;
import javafx.scene.control.TextInputDialog;
import javafx.scene.control.Alert;
import java.util.Optional;

/**
 * Controlador principal do Dashboard, responsável pela navegação entre as
 * diferentes vistas.
 */
public class DashboardController {

    @FXML
    private StackPane contentArea;

    private SceneManager sceneManager;
    private AppState state;
    private DataStore store;

    private javafx.animation.Timeline rainbowTimeline;
    private boolean isRainbowEnabled = true;
    private String currentFont = "Verdana";
    private String staticColor = "#E0F7FA";

    public void init(SceneManager sceneManager, AppState state, DataStore store) {
        this.sceneManager = sceneManager;
        this.state = state;
        this.store = store;

        onHome(); // Carrega a vista inicial por defeito
        startRainbowAnimation();

        // Check for weigh-in
        javafx.application.Platform.runLater(this::checkWeighIn);
    }

    private void checkWeighIn() {
        UserProfile user = state.getActiveProfile();
        if (user != null && user.isWeighInDue()) {
            TextInputDialog dialog = new TextInputDialog();
            dialog.setTitle("Registo de Peso");
            dialog.setHeaderText("Está na altura de se pesar!");
            dialog.setContentText("Por favor, introduza o seu peso atual (kg):");

            Optional<String> result = dialog.showAndWait();
            result.ifPresent(weightStr -> {
                try {
                    double weight = Double.parseDouble(weightStr.replace(",", "."));
                    if (weight > 0) {
                        user.addWeightEntry(weight);
                        store.save(state);

                        // Check target
                        checkTargetAchievement(user);
                    }
                } catch (NumberFormatException e) {
                    // Ignore invalid input
                }
            });
        }
    }

    private void checkTargetAchievement(UserProfile user) {
        double current = user.getPesoKg();
        double target = user.getTargetWeightKg();

        // Check if within 0.5kg of target
        if (Math.abs(current - target) <= 0.5) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Parabéns!");
            alert.setHeaderText("Atingiu a sua meta de peso!");
            alert.setContentText("Excelente trabalho! Continue assim para manter a sua saúde.");
            alert.showAndWait();
        }
    }

    public boolean isRainbowEnabled() {
        return isRainbowEnabled;
    }

    public void setRainbowEnabled(boolean enabled) {
        this.isRainbowEnabled = enabled;
        if (enabled) {
            if (rainbowTimeline != null)
                rainbowTimeline.play();
            else
                startRainbowAnimation();
        } else {
            if (rainbowTimeline != null)
                rainbowTimeline.stop();
            applyStaticStyle();
        }
    }

    public void setStaticColor(String hexColor) {
        this.staticColor = hexColor;
        if (!isRainbowEnabled)
            applyStaticStyle();
    }

    public void setFont(String fontName) {
        this.currentFont = fontName;
        if (!isRainbowEnabled)
            applyStaticStyle();
    }

    private void applyStaticStyle() {
        contentArea.getScene().getRoot().setStyle(
                String.format("-fx-background-color: %s; -fx-font-family: '%s';", staticColor, currentFont));
    }

    private void startRainbowAnimation() {
        if (rainbowTimeline != null) {
            rainbowTimeline.play();
            return;
        }

        rainbowTimeline = new javafx.animation.Timeline(
                new javafx.animation.KeyFrame(javafx.util.Duration.millis(100), e -> {
                    double hue = (System.currentTimeMillis() % 10000) / 10000.0 * 360;
                    String color1 = String.format("hsb(%.0f, 20%%, 95%%)", hue);
                    String color2 = String.format("hsb(%.0f, 20%%, 90%%)", (hue + 40) % 360);
                    contentArea.getScene().getRoot().setStyle(
                            String.format(
                                    "-fx-background-color: linear-gradient(to bottom right, %s, %s); -fx-font-family: '%s';",
                                    color1, color2, currentFont));
                }));
        rainbowTimeline.setCycleCount(javafx.animation.Animation.INDEFINITE);
        rainbowTimeline.play();
    }

    @FXML
    private void onSettings() {
        try {
            javafx.fxml.FXMLLoader loader = new javafx.fxml.FXMLLoader(
                    getClass().getResource("/fxml/SettingsView.fxml"));
            javafx.scene.Parent view = loader.load();

            SettingsController controller = loader.getController();
            controller.init(this);

            contentArea.getChildren().setAll(view);
        } catch (Exception e) {
            e.printStackTrace();
        }
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
    private void onExercise() {
        loadView("/fxml/ExerciseView.fxml");
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

            // Injetar dependências manualmente com base no tipo de controlador
            Object controller = loader.getController();
            if (controller instanceof HomeController) {
                ((HomeController) controller).init(sceneManager, state, store);
            } else if (controller instanceof MealsController) {
                ((MealsController) controller).init(sceneManager, state, store);
            } else if (controller instanceof HydrationController) {
                ((HydrationController) controller).init(sceneManager, state, store);
            } else if (controller instanceof HistoryController) {
                ((HistoryController) controller).init(sceneManager, state, store);
            } else if (controller instanceof ExerciseController) {
                ((ExerciseController) controller).init(sceneManager, state, store);
            }

            contentArea.getChildren().setAll(view);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
