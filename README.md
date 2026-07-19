# FutInform API

![Java](https://img.shields.io/badge/Java-21-orange?style=for-the-badge&logo=java)
![Spring Boot](https://img.shields.io/badge/Spring_Boot-4-6DB33F?style=for-the-badge&logo=spring)
![PostgreSQL](https://img.shields.io/badge/PostgreSQL-316192?style=for-the-badge&logo=postgresql)
![Docker](https://img.shields.io/badge/Docker-2496ED?style=for-the-badge&logo=docker)

A **FutInform API** é um sistema back-end desenvolvido em Java com Spring Boot para o gerenciamento de usuários apaixonados por futebol e acompanhamento de dados do Campeonato Brasileiro. A aplicação permite cadastrar usuários, vincular inteligentemente o time do coração e consultar informações em tempo real sobre tabelas e partidas consumindo uma API externa.

## 🚀 Tecnologias Utilizadas

*   **Java 21**
*   **Spring Boot 4** (Web, Data JPA, Validation)
*   **Spring RestClient** (Consumo de API externa)
*   **PostgreSQL** (Banco de dados relacional)
*   **Docker & Docker Compose** (Infraestrutura do banco)
*   **Lombok** (Redução de boilerplate)
*   **Padrão DTO** (Com separação para requisições e respostas)
*   **Cache** (Otimização de requisições externas com `@Cacheable`)

## 🏗️ Arquitetura e Modelagem

O projeto segue a arquitetura em camadas (Controllers, Services, Repositories e DTOs) e adota boas práticas RESTful. 
Todas as chaves primárias de usuários no sistema utilizam **UUID** para maior segurança e escalabilidade.

### Lógica de Negócios e Integração
A regra de negócios para o cadastro obedece ao seguinte fluxo:
1.  **Busca Inteligente**: O usuário informa o nome comum ou abreviado do time no payload (ex: "são paulo" ou "vasco").
2.  **Consulta Externa**: O sistema faz uma varredura (ignorando maiúsculas e minúsculas) na lista de times da API externa do Brasileirão usando a função `contains`.
3.  **Vinculação**: Ao encontrar a correspondência (ex: "São Paulo FC" ou "CR Vasco da Gama"), o ID oficial do time é extraído e salvo no banco de dados relacional atrelado ao usuário.

## ⚙️ Pré-requisitos

Para rodar o projeto localmente, você precisará de:
*   [JDK 21+](https://jdk.java.net/) (ou equivalente configurado no seu ambiente)
*   [Docker e Docker Compose](https://www.docker.com/) instalados
*   Maven (ou utilizar a IDE de sua preferência, como IntelliJ IDEA)

## 🛠️ Como Executar o Projeto

**1. Clone o repositório:**
```bash
git clone [https://github.com/seu-usuario/futinform.git](https://github.com/seu-usuario/futinform.git)
cd futinform

```

**2. Configure o Banco de Dados (Docker):**
O projeto utiliza um arquivo `.env` para carregar as variáveis do banco de dados. Acesse a pasta `infra/` e suba o contêiner do PostgreSQL na porta `5432` (mapeada no `.env`):

```bash
cd infra
docker-compose up -d

```

**3. Rode a aplicação:**
Volte para a raiz do projeto e execute a classe principal `FutinformApplication.java` na sua IDE (lembre-se de ativar o plugin EnvFile se usar IntelliJ) ou via Maven:

```bash
./mvnw spring-boot:run

```

A API estará disponível em: `http://localhost:8080`

## 🔗 Principais Endpoints

Abaixo estão os endpoints disponíveis. Note que as requisições de atualização (PATCH) suportam **atualizações parciais** de dados.

### Usuários (`/api/users`)

* `POST /api/users` - Cadastra um novo usuário (nome, email, nome do time).
* `GET /api/users` - Lista todos os usuários cadastrados.
* `GET /api/users/{id}` - Busca um usuário específico por UUID.
* `PATCH /api/users/{id}` - Atualiza parcialmente os dados do usuário (ex: trocar apenas o time do coração).
* `DELETE /api/users/{id}` - Remove o usuário do sistema.

### Brasileirão (`/api/brasileirao`)

* `GET /api/brasileirao/standings` - Retorna a tabela de classificação atualizada.
* `GET /api/brasileirao/matches` - Retorna os próximos jogos agendados.
* `GET /api/brasileirao/teams` - Retorna todos os times do campeonato.
* `GET /api/brasileirao/teams/{id}` - Busca os detalhes de um time específico pelo ID oficial.

## 👨‍💻 Autor

**Guilherme Perondi**

* [LinkedIn](https://www.google.com/search?q=https://www.linkedin.com/in/guilherme-perondi)
* [GitHub](https://www.google.com/search?q=https://github.com/perondi09)
