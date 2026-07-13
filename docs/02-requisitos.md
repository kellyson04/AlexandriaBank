# Requisitos

## Atores

- Cliente
- Administrador

## Funcionalidades do Cliente

![img.png](img.png)


## Funcionalidades do Administrador

- Consultar clientes
- Bloquear contas
- Desbloquear contas
- Consultar movimentações
- Gerenciar cartões

## Regras de negócio

- Um CPF não pode possuir dois cadastros
- Um e-mail não pode estar vinculado a dois usuários
- O usuário não pode realizar Pix sem saldo suficiente
- O valor do Pix deve ser maior que zero
- Um cartão bloqueado não pode ser utilizado
- O usuário só pode consultar sua própria conta