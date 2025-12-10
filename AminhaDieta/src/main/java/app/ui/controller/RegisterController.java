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
    private Label erroLabel;

    @FXML
    private RadioButton masculinoRadio;
    @FXML
    private RadioButton femininoRadio;
    private ToggleGroup genderGroup;

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

        // Setup ToggleGroup
        genderGroup = new ToggleGroup();
        masculinoRadio.setToggleGroup(genderGroup);
        femininoRadio.setToggleGroup(genderGroup);
        masculinoRadio.setUserData(UserProfile.Gender.MALE);
        femininoRadio.setUserData(UserProfile.Gender.FEMALE);
        masculinoRadio.setSelected(true); // Default

        // Se já existe user => modo editar (pré-preencher)
        if (profileToEdit != null) {
            tituloLabel.setText("Editar Perfil");
            guardarBtn.setText("Guardar Alterações");

            nomeField.setText(profileToEdit.getNome());
            idadeField.setText(String.valueOf(profileToEdit.getIdade()));
            pesoField.setText(String.valueOf(profileToEdit.getPesoKg()));
            alturaField.setText(String.valueOf(profileToEdit.getAlturaCm()));

            if (profileToEdit.getGender() == UserProfile.Gender.MALE)
                masculinoRadio.setSelected(true);
            else if (profileToEdit.getGender() == UserProfile.Gender.FEMALE)
                femininoRadio.setSelected(true);

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
            UserProfile.Gender gender = (UserProfile.Gender) genderGroup.getSelectedToggle().getUserData();

            if (nome.isEmpty())
                throw new IllegalArgumentException("Nome obrigatório.");
            if (idade <= 0)
                throw new IllegalArgumentException("Idade inválida.");
            if (peso <= 0)
                throw new IllegalArgumentException("Peso inválido.");
            if (altura <= 0)
                throw new IllegalArgumentException("Altura inválida.");

            if (profileToEdit != null) {
                profileToEdit.setNome(nome);
                profileToEdit.setIdade(idade);
                profileToEdit.setPesoKg(peso);
                profileToEdit.setAlturaCm(altura);
                profileToEdit.setGender(gender);
            } else {
                UserProfile newProfile = new UserProfile(nome, idade, peso, altura, gender);
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
