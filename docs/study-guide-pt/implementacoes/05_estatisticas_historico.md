# 5️⃣ Estatísticas do Histórico - Métodos de Análise

> Tutorial para adicionar estatísticas interessantes em cada separador do histórico

---

## 🎯 Objetivos

Criar métodos que mostrem:

### 📊 Separador Refeições:
- Alimento mais consumido
- Dia com mais calorias
- Total de calorias (período)

### 💪 Separador Exercícios:
- Dia com mais calorias queimadas
- Dia com mais tempo de exercício
- Exercício mais frequente

### 💧 Separador Água:
- Dia com mais água consumida
- Dia(s) sem água
- Média diária

---

## 📂 Estrutura

Vamos adicionar **Labels** para mostrar estatísticas + **métodos de cálculo** no Controller.

---

## 🔧 PARTE 1: Estatísticas de Refeições

### PASSO 1.1: Adicionar Labels no FXML

**Ficheiro:** `HistoryView.fxml`

**Localização:** Dentro do separador "Refeições" (Tab Refeições)

```xml
<Tab text="Refeições" closable="false">
    <VBox spacing="10" padding="10">
        <!-- Pesquisa e filtros existentes -->
        <!-- ... -->
        
        <!-- Tabela existente -->
        <TableView fx:id="mealsTable">
            <!-- colunas... -->
        </TableView>
        
        <!-- ✅ ADICIONA ISTO: -->
        <Separator/>
        <Label text="Estatísticas:" style="-fx-font-weight: bold; -fx-font-size: 14px;"/>
        <Label fx:id="mostConsumedFoodLabel" text="Alimento mais consumido: --"/>
        <Label fx:id="mostCaloriesDayLabel" text="Dia com mais calorias: --"/>
        <Label fx:id="totalCaloriesLabel" text="Total de calorias: --"/>
    </VBox>
</Tab>
```

---

### PASSO 1.2: Declarar no Controller

**Ficheiro:** `HistoryController.java`

**No topo da classe:**

```java
// Refeições - Estatísticas
@FXML private Label mostConsumedFoodLabel;
@FXML private Label mostCaloriesDayLabel;
@FXML private Label totalCaloriesLabel;
```

---

### PASSO 1.3: Criar Métodos de Cálculo

**Adiciona no final do HistoryController:**

```java
// ═══════════════════════════════════════════════════════
// ESTATÍSTICAS DE REFEIÇÕES
// ═══════════════════════════════════════════════════════

private void updateMealStatistics() {
    UserProfile user = state.getActiveProfile();
    if (user == null) return;
    
    // 1️⃣ ALIMENTO MAIS CONSUMIDO
    Map<String, Long> foodCount = user.getMeals().stream()
            .collect(Collectors.groupingBy(
                MealEntry::getDescription,
                Collectors.counting()
            ));
    
    String mostConsumed = foodCount.entrySet().stream()
            .max(Map.Entry.comparingByValue())
            .map(e -> e.getKey() + " (" + e.getValue() + "x)")
            .orElse("Nenhum");
    
    mostConsumedFoodLabel.setText("Alimento mais consumido: " + mostConsumed);
    
    // 2️⃣ DIA COM MAIS CALORIAS
    Map<LocalDate, Integer> caloriesByDay = user.getMeals().stream()
            .collect(Collectors.groupingBy(
                m -> m.getTimestamp().toLocalDate(),
                Collectors.summingInt(MealEntry::getCalories)
            ));
    
    Map.Entry<LocalDate, Integer> maxDay = caloriesByDay.entrySet().stream()
            .max(Map.Entry.comparingByValue())
            .orElse(null);
    
    if (maxDay != null) {
        String dateStr = maxDay.getKey().format(
            DateTimeFormatter.ofPattern("dd/MM/yyyy")
        );
        mostCaloriesDayLabel.setText(
            "Dia com mais calorias: " + dateStr + " (" + maxDay.getValue() + " kcal)"
        );
    } else {
        mostCaloriesDayLabel.setText("Dia com mais calorias: --");
    }
    
    // 3️⃣ TOTAL DE CALORIAS (período visível)
    int total = mealsTable.getItems().stream()
            .mapToInt(MealEntry::getCalories)
            .sum();
    
    totalCaloriesLabel.setText("Total de calorias (período): " + total + " kcal");
}
```

---

### PASSO 1.4: Chamar ao Carregar/Filtrar

**Na função `loadData()`, no final:**

```java
private void loadData() {
    // ... código existente ...
    
    // ✅ ADICIONA:
    updateMealStatistics();
}
```

**Na função `updateFilter()`, no final:**

```java
private void updateFilter() {
    // ... código existente ...
    
    // ✅ ADICIONA:
    updateMealStatistics();
}
```

---

## 🔧 PARTE 2: Estatísticas de Exercícios

### PASSO 2.1: Adicionar Labels no FXML

```xml
<Tab text="Exercícios" closable="false">
    <VBox spacing="10" padding="10">
        <TableView fx:id="exercisesTable">
            <!-- colunas... -->
        </TableView>
        
        <!-- ✅ ADICIONA: -->
        <Separator/>
        <Label text="Estatísticas:" style="-fx-font-weight: bold; -fx-font-size: 14px;"/>
        <Label fx:id="mostCaloriesBurnedDayLabel" text="Dia com mais calorias queimadas: --"/>
        <Label fx:id="mostActiveMinutesDayLabel" text="Dia com mais tempo de exercício: --"/>
        <Label fx:id="mostFrequentExerciseLabel" text="Exercício mais frequente: --"/>
    </VBox>
</Tab>
```

---

### PASSO 2.2: Declarar no Controller

```java
// Exercícios - Estatísticas
@FXML private Label mostCaloriesBurnedDayLabel;
@FXML private Label mostActiveMinutesDayLabel;
@FXML private Label mostFrequentExerciseLabel;
```

---

### PASSO 2.3: Criar Métodos de Cálculo

```java
// ═══════════════════════════════════════════════════════
// ESTATÍSTICAS DE EXERCÍCIOS
// ═══════════════════════════════════════════════════════

private void updateExerciseStatistics() {
    UserProfile user = state.getActiveProfile();
    if (user == null) return;
    
    // 1️⃣ DIA COM MAIS CALORIAS QUEIMADAS
    Map<LocalDate, Integer> caloriesByDay = user.getExercises().stream()
            .collect(Collectors.groupingBy(
                e -> e.getTimestamp().toLocalDate(),
                Collectors.summingInt(ExerciseEntry::getCaloriesBurned)
            ));
    
    Map.Entry<LocalDate, Integer> maxCalDay = caloriesByDay.entrySet().stream()
            .max(Map.Entry.comparingByValue())
            .orElse(null);
    
    if (maxCalDay != null) {
        String dateStr = maxCalDay.getKey().format(
            DateTimeFormatter.ofPattern("dd/MM/yyyy")
        );
        mostCaloriesBurnedDayLabel.setText(
            "Dia com mais calorias queimadas: " + dateStr + " (" + maxCalDay.getValue() + " kcal)"
        );
    } else {
        mostCaloriesBurnedDayLabel.setText("Dia com mais calorias queimadas: --");
    }
    
    // 2️⃣ DIA COM MAIS TEMPO DE EXERCÍCIO
    Map<LocalDate, Integer> minutesByDay = user.getExercises().stream()
            .collect(Collectors.groupingBy(
                e -> e.getTimestamp().toLocalDate(),
                Collectors.summingInt(ExerciseEntry::getDurationMinutes)
            ));
    
    Map.Entry<LocalDate, Integer> maxMinDay = minutesByDay.entrySet().stream()
            .max(Map.Entry.comparingByValue())
            .orElse(null);
    
    if (maxMinDay != null) {
        String dateStr = maxMinDay.getKey().format(
            DateTimeFormatter.ofPattern("dd/MM/yyyy")
        );
        mostActiveMinutesDayLabel.setText(
            "Dia com mais tempo: " + dateStr + " (" + maxMinDay.getValue() + " min)"
        );
    } else {
        mostActiveMinutesDayLabel.setText("Dia com mais tempo: --");
    }
    
    // 3️⃣ EXERCÍCIO MAIS FREQUENTE
    Map<String, Long> exerciseCount = user.getExercises().stream()
            .collect(Collectors.groupingBy(
                ExerciseEntry::getType,
                Collectors.counting()
            ));
    
    String mostFrequent = exerciseCount.entrySet().stream()
            .max(Map.Entry.comparingByValue())
            .map(e -> e.getKey() + " (" + e.getValue() + "x)")
            .orElse("Nenhum");
    
    mostFrequentExerciseLabel.setText("Exercício mais frequente: " + mostFrequent);
}
```

---

### PASSO 2.4: Chamar ao Carregar

**Na função `loadData()`, depois das exercícios:**

```java
exercisesTable.setItems(...);
// ...

// ✅ ADICIONA:
updateExerciseStatistics();
```

---

## 🔧 PARTE 3: Estatísticas de Água

### PASSO 3.1: Adicionar Labels no FXML

**Se tiveres separador de Água:**

```xml
<Tab text="Água" closable="false">
    <VBox spacing="10" padding="10">
        <!-- Gráfico ou lista existente -->
        
        <!-- ✅ ADICIONA: -->
        <Separator/>
        <Label text="Estatísticas:" style="-fx-font-weight: bold; -fx-font-size: 14px;"/>
        <Label fx:id="mostWaterDayLabel" text="Dia com mais água: --"/>
        <Label fx:id="daysWithoutWaterLabel" text="Dias sem água: --"/>
        <Label fx:id="avgWaterLabel" text="Média diária: --"/>
    </VBox>
</Tab>
```

---

### PASSO 3.2: Declarar no Controller

```java
// Água - Estatísticas
@FXML private Label mostWaterDayLabel;
@FXML private Label daysWithoutWaterLabel;
@FXML private Label avgWaterLabel;
```

---

### PASSO 3.3: Criar Métodos de Cálculo

```java
// ═══════════════════════════════════════════════════════
// ESTATÍSTICAS DE ÁGUA
// ═══════════════════════════════════════════════════════

private void updateWaterStatistics() {
    UserProfile user = state.getActiveProfile();
    if (user == null) return;
    
    // 1️⃣ DIA COM MAIS ÁGUA
    Map<LocalDate, Double> waterByDay = user.getWaters().stream()
            .collect(Collectors.groupingBy(
                w -> w.getTimestamp().toLocalDate(),
                Collectors.summingDouble(w -> w.getAmountMl())
            ));
    
    Map.Entry<LocalDate, Double> maxDay = waterByDay.entrySet().stream()
            .max(Map.Entry.comparingByValue())
            .orElse(null);
    
    if (maxDay != null) {
        String dateStr = maxDay.getKey().format(
            DateTimeFormatter.ofPattern("dd/MM/yyyy")
        );
        double liters = maxDay.getValue() / 1000.0;
        mostWaterDayLabel.setText(
            String.format("Dia com mais água: %s (%.1f L)", dateStr, liters)
        );
    } else {
        mostWaterDayLabel.setText("Dia com mais água: --");
    }
    
    // 2️⃣ DIAS SEM ÁGUA (últimos 30 dias)
    LocalDate today = LocalDate.now();
    Set<LocalDate> daysWithWater = user.getWaters().stream()
            .map(w -> w.getTimestamp().toLocalDate())
            .collect(Collectors.toSet());
    
    List<LocalDate> daysWithoutWater = new ArrayList<>();
    for (int i = 0; i < 30; i++) {
        LocalDate date = today.minusDays(i);
        if (!daysWithWater.contains(date)) {
            daysWithoutWater.add(date);
        }
    }
    
    if (daysWithoutWater.isEmpty()) {
        daysWithoutWaterLabel.setText("Dias sem água (30 dias): 0 ✓");
    } else {
        daysWithoutWaterLabel.setText(
            "Dias sem água (30 dias): " + daysWithoutWater.size()
        );
    }
    
    // 3️⃣ MÉDIA DIÁRIA (últimos 30 dias)
    double totalMl = waterByDay.values().stream()
            .mapToDouble(Double::doubleValue)
            .sum();
    
    int daysWithData = waterByDay.size();
    if (daysWithData > 0) {
        double avgMl = totalMl / daysWithData;
        double avgL = avgMl / 1000.0;
        avgWaterLabel.setText(
            String.format("Média diária: %.1f L (%d dias)", avgL, daysWithData)
        );
    } else {
        avgWaterLabel.setText("Média diária: --");
    }
}
```

---

### PASSO 3.4: Chamar ao Carregar

**Na função `loadData()`, depois do gráfico de água:**

```java
hydrationChart.getData().add(waterSeries);

// ✅ ADICIONA:
updateWaterStatistics();
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

3. **Testar:**
   - Vai a "Histórico"
   - Vê separador Refeições → verifica estatísticas
   - Vê separador Exercícios → verifica estatísticas
   - Vê separador Água → verifica estatísticas

---

## 📊 Exemplo de Output

### Refeições:
```
Alimento mais consumido: Arroz (15x)
Dia com mais calorias: 05/01/2026 (2850 kcal)
Total de calorias (período): 18450 kcal
```

### Exercícios:
```
Dia com mais calorias queimadas: 03/01/2026 (850 kcal)
Dia com mais tempo: 06/01/2026 (120 min)
Exercício mais frequente: Corrida (12x)
```

### Água:
```
Dia com mais água: 07/01/2026 (4.2 L)
Dias sem água (30 dias): 3
Média diária: 2.8 L (27 dias)
```

---

## ✅ Checklist

- [ ] Adicionei Labels no FXML (3 separadores)
- [ ] Declarei Labels no Controller
- [ ] Criei `updateMealStatistics()`
- [ ] Criei `updateExerciseStatistics()`
- [ ] Criei `updateWaterStatistics()`
- [ ] Chamei funções em `loadData()` e `updateFilter()`
- [ ] Compilei sem erros
- [ ] Testei e estatísticas aparecem
- [ ] Fiz commit: `git commit -m "Adicionar estatísticas ao histórico"`

---

**Próximo:** [06_filtro_por_separador.md](06_filtro_por_separador.md)  
**Anterior:** [04_editar_eliminar_exercicios.md](04_editar_eliminar_exercicios.md)  
**Índice:** [README.md](README.md)
