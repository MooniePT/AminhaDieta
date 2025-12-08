# Relatório do Projeto: A Minha Dieta

## 1. Visão Geral
O projeto "A Minha Dieta" foi desenvolvido para fornecer uma interface gráfica em JavaFX para monitorização de dieta e saúde. O sistema permite criar perfis de utilizador, registar alimentos e refeições, e acompanhar o consumo diário de calorias, água e macronutrientes.

## 2. Estrutura de Ficheiros
### Código Fonte (`app`)
- **`Launcher.java`**: Ponto de entrada da aplicação (workaround para JavaFX em algumas configurações).
- **`Main.java`**: Classe principal da aplicação JavaFX.
- **`model/`**: Classes de domínio.
    - `UserProfile.java`: Gere os dados do utilizador (peso, altura, género, histórico).
    - `MealEntry.java`: Registo individual de uma refeição.
    - `Food.java`: Representa um alimento com informação nutricional.
    - `WaterEntry.java`: Registo de consumo de água.
    - `AppState.java`: Gere o estado global e persistência (lista de perfis).
- **`ui/`**: Lógica de interface.
    - `SceneManager.java`: Controlador central de navegação entre ecrãs.
    - `controller/`: Controladores para cada vista FXML (Dashboard, Home, Meals, etc.).

### Recursos (`resources`)
- **`fxml/`**: Ficheiros de layout da interface.
    - `HomeView.fxml`: Ecrã principal com resumos e gráficos.
    - `MealsView.fxml`: Registo de refeições e base de dados de alimentos.
    - `LoginView.fxml`: Seleção de perfil.
    - `RegisterView.fxml`: Criação/Edição de perfil.
- **`css/`**: Estilos visuais (`styles.css` com tema Fresh & Healthy).

## 3. Funcionalidades Implementadas
1.  **Gestão de Perfis**:
    - Criação de múltiplos utilizadores.
    - Suporte para género (Masculino/Feminino) para cálculo preciso de metas.
    - Sistema de Login para trocar entre perfis.
2.  **Monitorização Diária**:
    - **Calorias**: Meta calculada automaticamente (Mifflin-St Jeor) vs Consumo real.
    - **Água**: Meta de 35ml/kg vs Consumo real.
    - **Macronutrientes**: Barras de progresso para Proteína, Hidratos e Gordura.
3.  **Diário Alimentar**:
    - Registo de refeições manuais.
    - Base de dados de alimentos ("Meus Alimentos") para registo rápido.
4.  **Interface Gráfica**:
    - Design responsivo e limpo.
    - Navegação por abas lateral.
    - Gráficos de evolução de peso (Placeholder funcional).

## 4. Testes Realizados (Validação Manual)
Todos os testes foram realizados manualmente executando a aplicação localmente.

### Protocolo de Teste 1: Registo e Login
- **Ação**: Iniciar a aplicação sem dados.
- **Resultado Esperado**: Redirecionamento para "Criar Perfil".
- **Resultado Obtido**: Sucesso.
- **Ação**: Criar perfil "Carlos", 25 anos, 70kg, 175cm.
- **Resultado Obtido**: Dashboard carregado com metas corretas.

### Protocolo de Teste 2: Mudança de Perfil
- **Ação**: Clicar em "Trocar Perfil".
- **Resultado Esperado**: Regresso ao ecrã de Login com lista de perfis.
- **Resultado Obtido**: Sucesso. Botão "Carlos" visível.

### Protocolo de Teste 3: Inserção de Refeição e Macros
- **Ação**: Adicionar "Frango" (200kcal, 30g Prot) em "Meus Alimentos".
- **Ação**: Ir a "Diário", selecionar "Frango", adicionar refeição.
- **Resultado Esperado**: Lista atualizada, barras de progresso no Dashboard atualizadas.
- **Resultado Obtido**: Sucesso. Dashboard reflete valores de Macronutrientes.

### Protocolo de Teste 4: Resistência a Erros
- **Ação**: Tentar inserir texto no campo "Calorias".
- **Resultado Esperado**: Mensagem de erro "Valores numéricos inválidos".
- **Resultado Obtido**: Sucesso.

## 5. Conclusão
O sistema encontra-se estável, com todas as funcionalidades críticas operacionais e validadas. O código foi revisto para garantir consistência na língua portuguesa.
