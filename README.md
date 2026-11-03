# Arena Hub

> Sistema web desenvolvido para facilitar o agendamento de horários em quadras de areia. Por meio da plataforma, o próprio usuário consulta a disponibilidade e cadastra suas aulas de forma automática, a qualquer hora e sem precisar entrar em contato com um representante da arena. Com isso, o sistema reduz a troca manual de mensagens, evita conflitos e erros de agendamento e dá mais autonomia ao cliente, ao mesmo tempo em que simplifica a rotina de organização da arena.

**Disciplina:** Engenharia de Software
**Instituição:** Universidade Federal de Lavras - UFLA
**Semestre:** 2026-2
**Status:** Sprint 0 — Preparação do Ambiente

**Equipe:**
* Felipe Colaço Emmendoerfer — Product Owner (PO)
* João Vitor Rezende Marciano — Desenvolvedor Full-Stack
* Paulo Sergio Mendes Taciano — Desenvolvedor Full-Stack

---

## 1. Contexto do Problema e Solução

### 1.1 Problema

Hoje, para marcar uma aula em uma quadra de areia, o cliente precisa entrar em contato com um representante da arena (por mensagem ou telefone) e esperar a resposta. Esse processo é lento, depende do horário de atendimento e gera trocas manuais de mensagens, o que favorece erros de agendamento e conflitos de horário. Para a arena, o controle manual toma tempo da equipe.

### 1.2 Solução

O Arena Hub é um sistema web em que o próprio usuário consulta a disponibilidade das quadras e cadastra suas aulas de forma automática, a qualquer hora, sem intermediários. O sistema valida os horários para evitar conflitos e centraliza a agenda da arena.

**Principais funcionalidades:**
* Login obrigatório para acessar qualquer funcionalidade
* Consulta de disponibilidade de horários das quadras
* Cadastro de aulas pelo próprio usuário, sem contato com a arena
* Prevenção de conflitos de horário

> A lista completa dos requisitos funcionais (RF) será publicada aqui ao final da Sprint 1, a partir do documento em [`docs/requisitos/`](docs/requisitos/).

---

## 2. Instruções para Uso

> Para quem **só quer usar** o sistema (não precisa ser desenvolvedor).

### Pré-requisitos
* **Java 21** — https://adoptium.net
* **Node.js <versão LTS>** (inclui o npm) — https://nodejs.org
* **Conexão com a internet** (o banco de dados é online, hospedado no Neon)
* Arquivo **`.env`** com as credenciais do banco, fornecido pela equipe
* Git (opcional, só se for clonar em vez de baixar o ZIP) — https://git-scm.com
* *Não é necessário instalar Maven nem PostgreSQL.*

### Passo a passo
1. **Baixe o sistema:** https://github.com/jvrezendem/arenaHUB → botão **Code** → **Download ZIP** → extraia em uma pasta.
2. **Coloque o arquivo `.env`** (recebido da equipe) dentro da pasta **`backend/`**, ao lado do `pom.xml`.
3. **Inicie o backend:** abra um terminal na pasta `backend/` e rode:
   - Linux/macOS:
     ```bash
     ./mvnw spring-boot:run
     ```
   - Windows:
     ```bash
     mvnw.cmd spring-boot:run
     ```
   (as dependências são baixadas automaticamente na primeira execução). A API ficará em `http://localhost:8080`.
4. **Inicie o frontend:** abra **outro** terminal na pasta `frontend/` e rode:
   ```bash
   npm install
   npm run dev
   ```
5. **Acesse no navegador** o endereço exibido no terminal do frontend (normalmente `http://localhost:5173`).

### Problemas comuns
* **`java` não é reconhecido:** instale o Java 21 e reabra o terminal.
* **`npm` não é reconhecido:** instale o Node.js e reabra o terminal.
* **Erro de conexão com o banco:** confira se o `.env` está na pasta `backend/` e se há internet.
* **Porta 8080 já em uso:** adicione `server.port=8081` ao `backend/src/main/resources/application.properties` e ajuste a URL da API no frontend.

---

## 3. Instruções para Devs

Siga os passos abaixo para preparar o ambiente e se tornar dev do projeto.

1. **Clone o projeto:**
   ```bash
   git clone https://github.com/jvrezendem/arenaHUB.git
   cd arenaHUB
   ```
   *Alternativa:* baixe o ZIP pelo botão **Code → Download ZIP** e extraia.
2. **Configure as variáveis de ambiente:** a partir da raiz do projeto, copie `backend/.env.example` para `backend/.env` e preencha com as credenciais do Neon (peça à equipe; **nunca suba o `.env` no GitHub**):
   ```bash
   cp backend/.env.example backend/.env
   ```
   ```
   DB_URL=jdbc:postgresql://<host>.neon.tech/<banco>?sslmode=require
   DB_USER=<usuario>
   DB_PASSWORD=<senha>
   ```
   O `backend/src/main/resources/application.properties` lê essas variáveis:
   ```properties
   spring.config.import=optional:file:.env[.properties]
   spring.datasource.url=${DB_URL}
   spring.datasource.username=${DB_USER}
   spring.datasource.password=${DB_PASSWORD}
   ```
3. **Instale as dependências:**
   ```bash
   cd backend && ./mvnw clean install -DskipTests
   cd ../frontend && npm install
   ```
4. **Execute o backend** (pasta `backend/`):
   ```bash
   ./mvnw spring-boot:run
   ```
5. **Execute o frontend** (pasta `frontend/`):
   ```bash
   npm run dev
   ```
6. **Acesse no navegador** o endereço exibido pelo frontend (ex.: `http://localhost:5173`) — você deverá ver o sistema funcionando.

**Rodar os testes (backend):**
```bash
cd backend && ./mvnw test
```

### Fluxo de trabalho (Git)

* **Branch principal:** `main` (sempre estável; não se faz commit direto nela).
* **Uma branch por tarefa:** `feature/nome-da-tarefa`, `fix/nome-do-bug` ou `docs/nome-do-documento`.
* **Commits pequenos e frequentes**, no padrão [Conventional Commits](https://www.conventionalcommits.org/pt-br/): `feat:`, `fix:`, `docs:`, `chore:`, `test:`, `refactor:`. Exemplo: `feat: adiciona validação de conflito de horário`.
* **Pull Request** para a `main`, com revisão de pelo menos um colega antes do merge.
* **Ao terminar a tarefa:** merge na `main` e atualização do cartão no Kanban.

---

## 4. Tecnologias

- **frontend:** JavaScript (ES2022), React
- **backend:** Java 21, Spring Boot 4.1.2, Maven 3.10.0 (via Maven Wrapper), Spring Web MVC 4.1.2, Spring Data JPA 4.1.2, Spring Security 4.1.2, Bean Validation (Spring Boot Starter Validation 4.1.2)
- **banco de dados:** PostgreSQL hospedado no [Neon](https://neon.tech)
- **testes:** JUnit (Spring Boot Test 4.1.2)
- **outras:**
  - Lombok — reduz código repetitivo (getters, setters, construtores) nas classes Java.
  - Driver JDBC do PostgreSQL — conexão do backend com o banco.
  - Git e GitHub — controle de versão, Kanban e releases.
  - Figma — prototipação das interfaces.
  - Astah UML  — diagramas de casos de uso, classes, sequência e implantação.
  - Visual Studio Code  — editor de código (IDE) usado no desenvolvimento do backend e do frontend.

---

## 5. Organização do Projeto

Este projeto está organizado nas pastas descritas abaixo:

### Estrutura de Pastas

```
arenahub/
├── backend/        projeto Spring Boot
├── frontend/       projeto React
├── docs/           documentação do projeto
├── .gitignore
└── README.md
```

*   **`backend/`**: API em Spring Boot.
    *   **`src/main/java/com/arenahub/arenahub/`**: código-fonte principal.
        *   **`controller/`**: recebem as requisições e devolvem as respostas (API REST).
        *   **`service/`**: regras de negócio (ex.: validação de conflito de horários).
        *   **`repository/`**: acesso ao banco de dados (Spring Data JPA).
        *   **`model/`**: entidades do sistema (ex.: Quadra, Aula, Usuário).
        *   **`dto/`**: objetos de transferência de dados (ex.: `AulaResponseDto`, `UsuarioRequestDto`).
    *   **`src/main/resources/application.properties`**: configurações (banco, porta etc.).
    *   **`src/test/java/`**: testes automatizados.
    *   **`.env.example`**: modelo das variáveis de ambiente (sem dados reais). O `.env` real fica nesta mesma pasta e não é versionado.
    *   **`pom.xml`**: dependências e configuração do Maven.
    *   **`mvnw` / `mvnw.cmd`**: Maven Wrapper (roda o projeto sem instalar o Maven).
*   **`frontend/`**: interface web em React.
    *   **`src/`**: componentes, páginas e serviços de acesso à API.
    *   **`public/`**: arquivos estáticos (imagens, `index.html`).
    *   **`package.json`**: dependências e scripts do npm.
*   **`docs/`**: documentação do projeto.
    *   **`requisitos/`**: documento de requisitos, diagramas (arquivos originais do Astah) e protótipo de interfaces.
    *   **`padroes-adotados/`**: padrões da equipe, como "Regras de Verificação e Análise de Requisitos".

---

## 6. Gestão do Projeto

* **Backlog e Kanban:** <a criar na Sprint 1>
* **Protótipo de interfaces (Figma):** <a criar na Sprint 1>
* **Releases:** veja a aba [Releases](https://github.com/jvrezendem/arenaHUB/releases) (baselines do projeto).

| Sprint | Foco | Situação |
|--------|------|----------|
| 0 | Preparação do ambiente | Em andamento |
| 1 | Especificação do produto (requisitos, casos de uso, protótipo) | Planejada |
| 2 | Projeto e implementação | Planejada |
| 3 | Implementação e testes | Planejada |
