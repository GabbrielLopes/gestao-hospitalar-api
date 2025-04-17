# Gestão Hospitalar API

Esta aplicação é uma API desenvolvida em **Java** utilizando o framework **Spring Boot**. Ela tem como objetivo gerenciar informações relacionadas a pacientes e profissionais de saúde em um ambiente hospitalar. A aplicação utiliza diversas tecnologias e práticas modernas, como **Lombok**, **JWT** para autenticação, e testes unitários com **JUnit** e **Mockito**.

---

## **Funcionalidades**

### **Pacientes**
- **Consultar todos os pacientes**: Retorna uma lista de pacientes cadastrados.
- **Consultar paciente por ID**: Retorna os dados de um paciente específico.
- **Cadastrar paciente**: Permite o cadastro de um novo paciente.
- **Editar paciente**: Atualiza os dados de um paciente existente.
- **Inativar paciente**: Marca um paciente como inativo.
- **Ativar paciente**: Marca um paciente como ativo.
- **Consultar pacientes com filtro**: Permite buscar pacientes por CPF, nome ou e-mail.

### **Profissionais de Saúde**
- **Consultar todos os profissionais**: Retorna uma lista de profissionais de saúde cadastrados.
- **Consultar profissional por ID**: Retorna os dados de um profissional específico.
- **Cadastrar profissional de saúde**: Permite o cadastro de um novo profissional.
- **Editar profissional de saúde**: Atualiza os dados de um profissional existente.
- **Inativar profissional de saúde**: Marca um profissional como inativo.
- **Ativar profissional de saúde**: Marca um profissional como ativo.
- **Consultar profissionais com filtro**: Permite buscar profissionais por CPF ou especialidade.

---

## **Tecnologias Utilizadas**
- **Java 17**
- **Spring Boot**
    - Spring Data JPA
    - Spring Security (JWT)
    - Spring Web
- **Lombok**: Reduz a verbosidade do código, gerando automaticamente getters, setters, builders, etc.
- **Maven**: Gerenciamento de dependências.
- **JUnit 5**: Framework para testes unitários.
- **Mockito**: Biblioteca para criação de mocks em testes unitários.

---

## **Autenticação**
A aplicação utiliza **JWT (JSON Web Token)** para autenticação e autorização. O token é gerado no login e deve ser enviado no cabeçalho `Authorization` em todas as requisições protegidas.

---

## **Principais Endpoints**

### **Pacientes**
| Método | Endpoint                      | Descrição                          |
|--------|-------------------------------|------------------------------------|
| GET    | `/vida-plus/pacientes`        | Lista todos os pacientes.          |
| GET    | `/vida-plus/pacientes/{id}`   | Consulta um paciente por ID.       |
| POST   | `/vida-plus/pacientes`        | Cadastra um novo paciente.         |
| PUT    | `/vida-plus/pacientes/{id}`   | Edita os dados de um paciente.     |
| POST   | `/vida-plus/pacientes/{id}`   | Ativa um paciente.                 |
| DELETE | `/vida-plus/pacientes/{id}`   | Inativa um paciente.               |
| GET    | `/vida-plus/pacientes/filtro` | Consulta pacientes com filtros.    |

### **Profissionais de Saúde**
| Método | Endpoint                                | Descrição                              |
|--------|-----------------------------------------|----------------------------------------|
| GET    | `/vida-plus/profissionais-saude`        | Lista todos os profissionais.          |
| GET    | `/vida-plus/profissionais-saude/{id}`   | Consulta um profissional por ID.       |
| POST   | `/vida-plus/profissionais-saude`        | Cadastra um novo profissional.         |
| PUT    | `/vida-plus/profissionais-saude/{id}`   | Edita os dados de um profissional.     |
| POST   | `/vida-plus/profissionais-saude/{id}`   | Ativa um profissional.                 |
| DELETE | `/vida-plus/profissionais-saude/{id}`   | Inativa um profissional.               |
| GET    | `/vida-plus/profissionais-saude/filtro` | Consulta profissionais com filtros.    |

### **Observações**
- Nos endpoints com path `/filtro`, é possível passar a queryParam `filtro` e assim filtrar por CPF, nome ou e-mail por apenas esse parâmetro.

---

## **Testes**

### **Testes Unitários  - Cobertura dos services 80% +**
A aplicação possui cobertura de testes unitários utilizando **JUnit 5** e **Mockito**. Os testes verificam o comportamento dos serviços e garantem a integridade das regras de negócio.

- **Mockito** é utilizado para criar mocks de dependências, como repositórios e serviços auxiliares.
- **JUnit** é utilizado para estruturar e executar os testes.

### **Exemplos de Testes**
- **Pacientes**:
    - Consultar pacientes.
    - Cadastrar paciente com dados válidos.
    - Cadastrar paciente com dados inválidos (ex.: CPF nulo).
    - Editar paciente.
    - Inativar/Ativar paciente.
- **Profissionais de Saúde**:
    - Consultar profissionais.
    - Cadastrar profissional com dados válidos.
    - Editar profissional.
    - Inativar/Ativar profissional.

---

## **Como Executar**

1. **Pré-requisitos**:
    - Java 17+
    - Maven
    - Banco de dados configurado - MySQL
    - Docker


2. **Clonar o repositório**:


3. **Configurar o banco de dados**:
   Atualize o arquivo `application.yml` com as credenciais do banco de dados. (caso alterado no docker compose)


4. **Executar a aplicação**:
   ```bash
   mvn spring-boot:run
   ```

5. **Executar os testes**:
   ```bash
   mvn test
   ```

---
