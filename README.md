# A Minha Dieta

## Sobre o Projeto
"A Minha Dieta" é uma aplicação desktop desenvolvida em Java (JavaFX) para ajudar os utilizadores a gerir a sua dieta, exercício físico e hidratação. A aplicação permite calcular metas calóricas e de macronutrientes personalizadas, registar refeições, monitorizar o consumo de água e visualizar o progresso através de gráficos intuitivos.

## Funcionalidades em Detalhe

### 👤 Gestão de Perfil
*   **Cálculo Automático**: Ao inserir idade, peso, altura e nível de atividade, a aplicação calcula automaticamente a sua Taxa Metabólica Basal (TMB) e necessidades calóricas diárias usando a equação de Mifflin-St Jeor.

*   **Metas de Peso**: Defina o seu peso alvo e a frequência com que deseja ser pesado (Semanal ou Mensal).
*   **Múltiplos Utilizadores**: Suporte para vários perfis na mesma aplicação, ideal para famílias.

### 🍎 Diário Alimentar
*   **Base de Dados**: Inclui uma lista de alimentos comuns com informação nutricional.
*   **Adição Rápida**: Botões de atalho para alimentos frequentes (Arroz, Massa, Batata, Leite).
*   **Personalização**: Possibilidade de adicionar novos alimentos à base de dados.

### 💧 Monitorização de Hidratação
*   **Metas Inteligentes**: A meta de água é ajustada com base no seu peso.
*   **Registo Fácil**: Adicione copos de 250ml ou garrafas de 500ml com um clique.

### 📊 Relatórios e Análise
*   **Gráficos**: Visualize a distribuição de macronutrientes (Proteína, Carbohidratos, Gordura) e a evolução do peso.
*   **Histórico de Evolução**: Acompanhe o seu peso ao longo do tempo com uma linha de meta clara para saber quão perto está do seu objetivo.
*   **Exportação PDF**: Gere relatórios detalhados para imprimir ou partilhar com o seu nutricionista.


### 🎨 Personalização Visual
*   **Temas**: Escolha entre um modo "Arco-íris" dinâmico ou cores estáticas vibrantes.
*   **Tipografia**: Selecione o tipo de letra que mais lhe agrada (Verdana, Arial, etc.).
*   **Interface Moderna**: Navegação superior intuitiva e ícones personalizados.

### 🏆 Gamificação e Motivação
*   **Feedback Imediato**: Receba mensagens motivacionais ao registar exercícios.

*   **Celebração de Metas**: Alertas de parabéns ao atingir o objetivo diário de hidratação ou a sua meta de peso.

## Como Contribuir
Contribuições são bem-vindas! Se quiser melhorar este projeto:
1.  Faça um **Fork** do repositório.
2.  Crie um **Branch** para a sua funcionalidade (`git checkout -b feature/nova-funcionalidade`).
3.  Faça **Commit** das suas alterações (`git commit -m 'Adicionei nova funcionalidade'`).
4.  Faça **Push** para o Branch (`git push origin feature/nova-funcionalidade`).
5.  Abra um **Pull Request**.

Consulte o [Manual Git](GIT_MANUAL.md) para ajuda com os comandos.

## Requisitos do Sistema
- Java Development Kit (JDK) 17 ou superior.
- Maven 3.6 ou superior.

## Como Executar
1. **Compilar o projeto:**
   ```bash
   mvn clean compile
   ```
2. **Executar a aplicação:**
   ```bash
   mvn javafx:run
   ```

## Estrutura do Projeto
- `src/main/java/app`: Código fonte da aplicação.
    - `model`: Classes de domínio (UserProfile, Food, MealEntry, etc.).
    - `ui`: Controladores e gestor de cenas (JavaFX).
    - `persistence`: Gestão de persistência de dados.
- `src/main/resources`: Recursos (FXML, CSS, Imagens).

## Autoria
Desenvolvido com dedicação para auxiliar na gestão de um estilo de vida saudável.
