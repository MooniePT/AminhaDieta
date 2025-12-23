# Relatório de Projeto Final - A Minha Dieta

**Unidade Curricular:** Programação Orientada a Objetos  
**Data:** 23 de Dezembro de 2025

**Equipa de Desenvolvimento:**
*   Carlos Farinha
*   Joao Rodrigues
*   Henrique Marques
*   André Schroder

---

## 1. Introdução

### 1.1 Contextualização
Na sociedade atual, a preocupação com a saúde e o bem-estar tem vindo a crescer exponencialmente. A gestão de uma dieta equilibrada, a prática regular de exercício físico e uma hidratação adequada são pilares fundamentais para um estilo de vida saudável.

### 1.2 Objetivos do Projeto
O projeto "A Minha Dieta" fornece uma aplicação desktop intuitiva para:
*   Calcular necessidades calóricas diárias.
*   Registar refeições e monitorizar macronutrientes.
*   Acompanhar o consumo de água.
*   Registar atividades físicas.
*   Visualizar progresso com gráficos.

---

## 2. Cronograma de Desenvolvimento

### Fase 1: Planeamento e Análise (Semana 1-2)
*   Definição de requisitos e Mockups.
*   Modelação do domínio (Diagrama de Classes).

### Fase 2: Implementação do Core (Semana 3-4)
*   Estrutura Maven e classes de modelo (`UserProfile`, `Food`).
*   Persistência de dados (`DataStore`).

### Fase 3: Interface Gráfica e Lógica (Semana 5-6)
*   Desenvolvimento de vistas FXML e Controladores.
*   Integração lógica-interface.

### Fase 4: Refinamento e Testes (Semana 7-8)
*   Melhorias estéticas e gráficos.
*   Exportação PDF e testes.

### Fase 5: Polimento Visual e Personalização (Semana 9)
*   **Redesign da Interface**: Paleta de cores vibrante e tipografia moderna.
*   **Definições**: Ecrã para personalizar fundo (Arco-íris/Estático) e fontes.
*   **Gamificação**: Mensagens motivacionais ao atingir metas.
*   **Correções**: Ajustes visuais em componentes JavaFX.

---

## 3. Arquitetura do Sistema

A aplicação segue o padrão **MVC (Model-View-Controller)**:
*   **Model**: Lógica de negócio (`app.model`).
*   **View**: Interface FXML e CSS.
*   **Controller**: Gestão de eventos (`app.ui.controller`).

A persistência é feita via serialização de objetos em `appstate.dat`.

---

## 4. Conclusão

O projeto "A Minha Dieta" resultou numa aplicação robusta e moderna. A arquitetura MVC facilitou a expansão, permitindo adicionar funcionalidades como o "Modo Arco-íris" e as definições de personalização na fase final sem comprometer a estabilidade do código existente.
