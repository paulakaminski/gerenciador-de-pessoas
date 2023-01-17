package tech.attornatus.pessoas.dataprovider.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "endereco")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class EnderecoEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id", nullable = false)
    private Long id;

    @Column
    private String logradouro;

    @Column
    private String cep;

    @Column
    private String numero;

    @Column
    private String cidade;

    @Column
    private char enderecoPrincipal;

    public EnderecoEntity(String logradouro, String cep, String numero, String cidade, char  enderecoPrincipal) {
        this.logradouro = logradouro;
        this.cep = cep;
        this.numero = numero;
        this.cidade = cidade;
        this.enderecoPrincipal = enderecoPrincipal;
    }
}
