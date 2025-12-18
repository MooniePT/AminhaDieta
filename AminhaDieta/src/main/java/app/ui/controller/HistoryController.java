package app.ui.controller;

import app.model.AppState;
import app.model.MealEntry;
import app.model.UserProfile;
import app.persistence.DataStore;
import app.ui.SceneManager;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.fxml.FXML;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;

import java.time.format.DateTimeFormatter;
import java.util.Comparator;
import java.util.stream.Collectors;

public class HistoryController {

    @FXML
    private TableView<MealEntry> mealsTable;
    @FXML
    private TableColumn<MealEntry, String> dateCol;
    @FXML
    private TableColumn<MealEntry, String> descCol;
    @FXML
    private TableColumn<MealEntry, Number> calCol;

    @FXML
    private LineChart<String, Number> historyChart;

    @FXML
    private javafx.scene.control.TextField searchField;

    private AppState state;
    private javafx.collections.transformation.FilteredList<MealEntry> filteredData;

    public void init(SceneManager sceneManager, AppState state, DataStore store) {
        this.state = state;

        setupTable();
        loadData();
        setupSearch();
    }

    private void setupTable() {
        DateTimeFormatter fmt = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");
        dateCol.setCellValueFactory(cell -> new SimpleStringProperty(cell.getValue().getTimestamp().format(fmt)));
        descCol.setCellValueFactory(cell -> new SimpleStringProperty(cell.getValue().getDescription()));
        calCol.setCellValueFactory(cell -> new SimpleIntegerProperty(cell.getValue().getCalories()));
    }

    private void loadData() {
        UserProfile user = state.getActiveProfile();
        if (user == null)
            return;

        // Wrap the ObservableList in a FilteredList (initially display all data)
        filteredData = new javafx.collections.transformation.FilteredList<>(
                javafx.collections.FXCollections.observableArrayList(user.getMeals()), p -> true);

        // Wrap the FilteredList in a SortedList.
        javafx.collections.transformation.SortedList<MealEntry> sortedData = new javafx.collections.transformation.SortedList<>(
                filteredData);

        // Bind the SortedList comparator to the TableView comparator.
        sortedData.comparatorProperty().bind(mealsTable.comparatorProperty());

        // Add sorted (and filtered) data to the table.
        mealsTable.setItems(sortedData);

        // Initial Sort by date reversed
        mealsTable.getSortOrder().add(dateCol);
        dateCol.setSortType(TableColumn.SortType.DESCENDING);
        mealsTable.sort();

        // Chart - Let's show Weight history
        XYChart.Series<String, Number> series = new XYChart.Series<>();
        series.setName("Peso (kg)");
        // Add current
        series.getData().add(new XYChart.Data<>("Atual", user.getPesoKg()));

        historyChart.getData().clear();
        historyChart.getData().add(series);
    }

    private void setupSearch() {
        searchField.textProperty().addListener((observable, oldValue, newValue) -> {
            filteredData.setPredicate(meal -> {
                // If filter text is empty, display all persons.
                if (newValue == null || newValue.isEmpty()) {
                    return true;
                }

                // Compare description of every meal with filter text.
                String lowerCaseFilter = newValue.toLowerCase();

                if (meal.getDescription().toLowerCase().contains(lowerCaseFilter)) {
                    return true; // Filter matches description.
                }
                return false; // Does not match.
            });
        });
    }

    @FXML
    private void handleDownloadPdf() {
        System.out.println("DEBUG: handleDownloadPdf invoked");
        try {
            if (mealsTable == null) {
                System.out.println("DEBUG: mealsTable is NULL");
                return;
            }
            if (mealsTable.getItems() == null || mealsTable.getItems().isEmpty()) {
                System.out.println("DEBUG: mealsTable items are empty or null");
            } else {
                System.out.println("DEBUG: mealsTable has " + mealsTable.getItems().size() + " items");
            }

            javafx.stage.FileChooser fileChooser = new javafx.stage.FileChooser();
            fileChooser.setTitle("Salvar Histórico em PDF");
            fileChooser.getExtensionFilters().add(new javafx.stage.FileChooser.ExtensionFilter("PDF Files", "*.pdf"));
            fileChooser.setInitialFileName("historico_refeicoes.pdf");

            javafx.stage.Window window = mealsTable.getScene().getWindow();
            java.io.File file = fileChooser.showSaveDialog(window);

            if (file != null) {
                System.out.println("DEBUG: Saving to file: " + file.getAbsolutePath());
                com.lowagie.text.Document document = new com.lowagie.text.Document();
                com.lowagie.text.pdf.PdfWriter.getInstance(document, new java.io.FileOutputStream(file));
                document.open();

                com.lowagie.text.Font titleFont = new com.lowagie.text.Font(com.lowagie.text.Font.HELVETICA, 18,
                        com.lowagie.text.Font.BOLD);
                com.lowagie.text.Paragraph title = new com.lowagie.text.Paragraph("Histórico de Refeições", titleFont);
                title.setAlignment(com.lowagie.text.Element.ALIGN_CENTER);
                title.setSpacingAfter(20);
                document.add(title);

                com.lowagie.text.pdf.PdfPTable table = new com.lowagie.text.pdf.PdfPTable(3);
                table.setWidthPercentage(100);
                table.setSpacingBefore(10f);
                table.setSpacingAfter(10f);

                table.addCell("Data/Hora");
                table.addCell("Descrição");
                table.addCell("Calorias");

                DateTimeFormatter fmt = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");
                for (MealEntry meal : mealsTable.getItems()) {
                    table.addCell(meal.getTimestamp().format(fmt));
                    table.addCell(meal.getDescription());
                    table.addCell(String.valueOf(meal.getCalories()));
                }

                document.add(table);
                document.close();
                System.out.println("DEBUG: PDF generated successfully");

                javafx.scene.control.Alert alert = new javafx.scene.control.Alert(
                        javafx.scene.control.Alert.AlertType.INFORMATION);
                alert.setTitle("Sucesso");
                alert.setHeaderText(null);
                alert.setContentText("PDF gerado com sucesso!");
                alert.showAndWait();
            } else {
                System.out.println("DEBUG: File selection cancelled");
            }
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("DEBUG: Exception in handleDownloadPdf: " + e.getMessage());
            javafx.scene.control.Alert alert = new javafx.scene.control.Alert(
                    javafx.scene.control.Alert.AlertType.ERROR);
            alert.setTitle("Erro");
            alert.setHeaderText("Erro ao gerar PDF");
            alert.setContentText(e.getMessage());
            alert.showAndWait();
        }
    }
}
