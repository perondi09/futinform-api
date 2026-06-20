# FutInform ⚽

API REST para acompanhamento de futebol, inspirada no SofaScore. Permite consultar tabelas de classificação e jogos das principais ligas do mundo, além de oferecer cadastro de usuários com escolha de time favorito.

## 📌 Sobre o projeto

O FutInform consome dados em tempo real da [football-data.org](https://www.football-data.org/) e os disponibiliza através de uma API própria, construída em Java com Spring Boot. O sistema também conta com autenticação via JWT, permitindo que usuários se cadastrem, escolham um time do coração e tenham acesso a um painel personalizado com a tabela e os jogos do seu time favorito.

## 🚀 Funcionalidades

- **Consulta pública de ligas**: tabela de classificação e jogos (passados e futuros) das principais ligas do mundo, sem necessidade de autenticação.
- **Consulta de times**: listagem de times por liga, listagem geral e busca por nome.
- **Cadastro e login de usuários**: autenticação via JWT.
- **Time favorito**: o usuário escolhe um time no momento do cadastro.
- **Painel personalizado (dashboard)**: usuário autenticado visualiza automaticamente a tabela e os jogos da liga do seu time favorito.
- **Sincronização com API externa**: os dados de ligas, times, jogos e tabelas são obtidos da football-data.org e persistidos no banco local.

## 🛠️ Tecnologias utilizadas

- **Java**
- **Spring Boot**
- **Spring Security + JWT** — autenticação e autorização stateless
- **Spring Data JPA (Hibernate)** — persistência de dados
- **PostgreSQL** — banco de dados relacional
- **Docker** — containerização do ambiente
- **Spring Scheduler** — agendamento de tarefas (sincronização de dados)
- **RestClient** — integração com a API externa de futebol
- **football-data.org** — fonte de dados de ligas, times, jogos e tabelas
- **Lombok** — redução de boilerplate
- **Maven** — gerenciamento de dependências

## 📂 Estrutura do projeto

```
futinform/
├── .idea/
├── .mvn/
├── src/
│   ├── main/
│   │   ├── java/
│   │   │   └── perondi.futinform/
│   │   │       ├── client/          → integração HTTP com a API externa
│   │   │       ├── config/          → configurações (Security, RestClient)
│   │   │       ├── controller/      → endpoints REST
│   │   │       ├── dto/             → objetos de transferência de dados
│   │   │       ├── entity/          → entidades JPA
│   │   │       ├── exception/       → tratamento global de erros
│   │   │       ├── repository/      → acesso a dados (Spring Data JPA)
│   │   │       ├── security/        → JWT (geração e filtro de autenticação)
│   │   │       ├── service/         → regras de negócio
│   │   │       └── FutinformApplication.java
│   │   └── resources/
│   └── test/
├── .gitattributes
├── .gitignore
├── mvnw / mvnw.cmd
└── pom.xml
```

## 🔑 Autenticação

A API utiliza **JWT (JSON Web Token)** para autenticação stateless. O fluxo é:

1. O usuário se cadastra (`/auth/register`) ou faz login (`/auth/login`).
2. A API retorna um token JWT.
3. O token deve ser enviado no header `Authorization` das requisições aos endpoints protegidos:

```
Authorization: Bearer {token}
```

## 📡 Endpoints

### Autenticação — `/auth`

| Método | Endpoint | Descrição | Autenticação |
|--------|----------|------------|---------------|
| `POST` | `/auth/register` | Cadastra um novo usuário e retorna o token JWT | Pública |
| `POST` | `/auth/login` | Autentica um usuário existente e retorna o token JWT | Pública |

**Exemplo de corpo da requisição (`/auth/register`):**
```json
{
  "fullName": "Guilherme Perondi",
  "email": "usuario@email.com",
  "password": "Senha1234",
  "phone": "+5511999999999",
  "favoriteTeamId": 122
}
```

---

### Ligas — `/leagues`

| Método | Endpoint | Descrição | Autenticação |
|--------|----------|------------|---------------|
| `GET` | `/leagues/{code}` | Retorna tabela e jogos de uma liga pelo código (ex: `PL`, `BSA`) | Pública |
| `GET` | `/leagues/premier-league` | Premier League (Inglaterra) | Pública |
| `GET` | `/leagues/la-liga` | La Liga (Espanha) | Pública |
| `GET` | `/leagues/bundesliga` | Bundesliga (Alemanha) | Pública |
| `GET` | `/leagues/serie-a` | Serie A (Itália) | Pública |
| `GET` | `/leagues/ligue-1` | Ligue 1 (França) | Pública |
| `GET` | `/leagues/champions-league` | Champions League (Europa) | Pública |
| `GET` | `/leagues/brasileirao` | Brasileirão Série A (Brasil) | Pública |
| `POST` | `/leagues/sync` | Sincroniza todas as ligas com a API externa | Pública |
| `POST` | `/leagues/sync/{code}` | Sincroniza uma liga específica pelo código | Pública |

Cada resposta de liga segue o formato do `LeagueResponseDTO`:
```json
{
  "leagueName": "...",
  "leagueCode": "...",
  "country": "...",
  "standings": [
    {
      "position": 1,
      "teamName": "...",
      "teamCrest": "...",
      "playedGames": 0,
      "points": 0,
      "won": 0,
      "draw": 0,
      "lost": 0,
      "goalsFor": 0,
      "goalsAgainst": 0,
      "goalDifference": 0
    }
  ],
  "upcomingMatches": [
    {
      "id": 0,
      "homeTeamName": "...",
      "awayTeamName": "...",
      "homeScore": null,
      "awayScore": null,
      "status": "SCHEDULED",
      "matchDate": "2026-01-01T16:00:00"
    }
  ],
  "pastMatches": [ /* mesmo formato de upcomingMatches */ ]
}
```

> **Códigos de liga suportados:** `PL`, `PD`, `BL1`, `SA`, `FL1`, `CL`, `BSA`

---

### Times — `/teams`

| Método | Endpoint | Descrição | Autenticação |
|--------|----------|------------|---------------|
| `GET` | `/teams` | Lista todos os times cadastrados | Pública |
| `GET` | `/teams/league/{leagueCode}` | Lista os times de uma liga específica | Pública |
| `GET` | `/teams/search?name={nome}` | Busca times pelo nome | Pública |

Resposta no formato do `TeamDTO`:
```json
{
  "id": 122,
  "name": "...",
  "shortName": "...",
  "tla": "...",
  "crestUrl": "...",
  "leagueName": "...",
  "leagueCode": "..."
}
```

---

### Jogos — `/matches`

| Método | Endpoint | Descrição | Autenticação |
|--------|----------|------------|---------------|
| `GET` | `/matches/league/{leagueId}` | Lista os jogos de uma liga pelo ID | Pública |

---

### Usuário — `/users`

| Método | Endpoint | Descrição | Autenticação |
|--------|----------|------------|---------------|
| `GET` | `/users/dashboard` | Retorna a tabela e os jogos da liga do time favorito do usuário autenticado | **JWT obrigatório** |

## ⚙️ Configuração e execução local

### Pré-requisitos
- Java 21+
- Maven
- Docker
- PostgreSQL (via Docker ou instalação local)
- Conta gratuita na [football-data.org](https://www.football-data.org/) para obter uma API Key

### Configuração

Configure o `application.properties` (ou variáveis de ambiente):

```properties
# Banco de dados
spring.datasource.url=jdbc:postgresql://localhost:5432/futinformdb
spring.datasource.username=postgres
spring.datasource.password=sua_senha

# API externa de futebol
football.api.url=https://api.football-data.org/v4
football.api.key=sua_api_key

# JWT
jwt.secret=sua_chave_secreta_com_pelo_menos_32_caracteres
jwt.expiration-ms=3600000
```

### Executando

```bash
mvn spring-boot:run
```

A API estará disponível em `http://localhost:8080`.

## 👤 Autor

Desenvolvido por **Guilherme Perondi**.
