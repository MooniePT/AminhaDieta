# A Minha Dieta

Projeto de Gestão de Dietas desenvolvido em Java (POO).

## 🚀 Guia de Início Rápido (Para Utilizadores)

Este guia explica passo-a-passo o que precisa de instalar para conseguir rodar este programa no seu computador (Windows), assumindo que não tem nada instalado.

### 1. Pré-requisitos (Obrigatório)

Para o programa funcionar, precisa de instalar duas ferramentas: o **Java (JDK)** e o **Maven**.

#### A. Instalar o Java (JDK 21)
O programa necessita do Java 21 para correr.
1.  Aceda ao site oficial do [Adoptium Temurin 21](https://adoptium.net/temurin/releases/?version=21&os=windows&arch=x64).
2.  Faça download do instalador **.msi**.
3.  Execute o instalador.
4.  **IMPORTANTE**: Durante a instalação, vai aparecer uma lista de funcionalidades. Clique no ícone "X" vermelho ao lado de **"Set JAVA_HOME variable"** e selecione *"Will be installed on local hard drive"*. Isto é essencial!
5.  Avance e conclua a instalação.

#### B. Instalar o Apache Maven
O Maven serve para organizar o projeto e as suas bibliotecas.
1.  Aceda ao site do [Apache Maven](https://maven.apache.org/download.cgi).
2.  Em "Files", faça download do **"Binary zip archive"** (ex: `apache-maven-3.x.x-bin.zip`).
3.  Extraia o conteúdo do ZIP para uma pasta simples, por exemplo: `C:\Maven`.
4.  Agora precisa de "dizer" ao Windows onde está o Maven:
    *   Prima a tecla `Windows` e pesquise por **"Editar as variáveis de ambiente do sistema"**. Abra essa opção.
    *   Clique no botão **"Variáveis de Ambiente..."**.
    *   Na lista de baixo (**Variáveis do sistema**), procure a linha que diz **Path** e clique em **Editar**.
    *   Clique em **Novo** e escreva o caminho para a pasta `bin` do Maven que extraiu (Exemplo: `C:\Maven\apache-maven-3.9.6\bin`).
    *   Clique em **OK** em todas as janelas para fechar.

### 2. Como Executar o Programa

Depois de ter o Java e o Maven instalados:

1.  **Baixe este projeto**: Clique no botão verde "Code" no GitHub e escolha "Download ZIP". Extraia para uma pasta (ex: Documentos).
2.  Abra a pasta do projeto.
3.  Encontre o ficheiro **`run.bat`** e clique duas vezes nele.
4.  Uma janela preta vai abrir. Se for a primeira vez, vai demorar um pouco a "baixar a internet" (bibliotecas necessárias).
5.  A aplicação "A Minha Dieta" deverá abrir automaticamente!

---

## 🛠️ Para Programadores (Desenvolvimento)
*   **Java Version**: 21
*   **JavaFX Version**: 21.0.4
*   **Build Tool**: Maven

Comandos úteis:
```bash
# Executar a aplicação
mvn javafx:run

# Limpar e compilar
mvn clean package
```
