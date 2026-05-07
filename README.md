# Desafio Técnico — API de Tabela Tarifária de Água

API REST para gerenciar e calcular tarifas de água baseada em categorias de consumidores e faixas de consumo parametrizáveis no banco de dados.

## Pré-requisitos
- Java 17 (ou superior)
- Maven 3.x
- PostgreSQL 16.x (ou superior)
- Docker (opcional, para rodar o banco de dados rapidamente)

## Instalação e Execução

### 1. Configurar Banco de Dados

Crie um banco de dados no PostgreSQL:
```sql
CREATE DATABASE postgres; -- ou use o nome que preferir
```

Ou use Docker para iniciar uma instância rápida:
```bash
docker run --name postgres-tarifaria -e POSTGRES_PASSWORD=123456 -e POSTGRES_DB=postgres -p 5435:5432 -d postgres
```

### 2. Ajustar as propriedades

Edite o arquivo `src/main/resources/application-dev.properties` se precisar:
```properties
spring.datasource.url=jdbc:postgresql://localhost:5435/postgres
spring.datasource.username=postgres
spring.datasource.password=123456
```

### 3. Compilar e executar o projeto

```bash
mvn clean install
mvn spring-boot:run
```

As migrações do Flyway serão executadas automaticamente na primeira inicialização.

## Funcionalidades da API

### Endpoints principais

| Método | URL | Descrição |
|---|---|---|
| `POST` | `/api/tabelas-tarifarias` | Criar uma nova tabela tarifária completa |
| `GET` | `/api/tabelas-tarifarias` | Listar todas as tabelas tarifárias cadastradas |
| `DELETE` | `/api/tabelas-tarifarias/{id}` | Desativar uma tabela tarifária |
| `POST` | `/api/calculos` | Calcular valor total a pagar para um consumo informado |

---

### 1. Criar Tabela Tarifária

**URL**: `POST /api/tabelas-tarifarias`

**Request Body**:
```json
{
  "nome": "Tarifa 2026",
  "categorias": [
    {
      "nome": "COMERCIAL",
      "faixas": [
        { "inicio": 0, "fim": 10, "valorUnitario": 1.50 },
        { "inicio": 11, "fim": 99999, "valorUnitario": 2.50 }
      ]
    },
    {
      "nome": "INDUSTRIAL",
      "faixas": [
        { "inicio": 0, "fim": 10, "valorUnitario": 1.00 },
        { "inicio": 11, "fim": 20, "valorUnitario": 2.00 },
        { "inicio": 21, "fim": 99999, "valorUnitario": 3.00 }
      ]
    },
    {
      "nome": "PARTICULAR",
      "faixas": [
        { "inicio": 0, "fim": 10, "valorUnitario": 2.00 },
        { "inicio": 11, "fim": 99999, "valorUnitario": 3.00 }
      ]
    },
    {
      "nome": "PÚBLICO",
      "faixas": [
        { "inicio": 0, "fim": 10, "valorUnitario": 3.50 },
        { "inicio": 11, "fim": 99999, "valorUnitario": 4.50 }
      ]
    }
  ]
}
```

**Response (201 Created)**:
```json
{
  "id": 1,
  "nome": "Tarifa 2026",
  "dataInicio": "2026-05-07",
  "dataFim": null,
  "ativo": true
}
```

---

### 2. Listar Tabelas Tarifárias

**URL**: `GET /api/tabelas-tarifarias`

**Response (200 OK)**:
```json
[
  {
    "id": 1,
    "nome": "Tarifa 2026",
    "dataInicio": "2026-05-07",
    "dataFim": null,
    "ativo": true,
    "categorias": [
      {
        "id": 1,
        "nome": "COMERCIAL",
        "faixas": [
          { "id": 1, "inicio": 0, "fim": 10, "valorUnitario": 1.50 },
          { "id": 2, "inicio": 11, "fim": 99999, "valorUnitario": 2.50 }
        ]
      }
    ]
  }
]
```

---

### 3. Desativar Tabela Tarifária

**URL**: `DELETE /api/tabelas-tarifarias/{id}`

**Response**: `204 No Content`

---

### 4. Calcular Valor a Pagar

**URL**: `POST /api/calculos`

**Request Body**:
```json
{
  "categoria": "INDUSTRIAL",
  "consumo": 18
}
```

**Response (200 OK)**:
```json
{
  "categoria": "INDUSTRIAL",
  "consumoTotal": 18,
  "valorTotal": 26.00,
  "detalhamento": [
    {
      "faixa": {
        "inicio": 0,
        "fim": 10
      },
      "m3Cobrados": 10,
      "valorUnitario": 1.00,
      "subtotal": 10.00
    },
    {
      "faixa": {
        "inicio": 11,
        "fim": 20
      },
      "m3Cobrados": 8,
      "valorUnitario": 2.00,
      "subtotal": 16.00
    }
  ]
}
```

## Testes Automatizados

Para rodar os testes:
```bash
mvn test
```

Os testes cobrem:
1. Cálculo progressivo correto por faixa (exemplo do enunciado)
2. Normalização da categoria PUBLICO/PÚBLICO

## Tecnologias Utilizadas
- Java 17
- Spring Boot 4.0.6
- Spring Data JPA
- PostgreSQL
- Flyway (migrações de banco)
- Lombok
- Maven
- JUnit 5 + Mockito

## Critérios de Aceite Implementados
- ✅ Cria tabela tarifária completa (todas categorias e faixas)
- ✅ Lista tabelas tarifárias
- ✅ Desativa tabela tarifária (não permite uso em cálculos futuros)
- ✅ Calcula valor progressivamente por faixas
- ✅ Aceita categorias com e sem acento (ex: PUBLICO → PÚBLICO)
- ✅ Valida faixas: início < fim, sem sobreposição, contínuas, iniciando em 0
- ✅ Todas categorias obrigatórias
- ✅ Alterações no banco refletem automaticamente no cálculo
- ✅ Tratamento de erros HTTP com mensagens claras

## Scripts de Banco de Dados
As migrações são gerenciadas pelo Flyway e estão em `src/main/resources/db/migration/`:
1. `V1__create_table_categoria.sql`
2. `V2___create_table_tarifario.sql`
3. `V3__faixa_consumo.sql`