# Documentação Técnica do Projeto: A Minha Dieta

## 1. Introdução e Visão Geral
**A Minha Dieta** é uma aplicação de desktop desenvolvida em **Java** utilizando a biblioteca gráfica **JavaFX**. O objetivo principal do programa é permitir que o utilizador monitorize a sua ingestão diária de calorias, macronutrientes (proteínas, hidratos de carbono, gorduras) e água, com base num perfil personalizado.

A aplicação foi desenhada para ser intuitiva, robusta e fácil de utilizar, recorrendo a uma interface moderna e responsiva. O sistema permite a gestão de múltiplos perfis de utilizador, garantindo que várias pessoas possam usar a mesma instalação mantendo os seus dados isolados.

## 2. Arquitetura do Sistema
O projeto segue o padrão de arquitetura **MVC (Model-View-Controller)**, o que separa a lógica de dados, a interface gráfica e o controlo das interações.

### Estrutura de Pastas
- **`app`**: Pacote raiz.
- **`app.model`**: Contém as classes que definem os dados (ex: `UserProfile`, `Food`, `AppState`).
- **`app.persistence`**: Responsável por guardar e carregar os dados (ex: `DataStore`).
- **`app.ui`**: Contém a lógica de interface (`SceneManager`) e os controladores.
- **`app.ui.controller`**: Controladores específicos para cada ecrã (ex: `DashboardController`, `MealsController`).

### Componentes Chave

#### 2.1. Pontos de Entrada (`Launcher.java` e `Main.java`)
- **`Launcher.java`**: É a classe responsável por iniciar a aplicação.
    - *Porquê:* Em aplicações JavaFX modernas, separar o `Launcher` do `Main` (que herda de `Application`) ajuda a evitar erros de carregamento de componentes nativos ou problemas com módulos Java ao criar executáveis (.jar ou .exe).
- **`Main.java`**: Configura o palco principal (`Stage`), define o ícone, carrega os dados iniciais via `DataStore` e inicializa o `SceneManager`. Define também o tamanho mínimo da janela para garantir a usabilidade.

#### 2.2. Gestão de Cenas (`SceneManager.java`)
- **Função**: Centraliza a navegação entre os diferentes ecrãs (Login, Registo, Dashboard).
- **Porquê**: Evita a duplicação de código de carregamento de FXML em cada controlador. O `SceneManager` possui referências ao `Stage`, `AppState` e `DataStore`, injetando estas dependências nos controladores sempre que uma nova vista é carregada.
- **Detalhe**: Método `setSceneRoot` permite trocar apenas o conteúdo da janela sem recriar a janela inteira, tornando a navegação mais fluida.

#### 2.3. Persistência de Dados (`DataStore.java` e `AppState.java`)
- **`AppState`**: Classe raiz que contém a lista de todos os perfis (`UserProfile`) e o ID do perfil ativo.
- **`DataStore`**: Utiliza **Serialização Java (`Serializable`)** para gravar o objeto `AppState` num ficheiro binário (`appstate.dat`).
    - *Porquê:* Para um projeto desta escala, a serialização é uma solução simples e eficaz que não requer a instalação de sistemas de base de dados externos (como MySQL). Permite guardar todo o estado da aplicação de forma atómica.

## 3. Descrição Detalhada das Funcionalidades

### 3.1. Gestão de Perfis e Autenticação
- **Funcionalidades**: Registo de novo utilizador, Login e Troca de Perfil.
- **Classes**: `LoginController`, `RegisterController`, `UserProfile`.
- **Lógica de Negócio**:
    - O perfil (`UserProfile`) armazena dados vitais: Nome, Idade, Peso, Altura e Género.
    - **Cálculo de Metas**: No momento da criação ou edição, o sistema calcula as necessidades calóricas usando a **Equação de Mifflin-St Jeor**, considerada uma das mais precisas para estimativa do TMB (Taxa Metabólica Basal).
    - *Detalhe*: O sistema assume um fator de atividade sedentário (1.2) por defeito, simplificando a entrada de dados inicial.
    - **Metas de Água**: Calculado como 35ml por kg de peso corporal.

### 3.2. Dashboard Principal
- **Classe**: `DashboardController`.
- **Funcionamento**: Utiliza um menu lateral para navegação. A área principal é um `StackPane` onde as vistas (Home, Refeições, Hidratação, Histórico) são injetadas dinamicamente.
- *Porquê:* Mantém a barra de navegação sempre visível e carrega apenas o conteúdo necessário, melhorando a experiência do utilizador.

### 3.3. Base de Dados de Alimentos (`FoodDatabaseController`)
- **Funcionalidade**: Permite ao utilizador criar e guardar alimentos personalizados com os respetivos macronutrientes.
- **Lógica**: Os alimentos são guardados dentro da lista `foods` do próprio `UserProfile`.
    - *Vantagem*: Cada utilizador tem a sua própria lista de alimentos favoritos, sem "poluir" a lista de outros utilizadores.
    - *Implementação*: Validação rigorosa dos inputs (impede valores negativos ou nomes vazios).

### 3.4. Registo de Refeições (`MealsController`)
- **Funcionalidade**: O utilizador seleciona um alimento da sua base de dados, define a quantidade (em gramas) e o sistema calcula automaticamente as calorias e macros ingeridos.
- **Modelo Relacionado**: `MealEntry` guarda o alimento, a quantidade e a data/hora (`LocalDateTime`). Isto permite análises futuras detalhadas (histórico).

### 3.5. Monitorização (`HomeController`)
- **Funcionalidade**: Apresenta o resumo do dia (calorias consumidas vs. meta, barras de progresso para macros e água).
- *Porquê:* É o "painel de controlo" onde o utilizador tem feedback imediato (visual) se está a cumprir a dieta. As barras de progresso (`ProgressBar` do JavaFX) dão uma noção visual rápida de "quanto falta".

## 4. Justificação das Escolhas Técnicas

1.  **Tecnologia JavaFX**:
    - Escolhida por permitir a criação de interfaces ricas, esteticamente agradáveis e independentes da plataforma (funciona em Windows, Mac, Linux).
    - O uso de **FXML** separa o design visual da lógica (código Java), facilitando a manutenção.

2.  **Estrutura de Dados em Memória (`List` vs Base de Dados SQL)**:
    - Como é uma aplicação local, carregar os dados para a memória (RAM) no arranque é extremamente rápido e simplifica o desenvolvimento. A persistência ocorre apenas ao modificar dados críticos ou fechar a aplicação.

3.  **Cálculos Automáticos**:
    - A escolha de automatizar o cálculo de calorias e macros retira o fardo cognitivo do utilizador. Ele apenas insere "Arroz", "100g", e o sistema faz a matemática (regra de três simples baseada nos valores por 100g).

## 5. Sugestões de Melhoria e Futuro Desenvolvimento

Para valorizar ainda mais o projeto numa defesa ou numa versão futura, poderiam ser implementadas as seguintes funcionalidades:

1.  **Persistência em Base de Dados SQL (SQLite)**:
    - *Cenário*: Se a aplicação tiver milhares de registos de histórico, carregar tudo para a memória pode tornar-se lento.
    - *Solução*: Migrar o `DataStore` para usar SQLite ou H2 (bases de dados em ficheiro local). Isso permitiria consultas mais complexas (ex: "Média de calorias do último mês") de forma eficiente.

2.  **Gráficos de Evolução (Charts)**:
    - Implementar gráficos de linha (usando `javafx.scene.chart.LineChart`) na aba "Histórico" para visualizar a evolução do peso e das calorias ao longo do tempo.

3.  **Base de Dados de Alimentos Online**:
    - Integrar com uma API pública (como OpenFoodFacts) para que o utilizador não precise de registar manualmente cada alimento, podendo pesquisar por nome ou código de barras.

4.  **Exportação de Relatórios**:
    - Adicionar um botão para gerar um ficheiro PDF ou Excel com o relatório semanal para o utilizador levar a um nutricionista.

5.  **Personalização do Nível de Atividade**:
    - Permitir que o utilizador escolha o seu nível de atividade física (Sedentário, Ligeiro, Moderado, Intenso) no perfil para ajustar o cálculo calórico com maior precisão.

## 6. Conclusão
O projeto **A Minha Dieta** é uma solução completa para gestão pessoal de nutrição, demonstrando competências sólidas em Programação Orientada a Objetos, Design de Interface Gráfica e Arquitetura de Software. O código é modular, o que facilita a sua manutenção e expansão futura.
