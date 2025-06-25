# 📊 Testes de Carga com JMeter - API AV2

## 🎯 Objetivo
Este documento explica como executar testes de carga na API AV2 usando Apache JMeter para validar o desempenho e comportamento da aplicação sob diferentes níveis de carga.

## 📋 Pré-requisitos

### 1. Instalação do JMeter
1. **Baixar o JMeter**: Acesse [https://jmeter.apache.org/download_jmeter.cgi](https://jmeter.apache.org/download_jmeter.cgi)
2. **Extrair o arquivo**: Descompacte em uma pasta de sua preferência
3. **Executar**: 
   - **Windows**: Execute `jmeter.bat`
   - **Linux/macOS**: Execute `jmeter.sh`

### 2. API em Execução
Certifique-se de que a API AV2 está rodando:
```bash
mvn spring-boot:run
```

## 🚀 Configuração dos Testes

### Estrutura do Plano de Teste

O arquivo `testes-carga.jmx` contém:

#### 1. **Thread Group - Login**
- **Número de Threads**: 50 usuários simultâneos
- **Ramp-up Time**: 30 segundos (usuários são iniciados gradualmente)
- **Loop Count**: 10 execuções por usuário
- **Total de Requests**: 500 requisições de login

#### 2. **Thread Group - Produtos**
- **Número de Threads**: 20 usuários simultâneos
- **Ramp-up Time**: 10 segundos
- **Loop Count**: 5 execuções por usuário
- **Total de Requests**: 100 requisições de listagem de produtos

### Configurações dos Endpoints

#### Endpoint de Login
- **URL**: `http://localhost:8080/api/auth/login`
- **Método**: POST
- **Content-Type**: application/json
- **Payload**: 
```json
{
  "username": "admin",
  "password": "admin123"
}
```

#### Endpoint de Produtos
- **URL**: `http://localhost:8080/api/produtos`
- **Método**: GET
- **Authorization**: Bearer Token (extraído do login)

## 📈 Métricas Coletadas

### 1. **Throughput (Taxa de Transferência)**
- Requisições por segundo (RPS)
- Indica a capacidade da API de processar requisições

### 2. **Tempo Médio de Resposta**
- Tempo médio para processar uma requisição
- Inclui tempo de rede e processamento

### 3. **Taxa de Erro (%)**
- Percentual de requisições que falharam
- Códigos de erro HTTP (4xx, 5xx)

### 4. **Outras Métricas**
- **Latência**: Tempo mínimo, máximo e percentis
- **Bytes Transferidos**: Volume de dados
- **Conexões Simultâneas**: Número de conexões ativas

## 🔧 Como Executar os Testes

### 1. **Abrir o JMeter**
```bash
# Windows
jmeter.bat

# Linux/macOS
./jmeter.sh
```

### 2. **Carregar o Plano de Teste**
1. Clique em **File** → **Open**
2. Selecione o arquivo `testes-carga.jmx`

### 3. **Configurar Variáveis (se necessário)**
- **Host**: localhost (padrão)
- **Porta**: 8080 (padrão)
- **Credenciais**: admin/admin123 (padrão)

### 4. **Executar os Testes**
1. Clique no botão **Start** (▶️)
2. Monitore os resultados em tempo real
3. Aguarde a conclusão de todos os threads

## 📊 Análise dos Resultados

### 1. **Summary Report**
- Visão geral de todas as requisições
- Métricas agregadas por endpoint
- Taxa de sucesso e erro

### 2. **Aggregate Report**
- Estatísticas detalhadas por sampler
- Percentis de tempo de resposta
- Throughput por endpoint

### 3. **Graph Results**
- Gráfico de tempo de resposta ao longo do tempo
- Identificação de gargalos
- Comportamento sob carga

### 4. **View Results Tree**
- Detalhes de cada requisição individual
- Respostas e headers
- Útil para debug

## 🎯 Cenários de Teste

### Cenário 1: Carga Normal
- **Threads**: 10-20
- **Ramp-up**: 10 segundos
- **Objetivo**: Validar comportamento normal

### Cenário 2: Carga Moderada
- **Threads**: 30-50
- **Ramp-up**: 30 segundos
- **Objetivo**: Testar capacidade média

### Cenário 3: Carga Alta
- **Threads**: 100+
- **Ramp-up**: 60 segundos
- **Objetivo**: Identificar limites da aplicação

### Cenário 4: Teste de Estresse
- **Threads**: 200+
- **Ramp-up**: 120 segundos
- **Objetivo**: Encontrar ponto de quebra

## 📋 Interpretação dos Resultados

### ✅ **Resultados Esperados (Boa Performance)**
- **Throughput**: > 100 RPS
- **Tempo Médio**: < 500ms
- **Taxa de Erro**: < 1%
- **Latência 95%**: < 1000ms

### ⚠️ **Pontos de Atenção**
- **Throughput**: < 50 RPS
- **Tempo Médio**: > 2000ms
- **Taxa de Erro**: > 5%
- **Timeouts**: Muitas requisições expirando

### ❌ **Problemas Críticos**
- **Throughput**: < 10 RPS
- **Tempo Médio**: > 5000ms
- **Taxa de Erro**: > 20%
- **Crashes**: Aplicação parando

## 🔧 Otimizações Baseadas nos Testes

### 1. **Se Tempo de Resposta Alto**
- Otimizar queries do banco
- Implementar cache
- Aumentar recursos do servidor

### 2. **Se Taxa de Erro Alta**
- Verificar logs de erro
- Ajustar configurações de pool de conexões
- Implementar circuit breakers

### 3. **Se Throughput Baixo**
- Otimizar código da aplicação
- Aumentar número de threads do servidor
- Considerar load balancing

## 📝 Exemplo de Relatório

```
Teste de Carga - API AV2
Data: 2024-01-15
Duração: 5 minutos

RESULTADOS GERAIS:
- Total de Requisições: 600
- Requisições com Sucesso: 595 (99.2%)
- Requisições com Erro: 5 (0.8%)

MÉTRICAS DE PERFORMANCE:
- Throughput: 120.5 RPS
- Tempo Médio de Resposta: 245ms
- Tempo Médio de Resposta (95%): 890ms
- Tempo Médio de Resposta (99%): 1200ms

POR ENDPOINT:
Login (/auth/login):
- Throughput: 80.2 RPS
- Tempo Médio: 180ms
- Taxa de Erro: 0.5%

Produtos (/produtos):
- Throughput: 40.3 RPS
- Tempo Médio: 310ms
- Taxa de Erro: 1.1%

CONCLUSÃO:
A API demonstra boa performance sob carga, com tempo de resposta adequado e baixa taxa de erro.
```

## 🛠️ Comandos Úteis

### Executar Teste via Linha de Comando
```bash
# Executar teste sem interface gráfica
jmeter -n -t testes-carga.jmx -l resultados.jtl -e -o relatorio/

# Executar com propriedades customizadas
jmeter -n -t testes-carga.jmx -Jhost=localhost -Jport=8080 -l resultados.jtl
```

### Gerar Relatório HTML
```bash
# Gerar relatório a partir de arquivo de resultados
jmeter -g resultados.jtl -o relatorio-html/
```

## 📚 Recursos Adicionais

- [Documentação Oficial do JMeter](https://jmeter.apache.org/usermanual/index.html)
- [Melhores Práticas de Teste de Carga](https://jmeter.apache.org/usermanual/best-practices.html)
- [Plugins JMeter](https://jmeter-plugins.org/)

## 🔍 Troubleshooting

### Problemas Comuns

1. **Connection Refused**
   - Verificar se a API está rodando
   - Confirmar porta e host

2. **Timeout Errors**
   - Aumentar timeout no JMeter
   - Verificar performance da API

3. **Out of Memory**
   - Aumentar heap do JMeter
   - Reduzir número de threads

4. **Invalid JSON Response**
   - Verificar formato do payload
   - Confirmar Content-Type headers 