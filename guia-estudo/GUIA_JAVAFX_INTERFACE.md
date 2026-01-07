# 🎨 Guia JavaFX - Interface Gráfica Explicada

> Aprende como funciona a parte visual da aplicação "A Minha Dieta"

---

## 📑 Índice

1. [O que é JavaFX?](#o-que-é-javafx)
2. [Estrutura de um Ecrã](#estrutura-de-um-ecrã)
3. [FXML - Desenhando Interfaces](#fxml---desenhando-interfaces)
4. [CSS - Estilização](#css---estilização)
5. [Componentes Mais Usados](#componentes-mais-usados)
6. [Layouts](#layouts)
7. [Eventos](#eventos)
8. [Gráficos](#gráficos)

---

## 🖼️ O que é JavaFX?

JavaFX é uma **biblioteca** (conjunto de ferramentas) para criar interfaces gráficas em Java.

**Analogia:** Se Java é a língua que falas, JavaFX é o conjunto de pincéis e tintas para pintar a interface.

### Componentes Principais

```
Stage (Janela)
  └── Scene (Conteúdo)
       └── Layout (Organização)
            └── Nodes (Elementos: botões, textos, etc.)
```

**Exemplo:**
```java
Stage stage = new Stage();           // Janela
Scene scene = new Scene(root);       // Conteúdo
stage.setScene(scene);               // Coloca conteúdo na janela
stage.show();                        // Mostra tudo
```

---

## 📐 Estrutura de um Ecrã

### 1. Ficheiro FXML (O Desenho)

**Localização:** `src/main/resources/fxml/login.fxml`

```xml
<?xml version="1.0" encoding="UTF-8"?>
<?import javafx.scene.layout.VBox?>
<?import javafx.scene.control.Label?>
<?import javafx.scene.control.Button?>

<VBox xmlns:fx="http://javafx.com/fxml"
      fx:controller="app.ui.controller.LoginController"
      spacing="20" alignment="CENTER">
    
    <Label text="Bem-vindo!" style="-fx-font-size: 24px;" />
    <Button fx:id="loginButton" text="Entrar" onAction="#handleLogin" />
    
</VBox>
```

**Explicação linha por linha:**

```xml
<?xml version="1.0" encoding="UTF-8"?>
```
Declara que é um ficheiro XML (linguagem de marcação)

```xml
<?import javafx.scene.layout.VBox?>
```
Importa a classe `VBox` (caixa vertical)

```xml
<VBox xmlns:fx="http://javafx.com/fxml"
      fx:controller="app.ui.controller.LoginController"
```
- `VBox` → Container que organiza elementos em **vertical**
- `fx:controller` → Diz qual classe Java controla este ecrã

```xml
spacing="20" alignment="CENTER">
```
- `spacing="20"` → Espaço de 20px entre elementos
- `alignment="CENTER"` → Tudo centrado

```xml
<Label text="Bem-vindo!" style="-fx-font-size: 24px;" />
```
- `Label` → Texto simples
- `text` → O que mostra
- `style` → Estilo inline (tamanho da letra)

```xml
<Button fx:id="loginButton" text="Entrar" onAction="#handleLogin" />
```
- `Button` → Botão
- `fx:id="loginButton"` → ID para usar no Java
- `text="Entrar"` → Texto do botão
- `onAction="#handleLogin"` → Chama método `handleLogin()` quando clicado

### 2. Controlador Java (A Lógica)

**Localização:** `src/main/java/app/ui/controller/LoginController.java`

```java
package app.ui.controller;

import javafx.fxml.FXML;
import javafx.scene.control.Button;

public class LoginController {
    
    @FXML
    private Button loginButton;
    
    @FXML
    private void handleLogin() {
        System.out.println("Botão clicado!");
        // Lógica aqui
    }
}
```

**Explicação:**

```java
@FXML
private Button loginButton;
```
- `@FXML` → Liga ao elemento FXML com `fx:id="loginButton"`
- `private Button` → Só o controller vê

```java
@FXML
private void handleLogin() {
```
- `@FXML` → Método chamado pelo FXML
- Nome tem de corresponder ao `onAction="#handleLogin"`

### 3. Ficheiro CSS (O Estilo)

**Localização:** `src/main/resources/css/style.css`

```css
.button {
    -fx-background-color: #667eea;
    -fx-text-fill: white;
    -fx-font-size: 14px;
    -fx-padding: 10px 20px;
    -fx-background-radius: 5px;
}

.button:hover {
    -fx-background-color: #5568d3;
}

.label {
    -fx-font-family: "Segoe UI";
    -fx-text-fill: #333333;
}
```

**Explicação:**

```css
.button {
```
Aplica a **todos** os botões (classe CSS)

```css
-fx-background-color: #667eea;
```
Cor de fundo (roxo: #667eea)

```css
-fx-text-fill: white;
```
Cor do texto (branco)

```css
-fx-padding: 10px 20px;
```
Espaçamento interno (10px em cima/baixo, 20px esquerda/direita)

```css
.button:hover {
```
Quando passas o rato por cima

---

## 🧩 Componentes Mais Usados

### Label - Texto Simples

**FXML:**
```xml
<Label text="Olá Mundo!" style="-fx-font-size: 18px;" />
```

**Java:**
```java
@FXML
private Label welcomeLabel;

welcomeLabel.setText("Bem-vindo, Carlos!");
```

### TextField - Campo de Texto

**FXML:**
```xml
<TextField fx:id="nameField" promptText="Digite seu nome" />
```

**Java:**
```java
@FXML
private TextField nameField;

String nome = nameField.getText();        // Ler
nameField.setText("Carlos");              // Escrever
nameField.clear();                        // Limpar
```

**Propriedades:**
- `promptText` → Texto de ajuda (desaparece ao escrever)
- `editable` → Se pode editar (true/false)

### TextArea - Texto Multilinha

**FXML:**
```xml
<TextArea fx:id="notesArea" prefRowCount="5" wrapText="true" />
```

**Java:**
```java
@FXML
private TextArea notesArea;

notesArea.setText("Linha 1\nLinha 2\nLinha 3");
```

### Button - Botão

**FXML:**
```xml
<Button text="Clica-me!" onAction="#handleClick" />
```

**Java:**
```java
@FXML
private void handleClick() {
    System.out.println("Clicaste!");
}
```

### ListView - Lista de Itens

**FXML:**
```xml
<ListView fx:id="itemsList" />
```

**Java:**
```java
@FXML
private ListView<String> itemsList;

// Adicionar itens
itemsList.getItems().add("Item 1");
itemsList.getItems().addAll("Item 2", "Item 3");

// Obter selecionado
String selected = itemsList.getSelectionModel().getSelectedItem();

// Limpar
itemsList.getItems().clear();
```

### ComboBox - Menu Dropdown

**FXML:**
```xml
<ComboBox fx:id="genderCombo" promptText="Selecione..." />
```

**Java:**
```java
@FXML
private ComboBox<String> genderCombo;

// Preencher
genderCombo.getItems().addAll("Masculino", "Feminino");

// Obter selecionado
String gender = genderCombo.getValue();

// Definir valor
genderCombo.setValue("Masculino");
```

### Spinner - Seletor de Números

**FXML:**
```xml
<Spinner fx:id="ageSpinner" min="1" max="120" initialValue="25" />
```

**Java:**
```java
@FXML
private Spinner<Integer> ageSpinner;

// Configurar
SpinnerValueFactory<Integer> valueFactory = 
    new SpinnerValueFactory.IntegerSpinnerValueFactory(1, 120, 25);
ageSpinner.setValueFactory(valueFactory);

// Obter valor
int age = ageSpinner.getValue();
```

### ProgressBar - Barra de Progresso

**FXML:**
```xml
<ProgressBar fx:id="caloriesProgressBar" prefWidth="200" />
```

**Java:**
```java
@FXML
private ProgressBar caloriesProgressBar;

// Definir progresso (0.0 a 1.0)
double progress = consumed / goal;  // Ex: 1200 / 2000 = 0.6
caloriesProgressBar.setProgress(progress);

// 0% = 0.0, 50% = 0.5, 100% = 1.0
```

### DatePicker - Seletor de Data

**FXML:**
```xml
<DatePicker fx:id="datePicker" />
```

**Java:**
```java
@FXML
private DatePicker datePicker;

// Definir data
datePicker.setValue(LocalDate.now());

// Obter data
LocalDate date = datePicker.getValue();
```

---

## 📦 Layouts

Layouts organizam os elementos na tela.

### VBox - Caixa Vertical

```xml
<VBox spacing="10" alignment="CENTER">
    <Label text="Item 1" />
    <Label text="Item 2" />
    <Label text="Item 3" />
</VBox>
```

**Resultado:**
```
┌─────────┐
│ Item 1  │
│ Item 2  │
│ Item 3  │
└─────────┘
```

### HBox - Caixa Horizontal

```xml
<HBox spacing="10" alignment="CENTER">
    <Button text="Sim" />
    <Button text="Não" />
</HBox>
```

**Resultado:**
```
┌─────────────────┐
│  [Sim]  [Não]   │
└─────────────────┘
```

### GridPane - Grelha

```xml
<GridPane hgap="10" vgap="10">
    <Label text="Nome:" GridPane.rowIndex="0" GridPane.columnIndex="0" />
    <TextField GridPane.rowIndex="0" GridPane.columnIndex="1" />
    
    <Label text="Idade:" GridPane.rowIndex="1" GridPane.columnIndex="0" />
    <Spinner GridPane.rowIndex="1" GridPane.columnIndex="1" />
</GridPane>
```

**Resultado:**
```
┌──────────┬─────────────┐
│ Nome:    │ [______]    │
├──────────┼─────────────┤
│ Idade:   │ [↑25↓]      │
└──────────┴─────────────┘
```

### BorderPane - Layout de Bordas

```xml
<BorderPane>
    <top>
        <Label text="Cabeçalho" />
    </top>
    <left>
        <VBox><!-- Menu lateral --></VBox>
    </left>
    <center>
        <StackPane><!-- Conteúdo principal --></StackPane>
    </center>
    <bottom>
        <Label text="Rodapé" />
    </bottom>
</BorderPane>
```

**Resultado:**
```
┌─────────────────────────┐
│      Cabeçalho          │
├────┬──────────────┬─────┤
│ M  │              │     │
│ e  │   Conteúdo   │     │
│ n  │              │     │
│ u  │              │     │
├────┴──────────────┴─────┤
│      Rodapé             │
└─────────────────────────┘
```

---

## ⚡ Eventos

### 1. Eventos de Clique

**Método 1: FXML**
```xml
<Button text="Clique" onAction="#handleClick" />
```

```java
@FXML
private void handleClick() {
    System.out.println("Clicado!");
}
```

**Método 2: Java**
```java
@FXML
private Button myButton;

@FXML
public void initialize() {
    myButton.setOnAction(event -> {
        System.out.println("Clicado!");
    });
}
```

### 2. Eventos de Teclado

```java
textField.setOnKeyPressed(event -> {
    if (event.getCode() == KeyCode.ENTER) {
        System.out.println("Enter pressionado!");
    }
});
```

### 3. Eventos de Mudança de Valor

```java
spinner.valueProperty().addListener((obs, oldVal, newVal) -> {
    System.out.println("Mudou de " + oldVal + " para " + newVal);
});
```

---

## 📊 Gráficos

### PieChart - Gráfico Circular

**FXML:**
```xml
<PieChart fx:id="macrosChart" />
```

**Java:**
```java
@FXML
private PieChart macrosChart;

public void updateChart() {
    macrosChart.getData().clear();
    
    macrosChart.getData().add(new PieChart.Data("Proteína", 60));
    macrosChart.getData().add(new PieChart.Data("Hidratos", 200));
    macrosChart.getData().add(new PieChart.Data("Gordura", 70));
}
```

**Resultado:**
```
    Proteína
    ╱─────╲
   │   🥧  │
    ╲─────╱
  Hidratos  Gordura
```

### LineChart - Gráfico de Linhas

**FXML:**
```xml
<LineChart fx:id="weightChart">
    <xAxis>
        <CategoryAxis label="Data" fx:id="xAxis" />
    </xAxis>
    <yAxis>
        <NumberAxis label="Peso (kg)" fx:id="yAxis" />
    </yAxis>
</LineChart>
```

**Java:**
```java
@FXML
private LineChart<String, Number> weightChart;

public void updateChart() {
    XYChart.Series<String, Number> series = new XYChart.Series<>();
    series.setName("Evolução do Peso");
    
    series.getData().add(new XYChart.Data<>("01/01", 72.0));
    series.getData().add(new XYChart.Data<>("08/01", 71.5));
    series.getData().add(new XYChart.Data<>("15/01", 70.8));
    
    weightChart.getData().clear();
    weightChart.getData().add(series);
}
```

### BarChart - Gráfico de Barras

**Java:**
```java
@FXML
private BarChart<String, Number> exerciseChart;

public void updateChart() {
    XYChart.Series<String, Number> series = new XYChart.Series<>();
    series.setName("Calorias Queimadas");
    
    series.getData().add(new XYChart.Data<>("Seg", 300));
    series.getData().add(new XYChart.Data<>("Ter", 450));
    series.getData().add(new XYChart.Data<>("Qua", 200));
    
    exerciseChart.getData().clear();
    exerciseChart.getData().add(series);
}
```

---

## 🎨 Estilização CSS

### Cores

```css
/* Cor sólida */
-fx-background-color: #667eea;

/* Gradiente */
-fx-background-color: linear-gradient(to right, #667eea, #764ba2);

/* Transparência */
-fx-background-color: rgba(102, 126, 234, 0.5);
```

### Texto

```css
.title {
    -fx-font-family: "Segoe UI";
    -fx-font-size: 24px;
    -fx-font-weight: bold;
    -fx-text-fill: #333333;
}
```

### Bordas e Cantos

```css
.fancy-button {
    -fx-border-color: #667eea;
    -fx-border-width: 2px;
    -fx-border-radius: 10px;
    -fx-background-radius: 10px;
}
```

### Espaçamento

```css
.container {
    -fx-padding: 20px;           /* Todos os lados */
    -fx-padding: 10px 20px;      /* Vertical | Horizontal */
    -fx-padding: 5px 10px 15px 20px;  /* Cima Direita Baixo Esquerda */
}
```

### Estados

```css
.button {
    -fx-background-color: #667eea;
}

.button:hover {
    -fx-background-color: #5568d3;
}

.button:pressed {
    -fx-background-color: #4450b8;
}

.text-field:focused {
    -fx-border-color: #667eea;
}
```

---

## 🔄 Animações

### Fade - Aparecer/Desaparecer

```java
FadeTransition fade = new FadeTransition(Duration.seconds(1), node);
fade.setFromValue(0.0);  // Invisível
fade.setToValue(1.0);    // Visível
fade.play();
```

### Translate - Mover

```java
TranslateTransition move = new TranslateTransition(Duration.seconds(1), node);
move.setByX(100);  // Move 100px para a direita
move.play();
```

### Scale - Aumentar/Diminuir

```java
ScaleTransition scale = new ScaleTransition(Duration.seconds(0.5), node);
scale.setToX(1.2);  // 120% largura
scale.setToY(1.2);  // 120% altura
scale.play();
```

### Rotate - Rodar

```java
RotateTransition rotate = new RotateTransition(Duration.seconds(2), node);
rotate.setByAngle(360);  // Roda 360 graus
rotate.play();
```

---

## 🛠️ Exemplo Completo

### Ecrã de Login Simples

**login.fxml:**
```xml
<?xml version="1.0" encoding="UTF-8"?>
<?import javafx.scene.layout.VBox?>
<?import javafx.scene.control.*?>

<VBox xmlns:fx="http://javafx.com/fxml"
      fx:controller="app.ui.controller.LoginController"
      spacing="15" alignment="CENTER" 
      style="-fx-padding: 40px; -fx-background-color: #f5f5f5;">
    
    <Label text="Bem-vindo!" 
           style="-fx-font-size: 28px; -fx-font-weight: bold;" />
    
    <TextField fx:id="usernameField" 
               promptText="Nome de utilizador" 
               prefWidth="250" />
    
    <PasswordField fx:id="passwordField" 
                   promptText="Senha" 
                   prefWidth="250" />
    
    <Button fx:id="loginButton" 
            text="Entrar" 
            onAction="#handleLogin" 
            prefWidth="250" />
    
    <Label fx:id="errorLabel" 
           textFill="red" 
           visible="false" />
    
</VBox>
```

**LoginController.java:**
```java
package app.ui.controller;

import javafx.fxml.FXML;
import javafx.scene.control.*;

public class LoginController {
    
    @FXML private TextField usernameField;
    @FXML private PasswordField passwordField;
    @FXML private Button loginButton;
    @FXML private Label errorLabel;
    
    @FXML
    private void handleLogin() {
        String username = usernameField.getText();
        String password = passwordField.getText();
        
        if (username.isEmpty() || password.isEmpty()) {
            errorLabel.setText("Preencha todos os campos!");
            errorLabel.setVisible(true);
            return;
        }
        
        // Validar credenciais
        if (authenticate(username, password)) {
            System.out.println("Login bem-sucedido!");
            // Mudar de ecrã
        } else {
            errorLabel.setText("Credenciais inválidas!");
            errorLabel.setVisible(true);
        }
    }
    
    private boolean authenticate(String user, String pass) {
        // Lógica de autenticação
        return user.equals("admin") && pass.equals("1234");
    }
}
```

---

## 💡 Dicas Importantes

### 1. IDs FXML têm de corresponder

```xml
<!-- FXML -->
<Button fx:id="myButton" />
```

```java
// Java - MESMO NOME!
@FXML
private Button myButton;
```

### 2. Métodos de Ação não têm parâmetros (ou têm ActionEvent)

```java
// Correto ✓
@FXML
private void handleClick() { }

// Também correto ✓
@FXML
private void handleClick(ActionEvent event) { }

// ERRADO ✗
@FXML
private void handleClick(String param) { }
```

### 3. Atualizar UI na Thread Correta

```java
// Se estás numa thread diferente
Platform.runLater(() -> {
    label.setText("Atualizado!");
});
```

### 4. Validação de Campos

```java
if (nameField.getText().isEmpty()) {
    showError("Nome obrigatório!");
    return;
}

try {
    int age = Integer.parseInt(ageField.getText());
} catch (NumberFormatException e) {
    showError("Idade inválida!");
    return;
}
```

---

## ✅ Checklist de Compreensão

- [ ] Entendo a diferença entre Stage, Scene e Node
- [ ] Sei o que é FXML e como se liga ao Java
- [ ] Compreendo `@FXML` e para que serve
- [ ] Sei usar Label, TextField e Button
- [ ] Entendo VBox, HBox e GridPane
- [ ] Sei adicionar eventos de clique
- [ ] Consigo atualizar um PieChart
- [ ] Entendo como aplicar CSS
- [ ] Sei validar campos de texto
- [ ] Compreendo o ciclo de vida de um Controller

---

**Agora dominas JavaFX! 🎉**
