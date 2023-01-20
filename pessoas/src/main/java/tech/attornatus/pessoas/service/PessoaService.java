package tech.attornatus.pessoas.service;

import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import tech.attornatus.pessoas.controller.dto.EnderecoRequest;
import tech.attornatus.pessoas.controller.dto.EnderecoResponse;
import tech.attornatus.pessoas.controller.dto.PessoaRequest;
import tech.attornatus.pessoas.controller.dto.PessoaResponse;
import tech.attornatus.pessoas.dataprovider.entity.EnderecoEntity;
import tech.attornatus.pessoas.dataprovider.entity.PessoaEntity;
import tech.attornatus.pessoas.exception.BadRequestException;
import tech.attornatus.pessoas.exception.NotFoundException;
import tech.attornatus.pessoas.exception.ServerSideException;
import tech.attornatus.pessoas.dataprovider.repository.EnderecoRepository;
import tech.attornatus.pessoas.dataprovider.repository.PessoaRepository;

import java.util.ArrayList;
import java.util.List;

@Service
public class PessoaService {

    private final PessoaRepository pessoaRepository;

    private final EnderecoRepository enderecoRepository;

    public PessoaService(PessoaRepository pessoaRepository, EnderecoRepository enderecoRepository) {
        this.pessoaRepository = pessoaRepository;
        this.enderecoRepository = enderecoRepository;
    }

    public PessoaResponse cadastrarNovaPessoa(PessoaRequest pessoaRequest) {

        EnderecoEntity enderecoEntity = enderecoRepository.save(
                new EnderecoEntity(pessoaRequest.getLogradouro()
                        , pessoaRequest.getCep()
                        , pessoaRequest.getNumero()
                        , pessoaRequest.getCidade()
                        , 'S')
        );

        List<EnderecoEntity> enderecoEntities = new ArrayList<>();
        enderecoEntities.add(enderecoEntity);

        List<EnderecoResponse> enderecoResponseList = new ArrayList<>();
        enderecoResponseList.add(new EnderecoResponse(
                enderecoEntity.getId()
                , enderecoEntity.getLogradouro()
                , enderecoEntity.getCep()
                , enderecoEntity.getNumero()
                , enderecoEntity.getCidade()
                , enderecoEntity.getEnderecoPrincipal())
        );

        PessoaEntity pessoaEntity = pessoaRepository.save(
                new PessoaEntity(pessoaRequest.getNome()
                        , pessoaRequest.getDataNascimento()
                        , enderecoEntities)
        );

        return new PessoaResponse(pessoaEntity.getNome()
                , pessoaEntity.getDataNascimento()
                , enderecoResponseList
        );

    }

    public List<PessoaResponse> listarTodasAsPessoas() {
        List<PessoaEntity> entityList = pessoaRepository.findAll();

        List<PessoaResponse> responseList = new ArrayList<>();

        for (PessoaEntity pessoaEntity : entityList) {
            List<EnderecoResponse> enderecoResponseList = criarResponseListDoEndereco(pessoaEntity);

            responseList.add(new PessoaResponse(pessoaEntity.getNome()
                    , pessoaEntity.getDataNascimento()
                    , enderecoResponseList)
            );
        }

        return  responseList;
    }

    public PessoaResponse consultarPessoaPorId(Long id) {
        PessoaEntity pessoaEntity = pessoaRepository.findById(id)
                .orElseThrow(()->new NotFoundException("Pessoa não econtrada com o id: " + id));

        List<EnderecoResponse> enderecoResponseList = criarResponseListDoEndereco(pessoaEntity);

        return new PessoaResponse(pessoaEntity.getNome()
                , pessoaEntity.getDataNascimento()
                , enderecoResponseList);
    }

    public PessoaResponse editarCadastroDePessoa(Long idPessoa, Long idEndereco, PessoaRequest pessoaRequest) {
        PessoaEntity pessoaEntity = pessoaRepository.findById(idPessoa)
                .orElseThrow(()->new NotFoundException("Pessoa não econtrada com o id: " + idPessoa));

        EnderecoEntity enderecoEntity = enderecoRepository.findById(idEndereco)
                .orElseThrow(()->new NotFoundException("Endereço não econtrado com o id: " + idEndereco));

        List<EnderecoEntity> enderecoEntities = pessoaEntity.getEnderecoEntity();

        if(enderecoEntities.contains(enderecoEntity)){

            if(pessoaRequest.getEnderecoPrincipal().equalsIgnoreCase("S")) {
                for (EnderecoEntity entity : enderecoEntities) {
                    entity.setEnderecoPrincipal('N');
                    enderecoRepository.save(entity);
                }
            }

            enderecoEntity.setLogradouro(pessoaRequest.getLogradouro());
            enderecoEntity.setCep(pessoaRequest.getCep());
            enderecoEntity.setNumero(pessoaRequest.getNumero());
            enderecoEntity.setCidade(pessoaRequest.getCidade());
            enderecoEntity.setEnderecoPrincipal(pessoaRequest.getEnderecoPrincipal().toUpperCase().charAt(0));

            enderecoRepository.save(enderecoEntity);

            pessoaEntity.setNome(pessoaRequest.getNome());
            pessoaEntity.setDataNascimento(pessoaRequest.getDataNascimento());
            pessoaEntity.setEnderecoEntity(enderecoEntities);
            pessoaRepository.save(pessoaEntity);

        } else {
            throw new NotFoundException("Endereço não econtrado com o id: " + idEndereco + " para a pessoa com id: " + idPessoa);
        }

        List<EnderecoResponse> enderecoResponseList = criarResponseListDoEndereco(pessoaEntity);

        return new PessoaResponse(pessoaEntity.getNome()
                , pessoaEntity.getDataNascimento()
                , enderecoResponseList);

    }

    public EnderecoResponse cadastrarNovoEndereco(Long idPessoa, EnderecoRequest enderecoRequest) {

        try {
            PessoaEntity pessoaEntity = pessoaRepository.findById(idPessoa)
                    .orElseThrow(()->new NotFoundException("Pessoa não econtrada com o id: " + idPessoa));

            if (!enderecoRequest.getEnderecoPrincipal().equalsIgnoreCase("S")
                    && !enderecoRequest.getEnderecoPrincipal().equalsIgnoreCase("N")) {
                throw new BadRequestException("Deve ser informado S (sim) ou N (não) para o campo enderecoPrincipal!");
            }

            EnderecoEntity enderecoEntity = enderecoRepository.save(
                    new EnderecoEntity(enderecoRequest.getLogradouro()
                            , enderecoRequest.getCep()
                            , enderecoRequest.getNumero()
                            , enderecoRequest.getCidade()
                            , enderecoRequest.getEnderecoPrincipal().toUpperCase().charAt(0))
            );

            List<EnderecoEntity> enderecoEntities = pessoaEntity.getEnderecoEntity();

            if(enderecoEntity.getEnderecoPrincipal() == 'S') {
                for (EnderecoEntity entity : enderecoEntities) {
                    entity.setEnderecoPrincipal('N');
                    enderecoRepository.save(entity);
                }
            }

            enderecoEntities.add(enderecoEntity);

            pessoaEntity.setEnderecoEntity(enderecoEntities);
            pessoaRepository.save(pessoaEntity);

            return new EnderecoResponse(enderecoEntity.getId()
                    , enderecoEntity.getLogradouro()
                    , enderecoEntity.getCep()
                    , enderecoEntity.getNumero()
                    , enderecoEntity.getCidade()
                    , enderecoEntity.getEnderecoPrincipal()
            );
        } catch (NotFoundException e) {
            throw e;
        } catch (Exception e) {
            throw new ServerSideException("Erro ao cadastrar endereço, mensagem localizada: " + e.getLocalizedMessage());
        }

    }

    private List<EnderecoResponse> criarResponseListDoEndereco(PessoaEntity pessoaEntity) {
        List<EnderecoEntity> enderecoEntities = pessoaEntity.getEnderecoEntity();
        List<EnderecoResponse> enderecoResponseList = new ArrayList<>();

        for (EnderecoEntity enderecoEntity : enderecoEntities) {
            EnderecoResponse enderecoResponse = new EnderecoResponse();
            BeanUtils.copyProperties(enderecoEntity, enderecoResponse);
            enderecoResponseList.add(enderecoResponse);
        }

        return enderecoResponseList;
    }

}
