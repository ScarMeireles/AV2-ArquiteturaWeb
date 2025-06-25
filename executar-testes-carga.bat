@echo off
echo ========================================
echo    TESTES DE CARGA - API AV2
echo ========================================
echo.

REM Verificar se o JMeter está instalado
where jmeter >nul 2>nul
if %errorlevel% neq 0 (
    echo ERRO: JMeter nao encontrado no PATH
    echo.
    echo Por favor, instale o JMeter e adicione ao PATH:
    echo 1. Baixe em: https://jmeter.apache.org/download_jmeter.cgi
    echo 2. Extraia em uma pasta
    echo 3. Adicione a pasta bin ao PATH do sistema
    echo.
    pause
    exit /b 1
)

REM Verificar se a API está rodando
echo Verificando se a API esta rodando...
curl -s http://localhost:8080/api/actuator/health >nul 2>nul
if %errorlevel% neq 0 (
    echo ERRO: API nao esta rodando em http://localhost:8080
    echo.
    echo Por favor, inicie a API primeiro:
    echo mvn spring-boot:run
    echo.
    pause
    exit /b 1
)

echo API encontrada! Iniciando testes de carga...
echo.

REM Criar diretório para resultados se não existir
if not exist "resultados" mkdir resultados

REM Definir timestamp para o nome do arquivo
set TIMESTAMP=%date:~-4,4%%date:~-10,2%%date:~-7,2%_%time:~0,2%%time:~3,2%%time:~6,2%
set TIMESTAMP=%TIMESTAMP: =0%

echo Executando testes de carga...
echo Timestamp: %TIMESTAMP%
echo.

REM Executar teste via linha de comando
jmeter -n -t testes-carga.jmx -l resultados/resultados_%TIMESTAMP%.jtl -e -o resultados/relatorio_%TIMESTAMP%/

if %errorlevel% equ 0 (
    echo.
    echo ========================================
    echo    TESTES CONCLUIDOS COM SUCESSO!
    echo ========================================
    echo.
    echo Arquivos gerados:
    echo - Resultados: resultados/resultados_%TIMESTAMP%.jtl
    echo - Relatorio: resultados/relatorio_%TIMESTAMP%/
    echo.
    echo Para abrir o relatorio, abra o arquivo:
    echo resultados/relatorio_%TIMESTAMP%/index.html
    echo.
) else (
    echo.
    echo ========================================
    echo    ERRO AO EXECUTAR OS TESTES!
    echo ========================================
    echo.
    echo Verifique:
    echo 1. Se a API esta rodando
    echo 2. Se o arquivo testes-carga.jmx existe
    echo 3. Se o JMeter esta configurado corretamente
    echo.
)

pause 