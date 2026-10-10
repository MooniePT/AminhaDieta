# 📚 Funcionalidades Detalhadas - Índice

> **Análise Completa e Discriminada** de TODAS as funções da aplicação "A Minha Dieta"  
> Cada ficheiro explica uma funcionalidade específica em detalhe

---

## 📂 Estrutura de Documentação

Este diretório contém análises detalhadas de cada funcionalidade da aplicação, mostrando:
- ✅ **Todas as funções** usadas
- ✅ **Código completo** com explicações linha por linha  
- ✅ **Origem dos dados** - de onde vem cada informação
- ✅ **Fluxos completos** - do clique até à persistência
- ✅ **Cálculos matemáticos** - todas as fórmulas explicadas

---

## 📖 Documentos Disponíveis

### 1️⃣ [Dashboard (Home)](01_Dashboard.md)
**O que mostra:**
- Bem-vindo + Nome do utilizador
- Calorias consumidas vs meta
- Água consumida vs meta  
- IMC e status
- Macronutrientes (Proteína, Hidratos, Gordura)
- 3 Gráficos (PieChart, BarChart, LineChart)

**Funções:** `init()`, `updateView()`, `updateMacro()`, `getBMIStatus()`, `onEditarPerfil()`

---

### 2️⃣ [Refeições](02_Refeicoes.md)
**O que permite:**
- Adicionar refeição manualmente
- Selecionar da base de dados pessoal
- Atalhos rápidos (Arroz, Massa, Batata, Leite, Ovos, Pão)
- Ver refeições de hoje

**Funções:** `init()`, `setupFoodSelector()`, `onFoodSelected()`, `onAddMeal()`, `askQuantityAndAdd()`, `onAddRice()`, `onAddPasta()`, `onAddPotato()`, `onAddMilk()`, `onAddEggs()`, `onAddBread()`, `parseDoubleOrZero()`, `updateList()`

---

### 3️⃣ [Hidratação](03_Hidratacao.md)
**O que permite:**
- Adicionar 250ml (copo)
- Adicionar 500ml (garrafa)
- Adicionar quantidade personalizada
- Remover último registo
- Ver progresso em tempo real

**Funções:** `init()`, `updateView()`, `add250()`, `add500()`, `addCustom()`, `removeLast()`, `addWater()`

---

### 4️⃣ [Exercícios](04_Exercicios.md)
**O que permite:**
- Registar exercício (tipo, duração, calorias)
- Tipos pré-definidos ou personalizados
- Ver exercícios do dia
- Alertas de motivação

**Funções:** `init()`, `onAddExercise()`, `refreshList()`

---

### 5️⃣ [Histórico](05_Historico.md)
**O que permite:**
- Ver todas as refeições em tabela
- Filtrar por descrição
- Filtrar por intervalo de datas
- Exportar para PDF
- Gráficos de evolução

**Funções:** `init()`, `setupTable()`, `loadData()`, `setupSearch()`, `updateFilter()`, `handleDownloadPdf()`

---

### 6️⃣ [Definições](06_Definicoes.md)
**O que permite:**
- Ativar/desativar modo arco-íris
- Escolher cor estática
- Mudar fonte da aplicação

**Funções:** `init()`, `onRainbowToggled()`, `onColorChanged()`, `onFontChanged()`

---

### 7️⃣ [Gestão de Perfis](07_Perfis.md)
**O que cobre:**
- **Criar Perfil:** Todos os campos, validações
- **Editar Perfil:** Pré-preenchimento, guardar alterações
- **Eliminar Perfil:** Confirmação, remoção
- **Trocar Perfil:** Voltar ao login
- **Login:** Selecionar perfil existente

**Funções:** `init()`, `onGuardar()`, `onCancelar()`, `onEliminar()`, `onTrocarPerfil()`, `handleLogin()`

---

### 8️⃣ [Navegação Global](08_Navegacao.md)
**O que explica:**
- SceneManager e como funciona
- Mudança entre ecrãs
- DashboardController como hub central
- Passagem de dados entre controllers

**Funções:** `showLogin()`, `showRegister()`, `showDashboard()`, `showInitialScene()`, `loadView()`, `onHome()`, `onMeals()`, `onHydration()`, `onExercise()`, `onHistory()`, `onSettings()`

---

### 🔥 9️⃣ [Persistência de Dados - O MAIS IMPORTANTE!](09_Persistencia.md)
**O que explica:**
- Como os dados são guardados em disco
- Serialização de objetos
- DataStore: save() e load()
- Onde fica o ficheiro (appstate.dat)
- O que acontece quando fechas/abres a aplicação
- Estrutura completa do AppState

**Funções:** `save()`, `load()`, estrutura de AppState, serialVersionUID

---

## 🎯 Como Usar Esta Documentação

### Para Compreender uma Funcionalidade
1. Abre o ficheiro correspondente (ex: `02_Refeicoes.md`)
2. Lê a secção "O Que Permite Fazer"
3. Vê a lista de funções
4. Lê cada função em detalhe

### Para Encontrar Onde Vem um Dado
1. Identifica o que queres saber (ex: "Calorias Hoje")
2. Procura no ficheiro relevante (`01_Dashboard.md`)
3. Vê a secção "Origem dos Dados"

### Para Entender o Fluxo Completo
Cada documento tem diagramas Mermaid mostrando:
- Fluxo de dados
- Sequência de chamadas
- Como tudo se liga

---

## 📊 Mapa de Funções por Ficheiro

| Ficheiro | Classe Java | Nº Funções | Complexidade |
|----------|-------------|------------|--------------|
| 01_Dashboard.md | HomeController | 5 | ⭐⭐⭐⭐ |
| 02_Refeicoes.md | MealsController | 13 | ⭐⭐⭐⭐⭐ |
| 03_Hidratacao.md | HydrationController | 7 | ⭐⭐⭐ |
| 04_Exercicios.md | ExerciseController | 3 | ⭐⭐⭐ |
| 05_Historico.md | HistoryController | 6 | ⭐⭐⭐⭐⭐ |
| 06_Definicoes.md | SettingsController | 4 | ⭐⭐ |
| 07_Perfis.md | RegisterController + LoginController | 6 | ⭐⭐⭐⭐ |
| 08_Navegacao.md | SceneManager + DashboardController | 12 | ⭐⭐⭐⭐ |
| 09_Persistencia.md | DataStore + AppState | 2 + estrutura | ⭐⭐⭐⭐⭐ |

**Total:** 58 funções documentadas em detalhe!

---

## 🔍 Glossário Rápido

| Termo | Onde Está Explicado |
|-------|---------------------|
| **AppState** | 09_Persistencia.md |
| **UserProfile** | 07_Perfis.md + 09_Persistencia.md |
| **MealEntry** | 02_Refeicoes.md |
| **WaterEntry** | 03_Hidratacao.md |
| **ExerciseEntry** | 04_Exercicios.md |
| **DataStore** | 09_Persistencia.md |
| **SceneManager** | 08_Navegacao.md |
| **Serialização** | 09_Persistencia.md |
| **IMC** | 01_Dashboard.md |
| **TMB / Mifflin-St Jeor** | 01_Dashboard.md |

---

## ✅ Checklist de Compreensão

Depois de ler todos os documentos, deves ser capaz de:

- [ ] Explicar como o dashboard obtém "Calorias: 0 / 1838"
- [ ] Descrever o fluxo completo de adicionar uma refeição
- [ ] Explicar como a meta de água é calculada
- [ ] Mostrar onde os dados são guardados em disco
- [ ] Explicar a diferença entre AppState e UserProfile
- [ ] Descrever como mudar de ecrã (Login → Dashboard)
- [ ] Explicar como funciona a serialização
- [ ] Mostrar como criar/editar/eliminar perfis
- [ ] Explicar os cálculos de macronutrientes
- [ ] Descrever como os gráficos são preenchidos

---

## 🎓 Recomendação de Leitura

**Para defesa de trabalho / exame:**
1. Começa por 09_Persistencia.md (o mais importante!)
2. Lê 01_Dashboard.md (mostra tudo junto)
3. Escolhe 1-2 funcionalidades para detalhar (ex: Refeições, Histórico)

**Para desenvolvimento:**
1. Lê o ficheiro da funcionalidade que vais modificar
2. Consulta 08_Navegacao.md para perceber como mudar de ecrã
3. Sempre verifica 09_Persistencia.md antes de modificar dados

---

**Boa sorte com o estudo! 🚀**
