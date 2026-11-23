# Padrões de Codificação — ArenaHub

Regras que a equipe segue ao programar o backend (Java / Spring Boot) e o frontend (JavaScript / React). Elas se baseiam em **Clean Code** e nos princípios **SOLID**, e cada uma traz um exemplo do próprio projeto e a forma de verificá-la.

> Tarefa de origem: [S2][#22] Detalhar e aplicar boas práticas de codificação para garantir legibilidade.

## Resumo das regras

| # | Regra | Base |
|---|-------|------|
| 1 | Padrão de notação (nomenclatura e formatação) | Clean Code |
| 2 | Comentários e documentação | Clean Code |
| 3 | Uma responsabilidade por camada e por classe | SOLID — S, D |
| 4 | Nomes que revelam a intenção e métodos pequenos | Clean Code |
| 5 | Exceções específicas, tratadas em um só lugar | Clean Code |
| 6 | Validação na borda e uso de DTOs | SOLID — S |
| 7 | Sem duplicação e sem código morto | Clean Code (DRY) |
| 8 | Verificação automática antes de commitar | Qualidade |

---

## 1. Padrão de notação (nomenclatura e formatação)

**Idioma:** termos do domínio em português (`Quadra`, `Gestor`, `Reserva`); termos técnicos em inglês (`Service`, `Controller`, `handle`).

**Java (backend)**

| Elemento | Padrão | Exemplo |
|----------|--------|---------|
| Pacote | minúsculas | `com.arenahub.arenahub.service` |
| Classe / interface / enum | `PascalCase` | `QuadraService` |
| Método e variável | `camelCase`, método começa com verbo | `getQuadraById`, `horaAbertura` |
| Constante | `UPPER_SNAKE_CASE` | `SENHA_FORTE` |
| Repositório | `I` + entidade + `Repository` | `IQuadraRepository` |
| DTO | entidade + `Dto` (ou `RequestDto` / `ResponseDto`) | `LoginRequestDto` |
| Exceção | situação + `Exception` | `QuadraNaoEncontradaException` |
| Tabela e coluna no banco | `snake_case` | `hora_abertura` |

**JavaScript / React (frontend)**

| Elemento | Padrão | Exemplo |
|----------|--------|---------|
| Componente e página | `PascalCase`, arquivo com o mesmo nome | `AuthShell.jsx` |
| Hook | prefixo `use` | `useAuth` |
| Função e variável | `camelCase` | `handleSubmit`, `senhaVisivel` |
| Constante de módulo | `UPPER_SNAKE_CASE` | `EMAIL_VALIDO` |
| Classe CSS | `kebab-case` no estilo BEM | `field__input--error` |

**Formatação (Java e JavaScript):** indentação de 4 espaços, UTF-8, uma classe ou componente por arquivo, sem `import` com `*`.

## 2. Comentários e documentação

O código deve se explicar pelos nomes. Comentário existe para dizer **por que**, nunca **o que** o código faz.

* Comente decisões não óbvias, regras de negócio e limitações conhecidas.
* **Não** comente o que o código já diz, não deixe **código comentado** (o histórico está no Git) e não coloque o caminho do arquivo no topo.
* `TODO` só com a referência do card: `TODO(#34): descrição`.
* Javadoc/JSDoc só em métodos públicos cuja regra de negócio não é óbvia pelo nome.
* Documentação de uso, decisões e fluxo de trabalho ficam no `README.md` e em `Padrões Adotados/`, não espalhadas pelo código.

```java
// Ruim: repete o que o código diz
// gerar o token
public String generateToken(Authentication authentication) { ... }

// Bom: o nome já explica; nenhum comentário é necessário
public String generateToken(Authentication authentication) { ... }
```

## 3. Uma responsabilidade por camada e por classe

Cada camada faz uma única coisa, e as dependências chegam por **injeção no construtor** (`@RequiredArgsConstructor` com campos `private final`).

| Camada | Responsabilidade | Não deve |
|--------|------------------|----------|
| `controller` | receber a requisição, chamar o service e devolver `ResponseEntity` | conter regra de negócio nem acessar repositório |
| `service` | regras de negócio e orquestração | conhecer HTTP (`ResponseEntity`, status) |
| `database/repository` | acesso a dados (Spring Data JPA) | conter regra de negócio |
| `database/models` | entidades | ser devolvidas pela API |
| `handler` | traduzir exceção em resposta HTTP | conter regra de negócio |

No frontend: `pages` orquestram a tela, `components` apresentam, `api` fala com o servidor e `utils` guarda funções puras.

## 4. Nomes que revelam a intenção e métodos pequenos

* O nome diz o que a coisa é ou faz; evite abreviações (`e` só em `catch` e lambda curto).
* Cada método faz **uma** coisa. Como guia, até cerca de 20 linhas; se passar disso ou tiver `if` aninhado, extraia métodos.
* Prefira retorno antecipado a blocos aninhados.
* Sem números ou textos "mágicos" repetidos: use constante com nome.

```js
const SENHA_FORTE = /^(?=.*[a-z])(?=.*[A-Z])(?=.*\d)(?=.*[@$!%*?&]).+$/
```

## 5. Exceções específicas, tratadas em um só lugar

* Falhas de regra de negócio viram uma exceção própria, `<Situacao>Exception extends RuntimeException`, com a mensagem montada no construtor.
* O `service` **lança**; só o `GlobalExceptionHandler` **trata** e escolhe o status:

| Situação | Status |
|----------|--------|
| entrada inválida | 400 |
| credenciais inválidas | 401 |
| recurso não encontrado | 404 |
| conflito (já existe) | 409 |
| erro inesperado | 500, com mensagem genérica |

* Proibido: `throw new RuntimeException(...)`, `catch (Exception)` que engole o erro, `printStackTrace()` e devolver detalhes internos ao cliente.
* No frontend, trate o erro da API pelo **status** da resposta e mostre uma mensagem amigável.

## 6. Validação na borda e uso de DTOs

* Toda entrada da API é um **DTO** com Bean Validation (`@NotBlank`, `@Email`, `@Size`...) e o `@RequestBody` leva `@Valid`.
* **Nunca** devolva uma entidade JPA na resposta; converta para DTO.
* O frontend repete as regras no formulário só para dar retorno rápido ao usuário. O **backend é a fonte da verdade**.

## 7. Sem duplicação e sem código morto

* Lógica repetida três ou mais vezes vira método ou componente (ex.: `Field` e `AuthShell` no frontend).
* Remova `import`, variável, função, arquivo e CSS que ninguém usa.
* Se um trecho "pode servir depois", ele não entra: o Git guarda o histórico.

## 8. Verificação automática antes de commitar

Antes de abrir um Pull Request, estes comandos devem passar **sem erros**:

```bash
# backend (pasta backend/)
./mvnw test

# frontend (pasta frontend/)
npm run lint
npm run build
```

## Checklist de revisão de Pull Request

- [ ] Nomes seguem a regra 1 e não há comentário que repete o código (regra 2).
- [ ] Controller sem regra de negócio; service sem HTTP (regra 3).
- [ ] Métodos curtos e sem números ou textos mágicos (regra 4).
- [ ] Nenhum `RuntimeException` genérico nem `catch (Exception)` que engole erro (regra 5).
- [ ] `@Valid` presente e nenhuma entidade exposta (regra 6).
- [ ] Sem código duplicado, imports ou arquivos sem uso (regra 7).
- [ ] `./mvnw test`, `npm run lint` e `npm run build` passam (regra 8).
