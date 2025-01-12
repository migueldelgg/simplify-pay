# SimplifyPay:   

![Build Status](https://img.shields.io/badge/build-passing-brightgreen)  
![Version](https://img.shields.io/badge/version-1.0.0-blue)  

---

## Descrição  
**SimplifyPay** é uma API desenvolvida como solução para um **Desafio Técnico**.  
O objetivo é implementar funcionalidades essenciais de um sistema de pagamentos entre usuários, com foco em desempenho, escalabilidade e boas práticas de desenvolvimento.  

Atualmente, a API conta com os seguintes recursos:  
- **Criação de Usuários**: Geração de usuários com dois tipos de perfis:  
  - `common` (com CPF).  
  - `merchant` (com CNPJ).  
  - A abordagem utiliza o **Design Pattern Strategy**, permitindo a extensão para futuras features.  
- **Transferência de Dinheiro**: Realização de transferências entre usuários com bloqueio transacional utilizando `FOR UPDATE`, garantindo a integridade dos dados em operações concorrentes.  
- **Testes Automatizados**: Cobertura abrangente de casos de uso com testes de integração.

O projeto implementa dois endpoints, com validações completas para criação de usuários e respostas adequadas de sucesso ou erro.

---

## **Endpoint 1: Criar Usuário**

### **Método**: `POST`
### **URL**: `/v1/user`

### **Request Body**:
```json
{
  "name": "Miguel",
  "document": "604.414.240-07", // CPF cria usuário comum; CNPJ cria usuário merchant
  "email": "Miguel@gmail.com",
  "password": "123"
}
```

### Validações:
- **name**: obrigatório.
- **document**: obrigatório e único (não pode já existir no sistema).
- **email**: obrigatório e único (não pode já existir no sistema).
- **password**: obrigatório.

> **Nota**: No ambiente de desenvolvimento, as senhas não estão sendo criptografadas.

---

### Respostas

#### 1. Sucesso
**HTTP Status**: `201 Created`  
**Response Body**:
```json
{
  "data": {
    "id": 453,
    "name": "Miguel",
    "email": "Miguel@gmail.com",
    "document": "60441424007",
    "wallet_type": "COMMON"
  }
}
```

#### 2. Erros de validação
**HTTP Status**: `409 Conflict`  
**Response Body**:
```json
{
  "status_phrase": "Conflict",
  "message": "Validation error: message"
}
```

---

## **Endpoint 2: Transferir dinheiro**

### **Método**: `POST`
### **URL**: `/v1/transfer`

### **Request Body**:
```json
{
  "value": 10.00,
  "payer": 3,
  "payee": 1
}

```

### Validações:
- **value**: obrigatório.
- **payer**: obrigatório.
- **payee**: obrigatório.

> **Nota**: Mais validações em desenvolvimento.

---

### Respostas

#### 1. Sucesso
**HTTP Status**: `200 OK`  
**Response Body**:
```json
{
    "payer_wallet_id": "53a13924-729d-460c-8883-fa88a7ae58a7",
    "payee_wallet_id": "8d1319ea-19ec-43a5-83f9-38d13638d046",
    "value": 10.00
}
```

#### 2. Erros de validação

**HTTP Status**: `400 Bad Request`  
**Response Body**:
```json
{
  "status_phrase": "Bad request",
  "message": "Validation error: message"
}
```
