# Arena Hub

> Sistema web desenvolvido para facilitar o agendamento de horários em quadras de areia. Por meio da plataforma, o próprio usuário consulta a disponibilidade e cadastra suas aulas de forma automática, a qualquer hora e sem precisar entrar em contato com um representante da arena. Com isso, o sistema reduz a troca manual de mensagens, evita conflitos e erros de agendamento e dá mais autonomia ao cliente, ao mesmo tempo em que simplifica a rotina de organização da arena.

**Disciplina:** Engenharia de Software
**Instituição:** Universidade Federal de Lavras - UFLA
**Semestre:** 2026-2
**Equipe:**
* [Nome 1] — [papel]
* João Vitor Rezende Marciano — Desenvolvedor Full-Stack
* Paulo Sergio Mendes Taciano — Desenvolvedor Full-Stack

---

## 1. Contexto do Problema e Solução

### 1.1 Problema

Hoje, para marcar uma aula em uma quadra de areia, o cliente precisa entrar em contato com um representante da arena (por mensagem ou telefone) e esperar a resposta. Esse processo é lento, depende do horário de atendimento e gera trocas manuais de mensagens, o que favorece erros de agendamento e conflitos de horário. Para a arena, o controle manual toma tempo da equipe.

### 1.2 Solução

O Arena Hub é um sistema web em que o próprio usuário consulta a disponibilidade das quadras e cadastra suas aulas de forma automática, a qualquer hora, sem intermediários. O sistema valida os horários para evitar conflitos e centraliza a agenda da arena.

**Principais funcionalidades:**
* Consulta de disponibilidade de horários das quadras
* Cadastro de aulas pelo próprio usuário, sem contato com a arena
* Prevenção de conflitos de horário

---

## 2. Instruções para Uso

> Para quem **só quer usar** o sistema (não precisa ser desenvolvedor).

### Pré-requisitos
* **Java 21** instalado — https://adoptium.net
* **Conexão com a internet** (o banco de dados é online, hospedado no Neon)
* Arquivo **`.env`** com as credenciais do banco, fornecido pela equipe
* Git (opcional, só se for clonar em vez de baixar o ZIP) — https://git-scm.com
* *Não é necessário instalar Maven nem PostgreSQL.*

### Passo a passo
1. **Baixe o sistema:** (https://github.com/jvrezendem/arenaHUB) → botão **Code** → **Download ZIP** → extraia em uma pasta.
2. **Coloque o arquivo `.env`** (recebido da equipe) na raiz da pasta extraída, ao lado do `pom.xml`.
3. **Abra o terminal** dentro dessa pasta.
4. **Inicie o sistema** (as dependências são baixadas automaticamente na primeira execução):
   - Linux/macOS:
     ```bash
     ./mvnw spring-boot:run
     ```
   - Windows:
     ```bash
     mvnw.cmd spring-boot:run
     ```
5. **Acesse no navegador:** http://localhost:8080


### Problemas comuns
* **`java` não é reconhecido:** instale o Java 21 e reabra o terminal.
* **Erro de conexão com o banco:** confira se o arquivo `.env` está na raiz do projeto e se há internet.
* **Porta 8080 já em uso:** adicione `server.port=8081` ao `application.properties` e acesse a nova porta.

---

## 3. Instruções para Devs

Siga os passos abaixo para preparar o ambiente e se tornar dev do projeto.

1. **Clone o projeto:**
   ```bash
   git clone https://github.com/jvrezendem/arenaHUB
   cd arenahub
   ```
   *Alternativa:* baixe o ZIP pelo botão **Code → Download ZIP** e extraia.
2. **Instale as dependências:**
   ```bash
   ./mvnw clean install -DskipTests
   ```
3. **Configure as variáveis de ambiente:** copie `.env.example` para `.env` e preencha com as credenciais do Neon (peça à equipe, nunca suba o `.env` no GitHub):
   ```bash
   cp .env.example .env
   ```
   ```
   DB_URL=jdbc:postgresql://<host>.neon.tech/<banco>?sslmode=require
   DB_USER=<usuario>
   DB_PASSWORD=<senha>
   ```
   O `application.properties` lê essas variáveis:
   ```properties
   spring.config.import=optional:file:.env[.properties]
   spring.datasource.url=${DB_URL}
   spring.datasource.username=${DB_USER}
   spring.datasource.password=${DB_PASSWORD}
   ```
4. **Execute o projeto:** na pasta raiz (onde está o `pom.xml`), rode:
   ```bash
   ./mvnw spring-boot:run
   ```
5. **Acesse no navegador:** `http://localhost:8080` — você deverá ver o sistema funcionando.

**Rodar os testes:**
```bash
./mvnw test
```

**Fluxo de trabalho (Git):**
* Branch principal: `main`
* Crie uma branch por tarefa: `feature/nome-da-tarefa`
* Abra um Pull Request e peça revisão de pelo menos um colega antes do merge.

---

## 4. Tecnologias

- frontend: JavaScript, React
- backend: Java 21,  Spring 4.1.2 , Maven, Spring Data JPA, Spring Security, Spring Web
- banco de dados: PostgreSQL hospedado no [Neon](https://neon.tech)
- testes: JUnit (Spring Boot Test)

---

## 5. Organização do Projeto

Este projeto está organizado nas pastas descritas abaixo:

### Estrutura de Pastas

*Reflete o projeto Spring dentro de `backend/`
*

*   *src/main/java/com/arenahub/arenahub/*: Código-fonte principal da aplicação.
    *   *controller/*: Recebem as requisições e devolvem as respostas (telas/API).
    *   *service/*: Regras de negócio (ex.: validação de conflito de horários).
    *   *repository/*: Acesso ao banco de dados (Spring Data JPA).
    *   *model/*: Entidades do sistema (ex.: Quadra, Aula, Usuário).
    *   *dto/*: Objetos de transferencia de dados (ex: AulaResponseDto, UsuarioRequstDto)
        
*   *src/main/resources/*: Recursos da aplicação.
    *   *application.properties*: Configurações (banco, porta etc.).
    *   *templates/*: Páginas HTML (Thymeleaf), se aplicável.
    *   *static/*: Arquivos estáticos (CSS, JS, imagens).
*   *src/test/java/*: Testes automatizados.
*   *docs/*: Documentação do projeto (requisitos, diagramas, manuais).
*   *.env.example*: Modelo das variáveis de ambiente (sem dados reais).
*   *pom.xml*: Dependências e configuração do Maven.
*   *mvnw / mvnw.cmd*: Maven Wrapper (permite rodar o projeto sem instalar o Maven).
