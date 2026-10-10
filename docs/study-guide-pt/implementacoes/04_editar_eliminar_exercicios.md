# 4️⃣ Como Adicionar Botões Editar e Eliminar Exercícios

> Tutorial completo para gerir exercícios já registados

---

## 🎯 Objetivo

Adicionar duas funcionalidades à lista de exercícios:
1. **Editar:** Corrigir dados de um exercício
2. **Eliminar:** Remover exercício por engano

---

## 🎨 Como Vai Ficar

**ANTES:**
```
[09:00] Corrida - 30 min - 300 kcal
[18:30] Caminhada - 45 min - 200 kcal
```

**DEPOIS:**
```
[09:00] Corrida - 30 min - 300 kcal    [Editar] [Eliminar]
[18:30] Caminhada -45 min - 200 kcal   [Editar] [Eliminar]
```

---

## 🔧 OPÇÃO 1: Botões ao Lado de Cada Item (Mais Complexo)

### PASSO 1: Mudar de ListView para TableView

**Problema:** ListView só mostra texto simples  
**Solução:** Usar TableView com colunas para botões

**Ficheiro FXML:** `ExerciseView.fxml`

#### Código ANTES:
```xml
<ListView fx:id="exerciseList"/>
```

#### Código DEPOIS:
```xml
<TableView fx:id="exerciseTable">
    <columns>
        <TableColumn text="Hora" fx:id="timeCol" prefWidth="80"/>
        <TableColumn text="Tipo" fx:id="typeCol" prefWidth="120"/>
        <TableColumn text="Duração (min)" fx:id="durationCol" prefWidth="100"/>
        <TableColumn text="Calorias" fx:id="caloriesCol" prefWidth="100"/>
        <TableColumn text="Ações" fx:id="actionsCol" prefWidth="150"/>
    </columns>
</TableView>
```

---

### PASSO 2: Modificar Controller

**Ficheiro:** `ExerciseController.java`

#### Adicionar Imports:

```java
import javafx.scene.control.TableView;
import javafx.scene.control.TableColumn;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TextInputDialog;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.HBox;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.SimpleIntegerProperty;
import java.time.format.DateTimeFormatter;
import java.util.Optional;
```

#### Substituir Declarações:

**ANTES:**
```java
@FXML private ListView<String> exerciseList;
```

**DEPOIS:**
```java
@FXML private TableView<ExerciseEntry> exerciseTable;
@FXML private TableColumn<ExerciseEntry, String> timeCol;
@FXML private TableColumn<ExerciseEntry, String> typeCol;
@FXML private TableColumn<ExerciseEntry, Integer> durationCol;
@FXML private TableColumn<ExerciseEntry, Integer> caloriesCol;
@FXML private TableColumn<ExerciseEntry, Void> actionsCol;
```

---

### PASSO 3: Configurar Tabela no init()

**Adiciona ANTES de `refreshList()`:**

```java
public void init(SceneManager sceneManager, AppState state, DataStore store) {
    this.state = state;
    this.store = store;
    
    typeCombo.getItems().addAll(
        "Caminhada", "Corrida", "Ciclismo", 
        "Natação", "Musculação", "Yoga"
    );
    
    // ═══════════════════════════════════════════════
    // ✅ ADICIONA ISTO:
    // ═══════════════════════════════════════════════
    setupTable();
    // ═══════════════════════════════════════════════
    
    refreshList();
}
```

---

### PASSO 4: Criar Função setupTable()

**Adiciona DEPOIS de `init()`:**

```java
private void setupTable() {
    DateTimeFormatter fmt = DateTimeFormatter.ofPattern("HH:mm");
    
    // Configurar colunas simples
    timeCol.setCellValueFactory(cellData -> 
        new SimpleStringProperty(cellData.getValue().getTimestamp().format(fmt))
    );
    
    typeCol.setCellValueFactory(cellData -> 
        new SimpleStringProperty(cellData.getValue().getType())
    );
    
    durationCol.setCellValueFactory(cellData -> 
        new SimpleIntegerProperty(cellData.getValue().getDurationMinutes()).asObject()
    );
    
    caloriesCol.setCellValueFactory(cellData -> 
        new SimpleIntegerProperty(cellData.getValue().getCaloriesBurned()).asObject()
    );
    
    // ═══════════════════════════════════════════════
    // COLUNA DE AÇÕES (Botões)
    // ═══════════════════════════════════════════════
    actionsCol.setCellFactory(param -> new javafx.scene.control.TableCell<>() {
        private final Button editBtn = new Button("Editar");
        private final Button deleteBtn = new Button("Eliminar");
        private final HBox buttons = new HBox(5, editBtn, deleteBtn);
        
        {
            // Estilo dos botões
            editBtn.setStyle("-fx-background-color: #4CAF50; -fx-text-fill: white;");
            deleteBtn.setStyle("-fx-background-color: #f44336; -fx-text-fill: white;");
            
            // Ação: Editar
            editBtn.setOnAction(event -> {
                ExerciseEntry exercise = getTableView().getItems().get(getIndex());
                handleEdit(exercise);
            });
            
            // Ação: Eliminar
            deleteBtn.setOnAction(event -> {
                ExerciseEntry exercise = getTableView().getItems().get(getIndex());
                handleDelete(exercise);
            });
        }
        
        @Override
        protected void updateItem(Void item, boolean empty) {
            super.updateItem(item, empty);
            setGraphic(empty ? null : buttons);
        }
    });
}
```

---

### PASSO 5: Função para Editar

**Adiciona no final da classe:**

```java
private void handleEdit(ExerciseEntry exercise) {
    // ═══ DIÁLOGO PARA EDITAR TIPO ═══
    TextInputDialog typeDialog = new TextInputDialog(exercise.getType());
    typeDialog.setTitle("Editar Exercício");
    typeDialog.setHeaderText("Editar tipo de exercício");
    typeDialog.setContentText("Tipo:");
    
    Optional<String> typeResult = typeDialog.showAndWait();
    if (!typeResult.isPresent()) return;  // Cancelou
    
    String newType = typeResult.get().trim();
    if (newType.isEmpty()) {
        showError("Tipo não pode estar vazio!");
        return;
    }
    
    // ═══ DIÁLOGO PARA EDITAR DURAÇÃO ═══
    TextInputDialog durationDialog = new TextInputDialog(
        String.valueOf(exercise.getDurationMinutes())
    );
    durationDialog.setTitle("Editar Exercício");
    durationDialog.setHeaderText("Editar duração");
    durationDialog.setContentText("Duração (minutos):");
    
    Optional<String> durationResult = durationDialog.showAndWait();
    if (!durationResult.isPresent()) return;
    
    int newDuration;
    try {
        newDuration = Integer.parseInt(durationResult.get().trim());
        if (newDuration <= 0) throw new NumberFormatException();
    } catch (NumberFormatException e) {
        showError("Duração inválida!");
        return;
    }
    
    // ═══ DIÁLOGO PARA EDITAR CALORIAS ═══
    TextInputDialog caloriesDialog = new TextInputDialog(
        String.valueOf(exercise.getCaloriesBurned())
    );
    caloriesDialog.setTitle("Editar Exercício");
    caloriesDialog.setHeaderText("Editar calorias queimadas");
    caloriesDialog.setContentText("Calorias:");
    
    Optional<String> caloriesResult = caloriesDialog.showAndWait();
    if (!caloriesResult.isPresent()) return;
    
    int newCalories;
    try {
        newCalories = Integer.parseInt(caloriesResult.get().trim());
        if (newCalories <= 0) throw new NumberFormatException();
    } catch (NumberFormatException e) {
        showError("Calorias inválidas!");
        return;
    }
    
    // ═══ APLICAR ALTERAÇÕES ═══
    exercise.setType(newType);
    exercise.setDurationMinutes(newDuration);
    exercise.setCaloriesBurned(newCalories);
    
    // Guardar
    store.save(state);
    
    // Atualizar tabela
    exerciseTable.refresh();
    
    // Confirmação
    showInfo("Exercício editado com sucesso!");
}
```

---

### PASSO 6: Função para Eliminar

```java
private void handleDelete(ExerciseEntry exercise) {
    // Confirmação
    javafx.scene.control.Alert alert = new javafx.scene.control.Alert(
        javafx.scene.control.Alert.AlertType.CONFIRMATION
    );
    alert.setTitle("Eliminar Exercício");
    alert.setHeaderText("Tem a certeza?");
    alert.setContentText(
        String.format("Eliminar exercício:\n%s - %d min - %d kcal?",
            exercise.getType(),
            exercise.getDurationMinutes(),
            exercise.getCaloriesBurned())
    );
    
    Optional<ButtonType> result = alert.showAndWait();
    if (result.isPresent() && result.get() == ButtonType.OK) {
        // Remover da lista
        state.getActiveProfile().getExercises().remove(exercise);
        
        // Guardar
        store.save(state);
        
        // Atualizar tabela
        refreshList();
        
        // Confirmação
        showInfo("Exercício eliminado!");
    }
}
```

---

### PASSO 7: Funções Auxiliares de Alertas

```java
private void showError(String message) {
    javafx.scene.control.Alert alert = new javafx.scene.control.Alert(
        javafx.scene.control.Alert.AlertType.ERROR
    );
    alert.setTitle("Erro");
    alert.setHeaderText(null);
    alert.setContentText(message);
    alert.showAndWait();
}

private void showInfo(String message) {
    javafx.scene.control.Alert alert = new javafx.scene.control.Alert(
        javafx.scene.control.Alert.AlertType.INFORMATION
    );
    alert.setTitle("Sucesso");
    alert.setHeaderText(null);
    alert.setContentText(message);
    alert.showAndWait();
}
```

---

### PASSO 8: Adicionar Setters em ExerciseEntry

**Ficheiro:** `AminhaDieta/src/main/java/app/model/ExerciseEntry.java`

**Adiciona estes métodos:**

```java
public void setType(String type) {
    this.type = type;
}

public void setDurationMinutes(int durationMinutes) {
    this.durationMinutes = durationMinutes;
}

public void setCaloriesBurned(int caloriesBurned) {
    this.caloriesBurned = caloriesBurned;
}
```

---

### PASSO 9: Atualizar refreshList()

**ANTES:**
```java
private void refreshList() {
    // ... código com ListView ...
}
```

**DEPOIS:**
```java
private void refreshList() {
    if (state.getActiveProfile() == null) return;
    
    LocalDate today = LocalDate.now();
    
    // Filtrar exercícios de hoje
    java.util.List<ExerciseEntry> todayExercises = 
        state.getActiveProfile().getExercises().stream()
            .filter(e -> e.getTimestamp().toLocalDate().equals(today))
            .collect(java.util.stream.Collectors.toList());
    
    // Atualizar tabela
    exerciseTable.setItems(
        javafx.collections.FXCollections.observableArrayList(todayExercises)
    );
}
```

---

## 🧪 Testar

1. **Compilar:**
   ```bash
   mvn clean compile
   ```

2. **Executar:**
   ```bash
   mvn javafx:run
   ```

3. **Testar Editar:**
   - Adiciona exercício: "Corrida, 30 min, 300 kcal"
   - Clica botão "Editar"
   - Muda tipo para "Jogging"
   - Muda duração para "35"
   - Verifica que mudou na tabela

4. **Testar Eliminar:**
   - Clica botão "Eliminar"
   - Confirma
   - Verifica que desapareceu

---

## ✅ Checklist

- [ ] Mudei ListView para TableView no FXML
- [ ] Adicionei imports necessários
- [ ] Criei `setupTable()`
- [ ] Criei `handleEdit()` e `handleDelete()`
- [ ] Adicionei setters em ExerciseEntry
- [ ] Atualizei `refreshList()`
- [ ] Compilei sem erros
- [ ] Testei editar e funciona
- [ ] Testei eliminar e funciona
- [ ] Fiz commit: `git commit -m "Adicionar editar/eliminar exercícios"`

---

**Próximo:** [05_estatisticas_historico.md](05_estatisticas_historico.md)  
**Anterior:** [03_adicionar_tipo_exercicio.md](03_adicionar_tipo_exercicio.md)  
**Índice:** [README.md](README.md)
