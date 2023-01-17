package tech.attornatus.pessoas.controller.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PessoaRequest {
    private String nome;
    private String dataNascimento;
    private String logradouro;
    private String cep;
    private String numero;
    private String cidade;
    private String enderecoPrincipal;
}
