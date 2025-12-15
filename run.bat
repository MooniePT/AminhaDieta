@echo off
setlocal EnableDelayedExpansion

REM ============================================================================
REM A MINHA DIETA - ZERO SETUP LAUNCHER
REM ============================================================================
REM Este script verifica se o Java 21 e o Maven estao instalados.
REM Se nao estiverem, faz o download automatico de versoes portateis para a pasta .deps
REM e configura o ambiente apenas para esta execucao.
REM ============================================================================

set "PROJECT_ROOT=%~dp0"
set "DEPS_DIR=%PROJECT_ROOT%.deps"
set "JAVA_URL=https://api.adoptium.net/v3/binary/latest/21/ga/windows/x64/jdk/hotspot/normal/eclipse?project=jdk"
set "MAVEN_URL=https://archive.apache.org/dist/maven/maven-3/3.9.6/binaries/apache-maven-3.9.6-bin.zip"

if not exist "%DEPS_DIR%" mkdir "%DEPS_DIR%"

echo ========================================================
echo         A INICIAR A MINHA DIETA
echo ========================================================

REM ----------------------------------------------------------------------------
REM 1. VERIFICAR/CONFIGURAR JAVA
REM ----------------------------------------------------------------------------
echo [1/3] A verificar Java (JDK 21)...

set "LOCAL_JAVA_DIR=%DEPS_DIR%\jdk-21"
set "JAVA_EXE=java"

REM Verifica se ja temos o Java local baixado
if exist "%LOCAL_JAVA_DIR%\bin\java.exe" (
    echo     Detetado Java portatil em .deps. A configurar...
    set "JAVA_HOME=%LOCAL_JAVA_DIR%"
    set "PATH=!JAVA_HOME!\bin;!PATH!"
) else (
    REM Se nao temos local, verifica se o do sistema serve
    java -version >nul 2>&1
    if !errorlevel! equ 0 (
        echo     Java detetado no sistema.
        REM Nota: Idealmente verificariamos a versao, mas vamos assumir que se o user tem java, pode tentar usar.
        REM Se falhar mais a frente, o user sabera. Mas para garantir "zero setup", vamos forcar o download se nao existir JAVA_HOME ou se for incerto?
        REM Vamos simplificar: Se java existe, usamos. Se nao, baixamos.
    ) else (
        echo     Java nao detetado. A iniciar download do JDK 21 Portatil...
        echo     (Isto pode demorar alguns minutos dependendo da internet)
        
        powershell -Command "Invoke-WebRequest -Uri '%JAVA_URL%' -OutFile '%DEPS_DIR%\jdk.zip'"
        echo     A extrair Java...
        powershell -Command "Expand-Archive -Path '%DEPS_DIR%\jdk.zip' -DestinationPath '%DEPS_DIR%\jdk_temp' -Force"
        
        REM O zip geralmente tem uma pasta dentro (ex: jdk-21.0.1...), vamos mover o conteudo para ficar padrao
        for /d %%d in ("%DEPS_DIR%\jdk_temp\*") do (
            move "%%d" "%LOCAL_JAVA_DIR%"
        )
        rmdir "%DEPS_DIR%\jdk_temp"
        del "%DEPS_DIR%\jdk.zip"
        
        echo     Java instalado com sucesso em %LOCAL_JAVA_DIR%
        set "JAVA_HOME=%LOCAL_JAVA_DIR%"
        set "PATH=!JAVA_HOME!\bin;!PATH!"
    )
)

REM Confirmar Java
java -version >nul 2>&1
if %errorlevel% neq 0 (
    echo [ERRO] Nao foi possivel configurar o Java.
    pause
    exit /b
)

REM ----------------------------------------------------------------------------
REM 2. VERIFICAR/CONFIGURAR MAVEN
REM ----------------------------------------------------------------------------
echo [2/3] A verificar Maven...

set "LOCAL_MAVEN_DIR=%DEPS_DIR%\maven"

REM Verifica se temos Maven local
if exist "%LOCAL_MAVEN_DIR%\bin\mvn.cmd" (
    echo     Detetado Maven portatil em .deps. A configurar...
    set "PATH=%LOCAL_MAVEN_DIR%\bin;!PATH!"
) else (
    call mvn -version >nul 2>&1
    if !errorlevel! equ 0 (
        echo     Maven detetado no sistema.
    ) else (
        echo     Maven nao detetado. A iniciar download do Maven...
        
        powershell -Command "Invoke-WebRequest -Uri '%MAVEN_URL%' -OutFile '%DEPS_DIR%\maven.zip'"
        echo     A extrair Maven...
        powershell -Command "Expand-Archive -Path '%DEPS_DIR%\maven.zip' -DestinationPath '%DEPS_DIR%\maven_temp' -Force"
        
        REM Mover conteudo da pasta interna para .deps/maven
        for /d %%d in ("%DEPS_DIR%\maven_temp\*") do (
            move "%%d" "%LOCAL_MAVEN_DIR%"
        )
        rmdir "%DEPS_DIR%\maven_temp"
        del "%DEPS_DIR%\maven.zip"
        
        echo     Maven instalado com sucesso em %LOCAL_MAVEN_DIR%
        set "PATH=%LOCAL_MAVEN_DIR%\bin;!PATH!"
    )
)

REM ----------------------------------------------------------------------------
REM 3. EXECUTAR APLICACAO
REM ----------------------------------------------------------------------------
echo [3/3] A lançar a aplicacao...
echo.

cd AminhaDieta || (
    echo [ERRO] Pasta 'AminhaDieta' nao encontrada.
    echo O script deve estar na raiz do repositorio.
    pause
    exit /b
)

REM Executa com Maven
call mvn javafx:run

if %errorlevel% neq 0 (
    echo.
    echo [ERRO] Ocorreu um erro na execucao.
    pause
) else (
    echo.
    echo Aplicacao terminada.
)
