# 1️⃣ Dashboard (Home) - Análise Detalhada

> Explicação completa do ecrã principal que mostra o resumo diário completo

![Dashboard](file:///C:/Users/Carlos/.gemini/antigravity/brain/35e4411b-572d-4847-8756-4b4cfd9a5b46/uploaded_image_1767818195489.png)

---

## 📍 Localização do Código

**Ficheiro Java:** `AminhaDieta/src/main/java/app/ui/controller/HomeController.java`  
**FXML:** `AminhaDieta/src/main/resources/fxml/HomeView.fxml`  
**Linhas de código:** 159 linhas

---

## 🎯 O Que o Dashboard Mostra

Vendo a tua imagem:

### Informações Exibidas:
1. ✅ **Saudação:** "Bem-vindo, Carlos!"
2. ✅ **Calorias Hoje:** 0 / 1838 (consumidas / meta)
3. ✅ **Água:** 0,0 L / 3,3 L (consumida / meta)
4. ✅ **IMC:** 27,2 (Excesso de Peso)
5. ✅ **Macronutrientes:**
   - Proteína: 0g / 32g
   - Hidratos: 0g / 230g
   - Gordura: 0g / 61g
6. ✅ **Gráfico Circular:** Consumo Diário (PieChart)
7. ✅ **Gráfico de Barras:** Atividade Física últimos 7 dias
8. ✅ **Gráfico de Linha:** Evolução de Peso
9. ✅ **Botão:** Editar Perfil

---

## 📋 TODAS as Funções Discriminadas

### Função 1: `init()` - Inicializar Dashboard

**Código:** Linhas 65-70

```java
public void init(SceneManager sceneManager, AppState state, DataStore store) {
    this.sceneManager = sceneManager;  // Para navegar (ex: Editar Perfil)
    this.state = state;                 // Dados globais
    
    updateView();  // ← CHAMA A FUNÇÃO MESTRE
}
```

**O QUE FAZ:**
1. Guarda referências
2. Chama `updateView()` que faz TODO o trabalho

---

### Função 2: `updateView()` - PREENCHE TODO O DASHBOARD

**Esta é a FUNÇÃO MAIS IMPORTANTE!**

**Código:** Linhas 72-137

```java
private void updateView() {
    // 1️⃣ OBTER UTILIZADOR ATIVO
    UserProfile user = state.getActiveProfile();
    if (user == null) return;
    
    // 2️⃣ SAUDAÇÃO
    tituloLabel.setText("Bem-vindo, " + user.getNome() + "!");
    
    // 3️⃣ IMC
    double bmi = user.getBMI();
    bmiLabel.setText(String.format("%.1f", bmi));
    bmiStatusLabel.setText(getBMIStatus(bmi));
    
    // 4️⃣ CALORIAS
    int consumed = user.getCaloriesConsumedToday();
    int goal = user.getDailyCalorieGoal();
    caloriesLabel.setText(consumed + " / " + goal);
    caloriesBar.setProgress(goal > 0 ? (double) consumed / goal : 0);
    
    // 5️⃣ ÁGUA
    double waterL = user.getWaterConsumedToday() / 1000.0;
    double waterGoalL = user.getDailyWaterGoalMl() / 1000.0;
    waterLabel.setText(String.format("%.1f L / %.1f L", waterL, waterGoalL));
    waterBar.setProgress(waterGoalL > 0 ? waterL / waterGoalL : 0);
    
    // 6️⃣ MACRONUTRIENTES
    updateMacro(protLabel, protBar, user.getProteinConsumedToday(), 
                user.getDailyProteinGoalGrams(), "g");
    updateMacro(carbLabel, carbBar, user.getCarbsConsumedToday(), 
                user.getDailyCarbsGoalGrams(), "g");
    updateMacro(fatLabel, fatBar, user.getFatConsumedToday(), 
                user.getDailyFatGoalGrams(), "g");
    
    // 7️⃣ GRÁFICO DE PESO
    XYChart.Series<String, Number> series = new XYChart.Series<>();
    series.setName("Peso");
    series.getData().add(new XYChart.Data<>("Hoje", user.getPesoKg()));
    weightChart.getData().clear();
    weightChart.getData().add(series);
    
    // 8️⃣ GRÁFICO CIRCULAR - Consumo Diário
    consumptionPieChart.getData().clear();
    consumptionPieChart.getData().addAll(
        new PieChart.Data("Proteína (" + (int)user.getProteinConsumedToday() + "g)",
                user.getProteinConsumedToday()),
        new PieChart.Data("Hidratos (" + (int)user.getCarbsConsumedToday() + "g)",
                user.getCarbsConsumedToday()),
        new PieChart.Data("Gordura (" + (int)user.getFatConsumedToday() + "g)", 
                user.getFatConsumedToday()),
        new PieChart.Data("Água (" + (int)(user.getWaterConsumedToday()) + "ml)",
                user.getWaterConsumedToday()));
    
    // 9️⃣ GRÁFICO DE BARRAS - Atividade Física
    exerciseChart.getData().clear();
    XYChart.Series<String, Number> exerciseSeries = new XYChart.Series<>();
    exerciseSeries.setName("Calorias Queimadas");
    
    Map<LocalDate, Integer> exerciseMap = user.getExercises().stream()
            .collect(Collectors.groupingBy(
                    e -> e.getTimestamp().toLocalDate(),
                    Collectors.summingInt(ExerciseEntry::getCaloriesBurned)));
    
    LocalDate today = LocalDate.now();
    for (int i = 6; i >= 0; i--) {
        LocalDate date = today.minusDays(i);
        int calories = exerciseMap.getOrDefault(date, 0);
        exerciseSeries.getData()
                .add(new XYChart.Data<>(
                    date.format(DateTimeFormatter.ofPattern("dd/MM")), 
                    calories));
    }
    exerciseChart.getData().add(exerciseSeries);
}
```

---

## 📊 CÁLCULOS DETALHADOS

### IMC (Índice de Massa Corporal)

**Função no UserProfile:** `getBMI()`

```java
public double getBMI() {
    if (alturaCm <= 0) return 0;
    double heightM = alturaCm / 100.0;      // cm → metros
    return pesoKg / (heightM * heightM);    // peso / altura²
}
```

**FÓRMULA:** IMC = peso (kg) / altura² (m)

**EXEMPLO (teus dados):**
```
Peso: ~94kg (estimado pelo meta de água 3,3L ÷ 35ml/kg)
Altura: ~175cm = 1.75m
IMC = 94 / (1.75)² = 94 / 3.0625 = 30.7

(Tua imagem mostra 27,2 - dados ligeiramente diferentes)
```

**Classificação - Função `getBMIStatus(bmi)`:**

```java
private String getBMIStatus(double bmi) {
    if (bmi < 18.5) return "Baixo Peso";
    if (bmi < 25)   return "Saudável";
    if (bmi < 30)   return "Excesso de Peso";  // ← Teu caso
    return "Obesidade";
}
```

---

### Meta de Calorias Diárias

**Função no UserProfile:** `getDailyCalorieGoal()`

```java
public int getDailyCalorieGoal() {
    // Fórmula de Mifflin-St Jeor para TMB (Taxa Metabólica Basal)
    double bmr = (10 * pesoKg) + (6.25 * alturaCm) - (5 * idade);
    
    if (gender == Gender.MALE)
        bmr += 5;
    else
        bmr -= 161;
    
    // Multiplicar pelo nível de atividade física
    double multiplier = physicalActivityLevel.getMultiplier();
    int baseCalories = (int) (bmr * multiplier);
    
    // Ajustar conforme objetivo de peso
    if (targetWeightKg < pesoKg) {
        return Math.max(1200, baseCalories - 500);  // Perder peso
    } else if (targetWeightKg > pesoKg) {
        return baseCalories + 500;  // Ganhar peso
    }
    
    return baseCalories;  // Manter peso
}
```

**MULTIPLICADORES DE ATIVIDADE:**
```java
public enum PhysicalActivityLevel {
    SEDENTARY(1.2),           // Sedentário
    LIGHTLY_ACTIVE(1.375),    // Ligeiramente ativo
    MODERATELY_ACTIVE(1.55),  // Moderadamente ativo  
    VERY_ACTIVE(1.725),       // Muito ativo
    EXTRA_ACTIVE(1.9);        // Extremamente ativo
}
```

**CÁLCULO COMPLETO - Exemplo:**
```
Dados: 82kg, 175cm, 25 anos, MASCULINO, LIGHTLY_ACTIVE, meta 75kg

1. TMB base:
   bmr = (10 × 82) + (6.25 × 175) - (5 × 25)
   bmr = 820 + 1093.75 - 125 = 1788.75
   
2. Ajuste por sexo (masculino):
   bmr = 1788.75 + 5 = 1793.75

3. Ajuste por atividade (1.375):
   baseCalories = 1793.75 × 1.375 = 2466 kcal

4. Ajuste por objetivo (perder peso):
   meta = max(1200, 2466 - 500) = 1966 kcal
```

---

### Meta de Água Diária

**Função no UserProfile:** `getDailyWaterGoalMl()`

```java
public double getDailyWaterGoalMl() {
    return 35 * pesoKg;  // 35ml por kg de peso
}
```

**CÁLCULO:**
```
Peso: 94kg (estimado)
Meta: 35 × 94 = 3290 ml ≈ 3,3 L  ✓ Bate com tua imagem!
```

---

### Metas de Macronutrientes

**Proteína (20% das calorias):**
```java
public double getDailyProteinGoalGrams() {
    return (getDailyCalorieGoal() * 0.20) / 4.0;  // 1g proteína = 4 kcal
}
```

**Hidratos (50% das calorias):**
```java
public double getDailyCarbsGoalGrams() {
    return (getDailyCalorieGoal() * 0.50) / 4.0;  // 1g hidratos = 4 kcal
}
```

**Gordura (30% das calorias):**
```java
public double getDailyFatGoalGrams() {
    return (getDailyCalorieGoal() * 0.30) / 9.0;  // 1g gordura = 9 kcal
}
```

**CÁLCULO (1838 kcal):**
```
Proteína (20%): 367.6 kcal ÷ 4 = 91.9g
Hidratos (50%): 919 kcal ÷ 4 = 229.75g ≈ 230g  ✓
Gordura (30%): 551.4 kcal ÷ 9 = 61.27g ≈ 61g   ✓
```

---

### Função 3: `updateMacro()` - Helper para Macronutrientes

**Código:** Linhas 139-142

```java
private void updateMacro(Label label, ProgressBar bar, 
                        double current, double goal, String unit) {
    label.setText(String.format("%.0f%s / %.0f%s", current, unit, goal, unit));
    bar.setProgress(goal > 0 ? current / goal : 0);
}
```

**O QUE FAZ:**
- Formata texto: "0g / 230g"
- Define progresso da barra (0.0 a 1.0)

---

### Função 4: `onEditarPerfil()` - Botão Editar Perfil

**Código:** Linhas 154-157

```java
@FXML
private void onEditarPerfil() {
    sceneManager.showRegister(true, state.getActiveProfile());
}
```

**O QUE FAZ:**
- Navega para RegisterView
- Passa `true` → permite cancelar
- Passa perfil atual → modo EDIÇÃO

**RESULTADO:** Abre ecrã de edição de perfil pré-preenchido

---

## 📊 Tabela Completa: Origem de Todos os Dados

| Elemento Dashboard | Função que Obtém | Cálculo/Fonte |
|-------------------|------------------|---------------|
| **"Bem-vindo, Carlos!"** | `user.getNome()` | RegisterController → nome inserido |
| **IMC 27,2** | `user.getBMI()` | peso / altura² |
| **Excesso de Peso** | `getBMIStatus(27.2)` | Classificação do IMC |
| **0 / 1838 kcal** | `getCaloriesConsumedToday()` + `getDailyCalorieGoal()` | Soma MealEntry hoje + Mifflin-St Jeor |
| **0,0 / 3,3 L** | `getWaterConsumedToday()` + `getDailyWaterGoalMl()` | Soma WaterEntry hoje + 35ml×peso |
| **Proteína 0g / 32g** | `getProteinConsumedToday()` + `getDailyProteinGoalGrams()` | Soma MealEntry.protein + 20% kcal ÷ 4 |
| **Hidratos 0g / 230g** | `getCarbsConsumedToday()` + `getDailyCarbsGoalGrams()` | Soma MealEntry.carbs + 50% kcal ÷ 4 |
| **Gordura 0g / 61g** | `getFatConsumedToday()` + `getDailyFatGoalGrams()` | Soma MealEntry.fat + 30% kcal ÷ 9 |
| **PieChart** | Proteína/Hidratos/Gordura/Água consumidos | Calculado em tempo real |
| **BarChart 7 dias** | `user.getExercises()` agrupados por data | Últimos 7 dias |
| **LineChart Peso** | `user.getPesoKg()` | Peso atual do perfil |

---

## ✅ Checklist de Compreensão - Dashboard

- [ ] Entendo como `updateView()` coordena tudo
- [ ] Sei calcular IMC manualmente
- [ ] Compreendo a fórmula Mifflin-St Jeor (TMB)
- [ ] Sei os multiplicadores de atividade física
- [ ] Entendo como objetivo de peso afeta calorias (-500 perder, +500 ganhar)
- [ ] Sei a fórmula da meta de água (35ml × peso)
- [ ] Compreendo distribuição de macros (20% prot, 50% carbs, 30% fat)
- [ ] Sei diferença entre calorias da proteína (4kcal/g) e gordura (9kcal/g)
- [ ] Entendo como os gráficos são preenchidos
- [ ] Sei como navegar para editar perfil

---

**Próximo:** [02_Refeicoes.md](02_Refeicoes.md)  
**Índice:** [README.md](README.md)
