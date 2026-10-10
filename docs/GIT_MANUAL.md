# Manual de Git e GitHub - A Minha Dieta

Este guia foi desenhado para ajudar tanto quem prefere interfaces visuais (GitHub Desktop) como quem gosta da rapidez do terminal.

## Índice
*   [Opção A: GitHub Desktop (Visual)](#opção-a-github-desktop-visual) - **Recomendado para iniciantes**
*   [Opção B: Linha de Comandos (Terminal)](#opção-b-linha-de-comandos-terminal) - Para utilizadores avançados
*   [Conceitos Avançados](#conceitos-avançados)
*   [Resolução de Problemas](#resolução-de-problemas)
*   [Fluxo de Trabalho da Equipa](#fluxo-de-trabalho-da-equipa)

---

## Opção A: GitHub Desktop (Visual)

O GitHub Desktop é uma aplicação oficial que simplifica o uso do Git.

### 1. Instalar GitHub Desktop
1.  Aceda a [desktop.github.com](https://desktop.github.com/).
2.  Descarregue e instale a aplicação.
3.  Inicie sessão com a sua conta GitHub.

### 2. Clonar o Repositório (Baixar o projeto)
1.  Abra o GitHub Desktop.
2.  Vá a **File** > **Clone repository**.
3.  Na aba **URL**, cole: `https://github.com/MooniePT/AminhaDieta.git`
4.  Escolha o diretório local e clique em **Clone**.

### 3. Guardar Alterações (Commit)
Quando faz alterações nos ficheiros, elas aparecem na lista à esquerda.
1.  Marque os ficheiros que quer guardar (geralmente todos).
2.  No campo **Summary** (canto inferior esquerdo), escreva uma mensagem curta (ex: "Adicionei módulo de exercícios").
3.  Opcionalmente, adicione uma **Description** mais detalhada.
4.  Clique no botão azul **Commit to main**.

### 4. Enviar e Receber (Push & Pull)
*   **Push origin**: Envia os seus commits para o GitHub online.
*   **Fetch origin**: Verifica se há novidades no GitHub. Se houver, o botão muda para **Pull origin**. Clique para baixar.

### 5. Criar um Ramo (Branch)
Para trabalhar numa nova funcionalidade sem afetar o código principal:
1.  Clique no menu **Current Branch** (no topo da janela).
2.  Clique em **New Branch**.
3.  Dê um nome descritivo (ex: `feature/exportar-pdf`).
4.  Clique em **Create Branch**.
5.  Quando terminar, faça **Publish branch** e depois abra um Pull Request no GitHub.

---

## Opção B: Linha de Comandos (Terminal)

### Configuração Inicial (apenas uma vez)
```bash
git config --global user.name "O Seu Nome"
git config --global user.email "o.seu.email@exemplo.com"
```

### Clonar o Repositório
```bash
git clone https://github.com/MooniePT/AminhaDieta.git
cd AminhaDieta
```

### Comandos Essenciais
| Comando | Descrição |
|---------|-----------|
| `git status` | Ver o que mudou |
| `git add .` | Preparar todos os ficheiros para commit |
| `git add ficheiro.java` | Preparar ficheiro específico |
| `git commit -m "mensagem"` | Guardar as alterações localmente |
| `git push` | Enviar commits para o GitHub |
| `git pull` | Receber alterações do GitHub |
| `git log --oneline -10` | Ver últimos 10 commits |

### Trabalhar com Ramos (Branches)
```bash
# Criar e mudar para um novo ramo
git checkout -b feature/nova-funcionalidade

# Ver todos os ramos
git branch -a

# Mudar para outro ramo existente
git checkout main

# Atualizar lista de ramos remotos
git fetch --all

# Juntar ramo ao main (estando no main)
git merge feature/nova-funcionalidade

# Apagar ramo local após merge
git branch -d feature/nova-funcionalidade
```

---

## Conceitos Avançados

### Stash (Guardar na Gaveta)
Quando está a meio de um trabalho e precisa mudar de ramo urgentemente sem fazer commit:

**GitHub Desktop:**
*   Clique com o botão direito na barra superior > **Stash all changes**
*   Para recuperar: vá a **Stashed Changes** > **Restore**

**Terminal:**
```bash
git stash              # Guardar alterações
git stash list         # Ver stashes guardados
git stash pop          # Recuperar última stash
git stash drop         # Apagar última stash
```

### Revert (Desfazer um Commit)
Cria um *novo* commit que anula um commit anterior, mantendo o histórico.

**GitHub Desktop:**
*   Vá ao separador **History**, clique com o botão direito no commit > **Revert changes in commit**

**Terminal:**
```bash
git revert <hash-do-commit>
```

### Discard (Descartar Alterações Locais)
Volta ao estado do último commit.

**GitHub Desktop:**
*   Clique com o botão direito no ficheiro > **Discard changes**

**Terminal:**
```bash
git checkout -- ficheiro.java    # Descartar ficheiro específico
git checkout -- .                 # Descartar todas as alterações
```

### Reset (Voltar Atrás no Tempo)
⚠️ **Cuidado**: Pode perder trabalho!

```bash
git reset --soft HEAD~1   # Desfaz último commit, mantém alterações staged
git reset --hard HEAD~1   # Desfaz último commit e PERDE alterações
```

---

## Resolução de Problemas

### "Merge Conflict"
Acontece quando duas pessoas alteram a mesma linha.

1.  O Git avisa que há conflito.
2.  Abra o ficheiro num editor (VS Code marca as secções).
3.  Procure por:
    ```
    <<<<<<< HEAD
    código atual
    =======
    código do outro ramo
    >>>>>>> nome-do-ramo
    ```
4.  Escolha o código que quer manter e apague as linhas de marcação.
5.  Faça `git add` e depois um novo commit.

### "Detached HEAD"
Significa que não está em nenhum ramo.

**Solução:**
```bash
git checkout main
```

### "Your branch is behind 'origin/main'"
Significa que há commits no GitHub que não tem localmente.

**Solução:**
```bash
git pull origin main
```

### "Permission denied (publickey)"
A sua chave SSH não está configurada.

**Solução:**
1.  Use HTTPS em vez de SSH, ou
2.  Configure uma chave SSH: [Instruções GitHub](https://docs.github.com/en/authentication/connecting-to-github-with-ssh)

---

## Fluxo de Trabalho da Equipa

### Antes de Começar a Trabalhar
```bash
git checkout main
git pull origin main
git checkout -b feature/minha-tarefa
```

### Durante o Trabalho
```bash
# Após cada bloco de trabalho significativo
git add .
git commit -m "Descrição clara do que foi feito"
```

### Ao Terminar
```bash
# Atualizar com possíveis alterações do main
git checkout main
git pull origin main
git checkout feature/minha-tarefa
git merge main    # Resolver conflitos se existirem

# Enviar para GitHub
git push -u origin feature/minha-tarefa
```

### No GitHub
1.  Abra um **Pull Request** da sua branch para `main`.
2.  Peça a um colega para rever.
3.  Após aprovação, faça **Merge**.

---

## Boas Práticas

### Mensagens de Commit
*   Use verbos no imperativo: "Adiciona", "Corrige", "Remove"
*   Seja específico: ❌ "Atualizações" → ✅ "Adiciona validação de email no registo"
*   Mantenha curto (máx. 50 caracteres para o título)

### Frequência de Commits
*   Faça commits pequenos e frequentes
*   Cada commit deve representar uma unidade lógica de trabalho
*   ❌ Um commit gigante com "Implementei tudo"
*   ✅ Vários commits: "Adiciona modelo", "Cria controlador", "Implementa vista"

### Nomes de Branches
*   `feature/nome-da-funcionalidade` - Para novas funcionalidades
*   `bugfix/descricao-do-bug` - Para correções de bugs
*   `hotfix/urgente` - Para correções críticas em produção
