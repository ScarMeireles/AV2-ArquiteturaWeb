# AV2 - API de Autenticação e Gerenciamento de Produtos

## 📋 Descrição

Este é um projeto Spring Boot que implementa uma API REST completa com autenticação JWT, gerenciamento de produtos e monitoramento com Prometheus e Grafana. O projeto foi desenvolvido a AV2 de Arquitetura Web.

## 🚀 Tecnologias Utilizadas

- **Java 21**
- **Spring Boot 3.5.3**
- **Spring Security** com JWT
- **Spring Data JPA**
- **H2 Database** (banco em memória)
- **Lombok** para redução de boilerplate
- **SpringDoc OpenAPI** para documentação da API
- **Spring Boot Actuator** para monitoramento
- **Micrometer** para métricas
- **Prometheus** para coleta de métricas
- **Grafana** para visualização
- **Docker** e **Docker Compose** para containerização
- **Maven** para gerenciamento de dependências

## 🏗️ Arquitetura do Projeto

O projeto segue uma arquitetura em camadas bem definida:

```
src/main/java/com/example/av2/
├── config/          # Configurações da aplicação
├── controller/      # Controladores REST
├── service/         # Lógica de negócio
├── repository/      # Acesso a dados
├── model/          # Entidades JPA
├── dto/            # Objetos de transferência de dados
└── Av2Application.java
```

## 📦 Funcionalidades

### 🔐 Autenticação e Autorização
- Registro de usuários
- Login com JWT
- Controle de acesso baseado em roles (USER/ADMIN)
- Filtro de autenticação JWT

### 🛍️ Gerenciamento de Produtos
- CRUD completo de produtos
- Busca por ID, categoria e nome
- Controle de estoque
- Timestamps automáticos (criação/atualização)

### 📊 Monitoramento
- Métricas do Spring Boot Actuator
- Integração com Prometheus
- Dashboard Grafana configurado
- Health checks detalhados

### 📚 Documentação
- Swagger UI integrado
- Documentação automática da API
- Endpoints bem documentados

## 🛠️ Configuração e Instalação

### Pré-requisitos
- Java 21 ou superior
- Maven 3.6+
- Docker e Docker Compose (opcional)

### Executando Localmente

1. **Clone o repositório**
   ```bash
   git clone <url-do-repositorio>
   cd av2
   ```

2. **Compile o projeto**
   ```bash
   mvn clean compile
   ```

3. **Execute a aplicação**
   ```bash
   mvn spring-boot:run
   ```

4. **Acesse a aplicação**
   - API: http://localhost:8080/api
   - Swagger UI: http://localhost:8080/api/swagger-ui.html
   - H2 Console: http://localhost:8080/api/h2-console
   - Actuator: http://localhost:8080/api/actuator

### Executando com Docker

1. **Compile o projeto**
   ```bash
   mvn clean package
   ```

2. **Execute com Docker Compose**
   ```bash
   docker-compose up -d
   ```

3. **Acesse os serviços**
   - API: http://localhost:8080/api
   - Prometheus: http://localhost:9090
   - Grafana: http://localhost:3000 (admin/admin)

## 📖 Endpoints da API

### Autenticação (`/auth`)
- `POST /auth/register` - Registrar novo usuário
- `POST /auth/login` - Fazer login

### Produtos (`/produtos`)
- `GET /produtos` - Listar todos os produtos
- `GET /produtos/{id}` - Buscar produto por ID
- `GET /produtos/categoria/{categoria}` - Buscar por categoria
- `GET /produtos/buscar?nome={nome}` - Buscar por nome
- `POST /produtos` - Criar produto (ADMIN)
- `PUT /produtos/{id}` - Atualizar produto (ADMIN)
- `DELETE /produtos/{id}` - Deletar produto (ADMIN)

### Monitoramento (`/actuator`)
- `GET /actuator/health` - Status da aplicação
- `GET /actuator/metrics` - Métricas da aplicação
- `GET /actuator/prometheus` - Métricas no formato Prometheus

## 🔧 Configurações

### Banco de Dados
- **Tipo**: H2 (em memória)
- **URL**: jdbc:h2:mem:testdb
- **Usuário**: sa
- **Senha**: password
- **Console**: http://localhost:8080/api/h2-console

### Segurança
- JWT com chave secreta configurada
- Roles: USER e ADMIN
- Endpoints protegidos por autenticação
- CORS configurado

### Monitoramento
- Prometheus scraping a cada 5 segundos
- Métricas customizadas disponíveis
- Grafana com dashboard pré-configurado

## 🧪 Testes

O projeto inclui testes básicos:

```bash
mvn test
```

## 📊 Dados Iniciais

A aplicação é inicializada com dados de exemplo:
- Usuários padrão (admin/admin, user/user)
- Produtos de exemplo em diferentes categorias

## 🔍 Logs e Debug

- Logs detalhados habilitados para DEBUG
- SQL queries logadas
- Logs de segurança disponíveis

## 📈 Métricas Disponíveis

- **JVM**: memória, threads, garbage collection
- **HTTP**: requests, response times, status codes
- **Database**: conexões, queries
- **Custom**: métricas específicas da aplicação

## 🚀 Deploy

### Produção
Para deploy em produção, recomenda-se:
1. Configurar um banco de dados persistente (PostgreSQL, MySQL)
2. Configurar variáveis de ambiente para JWT secret
3. Configurar logging apropriado
4. Configurar monitoramento de produção

### Docker
```bash
# Build da imagem
docker build -t av2-api .

# Execução
docker run -p 8080:8080 av2-api
```

## 🤝 Contribuição

1. Fork o projeto
2. Crie uma branch para sua feature (`git checkout -b feature/AmazingFeature`)
3. Commit suas mudanças (`git commit -m 'Add some AmazingFeature'`)
4. Push para a branch (`git push origin feature/AmazingFeature`)
5. Abra um Pull Request

## 📝 Licença

Este projeto está sob a licença MIT. Veja o arquivo `LICENSE` para mais detalhes.

## 👨‍💻 Autor

Desenvolvido como parte da avaliação de Arquitetura Web.

## 📞 Suporte

Para dúvidas ou problemas, abra uma issue no repositório do projeto.
