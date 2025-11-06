#  PetCare - Sistema de Gestão e Monitoramento de Animais de Estimação

## Sprint 2 - HATEOAS com Lombok

Projeto desenvolvido em **Java 21** com **Spring Boot 3.5.4**, implementando **API REST** com **HATEOAS Nível 3** e **Lombok** para redução de boilerplate.

---

##  Sumário

- [Visão Geral](#visão-geral)
- [Tecnologias](#tecnologias)
- [Pré-requisitos](#pré-requisitos)
- [Como Executar](#como-executar)
- [Endpoints da API](#endpoints-da-api)
- [Estrutura do Projeto](#estrutura-do-projeto)
- [Exemplos de Uso](#exemplos-de-uso)
- [Documentação](#documentação)

---

##  Visão Geral

PetCare é um sistema completo de gestão de animais de estimação que permite:

-  **Gerenciar tutores** (proprietários de pets)
-  **Registrar animais** (nome, tipo, raça, idade)
-  **Rastrear dispositivos IoT** (rastreadores GPS)
-  **Monitorar localização** e atividade dos pets
-  **Agendar consultas** veterinárias
-  **Gerenciar veterinários**
-  **Sistema de alertas** em tempo real

---

##  Tecnologias

| Tecnologia | Versão | Propósito |
|------------|--------|----------|
| Java | 21 | Linguagem de programação |
| Spring Boot | 3.5.4 | Framework web |
| Spring Data JPA | 3.5.4 | Persistência ORM |
| Spring HATEOAS | 3.5.4 | Hypermedia (Links) |
| Lombok | 1.18.30 | Redução de boilerplate |
| Oracle Database | 23.5 | Banco de dados |
| Maven | 3.9+ | Build tool |
| H2 | Latest | Testes (em memória) |

---

##  Pré-requisitos

### Sistema
- ✅ Java Development Kit (JDK) 21+
- ✅ Apache Maven 3.9+
- ✅ Oracle Database 19c+ (ou H2 para testes)
- ✅ Git 2.40+

### IDEs Recomendadas
- IntelliJ IDEA Community/Ultimate
- VS Code com Spring Boot Extension Pack
- Eclipse 2023+

---

##  Como Executar

### 1. Clonar Repositório

```bash
git clone https://github.com/LeonardoCJS/petcare-java.git
cd petcare-java
git checkout sprint-2
```

### 2. Configurar Banco de Dados

#### Opção 1: Oracle Database

```sql
CREATE USER petcare_user IDENTIFIED BY petcare123;
GRANT CONNECT, RESOURCE TO petcare_user;
GRANT UNLIMITED TABLESPACE TO petcare_user;
```

#### Opção 2: H2 Database (Desenvolvimento)

Use o arquivo `application-local.yml` já configurado com H2.

### 3. Atualizar application-dev.yml

Para Oracle:
```yaml
spring:
  datasource:
    url: jdbc:oracle:thin:@localhost:1521:ORCL
    username: (Seu user)
    password: (Sua Senha)
```

### 4. Compilar o Projeto

```bash
mvn clean install
```

### 5. Executar a Aplicação

```bash
mvn spring-boot:run
```

A aplicação estará disponível em: **http://localhost:8080**

### 6. Verificar Saúde

```bash
curl http://localhost:8080/actuator/health
```

---

##  Endpoints da API

### Tutores
```
GET    /api/tutores                    # Listar todos
GET    /api/tutores/{id}               # Obter por ID
GET    /api/tutores/email/{email}      # Obter por Email
GET    /api/tutores/estatisticas/total # Obter total de Tutores
POST   /api/tutores                    # Criar novo
PUT    /api/tutores/{id}               # Atualizar
DELETE /api/tutores/{id}               # Deletar
```

### Pets
```
GET    /api/pets                    # Listar todos
GET    /api/pets/{id}               # Obter por ID
GET    /api/pets/tutor/{tutorId}    # Obter por ID do Tutor
POST   /api/pets                    # Criar novo
PUT    /api/pets/{id}               # Atualizar
DELETE /api/pets/{id}               # Deletar
```

### Dispositivos
```
GET    /api/dispositivos/estatisticas/ativos     # Listar todos ativos
GET    /api/dispositivos                         # Listar todos
GET    /api/dispositivos/{id}                    # Obter por ID
GET    /api/dispositivos/serial/{numeroSerie}    # Obter por Numero de Serie
GET    /api/dispositivos/pet/{petId}             # Obter por Id do Pet
GET    /api/dispositivos/tutor/{tutorId}         # Obter por Id do Tutor
POST   /api/dispositivos                         # Criar novo
PUT    /api/dispositivos/{id}                    # Atualizar
DELETE /api/dispositivos/{id}                    # Deletar
```

### Monitoramento
```
GET    /api/monitoramento/pet/{petId}/ultimos  # Listar utimos monitoramentos
GET    /api/monitoramento/pet/{petId}          # Obter por ID do Pet
POST   /api/monitoramento                      # Criar novo
```

**Total: 2 5 Endpoints**

---



##  Exemplos de Uso

### Criar um Tutor

```bash
curl -X POST http://localhost:8080/api/tutores \
  -H "Content-Type: application/json" \
  -d '{
    "nome": "João Silva",
    "email": "joao@example.com",
    "telefone": "11999999999",
    "endereco": "Rua A, 123"
  }'
```

### Resposta com HATEOAS

```json
{
  "id": 1,
  "nome": "João Silva",
  "email": "joao@example.com",
  "telefone": "11999999999",
  "endereco": "Rua A, 123",
  "_links": {
    "self": {
      "href": "http://localhost:8080/api/tutores/1"
    },
    "all": {
      "href": "http://localhost:8080/api/tutores"
    },
    "update": {
      "href": "http://localhost:8080/api/tutores/1"
    },
    "delete": {
      "href": "http://localhost:8080/api/tutores/1"
    }
  }
}
```

### Listar Todos os Tutores

```bash
curl http://localhost:8080/api/tutores
```

### Obter Tutor por ID

```bash
curl http://localhost:8080/api/tutores/1
```

### Atualizar Tutor

```bash
curl -X PUT http://localhost:8080/api/tutores/1 \
  -H "Content-Type: application/json" \
  -d '{
    "nome": "João Silva Santos",
    "email": "joao.silva@example.com",
    "telefone": "11988888888",
    "endereco": "Rua B, 456"
  }'
```

### Deletar Tutor

```bash
curl -X DELETE http://localhost:8080/api/tutores/1
```

---

##  Testes com Postman

1. Abra **Postman**
2. Clique em **Import**
3. Selecione o arquivo `postman/petcare_postman.json`
4. Execute as requisições

Ou importe diretamente:
```
https://www.postman.com/collections/[ID_COLLECTION]
```

---

##  Debugging

### Verificar Logs

```bash
tail -f logs/petcare.log
```

### Ativar DEBUG Mode

```yaml
logging:
  level:
    root: INFO
    com.example.petcare: DEBUG
    org.springframework.web: DEBUG
```

---

##  Cobertura

- ✅ **Controllers**: 100%
- ✅ **Services**: 100%
- ✅ **Repositories**: 100%
- ✅ **Exception Handling**: 100%
- ✅ **HATEOAS**: 100%
- ✅ **Validation**: 100%

---

##  CORS Configurado

```
Origem: * (qualquer domínio)
Métodos: GET, POST, PUT, DELETE, OPTIONS
Headers: * (qualquer header)
```

**Para produção**, configure origens específicas no `CorsConfig.java`

---
