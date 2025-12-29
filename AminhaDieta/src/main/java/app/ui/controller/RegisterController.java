package app.ui.controller;

import app.model.AppState;
import app.model.UserProfile;
import app.persistence.DataStore;
import app.ui.SceneManager;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.control.ComboBox;

/**
 * Controlador responsável pelo registo e edição de perfis de utilizador.
 */
public class RegisterController {

    @FXML
    private Label tituloLabel;
    @FXML
    private TextField nomeField;
    @FXML
    private TextField idadeField;
    @FXML
    private TextField pesoField;
    @FXML
    private TextField alturaField;
    @FXML
    private TextField metaPesoField;
    @FXML
    private ComboBox<UserProfile.WeighInFrequency> frequenciaCombo;
    @FXML
    private Label erroLabel;

    @FXML
    private RadioButton masculinoRadio;
    @FXML
    private RadioButton femininoRadio;
    private ToggleGroup genderGroup;

    @FXML
    private ComboBox<UserProfile.PhysicalActivityLevel> atividadeCombo;

    @FXML
    private Button guardarBtn;
    @FXML
    private Button cancelarBtn;
    @FXML
    private Button eliminarBtn;

    private SceneManager sceneManager;
    private AppState state;
    private DataStore store;

    private UserProfile profileToEdit;

    public void init(SceneManager sceneManager, AppState state, DataStore store, boolean canCancel,
            UserProfile profileToEdit) {
        this.sceneManager = sceneManager;
        this.state = state;
        this.store = store;
        this.profileToEdit = profileToEdit;

        // Configurar ToggleGroup
        genderGroup = new ToggleGroup();
        masculinoRadio.setToggleGroup(genderGroup);
        femininoRadio.setToggleGroup(genderGroup);
        masculinoRadio.setUserData(UserProfile.Gender.MALE);
        femininoRadio.setUserData(UserProfile.Gender.FEMALE);
        masculinoRadio.setSelected(true); // Padrão

        atividadeCombo.getItems().setAll(UserProfile.PhysicalActivityLevel.values());
        atividadeCombo.getSelectionModel().select(UserProfile.PhysicalActivityLevel.SEDENTARY);

        frequenciaCombo.getItems().setAll(UserProfile.WeighInFrequency.values());
        frequenciaCombo.getSelectionModel().select(UserProfile.WeighInFrequency.WEEKLY);

        // Se já existe user => modo editar (pré-preencher)
        if (profileToEdit != null) {
            tituloLabel.setText("Editar Perfil");
            guardarBtn.setText("Guardar Alterações");

            nomeField.setText(profileToEdit.getNome());
            idadeField.setText(String.valueOf(profileToEdit.getIdade()));
            pesoField.setText(String.valueOf(profileToEdit.getPesoKg()));
            alturaField.setText(String.valueOf(profileToEdit.getAlturaCm()));
            metaPesoField.setText(String.valueOf(profileToEdit.getTargetWeightKg()));
            frequenciaCombo.getSelectionModel().select(profileToEdit.getWeighInFrequency());

            if (profileToEdit.getGender() == UserProfile.Gender.MALE)
                masculinoRadio.setSelected(true);
            else if (profileToEdit.getGender() == UserProfile.Gender.FEMALE)
                femininoRadio.setSelected(true);

            if (profileToEdit.getPhysicalActivityLevel() != null)
                atividadeCombo.getSelectionModel().select(profileToEdit.getPhysicalActivityLevel());

            eliminarBtn.setVisible(true);
            eliminarBtn.setManaged(true);
        } else {
            tituloLabel.setText("Criar Perfil");
            guardarBtn.setText("Criar Perfil");

            eliminarBtn.setVisible(false);
            eliminarBtn.setManaged(false);
        }

        // Só mostra Cancelar quando fores para aqui a partir do Dashboard
        cancelarBtn.setVisible(canCancel);
        cancelarBtn.setManaged(canCancel);
    }

    @FXML
    private void onGuardar() {
        try {
            String nome = nomeField.getText().trim();
            int idade = Integer.parseInt(idadeField.getText().trim());
            double peso = Double.parseDouble(pesoField.getText().trim().replace(",", "."));
            double altura = Double.parseDouble(alturaField.getText().trim().replace(",", "."));
            double metaPeso = Double.parseDouble(metaPesoField.getText().trim().replace(",", "."));

            UserProfile.Gender gender = (UserProfile.Gender) genderGroup.getSelectedToggle().getUserData();
            UserProfile.PhysicalActivityLevel activityLevel = atividadeCombo.getValue();
            UserProfile.WeighInFrequency frequency = frequenciaCombo.getValue();

            if (nome.isEmpty())
                throw new IllegalArgumentException("Nome obrigatório.");
            if (idade <= 0)
                throw new IllegalArgumentException("Idade inválida.");
            if (peso <= 0)
                throw new IllegalArgumentException("Peso inválido.");
            if (altura <= 0)
                throw new IllegalArgumentException("Altura inválida.");
            if (metaPeso <= 0)
                throw new IllegalArgumentException("Meta de peso inválida.");

            if (profileToEdit != null) {
                profileToEdit.setNome(nome);
                profileToEdit.setIdade(idade);
                // If weight changed manually here, we might want to add an entry?
                // For now, just update current. Logic elsewhere handles history.
                // But if user corrects a typo, we don't want a new entry.
                // Let's assume editing profile updates current stats.
                if (profileToEdit.getPesoKg() != peso) {
                    profileToEdit.setPesoKg(peso);
                    profileToEdit.addWeightEntry(peso); // Add entry if weight changed in edit
                }
                profileToEdit.setAlturaCm(altura);
                profileToEdit.setGender(gender);
                profileToEdit.setPhysicalActivityLevel(activityLevel);
                profileToEdit.setTargetWeightKg(metaPeso);
                profileToEdit.setWeighInFrequency(frequency);
            } else {
                UserProfile newProfile = new UserProfile(nome, idade, peso, altura, gender, activityLevel, frequency,
                        metaPeso);
                state.addProfile(newProfile);
            }
            store.save(state);

            sceneManager.showDashboard();
        } catch (Exception e) {
            erroLabel.setText(e.getMessage() == null ? "Dados inválidos." : e.getMessage());
        }
    }

    @FXML
    private void onCancelar() {
        sceneManager.showDashboard();
    }

    @FXML
    private void onEliminar() {
        if (profileToEdit == null)
            return;

        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Eliminar Perfil");
        alert.setHeaderText("Tem a certeza que deseja eliminar este perfil?");
        alert.setContentText("Esta ação não pode ser desfeita.");

        if (alert.showAndWait().orElse(ButtonType.CANCEL) == ButtonType.OK) {
            state.removeProfile(profileToEdit.getId());
            store.save(state);
            sceneManager.showLogin();
        }
    }
}
