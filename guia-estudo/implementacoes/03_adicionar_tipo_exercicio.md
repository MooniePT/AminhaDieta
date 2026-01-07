# 3️⃣ Como Adicionar Novo Tipo de Exercício

> Tutorial para adicionar tipo à lista (ex: "Dança", "Ténis", "Futebol")

---

## 🎯 Objetivo

Adicionar novos tipos de exercício ao ComboBox, mantendo possibilidade de escrever tipo personalizado.

---

## 🔧 PASSO 1: Modificar Controller

**Ficheiro:** `AminhaDieta/src/main/java/app/ui/controller/ExerciseController.java`

**Localização:** Função `init()`, linhas 35-42

### Código ANTES:

```java
public void init(SceneManager sceneManager, AppState state, DataStore store) {
    this.state = state;
    this.store = store;
    
    // Preencher ComboBox com tipos pré-definidos
    typeCombo.getItems().addAll(
        "Caminhada", "Corrida", "Ciclismo", 
        "Natação", "Musculação", "Yoga"
    );
    
    refreshList();
}
```

### Código DEPOIS (com novos tipos):

```java
public void init(SceneManager sceneManager, AppState state, DataStore store) {
    this.state = state;
    this.store = store;
    
    // Preencher ComboBox com tipos pré-definidos
    typeCombo.getItems().addAll(
        "Caminhada", 
        "Corrida", 
        "Ciclismo", 
        "Natação", 
        "Musculação", 
        "Yoga",
        // ✅ ADICIONA AQUI OS NOVOS:
        "Dança",
        "Ténis",
        "Futebol",
        "Basquetebol",
        "Escalada",
        "Boxe",
        "Pilates",
        "CrossFit",
        "Remo",
        "Hidroginástica"
    );
    
    refreshList();
}
```

---

## 📋 Lista Completa de Tipos Sugeridos

### Cardio
- Caminhada
- Corrida
- Ciclismo
- Natação
- Remo
- Elíptica
- Dança
- Jump (corda)

### Desportos de Equipa
- Futebol
- Basquetebol
- Voleibol
- Andebol
- Rugby
- Hóquei

### Desportos de Raquete
- Ténis
- Badminton
- Squash
- Padel
- Ténis de Mesa

### Força e Resistência
- Musculação
- Crossfit
- Boxe
- Escalada
- Ginástica
- Calistenia

### Flexibilidade e Equilíbrio
- Yoga
- Pilates
- Tai Chi
- Alongamento

### Aquáticos
- Natação
- Hidroginástica
- Surf
- Stand-Up Paddle
- Mergulho

---

## 🔧 ALTERNATIVA: Carregar de Ficheiro

Se quiseres uma lista MUITO grande ou editável:

### PASSO 1: Criar Lista em Constante

**No topo da classe ExerciseController:**

```java
public class ExerciseController {
    
    // ✅ ADICIONA ISTO NO TOPO:
    private static final String[] EXERCISE_TYPES = {
        // Cardio
        "Caminhada", "Corrida", "Ciclismo", "Natação", "Remo", 
        "Elíptica", "Dança", "Jump",
        
        // Desportos Equipa
        "Futebol", "Basquetebol", "Voleibol", "Andebol", 
        "Rugby", "Hóquei",
        
        // Raquetes
        "Ténis", "Badminton", "Squash", "Padel", "Ténis de Mesa",
        
        // Força
        "Musculação", "CrossFit", "Boxe", "Escalada", 
        "Ginástica", "Calistenia",
        
        // Flexibilidade
        "Yoga", "Pilates", "Tai Chi", "Alongamento",
        
        // Aquáticos
        "Hidroginástica", "Surf", "Stand-Up Paddle", "Mergulho",
        
        // Outros
        "Patinagem", "Ski", "Snowboard", "Skate"
    };
    
    @FXML private ComboBox<String> typeCombo;
    // ... resto do código
```

### PASSO 2: Usar no init()

```java
public void init(SceneManager sceneManager, AppState state, DataStore store) {
    this.state = state;
    this.store = store;
    
    // Preencher com lista completa
    typeCombo.getItems().addAll(EXERCISE_TYPES);
    
    refreshList();
}
```

---

## 🎨 Organizar por Categoria (Avançado)

Se quiseres separadores por categoria no ComboBox:

```java
typeCombo.getItems().addAll(
    "── CARDIO ──",
    "Caminhada", "Corrida", "Ciclismo", "Natação",
    "── DESPORTOS ──",
    "Futebol", "Basquetebol", "Ténis",
    "── FORÇA ──",
    "Musculação", "CrossFit", "Boxe",
    "── FLEXIBILIDADE ──",
    "Yoga", "Pilates", "Tai Chi"
);
```

**Nota:** As linhas `"── ... ──"` são só visuais, o utilizador PODE selecioná-las (não é ideal mas funciona)

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
   - Vai a "Exercícios"
   - Clica no ComboBox de tipo
   - Verifica que novos tipos aparecem
   - Seleciona "Dança"
   - Adiciona exercício normalmente
   - **Também testa** escrever tipo personalizado: "Skate no Parque"

---

## 📊 Tabela: Estimativa de Calorias por Hora

Para ajudar o utilizador:

| Tipo | Kcal/hora (aprox.) | Intensidade |
|------|-------------------|-------------|
| Caminhada | 200-300 | Baixa |
| Corrida | 600-900 | Alta |
| Ciclismo | 400-700 | Média-Alta |
| Natação | 400-700 | Média-Alta |
| Dança | 300-500 | Média |
| Ténis | 400-600 | Média-Alta |
| Futebol | 500-800 | Alta |
| Musculação | 300-500 | Média |
| Yoga | 150-300 | Baixa-Média |
| Pilates | 200-400 | Média |
| Boxe | 600-900 | Alta |
| CrossFit | 500-800 | Alta |

**Fonte:** American Council on Exercise (ACE)

---

## ✅ Checklist

- [ ] Adicionei novos tipos ao `typeCombo.getItems().addAll()`
- [ ] Compilei sem erros
- [ ] Testei selecionando novo tipo
- [ ] Verifiquei que ainda posso escrever tipo personalizado
- [ ] Fiz commit: `git commit -m "Adicionar novos tipos de exercício"`

---

**Próximo:** [04_editar_eliminar_exercicios.md](04_editar_eliminar_exercicios.md)  
**Anterior:** [02_adicionar_botao_agua.md](02_adicionar_botao_agua.md)  
**Índice:** [README.md](README.md)
