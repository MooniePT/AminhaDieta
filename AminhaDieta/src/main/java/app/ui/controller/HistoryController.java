package app.ui.controller;

import app.model.AppState;
import app.model.ExerciseEntry;
import app.model.MealEntry;
import app.model.UserProfile;
import app.persistence.DataStore;
import app.ui.SceneManager;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.fxml.FXML;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Comparator;
import java.util.stream.Collectors;
import java.io.ByteArrayOutputStream;
import javax.imageio.ImageIO;
import javafx.embed.swing.SwingFXUtils;
import javafx.scene.image.WritableImage;
import javafx.scene.SnapshotParameters;
import com.lowagie.text.Image;
import com.lowagie.text.Paragraph;
import com.lowagie.text.pdf.PdfPTable;

/**
 * Controlador responsável pela visualização do histórico de refeições,
 * exercícios e evolução.
 * Inclui funcionalidades de filtragem e exportação para PDF.
 */
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
    private TableView<ExerciseEntry> exercisesTable;
    @FXML
    private TableColumn<ExerciseEntry, String> exDateCol;
    @FXML
    private TableColumn<ExerciseEntry, String> exTypeCol;
    @FXML
    private TableColumn<ExerciseEntry, Number> exDurationCol;
    @FXML
    private TableColumn<ExerciseEntry, Number> exCalCol;

    @FXML
    private BarChart<String, Number> hydrationChart;

    @FXML
    private LineChart<String, Number> historyChart;

    @FXML
    private javafx.scene.control.TextField searchField;
    @FXML
    private DatePicker startDatePicker;
    @FXML
    private DatePicker endDatePicker;

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

        exDateCol.setCellValueFactory(cell -> new SimpleStringProperty(cell.getValue().getTimestamp().format(fmt)));
        exTypeCol.setCellValueFactory(cell -> new SimpleStringProperty(cell.getValue().getType()));
        exDurationCol.setCellValueFactory(cell -> new SimpleIntegerProperty(cell.getValue().getDurationMinutes()));
        exCalCol.setCellValueFactory(cell -> new SimpleIntegerProperty(cell.getValue().getCaloriesBurned()));
    }

    private void loadData() {
        UserProfile user = state.getActiveProfile();
        if (user == null)
            return;

        // Envolver a ObservableList numa FilteredList (inicialmente exibe todos os
        // dados)
        filteredData = new javafx.collections.transformation.FilteredList<>(
                javafx.collections.FXCollections.observableArrayList(user.getMeals()), p -> true);

        // Envolver a FilteredList numa SortedList.
        javafx.collections.transformation.SortedList<MealEntry> sortedData = new javafx.collections.transformation.SortedList<>(
                filteredData);

        // Ligar o comparador da SortedList ao comparador da TableView.
        sortedData.comparatorProperty().bind(mealsTable.comparatorProperty());

        // Adicionar dados ordenados (e filtrados) à tabela.
        mealsTable.setItems(sortedData);

        // Ordenação inicial por data (decrescente)
        mealsTable.getSortOrder().add(dateCol);
        dateCol.setSortType(TableColumn.SortType.DESCENDING);
        mealsTable.sort();

        // Gráfico - Mostrar histórico de Peso
        XYChart.Series<String, Number> series = new XYChart.Series<>();
        series.setName("Peso (kg)");

        DateTimeFormatter dateFmt = DateTimeFormatter.ofPattern("dd/MM");

        for (app.model.WeightEntry w : user.getWeights()) {
            series.getData().add(new XYChart.Data<>(w.getDate().format(dateFmt), w.getWeightKg()));
        }

        // Ensure at least current weight is shown if history is empty (shouldn't happen
        // with new logic)
        if (series.getData().isEmpty()) {
            series.getData().add(new XYChart.Data<>("Atual", user.getPesoKg()));
        }

        historyChart.getData().clear();
        historyChart.getData().add(series);

        // Add Target Line
        XYChart.Series<String, Number> targetSeries = new XYChart.Series<>();
        targetSeries.setName("Meta (" + user.getTargetWeightKg() + "kg)");

        // Add target points for the same X values as the weight series to make a line
        // Or just start and end? LineChart with CategoryAxis needs matching categories
        // or it looks weird.
        // Simplest: Add target point for every weight point date.
        for (XYChart.Data<String, Number> data : series.getData()) {
            targetSeries.getData().add(new XYChart.Data<>(data.getXValue(), user.getTargetWeightKg()));
        }

        historyChart.getData().add(targetSeries);

        // Exercícios
        exercisesTable.getItems().setAll(user.getExercises());
        // Ordenar
        exercisesTable.getSortOrder().add(exDateCol);
        exDateCol.setSortType(TableColumn.SortType.DESCENDING);
        exercisesTable.sort();

        // Gráfico de Hidratação
        hydrationChart.getData().clear();
        XYChart.Series<String, Number> waterSeries = new XYChart.Series<>();
        waterSeries.setName("Água (ml)");

        java.util.Map<LocalDate, Integer> waterMap = user.getWaters().stream()
                .collect(Collectors.groupingBy(
                        w -> w.getTimestamp().toLocalDate(),
                        Collectors.summingInt(w -> (int) w.getAmountMl())));

        LocalDate today = LocalDate.now();
        for (int i = 6; i >= 0; i--) {
            LocalDate date = today.minusDays(i);
            int amount = waterMap.getOrDefault(date, 0);
            waterSeries.getData().add(new XYChart.Data<>(date.format(DateTimeFormatter.ofPattern("dd/MM")), amount));
        }
        hydrationChart.getData().add(waterSeries);
    }

    private void setupSearch() {
        searchField.textProperty().addListener((observable, oldValue, newValue) -> updateFilter());
        startDatePicker.valueProperty().addListener((observable, oldValue, newValue) -> updateFilter());
        endDatePicker.valueProperty().addListener((observable, oldValue, newValue) -> updateFilter());
    }

    private void updateFilter() {
        filteredData.setPredicate(meal -> {
            boolean matchesSearch = true;
            boolean matchesDate = true;

            // Search
            String searchText = searchField.getText();
            if (searchText != null && !searchText.isEmpty()) {
                matchesSearch = meal.getDescription().toLowerCase().contains(searchText.toLowerCase());
            }

            // Date
            LocalDate mealDate = meal.getTimestamp().toLocalDate();
            if (startDatePicker.getValue() != null) {
                if (mealDate.isBefore(startDatePicker.getValue()))
                    matchesDate = false;
            }
            if (endDatePicker.getValue() != null) {
                if (mealDate.isAfter(endDatePicker.getValue()))
                    matchesDate = false;
            }

            return matchesSearch && matchesDate;
        });
    }

    @FXML
    private void handleDownloadPdf() {
        try {
            javafx.stage.FileChooser fileChooser = new javafx.stage.FileChooser();
            fileChooser.setTitle("Salvar Relatório Completo");
            fileChooser.getExtensionFilters().add(new javafx.stage.FileChooser.ExtensionFilter("PDF Files", "*.pdf"));
            fileChooser.setInitialFileName("relatorio_completo.pdf");

            javafx.stage.Window window = mealsTable.getScene().getWindow();
            java.io.File file = fileChooser.showSaveDialog(window);

            if (file != null) {
                com.lowagie.text.Document document = new com.lowagie.text.Document();
                com.lowagie.text.pdf.PdfWriter.getInstance(document, new java.io.FileOutputStream(file));
                document.open();

                UserProfile user = state.getActiveProfile();

                // 1. Título e Perfil
                com.lowagie.text.Font titleFont = new com.lowagie.text.Font(com.lowagie.text.Font.HELVETICA, 18,
                        com.lowagie.text.Font.BOLD);
                com.lowagie.text.Font headerFont = new com.lowagie.text.Font(com.lowagie.text.Font.HELVETICA, 14,
                        com.lowagie.text.Font.BOLD);

                Paragraph title = new Paragraph("Relatório de Progresso - A Minha Dieta", titleFont);
                title.setAlignment(com.lowagie.text.Element.ALIGN_CENTER);
                title.setSpacingAfter(20);
                document.add(title);

                document.add(new Paragraph("Perfil do Utilizador", headerFont));
                document.add(new Paragraph("Nome: " + user.getNome()));
                document.add(new Paragraph("Idade: " + user.getIdade()));
                document.add(new Paragraph("Peso Atual: " + user.getPesoKg() + " kg"));
                document.add(new Paragraph("Altura: " + user.getAlturaCm() + " cm"));
                document.add(new Paragraph("IMC: " + String.format("%.2f", user.getBMI())));
                document.add(new Paragraph("Nível de Atividade: "
                        + (user.getPhysicalActivityLevel() != null ? user.getPhysicalActivityLevel().toString()
                                : "N/A")));
                document.add(new Paragraph("Meta de Peso: " + user.getTargetWeightKg() + " kg"));
                document.add(new Paragraph("Frequência de Pesagem: " +
                        (user.getWeighInFrequency() != null ? user.getWeighInFrequency().toString() : "N/A")));
                document.add(new Paragraph(" "));

                // 2. Histórico de Refeições
                document.add(new Paragraph("Histórico de Refeições", headerFont));
                PdfPTable mealTable = new PdfPTable(3);
                mealTable.setWidthPercentage(100);
                mealTable.setSpacingBefore(10f);
                mealTable.setSpacingAfter(10f);
                mealTable.addCell("Data/Hora");
                mealTable.addCell("Descrição");
                mealTable.addCell("Calorias");

                DateTimeFormatter fmt = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");
                for (MealEntry meal : mealsTable.getItems()) {
                    mealTable.addCell(meal.getTimestamp().format(fmt));
                    mealTable.addCell(meal.getDescription());
                    mealTable.addCell(String.valueOf(meal.getCalories()));
                }
                document.add(mealTable);

                // 3. Histórico de Atividade Física
                document.add(new Paragraph("Histórico de Atividade Física", headerFont));
                PdfPTable exTable = new PdfPTable(4);
                exTable.setWidthPercentage(100);
                exTable.setSpacingBefore(10f);
                exTable.setSpacingAfter(10f);
                exTable.addCell("Data/Hora");
                exTable.addCell("Tipo");
                exTable.addCell("Duração (min)");
                exTable.addCell("Calorias");

                for (app.model.ExerciseEntry ex : user.getExercises()) {
                    exTable.addCell(ex.getTimestamp().format(fmt));
                    exTable.addCell(ex.getType());
                    exTable.addCell(String.valueOf(ex.getDurationMinutes()));
                    exTable.addCell(String.valueOf(ex.getCaloriesBurned()));
                }
                document.add(exTable);

                // 4. Gráficos
                document.add(new Paragraph("Gráficos de Evolução", headerFont));

                // Capturar Gráfico de Peso
                if (historyChart != null) {
                    WritableImage fxImage = historyChart.snapshot(new SnapshotParameters(), null);
                    ByteArrayOutputStream byteOutput = new ByteArrayOutputStream();
                    ImageIO.write(SwingFXUtils.fromFXImage(fxImage, null), "png", byteOutput);
                    Image graphImage = Image.getInstance(byteOutput.toByteArray());
                    graphImage.scaleToFit(500, 300);
                    graphImage.setAlignment(Image.ALIGN_CENTER);
                    document.add(graphImage);
                }

                document.close();

                javafx.scene.control.Alert alert = new javafx.scene.control.Alert(
                        javafx.scene.control.Alert.AlertType.INFORMATION);
                alert.setTitle("Sucesso");
                alert.setHeaderText(null);
                alert.setContentText("Relatório PDF gerado com sucesso!");
                alert.showAndWait();
            }
        } catch (Exception e) {
            e.printStackTrace();
            javafx.scene.control.Alert alert = new javafx.scene.control.Alert(
                    javafx.scene.control.Alert.AlertType.ERROR);
            alert.setTitle("Erro");
            alert.setHeaderText("Erro ao gerar PDF");
            alert.setContentText(e.getMessage());
            alert.showAndWait();
        }
    }
}
