package com.algaworks.algafood.api.controller;

import java.util.List;
import java.util.Optional;

import com.algaworks.algafood.api.assembler.CidadeInputDisassembler;
import com.algaworks.algafood.api.assembler.CidadeModelAssembler;
import com.algaworks.algafood.api.model.CidadeModel;
import com.algaworks.algafood.api.model.input.CidadeInput;
import com.algaworks.algafood.domain.exception.EstadoNaoEncontradoException;
import com.algaworks.algafood.domain.exception.NegocioException;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.algaworks.algafood.domain.exception.EntidadeEmUsoException;
import com.algaworks.algafood.domain.exception.EntidadeNaoEncontradaException;
import com.algaworks.algafood.domain.model.Cidade;
import com.algaworks.algafood.domain.repository.CidadeRepository;
import com.algaworks.algafood.domain.service.CadastroCidadeService;

import javax.validation.Valid;

@RestController
@RequestMapping(value = "/cidades")
public class CidadeController {

    @Autowired
    private CidadeRepository cidadeRepository;

    @Autowired
    private CadastroCidadeService cadastroCidade;

    @Autowired
    private CidadeModelAssembler cidadeModelAssembler;

    @Autowired
    private CidadeInputDisassembler cidadeInputDisassembler;

    @GetMapping
    public List<CidadeModel> listar() {
        List<Cidade> todasCidades = cidadeRepository.findAll();

        return cidadeModelAssembler.toCollectionModel(todasCidades);
    }

    @GetMapping("/{cidadeId}")
    public CidadeModel buscar(@PathVariable Long cidadeId) {
        Cidade cidade = cadastroCidade.buscarOuFalhar(cidadeId);

        return cidadeModelAssembler.toModel(cidade);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public CidadeModel adicionar(@RequestBody @Valid CidadeInput cidadeInput) {
        try {
            Cidade cidade = cidadeInputDisassembler.toDomainObject(cidadeInput);

            cidade = cadastroCidade.salvar(cidade);

            return cidadeModelAssembler.toModel(cidade);
        } catch (EstadoNaoEncontradoException e) {
            throw new NegocioException(e.getMessage(), e);
        }
    }

    @PutMapping("/{cidadeId}")
    public CidadeModel atualizar(@PathVariable Long cidadeId,
                                 @RequestBody @Valid CidadeInput cidadeInput) {
        try {
            Cidade cidadeAtual = cadastroCidade.buscarOuFalhar(cidadeId);

            cidadeInputDisassembler.copyToDomainObject(cidadeInput, cidadeAtual);

            cidadeAtual = cadastroCidade.salvar(cidadeAtual);

            return cidadeModelAssembler.toModel(cidadeAtual);
        } catch (EstadoNaoEncontradoException e) {
            throw new NegocioException(e.getMessage(), e);
        }
    }

    @DeleteMapping("/{cidadeId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void remover(@PathVariable Long cidadeId) {
        cadastroCidade.excluir(cidadeId);
    }

//    @Autowired
//    private CidadeRepository cidadeRepository;
//
//    @Autowired
//    private CadastroCidadeService cadastroCidade;
//
//    @GetMapping
//    public List<Cidade> listar() {
//        return cidadeRepository.findAll();
//    }
//
//    @GetMapping("/{cidadeId}")
//    public Cidade buscar(@PathVariable Long cidadeId) {
//        return cadastroCidade.buscarOuFalhar(cidadeId);
//    }
//
////	@PostMapping
////	public ResponseEntity<?> adicionar(@RequestBody Cidade cidade) {
////		try {
////			cidade = cadastroCidade.salvar(cidade);
////
////			return ResponseEntity.status(HttpStatus.CREATED)
////					.body(cidade);
////		} catch (EntidadeNaoEncontradaException e) {
////			return ResponseEntity.badRequest()
////					.body(e.getMessage());
////		}
////	}
//
//    @PostMapping
//    @ResponseStatus(HttpStatus.CREATED)
//    public Cidade adicionar(@RequestBody @Valid Cidade cidade) {
//        try {
//            return cadastroCidade.salvar(cidade);
//        } catch (EstadoNaoEncontradoException e) {
//            throw new NegocioException(e.getMessage(), e);
//        }
//    }
//
////	@PutMapping("/{cidadeId}")
////	public ResponseEntity<?> atualizar(@PathVariable Long cidadeId,
////			@RequestBody Cidade cidade) {
////		try {
////			Cidade cidadeAtual = cidadeRepository.findById(cidadeId).orElse(null);
////
////			if (cidadeAtual != null) {
////				BeanUtils.copyProperties(cidade, cidadeAtual, "id");
////
////				cidadeAtual = cadastroCidade.salvar(cidadeAtual);
////				return ResponseEntity.ok(cidadeAtual);
////			}
////
////			return ResponseEntity.notFound().build();
////
////		} catch (EntidadeNaoEncontradaException e) {
////			return ResponseEntity.badRequest()
////					.body(e.getMessage());
////		}
////	}
//
//    @PutMapping("/{cidadeId}")
//    public Cidade atualizar(@PathVariable Long cidadeId,
//                            @RequestBody @Valid Cidade cidade) {
//        try {
//            Cidade cidadeAtual = cadastroCidade.buscarOuFalhar(cidadeId);
//
//            BeanUtils.copyProperties(cidade, cidadeAtual, "id");
//
//            return cadastroCidade.salvar(cidadeAtual);
//        } catch (EstadoNaoEncontradoException e) {
//            throw new NegocioException(e.getMessage(), e);
//        }
//    }
//
//    @DeleteMapping("/{cidadeId}")
//    @ResponseStatus(HttpStatus.NO_CONTENT)
//    public void remover(@PathVariable Long cidadeId) {
//        cadastroCidade.excluir(cidadeId);
//    }

}