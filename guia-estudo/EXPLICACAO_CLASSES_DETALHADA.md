# 📋 Explicação Detalhada de Todas as Classes

> Este documento explica **cada classe** do projeto "A Minha Dieta" linha por linha, com exemplos práticos.

---

## 📑 Índice

1. [Classes Principais](#classes-principais)
2. [Classes do Model](#classes-do-model)
3. [Classes de Persistência](#classes-de-persistência)
4. [Classes de Interface](#classes-de-interface)
5. [Enumerações (Enums)](#enumerações-enums)

---

## 🚀 Classes Principais

### Main.java - O Coração da Aplicação

**Caminho:** `app/Main.java`

```java
package app;
```
**O que significa?** `package` é como uma "pasta" para organizar o código. Esta classe está na pasta `app`.

```java
import app.model.AppState;
import app.persistence.DataStore;
import app.ui.SceneManager;
import javafx.application.Application;
import javafx.stage.Stage;
```
**O que significa?** `import` traz classes de outros lugares para usar aqui.
- `AppState` → Estado da aplicação
- `DataStore` → Classe que guarda/carrega dados
- `SceneManager` → Gere os ecrãs
- `Application` → Classe base do JavaFX
- `Stage` → A janela

```java
public class Main extends Application {
```
**O que significa?**
- `public` → Toda a gente pode ver esta classe
- `class Main` → Cria uma classe chamada "Main"
- `extends Application` → Herda poderes da classe Application

```java
@Override
public void start(Stage stage) {
```
**O que significa?**
- `@Override` → Estamos a substituir um método que já existia
- `void` → Não devolve nada
- `start()` → Método que corre quando a aplicação começa
- `Stage stage` → Recebe a janela principal

```java
String appData = System.getenv("APPDATA");
if (appData == null) {
    appData = System.getProperty("user.home");
}
```
**O que significa?**
- Procura a pasta AppData do Windows
- Se não encontrar, usa a pasta do utilizador
- **Por que?** Para guardar dados num sítio seguro

```java
Path dataPath = Path.of(appData, "AMinhaDieta", "data", "appstate.dat");
```
**O que significa?**
- Cria o caminho completo para o ficheiro de dados
- **Exemplo:** `C:\Users\Carlos\AppData\Roaming\AMinhaDieta\data\appstate.dat`

```java
DataStore store = new DataStore(dataPath);
AppState state = store.load();
```
**O que significa?**
- Cria um objeto `DataStore` (gestor de dados)
- Carrega os dados guardados do ficheiro

```java
SceneManager sceneManager = new SceneManager(stage, state, store);
sceneManager.showInitialScene();
```
**O que significa?**
- Cria o gestor de ecrãs
- Mostra o primeiro ecrã (login ou dashboard)

```java
stage.setTitle("A Minha Dieta");
stage.getIcons().add(new javafx.scene.image.Image(...));
stage.show();
```
**O que significa?**
- Define o título da janela
- Adiciona o ícone
- Mostra a janela

### Launcher.java - O Arrancador

**Caminho:** `app/Launcher.java`

```java
public class Launcher {
    public static void main(String[] args) {
        Main.main(args);
    }
}
```

**Por que existe?** Resolve problemas com módulos do Java. É só um "intermediário".

---

## 🗂️ Classes do Model

### UserProfile.java - Perfil do Utilizador

**Caminho:** `app/model/UserProfile.java`

#### Atributos Principais

```java
private final UUID id = UUID.randomUUID();
```
**O que é?** Um identificador único para cada perfil
**Exemplo:** `550e8400-e29b-41d4-a716-446655440000`
**Por que `final`?** Não pode mudar depois de criado

```java
private String nome;
private int idade;
private double pesoKg;
private double alturaCm;
```
**O que são?** Dados pessoais do utilizador
**Por que `private`?** Só a própria classe pode mexer diretamente

```java
private Gender gender;
```
**O que é?** Género (Masculino/Feminino) - é um `enum` (explicado mais à frente)

```java
private PhysicalActivityLevel physicalActivityLevel;
```
**O que é?** Nível de atividade (Sedentário, Ativo, etc.)

#### Listas de Registos

```java
private final List<MealEntry> meals = new ArrayList<>();
private final List<WaterEntry> waters = new ArrayList<>();
private final List<WeightEntry> weights = new ArrayList<>();
private final List<Food> foods = new ArrayList<>();
private List<ExerciseEntry> exercises = new ArrayList<>();
```

**O que são?**
- `List<MealEntry>` → Lista de refeições
- `List<WaterEntry>` → Lista de registos de água
- `List<WeightEntry>` → História de pesos
- `List<Food>` → Base de dados pessoal de alimentos
- `List<ExerciseEntry>` → Exercícios feitos

**Exemplo de uso:**
```java
meals.add(new MealEntry("Arroz", 200, 5, 40, 2));
// Adiciona arroz: 200 calorias, 5g proteína, 40g hidratos, 2g gordura
```

#### Construtor

```java
public UserProfile(String nome, int idade, double pesoKg, double alturaCm, 
                   Gender gender, PhysicalActivityLevel physicalActivityLevel) {
    this.nome = nome;
    this.idade = idade;
    this.pesoKg = pesoKg;
    this.alturaCm = alturaCm;
    this.gender = gender;
    this.physicalActivityLevel = physicalActivityLevel;
    this.weighInFrequency = WeighInFrequency.WEEKLY;
    this.targetWeightKg = pesoKg;
    
    addWeightEntry(pesoKg);  // Adiciona peso inicial
}
```

**O que faz?** Cria um novo perfil com os dados fornecidos.

**Exemplo de uso:**
```java
UserProfile carlos = new UserProfile(
    "Carlos",                           // nome
    25,                                 // idade
    70.5,                               // peso em kg
    175,                                // altura em cm
    Gender.MALE,                        // género
    PhysicalActivityLevel.MODERATELY_ACTIVE  // nível atividade
);
```

#### Métodos de Cálculo

**Calcular IMC (Índice de Massa Corporal)**

```java
public double getBMI() {
    if (alturaCm <= 0) return 0;
    double heightM = alturaCm / 100.0;
    return pesoKg / (heightM * heightM);
}
```

**Como funciona:**
1. Converte altura de cm para metros
2. Divide peso por altura ao quadrado
3. **Fórmula:** IMC = peso / altura²

**Exemplo:**
```java
// Peso: 70kg, Altura: 175cm (1.75m)
double imc = perfil.getBMI();  // Resultado: 22.86
```

**Calcular Calorias Diárias**

```java
public int getDailyCalorieGoal() {
    // Equação de Mifflin-St Jeor
    double bmr = (10 * pesoKg) + (6.25 * alturaCm) - (5 * idade);
    
    if (gender == Gender.MALE)
        bmr += 5;
    else
        bmr -= 161;
    
    double multiplier = physicalActivityLevel.getMultiplier();
    int baseCalories = (int) (bmr * multiplier);
    
    // Ajusta para objetivo de peso
    if (targetWeightKg < pesoKg) {
        return Math.max(1200, baseCalories - 500);  // Perder peso
    } else if (targetWeightKg > pesoKg) {
        return baseCalories + 500;  // Ganhar peso
    }
    
    return baseCalories;  // Manter peso
}
```

**Como funciona:**
1. Calcula TMB (Taxa Metabólica Basal) - calorias em repouso
2. Adiciona 5 se homem, subtrai 161 se mulher
3. Multiplica pelo nível de atividade
4. Ajusta conforme objetivo (perder/ganhar/manter peso)

**Exemplo:**
```java
// Homem, 70kg, 175cm, 25 anos, moderadamente ativo
int calorias = perfil.getDailyCalorieGoal();  // ~2500 kcal
```

**Calcular Meta de Água**

```java
public double getDailyWaterGoalMl() {
    return 35 * pesoKg;
}
```

**Como funciona:** 35ml por kg de peso corporal.

**Exemplo:**
```java
// Peso: 70kg
double agua = perfil.getDailyWaterGoalMl();  // 2450ml = 2.45L
```

#### Métodos de Progresso Diário

**Calorias Consumidas Hoje**

```java
public int getCaloriesConsumedToday() {
    java.time.LocalDate today = java.time.LocalDate.now();
    return meals.stream()
            .filter(m -> m.getTimestamp().toLocalDate().equals(today))
            .mapToInt(MealEntry::getCalories)
            .sum();
}
```

**Como funciona:**
1. `LocalDate.now()` → Pega a data de hoje
2. `meals.stream()` → Transforma a lista numa "corrente" de dados
3. `.filter()` → Filtra só refeições de hoje
4. `.mapToInt()` → Pega as calorias de cada uma
5. `.sum()` → Soma tudo

**Exemplo:**
```java
// Comeste: pequeno-almoço (300), almoço (600), jantar (500)
int total = perfil.getCaloriesConsumedToday();  // 1400 kcal
```

**Proteína/Hidratos/Gordura de Hoje**

Funcionam da mesma forma, mas somam proteína, hidratos ou gordura:
```java
public double getProteinConsumedToday() { ... }
public double getCarbsConsumedToday() { ... }
public double getFatConsumedToday() { ... }
```

**Água Bebida Hoje**

```java
public double getWaterConsumedToday() {
    java.time.LocalDate today = java.time.LocalDate.now();
    return waters.stream()
            .filter(w -> w.getTimestamp().toLocalDate().equals(today))
            .mapToDouble(WaterEntry::getAmountMl)
            .sum();
}
```

### MealEntry.java - Registo de Refeição

**Caminho:** `app/model/MealEntry.java`

```java
public class MealEntry implements Serializable {
    private String description;      // "Arroz com frango"
    private int calories;            // 450
    private double protein;          // 30g
    private double carbs;            // 50g
    private double fat;              // 10g
    private LocalDateTime timestamp; // 2026-01-07 13:30
    
    public MealEntry(String description, int calories, double protein, 
                     double carbs, double fat) {
        this.description = description;
        this.calories = calories;
        this.protein = protein;
        this.carbs = carbs;
        this.fat = fat;
        this.timestamp = LocalDateTime.now();  // Hora atual
    }
}
```

**O que é `Serializable`?** Permite guardar o objeto num ficheiro.

### WaterEntry.java - Registo de Água

```java
public class WaterEntry implements Serializable {
    private double amountMl;         // 250ml
    private LocalDateTime timestamp; // Quando bebeste
    
    public WaterEntry(double amountMl) {
        this.amountMl = amountMl;
        this.timestamp = LocalDateTime.now();
    }
}
```

### ExerciseEntry.java - Registo de Exercício

```java
public class ExerciseEntry implements Serializable {
    private String type;             // "Corrida"
    private int duration;            // 30 minutos
    private double caloriesBurned;   // 300 kcal
    private LocalDateTime timestamp;
    
    public ExerciseEntry(String type, int duration, double caloriesBurned) {
        this.type = type;
        this.duration = duration;
        this.caloriesBurned = caloriesBurned;
        this.timestamp = LocalDateTime.now();
    }
}
```

### WeightEntry.java - Registo de Peso

```java
public class WeightEntry implements Serializable {
    private double weightKg;
    private LocalDate date;
    
    public WeightEntry(double weightKg) {
        this.weightKg = weightKg;
        this.date = LocalDate.now();
    }
}
```

### Food.java - Alimento

```java
public class Food implements Serializable {
    private String name;              // "Arroz"
    private double caloriesPer100g;   // 130 kcal
    private double proteinPer100g;    // 2.7g
    private double carbsPer100g;      // 28g
    private double fatPer100g;        // 0.3g
    
    public Food(String name, double caloriesPer100g, double proteinPer100g,
                double carbsPer100g, double fatPer100g) {
        this.name = name;
        this.caloriesPer100g = caloriesPer100g;
        this.proteinPer100g = proteinPer100g;
        this.carbsPer100g = carbsPer100g;
        this.fatPer100g = fatPer100g;
    }
}
```

### AppState.java - Estado Global

```java
public class AppState implements Serializable {
    private List<UserProfile> profiles = new ArrayList<>();
    private UserProfile currentProfile;
    
    public void addProfile(UserProfile profile) {
        profiles.add(profile);
    }
    
    public UserProfile getCurrentProfile() {
        return currentProfile;
    }
    
    public void setCurrentProfile(UserProfile profile) {
        this.currentProfile = profile;
    }
    
    public List<UserProfile> getProfiles() {
        return profiles;
    }
}
```

**O que faz?** Guarda todos os utilizadores e sabe qual está ativo.

---

## 💾 Classes de Persistência

### DataStore.java - Guardar e Carregar

```java
public class DataStore {
    private Path filePath;
    
    public DataStore(Path filePath) {
        this.filePath = filePath;
    }
    
    public void save(AppState state) {
        try {
            Files.createDirectories(filePath.getParent());
            
            try (ObjectOutputStream out = new ObjectOutputStream(
                    new FileOutputStream(filePath.toFile()))) {
                out.writeObject(state);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    public AppState load() {
        if (!Files.exists(filePath)) {
            return new AppState();  // Novo se não existe
        }
        
        try (ObjectInputStream in = new ObjectInputStream(
                new FileInputStream(filePath.toFile()))) {
            return (AppState) in.readObject();
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
            return new AppState();
        }
    }
}
```

**Como funciona:**

**Guardar:**
1. Cria as pastas se não existirem
2. Abre um `ObjectOutputStream` (corrente de objetos)
3. Escreve o objeto `AppState` no ficheiro
4. Fecha automaticamente (`try-with-resources`)

**Carregar:**
1. Verifica se o ficheiro existe
2. Se não existe, cria um `AppState` novo
3. Se existe, lê o objeto do ficheiro
4. Devolve o `AppState` carregado

---

## 🎨 Classes de Interface

### SceneManager.java - Gestor de Ecrãs

```java
public class SceneManager {
    private Stage stage;
    private AppState appState;
    private DataStore dataStore;
    
    public SceneManager(Stage stage, AppState appState, DataStore dataStore) {
        this.stage = stage;
        this.appState = appState;
        this.dataStore = dataStore;
    }
    
    public void showInitialScene() {
        if (appState.getProfiles().isEmpty()) {
            showRegisterScene();
        } else if (appState.getCurrentProfile() == null) {
            showLoginScene();
        } else {
            showDashboard();
        }
    }
    
    private void loadScene(String fxmlFile) {
        try {
            FXMLLoader loader = new FXMLLoader(
                getClass().getResource("/fxml/" + fxmlFile));
            Parent root = loader.load();
            
            // Passa dados ao controller
            Object controller = loader.getController();
            if (controller instanceof BaseController) {
                ((BaseController) controller).initialize(this, appState, dataStore);
            }
            
            Scene scene = new Scene(root);
            stage.setScene(scene);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    public void showLoginScene() { loadScene("login.fxml"); }
    public void showDashboard() { loadScene("dashboard.fxml"); }
    public void showRegisterScene() { loadScene("register.fxml"); }
}
```

**Como funciona:**
1. `showInitialScene()` decide qual ecrã mostrar primeiro
2. `loadScene()` carrega um ficheiro FXML
3. Passa os dados (`appState`, `dataStore`) ao controller
4. Muda o ecrã da janela

### Controllers - Exemplos

#### LoginController.java

```java
public class LoginController {
    @FXML
    private ListView<String> profileListView;
    
    @FXML
    private Button loginButton;
    
    private SceneManager sceneManager;
    private AppState appState;
    
    public void initialize(SceneManager sm, AppState state, DataStore ds) {
        this.sceneManager = sm;
        this.appState = state;
        
        // Preenche lista com nomes dos perfis
        profileListView.getItems().clear();
        for (UserProfile p : state.getProfiles()) {
            profileListView.getItems().add(p.getNome());
        }
    }
    
    @FXML
    private void handleLogin() {
        String selected = profileListView.getSelectionModel().getSelectedItem();
        if (selected != null) {
            // Encontra o perfil
            UserProfile profile = appState.getProfiles().stream()
                .filter(p -> p.getNome().equals(selected))
                .findFirst().orElse(null);
            
            if (profile != null) {
                appState.setCurrentProfile(profile);
                sceneManager.showDashboard();
            }
        }
    }
}
```

**Como funciona:**
1. `@FXML` liga ao ficheiro FXML
2. `initialize()` corre quando o ecrã é carregado
3. Preenche a lista com nomes
4. `handleLogin()` corre quando clicas "Entrar"
5. Encontra o perfil selecionado
6. Define como perfil ativo
7. Muda para o dashboard

---

## 🔢 Enumerações (Enums)

### Gender - Género

```java
public enum Gender {
    MALE("Masculino"), 
    FEMALE("Feminino");
    
    private String label;
    
    Gender(String label) {
        this.label = label;
    }
    
    public String toString() {
        return label;
    }
}
```

**O que é um `enum`?** Uma lista fixa de valores possíveis.

**Uso:**
```java
Gender genero = Gender.MALE;
System.out.println(genero);  // Imprime: "Masculino"
```

### PhysicalActivityLevel - Nível de Atividade

```java
public enum PhysicalActivityLevel {
    SEDENTARY("Sedentário", 1.2),
    LIGHTLY_ACTIVE("Levemente Ativo", 1.375),
    MODERATELY_ACTIVE("Moderadamente Ativo", 1.55),
    VERY_ACTIVE("Muito Ativo", 1.725),
    EXTRA_ACTIVE("Extremamente Ativo", 1.9);
    
    private final String label;
    private final double multiplier;
    
    PhysicalActivityLevel(String label, double multiplier) {
        this.label = label;
        this.multiplier = multiplier;
    }
    
    public double getMultiplier() {
        return multiplier;
    }
    
    public String toString() {
        return label;
    }
}
```

**Como usar:**
```java
PhysicalActivityLevel nivel = PhysicalActivityLevel.MODERATELY_ACTIVE;
double mult = nivel.getMultiplier();  // 1.55
```

---

## 🔗 Como Tudo se Liga - Exemplo Completo

### Cenário: Adicionar uma Refeição

**1. Utilizador clica no botão "Adicionar"**

```java
// MealsController.java
@FXML
private void handleAddMeal() {
```

**2. Recolhe dados dos campos**

```java
String description = descriptionField.getText();
int calories = Integer.parseInt(caloriesField.getText());
double protein = Double.parseDouble(proteinField.getText());
double carbs = Double.parseDouble(carbsField.getText());
double fat = Double.parseDouble(fatField.getText());
```

**3. Cria objeto MealEntry**

```java
MealEntry meal = new MealEntry(description, calories, protein, carbs, fat);
```

**4. Adiciona ao perfil atual**

```java
UserProfile currentProfile = appState.getCurrentProfile();
currentProfile.getMeals().add(meal);
```

**5. Guarda no ficheiro**

```java
dataStore.save(appState);
```

**6. Atualiza a interface**

```java
refreshMealsList();
showConfirmation("Refeição adicionada!");
}
```

**Fluxo completo:**
```
Botão → Controller → Criar Objeto → Adicionar à Lista → Guardar → Atualizar UI
```

---

## ✅ Checklist de Compreensão

- [ ] Entendo o que faz `Main.java`
- [ ] Sei para que serve cada atributo do `UserProfile`
- [ ] Compreendo como funcionam as listas (`List<>`)
- [ ] Entendo o cálculo de calorias diárias
- [ ] Sei como funciona a serialização
- [ ] Compreendo o papel do `SceneManager`
- [ ] Entendo como os Controllers ligam ao FXML
- [ ] Sei o que são Enums e para que servem
- [ ] Consigo seguir o fluxo de adicionar uma refeição

---

**Parabéns! 🎉 Agora conheces todas as classes em detalhe!**
