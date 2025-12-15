@echo off
REM Script para executar a aplicacao A Minha Dieta
REM Este script assume que o JAVA_HOME e o Maven estao configurados no PATH

echo ========================================================
echo         A INICIAR A MINHA DIETA - LAUNCHER
echo ========================================================
echo.

REM Verifica se o Java esta instalado
java -version >nul 2>&1
if %errorlevel% neq 0 (
    echo [ERRO] Java nao detetado. Por favor instale o JDK 21+.
    echo Consulte o README.md para instrucoes.
    pause
    exit /b
)

REM Verifica se o Maven esta instalado
call mvn -version >nul 2>&1
if %errorlevel% neq 0 (
    echo [ERRO] Maven nao detetado ou nao esta no PATH.
    echo Por favor instale o Apache Maven e adicione ao PATH.
    echo Consulte o README.md para instrucoes.
    pause
    exit /b
)

echo A entrar na pasta do projeto...
cd AminhaDieta || (
    echo [ERRO] Pasta 'AminhaDieta' nao encontrada.
    echo Certifique-se que este script esta na raiz do repositorio.
    pause
    exit /b
)

echo.
echo A compilar e executar a aplicacao...
echo (A primeira execucao pode demorar devido ao download de dependencias)
echo.

call mvn javafx:run

if %errorlevel% neq 0 (
    echo.
    echo [ERRO] Ocorreu um erro ao executar a aplicacao.
    pause
) else (
    echo.
    echo Aplicacao terminada com sucesso.
)
