package tech.attornatus.pessoas.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tech.attornatus.pessoas.controller.dto.EnderecoRequest;
import tech.attornatus.pessoas.controller.dto.EnderecoResponse;
import tech.attornatus.pessoas.controller.dto.PessoaRequest;
import tech.attornatus.pessoas.controller.dto.PessoaResponse;
import tech.attornatus.pessoas.service.PessoaService;

import java.util.List;

@RestController
@RequestMapping("/pessoa")
public class PessoaController {

    private final PessoaService pessoaService;

    public PessoaController(PessoaService pessoaService) {
        this.pessoaService = pessoaService;
    }

    @PostMapping
    public ResponseEntity<PessoaResponse> cadastrarNovaPessoa(@RequestBody PessoaRequest pessoaRequest) {
        PessoaResponse pessoaResponse = pessoaService.cadastrarNovaPessoa(pessoaRequest);

        return ResponseEntity.ok(pessoaResponse);
    }

    @GetMapping
    public ResponseEntity<List<PessoaResponse>> listarTodasAsPessoas() {
        List<PessoaResponse> responseList = pessoaService.listarTodasAsPessoas();

        return ResponseEntity.ok(responseList);
    }

    @GetMapping("/{id}")
    public ResponseEntity<PessoaResponse> consultarPessoaPorId(@PathVariable("id") Long id) {
        PessoaResponse pessoaResponse = pessoaService.consultarPessoaPorId(id);

        return ResponseEntity.ok(pessoaResponse);
    }

    @PutMapping("/{idPessoa}/{idEndereco}")
    public ResponseEntity<PessoaResponse> editarCadastroDePessoa(@PathVariable("idPessoa") Long idPessoa
            , @PathVariable("idEndereco") Long idEndereco
            , @RequestBody PessoaRequest pessoaRequest) {

        PessoaResponse pessoaResponse = pessoaService.editarCadastroDePessoa(idPessoa, idEndereco, pessoaRequest);

        return ResponseEntity.ok(pessoaResponse);
    }

    @PostMapping("/endereco/{idPessoa}")
    public ResponseEntity<EnderecoResponse> cadastrarNovoEndereco(@PathVariable("idPessoa") Long idPessoa
            , @RequestBody EnderecoRequest enderecoRequest) {

        EnderecoResponse enderecoResponse = pessoaService.cadastrarNovoEndereco(idPessoa, enderecoRequest);

        return ResponseEntity.ok(enderecoResponse);

    }

}
