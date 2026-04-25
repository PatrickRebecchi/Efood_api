# 🍔 Efood API

API REST desenvolvida com Spring Boot para gerenciamento de usuários e autenticação utilizando JWT.

O projeto foi construído com foco em boas práticas de backend, incluindo segurança, validação de dados e organização em camadas.

---

## 🚀 Tecnologias

* Java 21
* Spring Boot
* Spring Security
* JWT (JSON Web Token)
* JPA / Hibernate
* PostgreSQL
* Maven
* Lombok

---

## 🔐 Funcionalidades

* Cadastro de usuários
* Autenticação com geração de token JWT
* Criptografia de senha com BCrypt
* Validação de dados com Bean Validation
* Controle de acesso baseado em roles
* Tratamento global de exceções

---

## 🏗️ Arquitetura

O projeto segue uma arquitetura em camadas:

* **Controller** → Responsável pelas requisições HTTP
* **Service** → Regras de negócio *(em evolução)*
* **Repository** → Acesso ao banco de dados
* **DTOs** → Transferência de dados
* **Security** → Configuração de autenticação e autorização

---

## ⚙️ Como rodar o projeto

### 1. Clone o repositório

```bash
git clone https://github.com/PatrickRebecchi/efood.git
```

### 2. Acesse a pasta

```bash
cd efood
```

### 3. Configure o banco de dados

No arquivo `application.properties`:

```properties
spring.datasource.url=jdbc:postgresql://localhost:5432/efood_api_bd
spring.datasource.username=postgres
spring.datasource.password=123456

spring.jpa.hibernate.ddl-auto=update
```

### 4. Execute o projeto

```bash
mvn spring-boot:run
```

---

## 🔑 Autenticação

A API utiliza JWT para autenticação.

### 🔸 Login

**POST** `/auth/login`

```json
{
  "email": "test@example.com",
  "senha": "123456"
}
```

### 🔸 Resposta

```json
{
  "token": "jwt_token_aqui",
  "id": 1,
  "nome": "Test User",
  "email": "test@example.com",
  "role": "CLIENTE"
}
```

---

## 📌 Como usar o token

Adicione no header das requisições:

```
Authorization: Bearer SEU_TOKEN
```

---

## 🧪 Testes

O projeto conta com testes de controller utilizando:

* MockMvc
* Mockito
* Spring Boot Test

---

## 📈 Melhorias futuras

* Implementação completa da camada Service
* Testes de integração
* Deploy da aplicação

---

## 🎯 Objetivo

Este projeto foi desenvolvido com o objetivo de consolidar conhecimentos em:

* Segurança com Spring Security
* Autenticação com JWT
* Boas práticas de desenvolvimento backend
* Estruturação de APIs REST

---

## 👨‍💻 Autor

Desenvolvido por Patrick
