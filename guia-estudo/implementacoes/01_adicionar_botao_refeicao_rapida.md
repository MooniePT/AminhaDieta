# 1️⃣ Como Adicionar Botão de Inserção Rápida - Refeições

> Tutorial completo para adicionar novo botão similar ao "Arroz"

---

## 🎯 Objetivo

Adicionar botão "Banana" que ao clicar:
1. Pede quantidade (g)
2. Calcula valores nutricionais proporcionais
3. Adiciona refeição automaticamente

---

## 📝 Valores Nutricionais (por 100g)

Vamos adicionar **Banana**:
- **Calorias:** 89 kcal
- **Proteína:** 1.1g
- **Hidratos:** 23g
- **Gordura:** 0.3g

**Fonte:** USDA Food Database

---

## 🔧 PASSO 1: Adicionar Botão no FXML

**Ficheiro:** `AminhaDieta/src/main/resources/fxml/MealsView.fxml`

**Localização:** Procura a secção dos botões rápidos (onde está Arroz, Massa, etc.)

### Código a Adicionar:

```xml
<!-- PROCURA ESTA SECÇÃO (está perto da linha 50-80): -->
<HBox spacing="10">
    <Button text="Arroz" onAction="#onAddRice"/>
    <Button text="Massa" onAction="#onAddPasta"/>
    <Button text="Batata" onAction="#onAddPotato"/>
    <Button text="Leite" onAction="#onAddMilk"/>
    <Button text="Ovos" onAction="#onAddEggs"/>
    <Button text="Pão" onAction="#onAddBread"/>
    
    <!-- ✅ ADICIONA ESTA LINHA AQUI: -->
    <Button text="Banana" onAction="#onAddBanana"/>
</HBox>
```

**Nota:** `onAction="#onAddBanana"` liga o botão à função no Controller

---

## 🔧 PASSO 2: Adicionar Função no Controller

**Ficheiro:** `AminhaDieta/src/main/java/app/ui/controller/MealsController.java`

**Localização:** Procura as funções `onAddRice()`, `onAddPasta()`, etc. (linhas 177-207)

### Código a Adicionar:

```java
// ADICIONA ESTA FUNÇÃO DEPOIS DE onAddBread() (linha 207):

@FXML
private void onAddBanana() {
    askQuantityAndAdd("Banana", 89, 1.1, 23, 0.3, false);
    //                   nome   kcal  prot carb fat  líquido?
}
```

**Explicação dos Parâmetros:**
- `"Banana"` → Nome do alimento
- `89` → Calorias por 100g
- `1.1` → Proteína por 100g
- `23` → Hidratos por 100g
- `0.3` → Gordura por 100g
- `false` → NÃO é líquido (usar gramas, não ml)

---

## 📋 Código Completo - Onde Inserir

### No MealsController.java

```java
// ... (código existente)

@FXML
private void onAddBread() {
    askQuantityAndAdd("Pão", 265, 9, 49, 3.2, false);
}

// ═══════════════════════════════════════════════
// ✅ ADICIONA ESTA FUNÇÃO AQUI:
// ═══════════════════════════════════════════════

@FXML
private void onAddBanana() {
    askQuantityAndAdd("Banana", 89, 1.1, 23, 0.3, false);
}

// ═══════════════════════════════════════════════
// FIM DA ADIÇÃO
// ═══════════════════════════════════════════════

private double parseDoubleOrZero(String s) {
    // ... (código existente continua)
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
   - Vai a "Refeições"
   - Clica no novo botão "Banana"
   - Escreve quantidade: `150` (g)
   - Verifica resultado:
     ```
     Banana (150g)
     Calorias: 133 kcal  (89 × 1.5 = 133.5 ≈ 133)
     Proteína: 1.65g     (1.1 × 1.5)
     Hidratos: 34.5g     (23 × 1.5)
     Gordura: 0.45g      (0.3 × 1.5)
     ```

---

## 🍎 Adicionar Outros Alimentos

### Maçã
```java
@FXML
private void onAddApple() {
    askQuantityAndAdd("Maçã", 52, 0.3, 14, 0.2, false);
}
```

### Iogurte Natural
```java
@FXML
private void onAddYogurt() {
    askQuantityAndAdd("Iogurte", 59, 10, 3.6, 0.4, false);
}
```

### Frango Grelhado
```java
@FXML
private void onAddChicken() {
    askQuantityAndAdd("Frango Grelhado", 165, 31, 0, 3.6, false);
}
```

### Sopa (líquido!)
```java
@FXML
private void onAddSoup() {
    askQuantityAndAdd("Sopa", 38, 1.5, 6, 0.8, true);
    //                                            ↑ true = ml
}
```

---

## 📊 Tabela de Referência - Valores por 100g

| Alimento | Kcal | Proteína | Hidratos | Gordura | Líquido? |
|----------|------|----------|----------|---------|----------|
| Banana | 89 | 1.1 | 23 | 0.3 | `false` |
| Maçã | 52 | 0.3 | 14 | 0.2 | `false` |
| Laranja | 47 | 0.9 | 12 | 0.1 | `false` |
| Iogurte Natural | 59 | 10 | 3.6 | 0.4 | `false` |
| Frango Grelhado | 165 | 31 | 0 | 3.6 | `false` |
| Salmão | 208 | 20 | 0 | 13 | `false` |
| Atum | 132 | 28 | 0 | 1.3 | `false` |
| Amêndoas | 579 | 21 | 22 | 50 | `false` |
| Manteiga Amendoim | 588 | 25 | 20 | 50 | `false` |
| Queijo | 402 | 25 | 1.3 | 33 | `false` |
| Chocolate Preto | 546 | 5 | 61 | 31 | `false` |
| Mel | 304 | 0.3 | 82 | 0 | `true` |
| Azeite | 884 | 0 | 0 | 100 | `true` |

---

## ✅ Checklist

- [ ] Adicionei `<Button text="Banana" onAction="#onAddBanana"/>` no FXML
- [ ] Adicionei função `onAddBanana()` no Controller
- [ ] Compilei sem erros
- [ ] Testei e funciona
- [ ] Fiz commit: `git commit -m "Adicionar botão Banana"`

---

**Próximo:** [02_adicionar_botao_agua.md](02_adicionar_botao_agua.md)  
**Índice:** [README.md](README.md)
