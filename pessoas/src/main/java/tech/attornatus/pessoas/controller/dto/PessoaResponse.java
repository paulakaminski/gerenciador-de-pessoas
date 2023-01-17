package tech.attornatus.pessoas.controller.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PessoaResponse {
    private String nome;
    private String dataNascimento;
    private List<EnderecoResponse> enderecos;
}
