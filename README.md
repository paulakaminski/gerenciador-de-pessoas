# gerenciador-de-pessoas
Sistema para gerenciamento de pessoas com Java e Spring Boot
Projeto de desenvolvimento de API para gerenciamento de pessoas, codificado utilizando o Spring Boot, juntamente com a linguagem Java, utilizando o banco de dados H2 e retornando os dados no formato JSON.

A aplicação foi construída utilizando a plataforma Postman, a qual pode ser utilizada para testes e simulações, conforme as funcionalidades abaixo:

### Criar uma pessoa:

Endpoint: /pessoa

Método: POST

Campos:
- nome;
- dataNascimento;
- logradouro;
- cep;
- numero;
- cidade.

OBS.: O Post de pessoa salvará informações na tabela de pessoas e tabela de endereços. O endereço informado será automaticamente salvo como endereço principal.

### Criar endereço para a pessoa:

Endpoint: /pessoa/endereco/{id}

Método: POST

Campos: 
- logradouro;
- cep;
- numero;
- cidade;
- enderecoPrincipal;

OBS.: Deverá ser informado no campo “enderecoPrincipal” se o novo endereço cadastrado é o endereço principal da pessoa, utilizando char S ou N.

### Editar cadastro da pessoa:

Endpoint: /pessoa/{idPessoa}/{idEndereco}

Método: PUT

Campos:
- nome;
- dataNascimento;
- logradouro;
- cep;
- numero;
- cidade;
- enderecoPrincipal.

OBS.: Edição das informações da pessoa, bem como do endereço, devendo ser informado o id do endereço da pessoa que será editado, sendo possível alterar também como endereço principal.

### Listar pessoas:
Endpoint: /pessoa

Método: GET

OBS.: Irá retornar a lista com todas as pessoas cadastradas.

### Consultar uma pessoa:
Endpoint: /pessoa/{id}

Método: GET

Obs.: Irá retornar os dados da pessoa com id informado, bem como sua lista de endereços.
