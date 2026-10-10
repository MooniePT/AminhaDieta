# 📚 Guias de Implementação - Índice

> Tutoriais passo-a-passo para adicionar novas funcionalidades à aplicação

---

## 📂 Guias Disponíveis

### 1️⃣ Refeições
- **[01_adicionar_botao_refeicao_rapida.md](01_adicionar_botao_refeicao_rapida.md)**
  - Como adicionar novo botão de inserção rápida (ex: "Banana", "Iogurte")
  - Código exato + onde inserir no FXML e Controller

### 2️⃣ Água
- **[02_adicionar_botao_agua.md](02_adicionar_botao_agua.md)**
  - Como adicionar botão com quantidade diferente (ex: 1000ml, 750ml)
  - Código + posição no ficheiro

### 3️⃣ Exercícios
- **[03_adicionar_tipo_exercicio.md](03_adicionar_tipo_exercicio.md)**
  - Como adicionar novo tipo à lista (ex: "Dança", "Ténis")
  - Modificação no Controller

- **[04_editar_eliminar_exercicios.md](04_editar_eliminar_exercicios.md)**
  - Criar botões para editar/eliminar exercícios já registados
  - Código completo FXML + Controller + lógica

### 4️⃣ Histórico - Estatísticas
- **[05_estatisticas_historico.md](05_estatisticas_historico.md)**
  - **Refeições:** Alimento mais consumido, dia com mais kcal
  - **Exercícios:** Dia mais calorias, mais frequente, tipo mais usado
  - **Hidratação:** Dia com mais água, dia sem água
  - Código completo com métodos de análise

### 5️⃣ Histórico - Filtros Dinâmicos
- **[06_filtro_por_separador.md](06_filtro_por_separador.md)**
  - Adaptar caixa de pesquisa para filtrar separador ativo
  - Filtro automático: Refeições / Exercícios
  - Código + lógica de deteção de tab ativo

---

## 🎯 Como Usar Este Guia

1. **Escolhe a funcionalidade** que queres implementar
2. **Abre o ficheiro** correspondente
3. **Segue os passos** numerados
4. **Copia o código** exatamente como mostrado
5. **Testa** a funcionalidade

---

## ⚠️ IMPORTANTE - Antes de Começar

### Backup do Projeto
```bash
# Sempre faz backup antes de modificar!
cd c:\Users\Carlos\Documents\GitHub\AminhaDieta
git add .
git commit -m "Backup antes de adicionar funcionalidades"
```

### Ordem Recomendada de Implementação

**Fácil → Difícil:**
1. Adicionar botão água (mais simples)
2. Adicionar botão refeição rápida
3. Adicionar tipo exercício
4. Estatísticas histórico (médio)
5. Editar/eliminar exercícios (mais complexo)
6. Filtro por separador (mais complexo)

---

## 🧪 Testar Cada Funcionalidade

Após implementar, sempre:
1. ✅ Compilar: `mvn clean compile`
2. ✅ Executar: `mvn javafx:run`
3. ✅ Testar funcionalidade nova
4. ✅ Verificar se não quebrou nada existente
5. ✅ Se funcionar → commit!

---

**Boa sorte com as implementações! 🚀**
