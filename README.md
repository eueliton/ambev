
# Ambev Order API

## Descrição
A **Ambev Order API** é uma aplicação desenvolvida em Java utilizando Spring Boot para gerenciamento de pedidos e produtos. A API fornece funcionalidades para:

- Criar novos pedidos.
- Consultar pedidos por ID.
- Filtrar pedidos por status.
- Listar todos os pedidos.
- Validar a integridade dos dados (como evitar duplicidades e validar informações dos produtos).

O sistema utiliza MongoDB como banco de dados e suporta internacionalização (i18n) para mensagens de erro e sucesso em diferentes idiomas.

---

## Tecnologias Utilizadas
- **Java 17**
- **Spring Boot 3.4.0**
  - Spring Data MongoDB
  - Spring Validation
  - Spring Cache (com Caffeine)
  - Springdoc OpenAPI para documentação
- **MongoDB**
- **Maven** para gerenciamento de dependências

---

## Pré-requisitos

Antes de iniciar o projeto, certifique-se de ter instalado:

1. **Java 17** ou superior.
2. **Maven** (versão 3.8 ou superior).
3. **MongoDB** em execução localmente ou remotamente.

---

## Configuração

### 1. Configuração do Banco de Dados

Certifique-se de configurar corretamente a string de conexão para o MongoDB no arquivo `application.properties`:

```properties
spring.data.mongodb.uri=mongodb+srv://<USUARIO>:<SENHA>@<CLUSTER>/<DATABASE>
```
Substitua `<USUARIO>`, `<SENHA>`, `<CLUSTER>` e `<DATABASE>` pelos valores adequados para o seu ambiente.

### 2. Dependências

As dependências estão listadas no arquivo `pom.xml`. Para instalar, execute o comando:

```bash
mvn clean install
```

---

## Executando a Aplicação

### Localmente

1. Compile o projeto:
   ```bash
   mvn clean package
   ```

2. Execute o JAR gerado:
   ```bash
   java -jar target/order-1.0.0.jar
   ```

3. A aplicação estará disponível em `http://localhost:8281`.

---
 