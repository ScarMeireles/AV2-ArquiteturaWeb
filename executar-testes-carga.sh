#!/bin/bash

echo "========================================"
echo "    TESTES DE CARGA - API AV2"
echo "========================================"
echo

# Verificar se o JMeter está instalado
if ! command -v jmeter &> /dev/null; then
    echo "ERRO: JMeter não encontrado no PATH"
    echo
    echo "Por favor, instale o JMeter e adicione ao PATH:"
    echo "1. Baixe em: https://jmeter.apache.org/download_jmeter.cgi"
    echo "2. Extraia em uma pasta"
    echo "3. Adicione a pasta bin ao PATH do sistema"
    echo
    read -p "Pressione Enter para sair..."
    exit 1
fi

# Verificar se a API está rodando
echo "Verificando se a API está rodando..."
if ! curl -s http://localhost:8080/api/actuator/health > /dev/null 2>&1; then
    echo "ERRO: API não está rodando em http://localhost:8080"
    echo
    echo "Por favor, inicie a API primeiro:"
    echo "mvn spring-boot:run"
    echo
    read -p "Pressione Enter para sair..."
    exit 1
fi

echo "API encontrada! Iniciando testes de carga..."
echo

# Criar diretório para resultados se não existir
mkdir -p resultados

# Definir timestamp para o nome do arquivo
TIMESTAMP=$(date +"%Y%m%d_%H%M%S")

echo "Executando testes de carga..."
echo "Timestamp: $TIMESTAMP"
echo

# Executar teste via linha de comando
jmeter -n -t testes-carga.jmx -l "resultados/resultados_${TIMESTAMP}.jtl" -e -o "resultados/relatorio_${TIMESTAMP}/"

if [ $? -eq 0 ]; then
    echo
    echo "========================================"
    echo "    TESTES CONCLUÍDOS COM SUCESSO!"
    echo "========================================"
    echo
    echo "Arquivos gerados:"
    echo "- Resultados: resultados/resultados_${TIMESTAMP}.jtl"
    echo "- Relatório: resultados/relatorio_${TIMESTAMP}/"
    echo
    echo "Para abrir o relatório, abra o arquivo:"
    echo "resultados/relatorio_${TIMESTAMP}/index.html"
    echo
else
    echo
    echo "========================================"
    echo "    ERRO AO EXECUTAR OS TESTES!"
    echo "========================================"
    echo
    echo "Verifique:"
    echo "1. Se a API está rodando"
    echo "2. Se o arquivo testes-carga.jmx existe"
    echo "3. Se o JMeter está configurado corretamente"
    echo
fi

read -p "Pressione Enter para sair..." 