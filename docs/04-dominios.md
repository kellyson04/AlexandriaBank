# 04 - Domínios do Sistema

Este documento serve para separar as principais partes do sistema.

## Domínios principais

Neste primeiro momento, o sistema terá estes domínios:

- Cliente
- Conta
- Transação
- Cartão

## Cliente

Cliente representa a pessoa que vai usar o sistema.

Esse domínio é responsável por guardar a ideia de quem é o usuário dentro do sistema.

Responsabilidades:

- Representar o cliente.
- Permitir que um cliente crie uma conta.
- Evitar cadastro duplicado, por exemplo usando CPF.
- Permitir que o cliente acesse o sistema.

## Conta

Conta representa a conta bancária do cliente.

Esse domínio é responsável por controlar o dinheiro do cliente dentro do sistema.

Responsabilidades:

- Representar a conta de um cliente.
- Guardar o saldo.
- Permitir consultar o saldo.
- Estar ligada a um cliente.
- Ser usada nas movimentações de dinheiro.

## Transação

Transação representa uma movimentação de dinheiro.

Sempre que entrar ou sair dinheiro da conta, isso pode gerar uma transação.

Responsabilidades:

- Registrar movimentações da conta.
- Mostrar se o dinheiro entrou ou saiu.
- Guardar o histórico para o extrato.
- Representar operações como Pix.

## Cartão

Cartão representa o cartão ligado à conta do cliente.

Esse domínio é responsável por controlar a solicitação e o bloqueio do cartão.

Responsabilidades:

- Representar o cartão de uma conta.
- Permitir solicitar cartão.
- Permitir bloquear cartão.
- Guardar o status do cartão.

## Relação simples entre os domínios

```text
Cliente
↓
Conta
├── Transação
└── Cartão