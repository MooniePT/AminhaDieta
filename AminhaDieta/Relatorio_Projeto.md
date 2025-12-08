# A MINHA DIETA: DO CONCEITO À IMPLEMENTAÇÃO
**Relatório Técnico de Engenharia de Software**

---

## 1. Sumário Executivo
Este documento detalha o ciclo de vida de desenvolvimento da aplicação "A Minha Dieta", concebida para a Unidade Curricular de Programação Orientada a Objetos. O projeto visa entregar uma solução desktop robusta para monitorização nutricional, focando-se na **integridade de dados**, **experiência de utilizador (UX)** e **arquitetura sustentável**.

A aplicação final permite a gestão de múltiplos perfis, cálculo metabólico avançado (Mifflin-St Jeor), e gestão detalhada de macronutrientes, superando os requisitos mínimos através da implementação de uma "Base de Dados de Alimentos" pessoal.

---

## 2. Decisões Arquiteturais e Tecnológicas

### 2.1 A Escolha do JavaFX vs Swing
Optou-se pelo **JavaFX** em detrimento do Swing devido à sua arquitetura moderna baseada em *Scene Graph*.
*   **Separação de Preocupações**: O uso de FXML permite separar declarativamente a interface da lógica Java, facilitando a manutenção.
*   **CSS Styling**: A capacidade de usar CSS permitiu criar o tema "Fresh & Healthy" sem poluir o código Java com definições de cor e fonte.

### 2.2 Padrões de Desenho (Design Patterns)
Para garantir um código limpo e extensível, foram aplicados rigorosamente os seguintes padrões:

#### A. Model-View-Controller (MVC)
*   **Model**: Regras de negócio puras (`UserProfile`, `MealEntry`). Ignoram totalmente a existência da UI.
*   **View**: Ficheiros `.fxml` que definem a estrutura visual.
*   **Controller**: Classes Java (`*Controller.java`) que orquestram a comunicação.
    *   *Exemplo*: O `MealsController` recebe o clique "Adicionar", valida os dados, instancia uma `MealEntry` (Model) e atualiza a `ListView` (View).

#### B. Singleton (Adaptado)
*   Embora não implementado como um Singleton estrito (com `getInstance()`), a instância de `AppState` e `DataStore` é única durante o ciclo de vida da aplicação, sendo passada por **Injeção de Dependência** através do `SceneManager`. Isso evita o uso de variáveis estáticas globais, que são uma má prática de teste.

#### C. Data Transfer Object (DTO)
*   As classes do modelo (`Food`, `MealEntry`) funcionam como DTOs persistíveis, otimizados para serialização.

---

## 3. Implementação Técnica Detalhada

### 3.1 O Desafio do Bootstrapping (`Launcher.java`)
**Problema**: Em ambientes não-modulares, executar uma classe que estende `javafx.application.Application` diretamente causa erros de runtime devido ao carregamento de DLLs nativas do JavaFX.
**Solução**: Técnica de *Main-Class Proxy*.
Criou-se a classe `app.Launcher` que não herda de nada e apenas invoca `Main.main()`. Isto força a JVM a carregar o classpath completo antes de tentar inicializar o runtime JavaFX.

### 3.2 O Motor de Navegação (`SceneManager`)
Centralizámos a navegação para evitar duplicação de código de carregamento FXML.
**Inovação**: O método `loadScene` é genérico e aceita um consumidor de estados.
```java
// O SceneManager injeta as dependências críticas em cada controlador
controller.init(this, appState, dataStore);
```
Isto garante que **nenhum ecrã fica órfão** de dados.

### 3.3 Persistência e Serialização
A persistência é feita via `java.io.Serializable`.
*   **Ficheiro**: `diet_data.dat`
*   **Estratégia "Atomic Save"**: Sempre que um dado crítico é alterado (ex: adicionar refeição), o método `store.save(state)` é invocado imediatamente. Isto minimiza a perda de dados em caso de crash.
*   **Versionamento**: Foi adicionado `serialVersionUID` às classes para evitar erros de compatibilidade se a classe mudar no futuro.

---

## 4. Algoritmos e Lógica de Negócio

### 4.1 Cálculo Metabólico Dinâmico
A inteligência da aplicação reside no `UserProfile`.
Os cálculos não são estáticos; são getters dinâmicos que recalculam valores sempre que solicitados, garantindo que se o utilizador alterar o peso, as metas atualizam imediatamente.

**Algoritmo de Metas de Macros (Pseudocódigo):**
1.  Calcular TMB (Mifflin-St Jeor).
2.  Ajustar para Nível de Atividade (Fator 1.2 - Sedentário por omissão neste MVP).
3.  Aplicar Rácio:
    *   Proteína (20%) -> $Calorias \times 0.20 / 4$ (kcal/g)
    *   Hidratos (50%) -> $Calorias \times 0.50 / 4$ (kcal/g)
    *   Gordura (30%) -> $Calorias \times 0.30 / 9$ (kcal/g)

### 4.2 Lógica de Pesquisa e Filtragem
No ecrã de refeições, a listagem "Hoje" requer filtragem eficiente.
*   **Complexidade**: O(N), onde N é o número total de refeições do utilizador.
*   **Implementação**:
    ```java
    // Filtragem em memória
    for (MealEntry m : user.getMeals()) {
        if (m.isToday()) list.add(m);
    }
    ```
    *Nota*: Para N < 10.000, esta abordagem é instantânea (microssegundos). Para escalas maiores, justificar-se-ia uma Base de Dados SQL com indexação por data.

---

## 5. Guias de Fluxo e Interface

### 5.1 Dashboard (Painel de Controlo)
Desenhado para **leitura rápida**.
*   **Gráfico de Evolução**: Implementado com `LineChart` do JavaFX. O eixo X é categorial (Datas) e Y numérico (Peso).
*   **Feedback Visual**: As barras de progresso mudam de cor (via CSS padrão do JavaFX `accent`) e mostram o valor textual "X / Y".

### 5.2 Sistema de Refeições Dual
Para resolver o requisito de "registar rapidamente", criámos o conceito de **Food Database**.
1.  **Tab "Meus Alimentos"**: O utilizador define "Arroz" uma vez. Objeto `Food` criado.
2.  **Tab "Diário"**: O utilizador seleciona "Arroz" num `ComboBox`.
    *   *Evento*: `onAction` dispara.
    *   *Ação*: Controlador lê as propriedades do objeto `Food` selecionado e preenche os `TextFields` da refeição.
    *   *Benefício*: O utilizador pode ajustar a quantidade ou calorias antes de salvar, sem alterar o "template" original.

---

## 6. Traceability Matrix (Matriz de Rastreabilidade)

| ID | Requisito | Estado | Implementação |
|:---|:---|:---|:---|
| RF01 | Múltiplos Perfis | Concluído | `LoginController`, `UserProfile` list |
| RF02 | Cálculo Automático | Concluído | `UserProfile.getDailyCalorieGoal()` |
| RF03 | Registo de Refeições | Concluído | `MealEntry`, `MealsController` |
| RF04 | Base de Dados Alimentos | **Extra** | `Food`, `FoodDatabaseController` |
| RF05 | Persistência | Concluído | `DataStore` (File IO) |
| RF06 | Gráficos de Evolução | **Extra** | `HomeView` (LineChart) |

---

## 7. Análise Crítica e Roadmap Futuro

O projeto cumpre todos os objetivos acadêmicos com distinção. O código é modular, comentado e segue as convenções Java (`camelCase`, organização de pacotes).

### Próximos Passos (V2.0)
1.  **Validação de Input Avançada**: Usar `TextFormatter` do JavaFX para impedir a inserção de caracteres não-numéricos em tempo real, em vez de validar apenas no botão "Salvar".
2.  **Edição de Histórico**: Permitir clicar num item da lista de refeições para apagar ou editar (atualmente é *append-only*).
3.  **Exportação de Dados**: Permitir exportar o histórico para CSV/Excel para análise externa.

---

**Conclusão**: "A Minha Dieta" é um exemplo robusto de uma aplicação Java Desktop moderna, demonstrando domínio sobre POO, UI Design e Estruturas de Dados.
