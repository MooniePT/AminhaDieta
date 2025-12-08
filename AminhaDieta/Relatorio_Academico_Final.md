# UNIVERSIDADE DA BEIRA INTERIOR
**Engenharia Informática - 2º Ano / 1º Semestre**
**Unidade Curricular: Programação Orientada a Objetos**

<br>
<br>
<br>
<br>

# A Minha Dieta: Sistema de Monitorização Nutricional em Java

<br>
<br>
<br>

**Autor:** Carlos
**Data:** Dezembro de 2025

<br>
<br>
<br>
<br>
<br>
<br>
<br>
<div style="page-break-after: always;"></div>

# Agradecimentos

Gostaria de expressar o meu sincero agradecimento a todos os que contribuíram para a realização deste projeto.

Em primeiro lugar, ao corpo docente da Unidade Curricular de Programação Orientada a Objetos da Universidade da Beira Interior, por fornecerem os conhecimentos fundamentais de arquitetura de software, padrões de desenho e linguagem Java, sem os quais este trabalho não teria sido possível.

Aos meus colegas de curso, pelas discussões estimulantes e pela partilha de conhecimentos e dúvidas que enriqueceram o meu processo de aprendizagem.

Por fim, agradeço à minha família e amigos pelo apoio constante e pela paciência demonstrada durante as longas horas de desenvolvimento e estudo dedicadas a este projeto prático.

<div style="page-break-after: always;"></div>

# Resumo

O presente relatório descreve o desenvolvimento da aplicação "A Minha Dieta", um sistema de software desenvolvido em Java com interface gráfica JavaFX, destinado à gestão e monitorização da saúde nutricional dos utilizadores.

A aplicação foi concebida com o objetivo de permitir aos utilizadores registar o seu perfil biométrico, acompanhar a ingestão diária de alimentos e água, e visualizar o progresso em relação a metas de saúde estabelecidas automaticamente através de algoritmos reconhecidos cientificamente, como a Equação de Mifflin-St Jeor.

Ao longo deste documento, é detalhado todo o ciclo de vida do desenvolvimento, desde a análise de requisitos e escolha de tecnologias, passando pela arquitetura do sistema baseada no padrão Model-View-Controller (MVC), até à implementação concreta das funcionalidades de registo, cálculo de macronutrientes e visualização de dados. O projeto demonstra a aplicação prática de conceitos avançados de Programação Orientada a Objetos (POO), persistência de dados e design de interfaces modernas.

**Palavras-chave:** Java, JavaFX, POO, Monitorização de Saúde, MVC, Nutrição.

<div style="page-break-after: always;"></div>

# Índice

1. [Introdução](#1-introdução)
2. [Objetivos](#2-objetivos)
3. [Enquadramento Teórico](#3-enquadramento-teórico)
4. [Desenvolvimento e Implementação](#4-desenvolvimento-e-implementação)
    1. [Análise de Requisitos](#41-análise-de-requisitos)
    2. [Arquitetura do Sistema](#42-arquitetura-do-sistema)
    3. [Modelação de Dados](#43-modelação-de-dados)
    4. [Interface de Utilizador e Navegação](#44-interface-de-utilizador-e-navegação)
    5. [Algoritmos e Lógica de Negócio](#45-algoritmos-e-lógica-de-negócio)
5. [O Projeto: Manual de Utilização](#5-o-projeto-manual-de-utilização)
6. [Testes e Validação](#6-testes-e-validação)
7. [Epílogo e Trabalhos Futuros](#7-epílogo-e-trabalhos-futuros)
8. [Conclusão](#8-conclusão)
9. [Bibliografia](#9-bibliografia)

<div style="page-break-after: always;"></div>

## 1. Introdução

A monitorização da saúde e da nutrição tornou-se uma preocupação central na sociedade moderna. Com o aumento de doenças relacionadas com o estilo de vida, como a obesidade e a diabetes, a necessidade de ferramentas que auxiliem os indivíduos a gerir a sua alimentação e hidratação é cada vez mais premente. A tecnologia desempenha, neste contexto, um papel facilitador fundamental, transformando cálculos metabólicos complexos em interfaces simples e intuitivas.

Este projeto, intitulado "A Minha Dieta", surge no âmbito da disciplina de Programação Orientada a Objetos do curso de Engenharia Informática. O desafio proposto foi o de conceber e implementar uma aplicação desktop robusta, capaz de gerir dados de múltiplos utilizadores e persistir essa informação entre sessões.

A escolha da linguagem Java e da biblioteca gráfica JavaFX justifica-se pela sua portabilidade, robustez e pela vasta gama de componentes visuais que permitem criar uma experiência de utilizador rica e responsiva. Ao contrário de uma aplicação de consola, uma interface gráfica permite uma interação mais natural e uma visualização de dados (como gráficos de evolução) que é essencial para a motivação do utilizador.

Este relatório estrutura-se de forma a espelhar o processo de engenharia de software seguido. Inicia-se com a definição dos objetivos e o enquadramento teórico, avança para a especificação técnica e detalhes de implementação, e termina com a validação dos resultados e reflexões finais.

## 2. Objetivos

O objetivo principal deste projeto é desenvolver uma aplicação de gestão dietética funcional, intuitiva e esteticamente agradável, aplicando corretamente os paradigmas da Programação Orientada a Objetos.

### 2.1 Objetivos Gerais
*   Aplicar conceitos de POO: Encapsulamento, Herança, Polimorfismo e Abstração.
*   Implementar uma arquitetura de software organizada (MVC).
*   Garantir a persistência de dados (leitura e escrita de ficheiros).
*   Criar uma Interface Gráfica do Utilizador (GUI) amigável.

### 2.2 Objetivos Específicos
*   **Gestão de Perfis**: Permitir o registo, edição e autenticação de múltiplos utilizadores, armazenando dados como nome, idade, género, peso e altura.
*   **Cálculos Automáticos**: Implementar o cálculo automático do Índice de Massa Corporal (IMC), Taxa Metabólica Basal (TMB/BMR) e necessidades hídricas diárias.
*   **Monitorização Alimentar**: Possibilitar o registo de refeições, permitindo a inserção manual ou a seleção a partir de uma base de dados de alimentos pré-definidos.
*   **Gestão de Macronutrientes**: Rastrear e visualizar o consumo de Proteínas, Hidratos de Carbono e Gorduras em relação a metas ideais.
*   **Histórico e Evolução**: Apresentar visualmente (gráficos e tabelas) a evolução do peso e o histórico de refeições.

## 3. Enquadramento Teórico

### 3.1 Engenharia de Software e o Padrão MVC
Para garantir a manutenibilidade e escalabilidade do código, o projeto segue o padrão de arquitetura **Model-View-Controller (MVC)**.
*   **Model (Modelo)**: Representa os dados e a lógica de negócio. No nosso projeto, inclui classes como `UserProfile`, `MealEntry` e `Food`. Estas classes não têm conhecimento da interface gráfica.
*   **View (Vista)**: Responsável pela apresentação dos dados ao utilizador. Em JavaFX, isto é definido através de ficheiros FXML (`HomeView.fxml`, `MealsView.fxml`), que descrevem a estrutura visual (layouts, botões, tabelas).
*   **Controller (Controlador)**: Atua como intermediário. Recebe as ações do utilizador na Vista (cliques, inputs), manipula o Modelo correspondente e atualiza a Vista com os novos dados. Exemplos: `HomeController.java`, `MealsController.java`.

### 3.2 Java e JavaFX
Java é uma linguagem orientada a objetos estritamente tipada, conhecida pela sua filosofia "Write Once, Run Anywhere".
**JavaFX** é a plataforma padrão para criar aplicações ricas de internet (RIAs) e aplicações desktop em Java. Substituindo o antigo Swing, o JavaFX introduz o conceito de *Scene Graph*, separação de design (FXML/CSS) e lógica (Java), e suporte nativo para propriedades observáveis e *bindings*, que são cruciais para manter a interface sincronizada com os dados em tempo real.

### 3.3 Conceitos Nutricionais Aplicados
A lógica de negócio da aplicação baseia-se em fórmulas validadas:
*   **IMC (Índice de Massa Corporal)**: $Peso (kg) / Altura (m)^2$. Usado para classificar o peso (baixo peso, normal, sobrepeso, obesidade).
*   **Necessidade Hídrica**: Calculada regra geral como $35ml \times Peso (kg)$.
*   **Equação de Mifflin-St Jeor**: Considerada uma das fórmulas mais precisas para estimar a necessidade calórica diária.
    *   Homens: $(10 \times peso) + (6.25 \times altura) - (5 \times idade) + 5$
    *   Mulheres: $(10 \times peso) + (6.25 \times altura) - (5 \times idade) - 161$

## 4. Desenvolvimento e Implementação

### 4.1 Análise de Requisitos
Antes da codificação, foram identificados os requisitos fundamentais.
**Requisitos Funcionais:**
*   RF01: O sistema deve permitir criar, editar e selecionar perfis de utilizador.
*   RF02: O sistema deve calcular automaticamente as metas calóricas e hídricas baseadas no perfil.
*   RF03: O utilizador deve poder registar alimentos com informação nutricional (calorias, proteínas, hidratos, gordura).
*   RF04: O utilizador deve poder registar refeições associadas a um dia e hora.
*   RF05: O sistema deve persistir todos os dados entre execuções da aplicação.

**Requisitos Não-Funcionais:**
*   RNF01: A interface deve ser responsiva e adaptar-se a uma resolução mínima de 1280x800.
*   RNF02: As cores e o design devem evocar saúde e bem-estar (Tema "Fresh & Healthy").
*   RNF03: O feedback ao utilizador (erros de input, sucesso) deve ser imediato.

### 4.2 Arquitetura do Sistema
A estrutura de pacotes foi organizada para separar responsabilidades:
*   `app.model`: Classes de domínio (POJOs).
*   `app.ui`: Gestão de cenas (`SceneManager`).
*   `app.ui.controller`: Lógica de interação.
*   `app.persistence`: Gestão de gravação de dados (`DataStore`).
*   `app.Launcher` e `app.Main`: Pontos de entrada.

### 4.3 Modelação de Dados
O núcleo da aplicação reside no pacote `app.model`.

A classe **`UserProfile`** é a entidade central. Agrega listas de registos e contém a lógica de cálculo:
```java
public class UserProfile implements Serializable {
    private String nome;
    private int idade;
    private double pesoKg;
    private double alturaCm;
    private Gender gender;
    
    // Listas de registos
    private List<MealEntry> meals = new ArrayList<>();
    private List<WaterEntry> waters = new ArrayList<>();
    private List<Food> foods = new ArrayList<>(); // Base de dados pessoal
    // ...
}
```

A classe **`MealEntry`** foi evoluída para suportar macronutrientes, permitindo uma análise mais detalhada do que apenas calorias simples.
```java
public class MealEntry implements Serializable {
    private String description;
    private int calories;
    private double protein;
    private double carbs;
    private double fat;
    // ...
}
```

### 4.4 Interface de Utilizador e Navegação
A experiência do utilizador (UX) foi desenhada para ser fluida.

**SceneManager**:
Foi implementada uma classe `SceneManager` que atua como o "maestro" da navegação. Em vez de cada controlador instanciar novas janelas, eles solicitam ao `SceneManager` a mudança de ecrã (e.g., `sceneManager.showDashboard()`). Isto centraliza a injeção de dependências (`AppState`, `DataStore`) e facilita a manutenção.

**Dashboard Dinâmico**:
A vista principal (`DashboardView`) utiliza um `StackPane` central e uma barra lateral de navegação. Ao clicar nos botões do menu ("Resumo", "Refeições", "Hidratação"), apenas a área central é recarregada, mantendo o menu estático. Isto cria uma sensação de aplicação rápida e moderna, evitando o "flicker" de recarregar a janela inteira.

**Estilização (CSS)**:
Utilizou-se CSS (`styles.css`) para definir a identidade visual. Botões arredondados, sombras suaves (`-fx-effect: dropshadow`) e uma paleta de cores verde/azul foram escolhidos para transmitir tranquilidade e saúde.

### 4.5 Algoritmos e Lógica de Negócio
A precisão dos dados é garantida por métodos encapsulados no modelo.

**Cálculo de Progresso de Macros**:
O sistema não só regista o consumo, como define metas dinâmicas. Por defeito, a aplicação assume uma distribuição equilibrada:
*   Proteína: 20% das calorias totais.
*   Hidratos de Carbono: 50% das calorias totais.
*   Gordura: 30% das calorias totais.

O código converte estas percentagens em gramas (sabendo que 1g de Proteína/Hidratos = 4kcal e 1g Gordura = 9kcal) para preencher as barras de progresso na `HomeView`.

```java
public double getDailyProteinGoalGrams() {
    return (getDailyCalorieGoal() * 0.20) / 4.0;
}
```

## 5. O Projeto: Manual de Utilização

### 5.1 Início e Autenticação
Ao iniciar a aplicação, o utilizador é recebido pelo ecrã de **Login**. Aqui são listados todos os perfis existentes.
*   Se não existirem perfis, é automaticamente redirecionado para o ecrã de **Registo**.
*   No registo, o utilizador insere os seus dados. A seleção de género é feita através de botões de rádio ("Masculino"/"Feminino"), essenciais para o cálculo do metabolismo basal.

### 5.2 O Painel Principal (Dashboard)
Após o login, o utilizador vê o **Resumo do Dia**:
1.  **Barra de Calorias**: Verde, indicando o progresso em direção à meta diária.
2.  **Barra de Hidratação**: Azul, mostrando litros consumidos vs. objetivo.
3.  **Barras de Macronutrientes**: Três barras distintas para Proteína, Hidratos e Gordura.
4.  **Evolução**: Um gráfico de linhas que mostra a variação de peso ao longo do tempo.

### 5.3 Gestão de Refeições
No separador "Refeições", o sistema apresenta uma abordagem inovadora dividida em duas abas:
1.  **Diário**: Onde se registam as refeições do dia. O utilizador pode escrever o nome e valores manualmente OU selecionar um alimento da lista "Pré-definidos", que preenche automaticamente todos os campos de macros e calorias.
2.  **Meus Alimentos**: Uma base de dados pessoal onde o utilizador cria alimentos recorrentes (ex: "Aveia", "Peito de Frango"). Estes ficam guardados para sempre no perfil.

### 5.4 Hidratação
O ecrã de hidratação foca-se na rapidez. Botões de "Adic. Rápida" (+250ml, +500ml) permitem registar o consumo de água com um único clique, incentivando o registo frequente.

## 6. Testes e Validação

Para assegurar a qualidade do software, foram realizados exaustivos testes manuais funcionais.

| ID Teste | Cenário | Passos | Resultado Esperado | Resultado Obtido |
| :--- | :--- | :--- | :--- | :--- |
| **T01** | Validação de Input Numérico | Tentar inserir letras no campo "Peso" no registo. | A aplicação deve exibir erro e não avançar. | **Sucesso**. Label de erro exibe mensagem apropriada. |
| **T02** | Persistência de Dados | Adicionar uma refeição, fechar a aplicação e reabrir. | A refeição deve constar na lista e os gráficos atualizados. | **Sucesso**. Dados carregados corretamente do ficheiro `diet_data.dat`. |
| **T03** | Cálculo de Metas | Alterar o peso do utilizador no "Editar Perfil". | A meta calórica diária deve ser recalculada e atualizada no Home. | **Sucesso**. A meta subiu/desceu conforme o ajuste de peso. |
| **T04** | Base de Dados de Alimentos | Criar alimento, mudar de aba, selecionar alimento. | Os campos de texto devem ser autopreenchidos com os valores do alimento. | **Sucesso**. Integração entre abas funciona perfeitamente. |

A aplicação demonstrou estabilidade, sem falhas fatais (crashes) após a correção de erros de injeção de dependências FXML detetados durante a fase final de desenvolvimento.

## 7. Epílogo e Trabalhos Futuros

O desenvolvimento de "A Minha Dieta" foi uma jornada de aprendizagem contínua. Embora o produto final seja robusto e funcional, a natureza do software permite sempre evolução.

**Limitações Atuais:**
*   A persistência em ficheiro binário (`Serializable`) é eficiente para projetos académicos, mas limita a portabilidade dos dados e acesso concorrente.
*   A aplicação é local (Desktop), não permitindo acesso via telemóvel, o que seria ideal para registo de refeições *on-the-go*.

**Trabalhos Futuros:**
1.  **Base de Dados SQL**: Migrar o armazenamento de ficheiros para uma base de dados SQLite ou MySQL, permitindo consultas mais complexas e relatórios estatísticos avançados.
2.  **Sincronização Cloud**: Implementar uma API REST para permitir que os dados sejam sincronizados com uma aplicação móvel Android/iOS no futuro.
3.  **Gamificação**: Introduzir medalhas ou conquistas (ex: "Hidratação Perfeita por 7 dias") para aumentar a motivação do utilizador.

## 8. Conclusão

Este projeto permitiu consolidar de forma prática os conhecimentos teóricos de Programação Orientada a Objetos. A construção de uma aplicação completa, desde a conceção da interface até à implementação da lógica de negócio e persistência, ofereceu uma visão holística do desenvolvimento de software.

A utilização do JavaFX provou ser uma escolha acertada, permitindo criar uma interface moderna e reativa que se destaca das tradicionais interfaces Swing. A arquitetura MVC, embora tenha exigido um planeamento inicial mais rigoroso, revelou-se fundamental para organizar o código, facilitando imensamente a adição posterior de funcionalidades complexas como o registo de macronutrientes e a base de dados de alimentos pessoais.

Conclui-se que os objetivos propostos foram integralmente atingidos. "A Minha Dieta" não é apenas um exercício académico, mas uma ferramenta funcional que pode efetivamente auxiliar na melhoria dos hábitos de saúde dos seus utilizadores.

## 9. Bibliografia

1.  **Documentação Oficial Java**: Oracle. (2025). *Java Platform, Standard Edition Documentation*. Disponível em: https://docs.oracle.com/en/java/
2.  **Documentação JavaFX**: OpenJFX. (2025). *JavaFX Documentation*. Disponível em: https://openjfx.io/
3.  **Mifflin, M. D., et al.** (1990). *A new predictive equation for resting energy expenditure in healthy individuals*. The American Journal of Clinical Nutrition.
4.  **Horstmann, C.** (2018). *Core Java Volume I--Fundamentals*. Prentice Hall.
5.  **Fowler, M.** (2002). *Patterns of Enterprise Application Architecture*. Addison-Wesley.
