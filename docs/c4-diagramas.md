# Diagramas de Arquitetura — Modelo C4

Este documento reúne os quatro diagramas C4 do sistema **qa-gym-library**, organizados do nível mais alto de abstração (contexto) até o nível de código.

---

## O que é o modelo C4?

O modelo C4 foi criado por Simon Brown para descrever a arquitetura de software em quatro níveis de abstração progressiva:

| Nível | Nome | Pergunta central | Público |
|-------|------|-----------------|---------|
| 1 | Contexto | O sistema existe em qual ecossistema? | Qualquer pessoa |
| 2 | Containers | Como o sistema está dividido em partes executáveis? | Técnicos e gestores |
| 3 | Componentes | O que existe dentro de cada container? | Desenvolvedores |
| 4 | Código | Como as classes se relacionam? | Desenvolvedores |

Cada nível é um zoom no anterior. Você não precisa criar todos os quatro — escolha o que comunica melhor para o seu público.

---

## Nível 1 — Diagrama de Contexto

**Arquivo:** [`c4-nivel1-contexto.puml`](c4-nivel1-contexto.puml)

Mostra o sistema como uma caixa preta e suas relações com usuários e sistemas externos.

**Atores:**
- **Administrador** — gerencia usuários, papéis e configurações.
- **Bibliotecário** — cadastra livros, gerencia empréstimos e devoluções.
- **Professor** — realiza empréstimos com prazo estendido e acessa relatórios.
- **Aluno** — consulta o acervo e realiza empréstimos.

**Sistemas externos:**
- **SUAP** — provedor de identidade do IFRN; autenticação SSO via OAuth2.
- **Abacate Pay** — gateway de pagamento para multas por atraso e doações.
- **Google Gmail API** — envio de e-mails de confirmação, aviso de vencimento e notificação de atraso.

---

## Nível 2 — Diagrama de Containers

**Arquivo:** [`c4-nivel2-containers.puml`](c4-nivel2-containers.puml)

Mostra como o sistema está dividido em processos ou aplicações independentes (containers), suas tecnologias e como se comunicam.

> Este diagrama representa a **arquitetura projetada** — uma evolução natural do estado atual (aplicação Java simples) para uma arquitetura distribuída e persistente.

**Containers:**
| Container | Tecnologia | Responsabilidade |
|-----------|-----------|-----------------|
| Aplicação Web | HTML / CSS / JavaScript | Interface para todos os perfis de usuário |
| API REST | Java 21 · Spring Boot | Regras de negócio e orquestração |
| Banco de Dados | PostgreSQL | Persistência de livros, usuários e empréstimos |
| Worker de Notificações | Java 21 · Spring Scheduler | Verifica atrasos e dispara e-mails automáticos |

**Decisões arquiteturais que este diagrama permite discutir:**
- Por que separar a API do frontend?
- Por que um worker independente para notificações?
- Quando usar PostgreSQL em vez de persistência em memória?
- Como o SSO simplifica o gerenciamento de identidade?

---

## Nível 3 — Diagrama de Componentes

**Arquivo:** [`c4-nivel3-componentes.puml`](c4-nivel3-componentes.puml)

Detalha os componentes internos do container **API REST** e suas responsabilidades.

**Grupos de componentes:**

| Grupo | Componentes | Responsabilidade |
|-------|------------|-----------------|
| Controllers | BookController, UserController, LoanController, ReportController | Recebem requisições HTTP e delegam para os serviços |
| Services | LibraryService, LoanService, ReportService | Implementam as regras de negócio |
| Repositories | BookRepository, UserRepository, LoanRepository | Abstraem o acesso ao banco de dados |
| Adapters | SuapAuthAdapter, AbacatePayAdapter, GmailNotificationAdapter | Encapsulam a comunicação com sistemas externos |
| Utils | BookValidator, TextNormalizer | Funções auxiliares reutilizáveis |

**Padrões arquiteturais visíveis neste diagrama:**
- **Camadas (Layered Architecture):** Controller → Service → Repository.
- **Adapter Pattern:** integrações externas encapsuladas em adaptadores.
- **Dependency Inversion:** serviços dependem de abstrações, não de implementações concretas.

---

## Nível 4 — Diagrama de Código

**Arquivo:** [`c4-nivel4-codigo.puml`](c4-nivel4-codigo.puml)

Diagrama de classes do modelo de domínio e dos serviços principais, refletindo o código atual do projeto.

**Pacotes:**
- `model` — entidades do domínio: `Book`, `User`, `Loan`.
- `service` — serviços de negócio: `LibraryService`, `LoanService`, `ReportService`.
- `util` — utilitários: `BookValidator`, `TextNormalizer`.
- `exception` — exceções de domínio: `BookAlreadyExistsException`, `LoanNotAllowedException`.

**Relacionamentos relevantes:**
- `Loan` associa um `Book` a um `User`.
- `LibraryService` agrega coleções de `Book` e `User`.
- `LoanService` agrega coleções de `Loan`.
- Exceções herdam de `RuntimeException` (unchecked).

---

## Como visualizar os diagramas

### Opção 1 — VS Code
Instale a extensão **PlantUML** (jebbs.plantuml) e pressione `Alt+D` com o arquivo `.puml` aberto.

### Opção 2 — PlantUML Online
Acesse [plantuml.com/plantuml/uml](https://www.plantuml.com/plantuml/uml) e cole o conteúdo do arquivo.

### Opção 3 — CLI
```bash
java -jar plantuml.jar docs/c4-nivel1-contexto.puml
```

---

## Conexão com o código atual

O nível 4 reflete diretamente o código em `src/main/java/br/edu/ifrn/qagym/`. Os níveis 1, 2 e 3 descrevem a arquitetura projetada para quando o sistema evoluir além do escopo didático atual.

Essa distinção — entre o que **é** e o que **poderia ser** — é intencionalmente explorada na disciplina como oportunidade de discutir decisões arquiteturais, trade-offs e evolução incremental de software.
