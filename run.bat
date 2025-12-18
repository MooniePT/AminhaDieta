@echo off
setlocal EnableDelayedExpansion

REM ============================================================================
REM A MINHA DIETA - ZERO SETUP LAUNCHER
REM ============================================================================
REM Este script verifica se o Java 21 e o Maven estao instalados.
REM Se nao estiverem, faz o download automatico usando CURL (mais rapido)
REM ou PowerShell otimizado.
REM ============================================================================

set "PROJECT_ROOT=%~dp0"
cd /d "%PROJECT_ROOT%"

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

REM Verifica se ja temos o Java local baixado
if exist "%LOCAL_JAVA_DIR%\bin\java.exe" (
    echo     Detetado Java portatil em .deps. A configurar...
    set "JAVA_HOME=%LOCAL_JAVA_DIR%"
    set "PATH=!JAVA_HOME!\bin;!PATH!"
) else (
    REM Verifica se o Java do sistema existe e e compativel?
    REM Para garantir compatibilidade total, vamos preferir o portatil se nao for o 21.
    REM Mas para simplificar, se java existe, vamos tentar usar.
    java -version >nul 2>&1
    if !errorlevel! equ 0 (
        echo     Java detetado no sistema.
    ) else (
        echo     Java nao detetado.
        echo     A iniciar download do JDK 21 Portatil...
        echo     (Isto pode demorar um pouco - aprox. 200MB)
        
        call :DownloadFile "%JAVA_URL%" "%DEPS_DIR%\jdk.zip"
        
        echo     A extrair Java...
        powershell -Command "Expand-Archive -Path '%DEPS_DIR%\jdk.zip' -DestinationPath '%DEPS_DIR%\jdk_temp' -Force"
        
        REM O zip geralmente tem uma pasta dentro, vamos mover o conteudo
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
        
        call :DownloadFile "%MAVEN_URL%" "%DEPS_DIR%\maven.zip"
        
        echo     A extrair Maven...
        powershell -Command "Expand-Archive -Path '%DEPS_DIR%\maven.zip' -DestinationPath '%DEPS_DIR%\maven_temp' -Force"
        
        REM Mover conteudo da pasta interna para .deps/maven
        for /d %%d in ("%DEPS_DIR%\maven_temp\*") do (
            move "%%d" "%LOCAL_MAVEN_DIR%"
        )
        rmdir "%DEPS_DIR%\maven_temp"
        del "%DEPS_DIR%\maven.zip"
        
        echo     Maven instalado em %LOCAL_MAVEN_DIR%
        set "PATH=%LOCAL_MAVEN_DIR%\bin;!PATH!"
    )
)

REM ----------------------------------------------------------------------------
REM 3. EXECUTAR APLICACAO
REM ----------------------------------------------------------------------------
echo [3/3] A lancar a aplicacao...
echo.

REM ----------------------------------------------------------------------------
REM 3. EXECUTAR APLICACAO
REM ----------------------------------------------------------------------------
echo [3/3] A lancar a aplicacao...
echo.

REM Logica inteligente para encontrar o pom.xml
if exist "pom.xml" (
    echo     Encontrado pom.xml na pasta atual.
) else (
    if exist "AminhaDieta\pom.xml" (
        echo     Encontrado pom.xml na subpasta AminhaDieta. Entrando...
        cd AminhaDieta
    ) else (
        echo [ERRO] Nao foi possivel encontrar o ficheiro pom.xml.
        echo Pasta atual: %CD%
        echo A verifique se a pasta 'AminhaDieta' existe aqui.
        dir
        pause
        exit /b
    )
)

call mvn javafx:run

if %errorlevel% neq 0 (
    echo.
    echo [ERRO] Ocorreu um erro na execucao.
    pause
) else (
    echo.
    echo Aplicacao terminada.
)

exit /b

REM ============================================================================
REM FUNCAO DE DOWNLOAD ROBUSTA
REM Tenta usar curl (rapido), falha para PowerShell otimizado
REM ============================================================================
:DownloadFile
set "URL=%~1"
set "OUTPUT=%~2"

echo     Baixando %URL%...

REM Tenta usar CURL (Windows 10/11 tem nativo)
curl -L -o "%OUTPUT%" "%URL%"
if %errorlevel% equ 0 exit /b 0

echo     CURL nao encontrado, usando PowerShell...
REM Otimizacao: $ProgressPreference = 'SilentlyContinue' acelera MUITO o download
powershell -Command "$ProgressPreference = 'SilentlyContinue'; Invoke-WebRequest -Uri '%URL%' -OutFile '%OUTPUT%'"

if %errorlevel% neq 0 (
    echo [ERRO] Falha ao baixar ficheiro. Verifique a internet e tente novamente.
    pause
    exit /b 1
)
exit /b 0

