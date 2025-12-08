package app.ui.controller;

import app.model.AppState;
import app.model.UserProfile;
import app.persistence.DataStore;
import app.ui.SceneManager;
import javafx.fxml.FXML;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;

public class HomeController {

    @FXML
    private Label tituloLabel;
    @FXML
    private Label caloriesLabel;
    @FXML
    private ProgressBar caloriesBar;

    @FXML
    private Label waterLabel;
    @FXML
    private ProgressBar waterBar;

    @FXML
    private Label protLabel;
    @FXML
    private ProgressBar protBar;
    @FXML
    private Label carbLabel;
    @FXML
    private ProgressBar carbBar;
    @FXML
    private Label fatLabel;
    @FXML
    private ProgressBar fatBar;

    @FXML
    private Label bmiLabel;
    @FXML
    private Label bmiStatusLabel;
    @FXML
    private LineChart<String, Number> weightChart;

    private SceneManager sceneManager;
    private AppState state;

    public void init(SceneManager sceneManager, AppState state, DataStore store) {
        this.sceneManager = sceneManager;
        this.state = state;

        updateView();
    }

    private void updateView() {
        UserProfile user = state.getActiveProfile();
        if (user == null)
            return;

        tituloLabel.setText("Bem-vindo, " + user.getNome() + "!");

        // BMI
        double bmi = user.getBMI();
        bmiLabel.setText(String.format("%.1f", bmi));
        bmiStatusLabel.setText(getBMIStatus(bmi));

        // Calories
        int consumed = user.getCaloriesConsumedToday();
        int goal = user.getDailyCalorieGoal();
        caloriesLabel.setText(consumed + " / " + goal);
        caloriesBar.setProgress(goal > 0 ? (double) consumed / goal : 0);

        // Water
        double waterL = user.getWaterConsumedToday() / 1000.0;
        double waterGoalL = user.getDailyWaterGoalMl() / 1000.0;
        waterLabel.setText(String.format("%.1f L / %.1f L", waterL, waterGoalL));
        waterBar.setProgress(waterGoalL > 0 ? waterL / waterGoalL : 0);

        // Macros
        updateMacro(protLabel, protBar, user.getProteinConsumedToday(), user.getDailyProteinGoalGrams(), "g");
        updateMacro(carbLabel, carbBar, user.getCarbsConsumedToday(), user.getDailyCarbsGoalGrams(), "g");
        updateMacro(fatLabel, fatBar, user.getFatConsumedToday(), user.getDailyFatGoalGrams(), "g");

        // Chart (Mock data for now or real if implemented)
        XYChart.Series<String, Number> series = new XYChart.Series<>();
        series.setName("Peso");
        series.getData().add(new XYChart.Data<>("Hoje", user.getPesoKg()));
        weightChart.getData().clear();
        weightChart.getData().add(series);
    }

    private void updateMacro(Label label, ProgressBar bar, double current, double goal, String unit) {
        label.setText(String.format("%.0f%s / %.0f%s", current, unit, goal, unit));
        bar.setProgress(goal > 0 ? current / goal : 0);
    }

    private String getBMIStatus(double bmi) {
        if (bmi < 18.5)
            return "Baixo Peso";
        if (bmi < 25)
            return "Saudável";
        if (bmi < 30)
            return "Excesso de Peso";
        return "Obesidade";
    }

    @FXML
    private void onEditarPerfil() {
        sceneManager.showRegister(true, state.getActiveProfile());
    }
}
