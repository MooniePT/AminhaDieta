# 2️⃣ Como Adicionar Botão de Água com Quantidade Diferente

> Tutorial completo para adicionar botão "1 Litro" ou "750ml"

---

## 🎯 Objetivo

Adicionar botão "1 Litro (1000ml)" que adiciona 1000ml com um clique.

---

## 🔧 PASSO 1: Adicionar Botão no FXML

**Ficheiro:** `AminhaDieta/src/main/resources/fxml/HydrationView.fxml`

**Localização:** Procura a secção dos botões (onde está 250ml, 500ml)

### Código a Adicionar:

```xml
<!-- PROCURA ESTA SECÇÃO: -->
<HBox spacing="10">
    <Button text="Copo (250ml)" onAction="#add250"/>
    <Button text="Garrafa (500ml)" onAction="#add500"/>
    
    <!-- ✅ ADICIONA ESTAS LINHAS AQUI: -->
    <Button text="Garrafa Grande (750ml)" onAction="#add750"/>
    <Button text="1 Litro (1000ml)" onAction="#add1000"/>
</HBox>
```

---

## 🔧 PASSO 2: Adicionar Funções no Controller

**Ficheiro:** `AminhaDieta/src/main/java/app/ui/controller/HydrationController.java`

**Localização:** Procura `add250()` e `add500()` (linhas 48-56)

### Código a Adicionar:

```java
// ... (código existente)

@FXML
private void add500() {
    addWater(500);
}

// ═══════════════════════════════════════════════
// ✅ ADICIONA ESTAS FUNÇÕES AQUI:
// ═══════════════════════════════════════════════

@FXML
private void add750() {
    addWater(750);
}

@FXML
private void add1000() {
    addWater(1000);
}

// ═══════════════════════════════════════════════
// FIM DA ADIÇÃO
// ═══════════════════════════════════════════════

@FXML
private void addCustom() {
    // ... (código existente continua)
```

---

## 📋 Código Completo - Contexto

### HydrationController.java

```java
package app.ui.controller;

// ... (imports)

public class HydrationController {
    
    @FXML private Label mainLabel;
    @FXML private ProgressBar waterBar;
    @FXML private TextField customField;
    @FXML private Label statusLabel;
    
    private AppState state;
    private DataStore store;
    
    public void init(SceneManager sceneManager, AppState state, DataStore store) {
        this.state = state;
        this.store = store;
        updateView();
    }
    
    // ... updateView() ...
    
    @FXML
    private void add250() {
        addWater(250);
    }
    
    @FXML
    private void add500() {
        addWater(500);
    }
    
    // ✅ ADICIONA AQUI:
    @FXML
    private void add750() {
        addWater(750);
    }
    
    @FXML
    private void add1000() {
        addWater(1000);
    }
    // FIM
    
    @FXML
    private void addCustom() {
        // ... código existente ...
    }
    
    @FXML
    private void removeLast() {
        // ... código existente ...
    }
    
    private void addWater(double ml) {
        // ... código existente ...
    }
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

3. **Testar:**
   - Vai a "Hidratação"
   - Clica "1 Litro (1000ml)"
   - Verifica:
     - Label atualiza: ex. "1,0 L / 3,3 L"
     - Barra progride
     - Se atingir meta → alerta de parabéns!

---

## 🚰 Outras Quantidades Comuns

### Garrafa Pequena (330ml) - Lata
```java
@FXML
private void add330() {
    addWater(330);
}
```

**FXML:**
```xml
<Button text="Lata (330ml)" onAction="#add330"/>
```

---

### Garrafa Média (600ml)
```java
@FXML
private void add600() {
    addWater(600);
}
```

**FXML:**
```xml
<Button text="Garrafa Média (600ml)" onAction="#add600"/>
```

---

### Garrafa Extra Grande (1500ml)
```java
@FXML
private void add1500() {
    addWater(1500);
}
```

**FXML:**
```xml
<Button text="1.5 Litros" onAction="#add1500"/>
```

---

### Copo Pequeno (150ml)
```java
@FXML
private void add150() {
    addWater(150);
}
```

**FXML:**
```xml
<Button text="Copo Pequeno (150ml)" onAction="#add150"/>
```

---

## 🎨 Melhorar Layout (Opcional)

Se tiveres muitos botões, podes organizá-los em **2 linhas:**

```xml
<VBox spacing="10">
    <!-- Linha 1: Pequenas quantidades -->
    <HBox spacing="10">
        <Button text="Copo Pequeno (150ml)" onAction="#add150"/>
        <Button text="Copo (250ml)" onAction="#add250"/>
        <Button text="Lata (330ml)" onAction="#add330"/>
    </HBox>
    
    <!-- Linha 2: Grandes quantidades -->
    <HBox spacing="10">
        <Button text="Garrafa (500ml)" onAction="#add500"/>
        <Button text="Garrafa Grande (750ml)" onAction="#add750"/>
        <Button text="1 Litro" onAction="#add1000"/>
        <Button text="1.5 Litros" onAction="#add1500"/>
    </HBox>
</VBox>
```

---

## 📊 Referência Rápida - Quantidades

| Quantidade | ml | Litros | Uso Típico |
|------------|-----|--------|------------|
| Copo Pequeno | 150 | 0.15 | Café, chá |
| Copo Normal | 250 | 0.25 | Água, sumo |
| Lata | 330 | 0.33 | Refrigerante |
| Garrafa Pequena | 500 | 0.5 | Água comprada |
| Garrafa Média | 600 | 0.6 | Água desportiva |
| Garrafa Grande | 750 | 0.75 | Garrafa reutilizável |
| 1 Litro | 1000 | 1.0 | Garrafa grande |
| 1.5 Litros | 1500 | 1.5 | Garrafa família |

---

## ✅ Checklist

- [ ] Adicionei botões no FXML
- [ ] Adicionei funções `add750()` e `add1000()` no Controller
- [ ] Compilei sem erros
- [ ] Testei cada botão
- [ ] Verifiquei que meta é atingida corretamente
- [ ] Fiz commit: `git commit -m "Adicionar botões 750ml e 1L em Hidratação"`

---

**Próximo:** [03_adicionar_tipo_exercicio.md](03_adicionar_tipo_exercicio.md)  
**Anterior:** [01_adicionar_botao_refeicao_rapida.md](01_adicionar_botao_refeicao_rapida.md)  
**Índice:** [README.md](README.md)
