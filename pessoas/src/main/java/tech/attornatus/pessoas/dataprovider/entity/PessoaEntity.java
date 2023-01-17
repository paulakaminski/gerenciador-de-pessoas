package tech.attornatus.pessoas.dataprovider.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Entity
@Table(name = "pessoa")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PessoaEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id", nullable = false)
    private Long id;

    @Column
    private String nome;

    @Column
    private String dataNascimento;

    @OneToMany(fetch = FetchType.EAGER)
    @JoinColumn(name = "endereco_id")
    private List<EnderecoEntity> enderecoEntity;

    public PessoaEntity(String nome, String dataNascimento, List<EnderecoEntity> enderecoEntity) {
        this.nome = nome;
        this.dataNascimento = dataNascimento;
        this.enderecoEntity = enderecoEntity;
    }
}
