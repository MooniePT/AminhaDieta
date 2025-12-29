# Relatório de Projeto Final - A Minha Dieta

**Unidade Curricular:** Programação Orientada a Objetos  
**Data:** 29 de Dezembro de 2024

**Equipa de Desenvolvimento:**
*   Carlos Farinha
*   João Rodrigues
*   Henrique Marques
*   André Schroder

---

## 1. Introdução

### 1.1 Contextualização
Na sociedade atual, a preocupação com a saúde e o bem-estar tem vindo a crescer exponencialmente. A gestão de uma dieta equilibrada, a prática regular de exercício físico e uma hidratação adequada são pilares fundamentais para um estilo de vida saudável.

### 1.2 Objetivos do Projeto
O projeto "A Minha Dieta" fornece uma aplicação desktop intuitiva para:
*   Calcular necessidades calóricas diárias usando a equação de Mifflin-St Jeor.
*   Registar refeições e monitorizar macronutrientes (Proteína, Hidratos de Carbono, Gordura).
*   Acompanhar o consumo de água com metas personalizadas baseadas no peso.
*   Registar atividades físicas e contabilizar calorias queimadas.
*   Visualizar progresso com gráficos interativos (LineChart, PieChart, BarChart).
*   Exportar relatórios detalhados em formato PDF.

---

## 2. Cronograma de Desenvolvimento

### Fase 1: Planeamento e Análise (Semana 1-2)
*   Definição de requisitos funcionais e não funcionais.
*   Esboço da interface gráfica (Mockups).
*   Modelação do domínio (Diagrama de Classes inicial).

### Fase 2: Implementação do Core (Semana 3-4)
*   Criação da estrutura do projeto Maven.
*   Implementação das classes de modelo (`UserProfile`, `Food`, `MealEntry`, `WaterEntry`, `WeightEntry`).
*   Implementação do sistema de persistência de dados (`DataStore`).

### Fase 3: Interface Gráfica e Lógica (Semana 5-6)
*   Desenvolvimento das vistas FXML (Login, Dashboard, Refeições, Hidratação).
*   Implementação dos Controladores JavaFX.
*   Integração da lógica de negócio com a interface.

### Fase 4: Refinamento e Testes (Semana 7-8)
*   Melhorias estéticas (CSS, gradientes, ícones).
*   Implementação de funcionalidades avançadas (Gráficos, Exportação PDF).
*   Testes manuais e correção de bugs.

### Fase 5: Polimento Visual e Personalização (Semana 9)
*   **Redesign da Interface**: Paleta de cores vibrante (gradiente Azul/Ciano) e tipografia moderna (Verdana).
*   **Menu de Definições**: Ecrã de configurações com:
    - Toggle para Modo Arco-íris (animação dinâmica de gradiente)
    - ColorPicker para cor estática personalizada
    - ComboBox para seleção de tipo de letra
*   **Gamificação**: Mensagens motivacionais (Alertas JavaFX) ao:
    - Atingir meta de hidratação diária
    - Registar exercícios físicos
*   **Correções Visuais**: Ajuste de layouts e remoção de artefactos visuais.

### Fase 6: Exercício e Análise (Semana 10)
*   **Módulo de Exercício Físico**: `ExerciseEntry` e `ExerciseController` para registo de atividades.
*   **Gráfico de Exercício Semanal**: BarChart com calorias queimadas nos últimos 7 dias.
*   **Tipos Pré-definidos**: Caminhada, Corrida, Ciclismo, Natação, Musculação, Yoga.
*   **Atalhos de Alimentos**: Botões rápidos para Arroz, Massa, Batata, Leite, Ovos, Pão.

---

## 3. Arquitetura do Sistema

A aplicação segue o padrão **MVC (Model-View-Controller)**:

### 3.1 Model (Modelo) - `app.model`
| Classe | Descrição |
|--------|-----------|
| `UserProfile` | Perfil do utilizador com dados pessoais, cálculo de TMB/IMC e listas de registos |
| `AppState` | Estado global com lista de perfis e perfil ativo |
| `Food` | Alimento com informação nutricional por 100g |
| `MealEntry` | Registo de refeição com timestamp, descrição e macros |
| `WaterEntry` | Registo de consumo de água com quantidade em ml |
| `WeightEntry` | Registo histórico de peso |
| `ExerciseEntry` | Registo de exercício com tipo, duração e calorias queimadas |

### 3.2 View (Vista) - `resources/`
*   **FXML**: LoginView, RegisterView, DashboardView, HomeView, MealsView, HydrationView, ExerciseView, HistoryView, SettingsView, FoodDatabaseView
*   **CSS**: Estilos modernos com gradientes e efeitos visuais

### 3.3 Controller (Controlador) - `app.ui.controller`
| Controlador | Responsabilidade |
|-------------|------------------|
| `DashboardController` | Navegação principal, gestão de temas (Rainbow/Estático), animações |
| `HomeController` | Dashboard com resumos, progress bars e gráficos |
| `MealsController` | Registo de refeições, atalhos rápidos, base de dados de alimentos |
| `HydrationController` | Gestão de água com alertas de meta atingida |
| `ExerciseController` | Registo de exercícios com feedback motivacional |
| `HistoryController` | Tabelas filtráveis e exportação PDF |
| `SettingsController` | Configurações de tema e tipografia |
| `LoginController` | Seleção de perfil multi-utilizador |
| `RegisterController` | Criação e edição de perfis |

### 3.4 Persistência
Serialização de objetos Java para ficheiro binário local (`appstate.dat`).

---

## 4. Funcionalidades Implementadas

### 4.1 Cálculo de Metas
*   **TMB (Mifflin-St Jeor)**: `(10 × peso) + (6.25 × altura) - (5 × idade) + fator_género`
*   **Calorias Diárias**: TMB × multiplicador de atividade (1.2 a 1.9)
*   **Água Diária**: 35ml × peso (kg)
*   **Macronutrientes**: 50% Hidratos (4kcal/g), 30% Gordura (9kcal/g), 20% Proteína (4kcal/g)

### 4.2 Gráficos Implementados
*   **PieChart**: Distribuição de macronutrientes consumidos no dia
*   **LineChart**: Evolução do peso ao longo do tempo
*   **BarChart**: Calorias queimadas por dia (últimos 7 dias)
*   **ProgressBar**: Indicadores visuais para cada meta diária

### 4.3 Gamificação
*   Alertas de celebração ao atingir meta de hidratação
*   Mensagens motivacionais após registo de exercícios
*   Classificação visual de IMC (Baixo Peso, Saudável, Excesso de Peso, Obesidade)

---

## 5. Tecnologias Utilizadas

| Categoria | Tecnologia |
|-----------|------------|
| Linguagem | Java 17+ |
| Framework UI | JavaFX |
| Build | Maven |
| PDF | OpenPDF (LibrePDF) |
| Persistência | Java Serialization |
| IDE | VS Code / IntelliJ IDEA |

---

## 6. Conclusão

O projeto "A Minha Dieta" resultou numa aplicação robusta, moderna e completa. A arquitetura MVC facilitou a expansão progressiva de funcionalidades, permitindo adicionar o módulo de exercícios, o modo arco-íris, as definições de personalização e os alertas motivacionais sem comprometer a estabilidade do código existente.

A aplicação cumpre todos os objetivos propostos e demonstra uma aplicação prática dos conceitos de Programação Orientada a Objetos, incluindo encapsulamento, herança (enums), polimorfismo e padrões de design (MVC, Observer).
