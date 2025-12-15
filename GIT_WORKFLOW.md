# Guia de Workflow Git para a Equipa

Este documento explica como trabalhar com branches (ramos) e integrar alterações.

## Cenário
- **Branch Principal:** `main` (Contém a versão estável)
- **Branch de Desenvolvimento:** `dev` (Onde cada um trabalha ou integra antes de ir para main)
- **Membros:** 4 pessoas

## 1. Atualizar o teu branch local com o `main`
Antes de começares a trabalhar ou antes de partilhares o teu código, garante que tens as últimas alterações do `main`.

1.  **Muda para o branch main e atualiza-o:**
    ```bash
    git checkout main
    git pull origin main
    ```

2.  **Volta para o teu branch (ex: `dev` ou `o-teu-branch`):**
    ```bash
    git checkout dev
    ```
    *(Substitui `dev` pelo nome do teu branch se usares um nome diferente)*

3.  **Combina (Merge) o `main` no teu branch:**
    ```bash
    git merge main
    ```
    *Se houver conflitos, resolve-os (edita os ficheiros marcados) e depois faz `git add .` e `git commit`.*

## 2. Enviar as tuas alterações para o repositório (`dev`)
Quando acabares uma tarefa no teu branch:

1.  **Adiciona e comita as alterações:**
    ```bash
    git add .
    git commit -m "Descrição do que fizeste"
    ```

2.  **Envia para o GitHub:**
    ```bash
    git push origin dev
    ```

## 3. Juntar todos os branches `dev` no `main`
Quando a equipa decidir que as funcionalidades no `dev` estão prontas e testadas, um responsável deve juntar tudo no `main`.

1.  **Vai para o branch main:**
    ```bash
    git checkout main
    git pull origin main
    ```
    *(Para garantir que o teu main local está atualizado)*

2.  **Faz o Merge do `dev` no `main`:**
    ```bash
    git merge dev
    ```

3.  **Envia a versão atualizada do `main` para o GitHub:**
    ```bash
    git push origin main
    ```

---
**Resumo de Comandos Úteis:**
- `git status`: Ver ficheiros alterados.
- `git log`: Ver histórico de commits.
- `git branch`: Ver em que branch estás.
