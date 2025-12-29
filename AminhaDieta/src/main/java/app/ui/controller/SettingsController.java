package app.ui.controller;

import javafx.fxml.FXML;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ColorPicker;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;

public class SettingsController {

    @FXML
    private CheckBox rainbowCheck;
    @FXML
    private ColorPicker themeColorPicker;
    @FXML
    private ComboBox<String> fontCombo;
    @FXML
    private HBox colorBox;
    @FXML
    private Label statusLabel;

    private DashboardController dashboardController;

    public void init(DashboardController dashboardController) {
        this.dashboardController = dashboardController;

        // Initialize controls based on current state
        rainbowCheck.setSelected(dashboardController.isRainbowEnabled());
        colorBox.setDisable(rainbowCheck.isSelected());

        themeColorPicker.setValue(Color.web("#E0F7FA")); // Default

        fontCombo.getItems().addAll("Verdana", "Arial", "Segoe UI", "Tahoma", "Comic Sans MS");
        fontCombo.setValue("Verdana");
    }

    @FXML
    private void onRainbowToggled() {
        boolean enabled = rainbowCheck.isSelected();
        dashboardController.setRainbowEnabled(enabled);
        colorBox.setDisable(enabled);
        statusLabel.setText(enabled ? "Modo Arco-íris ativado!" : "Modo Estático ativado.");
    }

    @FXML
    private void onColorChanged() {
        if (!rainbowCheck.isSelected()) {
            Color c = themeColorPicker.getValue();
            String hex = String.format("#%02X%02X%02X",
                    (int) (c.getRed() * 255),
                    (int) (c.getGreen() * 255),
                    (int) (c.getBlue() * 255));
            dashboardController.setStaticColor(hex);
        }
    }

    @FXML
    private void onFontChanged() {
        String font = fontCombo.getValue();
        if (font != null) {
            dashboardController.setFont(font);
        }
    }
}
