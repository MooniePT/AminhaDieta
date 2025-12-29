# A Minha Dieta

## Sobre o Projeto
**"A Minha Dieta"** é uma aplicação desktop desenvolvida em **Java** (JavaFX) para ajudar os utilizadores a gerir a sua dieta, exercício físico e hidratação. A aplicação permite calcular metas calóricas e de macronutrientes personalizadas, registar refeições, monitorizar o consumo de água e visualizar o progresso através de gráficos intuitivos.

## Funcionalidades Principais

### 👤 Gestão de Perfil
*   **Múltiplos Perfis**: Suporte para vários utilizadores na mesma aplicação, ideal para famílias.
*   **Cálculo Automático de Metas**: Utiliza a equação de Mifflin-St Jeor para calcular a Taxa Metabólica Basal (TMB) e necessidades calóricas diárias com base em idade, peso, altura, género e nível de atividade física.
*   **Cálculo de IMC**: Índice de Massa Corporal com classificação automática (Baixo Peso, Saudável, Excesso de Peso, Obesidade).
*   **Edição de Perfil**: Possibilidade de atualizar dados pessoais a qualquer momento.

### 🍎 Diário Alimentar
*   **Base de Dados de Alimentos**: Lista personalizável de alimentos com informação nutricional completa (calorias, proteína, hidratos de carbono, gordura por 100g).
*   **Atalhos Rápidos**: Botões pré-configurados para adicionar alimentos comuns rapidamente (Arroz, Massa, Batata, Leite, Ovos, Pão).
*   **Registo Detalhado**: Cada refeição inclui descrição, calorias e macronutrientes.
*   **Histórico do Dia**: Lista de todas as refeições registadas no dia atual.

### 💧 Monitorização de Hidratação
*   **Meta Inteligente**: A meta diária de água é calculada automaticamente (35ml por kg de peso corporal).
*   **Registo Fácil**: Adicione copos de 250ml, garrafas de 500ml ou quantidades personalizadas.
*   **Alertas Motivacionais**: Mensagens de parabéns ao atingir o objetivo diário de hidratação.
*   **Remoção de Registo**: Possibilidade de remover o último registo em caso de erro.

### 🏃 Registo de Exercício Físico
*   **Tipos Pré-definidos**: Caminhada, Corrida, Ciclismo, Natação, Musculação, Yoga.
*   **Atividades Personalizadas**: Adicione qualquer tipo de exercício.
*   **Registo Completo**: Duração em minutos e calorias queimadas.
*   **Feedback Motivacional**: Mensagens de incentivo após cada registo de exercício.
*   **Gráfico Semanal**: Visualize as calorias queimadas nos últimos 7 dias.

### 📊 Dashboard e Relatórios
*   **Resumo Diário**: Progresso visual de calorias, água e macronutrientes.
*   **Barras de Progresso**: Indicadores visuais para cada meta diária.
*   **Gráfico de Macros**: PieChart mostrando a distribuição de proteína, hidratos, gordura e água.
*   **Evolução do Peso**: LineChart para acompanhar alterações no peso.
*   **Histórico Completo**: TableView com todas as refeições e exercícios registados.
*   **Filtros de Pesquisa**: Filtre o histórico por descrição ou intervalo de datas.
*   **Exportação PDF**: Gere relatórios detalhados para imprimir ou partilhar.

### 🎨 Personalização Visual
*   **Modo Arco-íris**: Fundo dinâmico com gradiente de cores em constante animação.
*   **Modo Estático**: Escolha uma cor fixa com ColorPicker.
*   **Tipografia Personalizável**: Selecione entre Verdana, Arial, Segoe UI, Tahoma ou Comic Sans MS.
*   **Interface Moderna**: Design com gradientes vibrantes e navegação intuitiva.

### 🏆 Gamificação e Motivação
*   **Alertas de Celebração**: Notificação de parabéns ao atingir a meta de hidratação.
*   **Feedback de Exercício**: Mensagens motivacionais após registar atividades físicas.
*   **Status de IMC**: Classificação visual do índice de massa corporal.

## Arquitetura do Projeto

A aplicação segue o padrão **MVC (Model-View-Controller)**:

*   **Model** (`app.model`): Classes de domínio
    - `UserProfile`: Perfil do utilizador com dados pessoais e registos
    - `AppState`: Estado global da aplicação com lista de perfis
    - `Food`: Alimentos da base de dados
    - `MealEntry`: Registo de refeições
    - `WaterEntry`: Registo de consumo de água
    - `WeightEntry`: Registo de peso
    - `ExerciseEntry`: Registo de exercícios físicos

*   **View** (`resources/fxml` e `resources/css`): Interface gráfica
    - Ficheiros FXML para cada ecrã
    - CSS para estilização

*   **Controller** (`app.ui.controller`): Gestão de eventos e lógica de interface
    - `DashboardController`: Navegação principal e temas
    - `HomeController`: Dashboard do utilizador
    - `MealsController`: Gestão de refeições
    - `HydrationController`: Gestão de água
    - `ExerciseController`: Gestão de exercícios
    - `HistoryController`: Histórico e exportação PDF
    - `SettingsController`: Definições visuais
    - `LoginController`: Seleção de perfil
    - `RegisterController`: Criação/edição de perfil

*   **Persistência** (`app.persistence`): Serialização Java para ficheiro local (`appstate.dat`)

## Requisitos do Sistema
*   **Java Development Kit (JDK)** 17 ou superior
*   **Maven** 3.6 ou superior

## Como Executar

1. **Clonar o repositório:**
   ```bash
   git clone https://github.com/MooniePT/AminhaDieta.git
   cd AminhaDieta
   ```

2. **Compilar o projeto:**
   ```bash
   cd AminhaDieta
   mvn clean compile
   ```

3. **Executar a aplicação:**
   ```bash
   mvn javafx:run
   ```

## Estrutura de Diretórios
```
AminhaDieta/
├── AminhaDieta/
│   └── src/main/
│       ├── java/app/
│       │   ├── model/           # Classes de domínio
│       │   ├── persistence/     # Gestão de dados
│       │   ├── ui/              # Controladores JavaFX
│       │   │   ├── controller/  # Controladores das vistas
│       │   │   └── SceneManager.java
│       │   ├── Main.java        # Ponto de entrada JavaFX
│       │   └── Launcher.java    # Launcher para módulos
│       └── resources/
│           ├── css/             # Estilos CSS
│           ├── fxml/            # Definições de interface
│           └── images/          # Ícones e imagens
├── data/                        # Dados persistentes
├── GIT_MANUAL.md               # Manual de Git
├── Project_Report.md           # Relatório do projeto
├── README.md                   # Este ficheiro
└── Relatorio_Final.tex         # Relatório LaTeX
```

## Como Contribuir
Contribuições são bem-vindas! Consulte o [Manual Git](GIT_MANUAL.md) para instruções detalhadas.

1.  Faça um **Fork** do repositório.
2.  Crie um **Branch** para a sua funcionalidade (`git checkout -b feature/nova-funcionalidade`).
3.  Faça **Commit** das suas alterações (`git commit -m 'Adicionei nova funcionalidade'`).
4.  Faça **Push** para o Branch (`git push origin feature/nova-funcionalidade`).
5.  Abra um **Pull Request**.

## Equipa de Desenvolvimento
*   Carlos Farinha
*   João Rodrigues
*   Henrique Marques
*   André Schroder

## Licença
Este projeto foi desenvolvido no âmbito da Unidade Curricular de Programação Orientada a Objetos.
