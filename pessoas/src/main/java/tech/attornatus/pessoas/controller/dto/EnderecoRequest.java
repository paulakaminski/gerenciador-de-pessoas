package tech.attornatus.pessoas.controller.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class EnderecoRequest {
    private String logradouro;
    private String cep;
    private String numero;
    private String cidade;
    private String enderecoPrincipal;
}
