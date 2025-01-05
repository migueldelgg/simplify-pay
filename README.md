# SimplifyPay: PicPay Simplificado  

![Build Status](https://img.shields.io/badge/build-passing-brightgreen)  
![Version](https://img.shields.io/badge/version-1.0.0-blue)  

---

## Descrição  
**SimplifyPay** é uma API desenvolvida como solução para o **Desafio Técnico da PicPay**.  
O objetivo é implementar funcionalidades essenciais de um sistema de pagamentos entre usuários, com foco em desempenho, escalabilidade e boas práticas de desenvolvimento.  

Atualmente, a API conta com os seguintes recursos:  
- **Criação de Usuários**: Geração de usuários com dois tipos de perfis:  
  - `common` (com CPF).  
  - `merchant` (com CNPJ).  
  - A abordagem utiliza o **Design Pattern Strategy**, permitindo a extensão para futuras features.  
- **Transferência de Dinheiro**: Realização de transferências entre usuários com bloqueio transacional utilizando `FOR UPDATE`, garantindo a integridade dos dados em operações concorrentes.  
- **Testes Automatizados**: Cobertura abrangente de casos de uso com testes de integração.

### Em construção...
---
