# Manual de Git e GitHub

Este guia foi desenhado para ajudar tanto quem prefere interfaces visuais (GitHub Desktop) como quem gosta da rapidez do terminal.

## Escolha a sua ferramenta:
*   [Opção A: GitHub Desktop (Visual)](#opção-a-github-desktop-visual) - **Recomendado para iniciantes**
*   [Opção B: Linha de Comandos (Terminal)](#opção-b-linha-de-comandos-terminal) - Para utilizadores avançados

---

## Opção A: GitHub Desktop (Visual)

O GitHub Desktop é uma aplicação oficial que simplifica o uso do Git.

### 1. Clonar um Repositório (Baixar o projeto)
1.  Abra o GitHub Desktop.
2.  Vá a **File** > **Clone repository**.
3.  Escolha o repositório da lista (se estiver logado) ou cole o URL na aba "URL".
4.  Clique em **Clone**.

### 2. Guardar Alterações (Commit)
Quando faz alterações nos ficheiros, elas aparecem na lista à esquerda.
1.  Marque os ficheiros que quer guardar (geralmente todos).
2.  No campo "Summary" (canto inferior esquerdo), escreva uma mensagem curta (ex: "Adicionei botão de login").
3.  Clique no botão azul **Commit to main**.

### 3. Enviar e Receber (Push & Pull)
No GitHub Desktop, o botão "Push" e "Pull" é muitas vezes o mesmo: **Fetch origin** ou **Push origin**.
*   **Push origin**: Envia os seus commits para o GitHub.
*   **Fetch origin**: Verifica se há novidades no GitHub. Se houver, o botão muda para **Pull origin**. Clique para baixar.

### 4. Criar um Ramo (Branch)
Para trabalhar numa nova funcionalidade sem estragar o que já está feito:
1.  Clique no menu **Current Branch** (no topo da janela).
2.  Clique em **New Branch**.
3.  Dê um nome (ex: `funcionalidade-nova`) e clique em **Create Branch**.
4.  Agora está a trabalhar nesse ramo. Quando terminar, faça **Publish branch**.

---

## Opção B: Linha de Comandos (Terminal)

### Comandos Essenciais
*   `git status`: Ver o que mudou.
*   `git add .`: Preparar todos os ficheiros para commit.
*   `git commit -m "mensagem"`: Guardar as alterações.
*   `git push`: Enviar para o GitHub.
*   `git pull`: Receber do GitHub.

### Trabalhar com Ramos
*   `git checkout -b nome-do-ramo`: Criar e mudar para um novo ramo.
*   `git checkout main`: Voltar ao ramo principal.
*   `git merge nome-do-ramo`: Juntar o ramo criado ao principal (estando no principal).

---

## Conceitos Avançados (Úteis para ambos)

### Stash (Guardar na Gaveta)
Imagine que está a meio de um trabalho, tudo "partido", e precisa de mudar de ramo urgentemente. Não quer fazer commit de código estragado.
*   **O que faz**: Guarda as alterações temporariamente numa "gaveta" e limpa o seu ambiente de trabalho.
*   **GitHub Desktop**: Clique com o botão direito na barra superior > **Stash all changes**. Para recuperar, vá a **Stashed Changes** > **Restore**.
*   **Terminal**: `git stash` (para guardar) e `git stash pop` (para recuperar).

### Revert (Desfazer)
Fez um commit que introduziu um erro grave? O Revert cria um *novo* commit que faz exatamente o contrário do commit errado, anulando-o sem apagar o histórico.
*   **GitHub Desktop**: Vá ao separador **History**, clique com o botão direito no commit errado e escolha **Revert changes in commit**.
*   **Terminal**: `git revert <hash-do-commit>`.

### Discard (Descartar)
Fez alterações num ficheiro mas arrependeu-se e quer voltar a como estava no último commit.
*   **GitHub Desktop**: Clique com o botão direito no ficheiro na lista de alterações > **Discard changes**.
*   **Terminal**: `git checkout -- nome-do-ficheiro`.

---

## Resolução de Problemas Comuns

### "Merge Conflict"
Acontece quando duas pessoas alteram a mesma linha.
1.  O Git avisa que há conflito.
2.  Abra o ficheiro num editor (VS Code, Notepad).
3.  Procure por `<<<<<<<`, `=======`, `>>>>>>>`.
4.  Escolha o código que quer manter e apague as linhas de marcação do Git.
5.  Faça um novo Commit.

### "Detached HEAD"
Significa que não está em nenhum ramo, está a "olhar" para um commit antigo específico.
*   **Solução**: Volte para um ramo oficial: `git checkout main`.
