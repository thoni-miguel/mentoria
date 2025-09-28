# Mentoria — Plataforma MVP

Aplicação backend em Java (Spring Boot) para um MVP de plataforma de mentoria médica. Fornece autenticação via Google (OIDC), persistência com JPA/MySQL e endpoints REST.

## Visão Geral
- Linguagem: Java 21
- Framework: Spring Boot 3 (Web, Security, OAuth2 Client, Data JPA, Validation)
- Banco de dados: MySQL 8+ (via JDBC e Hibernate)
- ORM: Hibernate (via Spring Data JPA)
- Segurança/Autenticação: OAuth2/OIDC com Google
- Build/Empacotamento: Maven (com Maven Wrapper `mvnw`)
- Logs/Dev: Spring Boot Devtools habilitado (opcional)

Endpoint principal disponível após login:
- GET `/me`: retorna informações do usuário autenticado (claims OIDC, email, etc.).

Entry point da aplicação: `com.mentoria.platform.MentoriaApplication` (método `main`).

## Requisitos
- Java 21 (JDK 21)
- Maven 3.9+ (ou utilizar o Maven Wrapper fornecido `./mvnw`)
- MySQL 8+ acessível (local ou remoto)
- Conta e credenciais do Google Cloud para OAuth2 (Client ID e Client Secret)

## Configuração
As configurações principais estão em `src/main/resources/application.yml` e `application.properties`.

Variáveis de ambiente suportadas:
- `DB_USER` (padrão: `root`)
- `DB_PASSWORD` (padrão: vazio)
- `GOOGLE_CLIENT_ID` (obrigatória)
- `GOOGLE_CLIENT_SECRET` (obrigatória)

Banco de dados (padrão):
- URL: `jdbc:mysql://localhost:3306/mentoria?createDatabaseIfNotExist=true&serverTimezone=UTC&characterEncoding=utf8`
- Driver: `com.mysql.cj.jdbc.Driver`
- DDL: `spring.jpa.hibernate.ddl-auto=update`

OAuth2/OIDC (Google):
- Provider issuer: `https://accounts.google.com`
- Scopes: `openid, email, profile`
- Observação: o login está restrito a emails `@gmail.com` verificados (ver `GoogleOidcConfig`).

Porta padrão: 8080 (se não for alterada).

## Como executar (desenvolvimento)
1. Configure as variáveis de ambiente necessárias (principalmente as do Google e do banco). Exemplo no Linux/macOS:
   - `export GOOGLE_CLIENT_ID=...`
   - `export GOOGLE_CLIENT_SECRET=...`
   - `export DB_USER=root`
   - `export DB_PASSWORD=...`
2. Certifique-se de que o MySQL está em execução e acessível conforme a URL configurada.
3. Rodar com Maven Wrapper:
   - `./mvnw spring-boot:run`

A aplicação iniciará em `http://localhost:8080`.

Para efetuar login, acesse `/login` e autentique-se com sua conta Google (restrição a `@gmail.com` verificado).

## Build e execução via JAR
- Gerar pacote:
  - `./mvnw clean package`
- Executar o JAR:
  - `java -jar target/mentoria-0.0.1-SNAPSHOT.jar`

Certifique-se das variáveis de ambiente estarem definidas no ambiente de execução.

## Scripts/Comandos úteis
- Iniciar em dev: `./mvnw spring-boot:run`
- Rodar testes: `./mvnw test`
- Limpar/compilar/empacotar: `./mvnw clean package`
- Ver logs em modo debug de SQL já estão configurados no `application.yml` (Hibernate SQL e Binders).

## Estrutura do Projeto
- `src/main/java/com/mentoria/platform/MentoriaApplication.java` — entry point Spring Boot
- `src/main/java/com/mentoria/platform/config/SecurityConfig.java` — configuração de segurança (HTTP, OAuth2 Login)
- `src/main/java/com/mentoria/platform/security/GoogleOidcConfig.java` — serviço de usuário OIDC customizado (restrições de email, upsert de usuário)
- `src/main/java/com/mentoria/platform/controller/MeController.java` — endpoint `/me`
- `src/main/java/com/mentoria/platform/user/User.java` — entidade de usuário
- `src/main/java/com/mentoria/platform/user/UserRepository.java` — repositório Spring Data JPA
- `src/main/resources/application.yml` — configurações de DB, JPA, OAuth2/Google e logging
- `src/test/java/com/mentoria/platform/MentoriaApplicationTests.java` — testes básicos
- `HELP.md` — referências adicionais do Spring Boot e Maven

## Testes
- Executar todos os testes:
  - `./mvnw test`

Você pode configurar perfis/ambiente de teste conforme necessário (por exemplo, banco em memória). Atualmente não há perfil dedicado de teste configurado — TODO: adicionar profile de teste (ex.: H2) para isolamento.

## Variáveis de Ambiente (Resumo)
- Banco de dados:
  - `DB_USER`, `DB_PASSWORD`
- OAuth2 (Google):
  - `GOOGLE_CLIENT_ID`, `GOOGLE_CLIENT_SECRET`

Outras propriedades podem ser ajustadas diretamente no `application.yml`.

## Notas de Segurança
- A autenticação permite apenas emails `@gmail.com` verificados.
- O usuário é criado/atualizado no login com as informações básicas (nome, foto, último login).
- TODO: Documentar políticas de autorização por papéis/perfis caso sejam adicionadas além de `ROLE_USER` padrão.

## Licença
- TODO: Definir licença do projeto em `LICENSE` e atualizar este README com o tipo de licença.

## Referências
- Spring Boot: https://spring.io/projects/spring-boot
- Documentação do Spring Security (OAuth2/OIDC): https://docs.spring.io/spring-security/reference/servlet/oauth2/index.html
- Documentação Maven: https://maven.apache.org/guides/

