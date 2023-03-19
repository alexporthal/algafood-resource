package com.algaworks.algafood.api.controller;

import com.algaworks.algafood.api.assembler.CozinhaInputDisassembler;
import com.algaworks.algafood.api.assembler.CozinhaModelAssembler;
import com.algaworks.algafood.api.model.CozinhaModel;
import com.algaworks.algafood.api.model.CozinhasXmlWrapper;
import com.algaworks.algafood.api.model.input.CozinhaInput;
import com.algaworks.algafood.core.security.CheckSecurity;
import com.algaworks.algafood.domain.exception.EntidadeEmUsoException;
import com.algaworks.algafood.domain.exception.EntidadeNaoEncontradaException;
import com.algaworks.algafood.domain.model.Cozinha;
import com.algaworks.algafood.domain.repository.CozinhaRepository;
import com.algaworks.algafood.domain.service.CadastroCozinhaService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

@Slf4j
//@ResponseBody já implementa @controller e @responsebody
//@Controller
//@ResponseBody (para definir o retorno dos metodos para resposta da requisição
@RestController
//@RequestMapping(value = "/cozinhas", produces = MediaType.APPLICATION_JSON_VALUE)
//@RequestMapping(value = "/cozinhas", produces = { MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE })
@RequestMapping("/cozinhas")
public class CozinhaController {

    @Autowired
    private CozinhaRepository cozinhaRepository;

    @Autowired
    private CadastroCozinhaService cadastroCozinha;

    @Autowired
    private CozinhaModelAssembler cozinhaModelAssembler;

    @Autowired
    private CozinhaInputDisassembler cozinhaInputDisassembler;

//    @PreAuthorize("isAuthenticated()")
    @CheckSecurity.Cozinhas.PodeConsultar
    @GetMapping
    public List<CozinhaModel> listar() {
        log.info("Consultando cozinhas...");

//        if (true){
//            throw new RuntimeException("teste");
//        }
        List<Cozinha> todasCozinhas = cozinhaRepository.findAll();

        return cozinhaModelAssembler.toCollectionModel(todasCozinhas);
    }

    @CheckSecurity.Cozinhas.PodeConsultar
    @GetMapping("/{cozinhaId}")
    public CozinhaModel buscar(@PathVariable Long cozinhaId) {
        Cozinha cozinha = cadastroCozinha.buscarOuFalhar(cozinhaId);

        return cozinhaModelAssembler.toModel(cozinha);
    }

//    @PreAuthorize("hasAuthority('EDITAR_COZINHAS')")
    @CheckSecurity.Cozinhas.PodeEditar
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public CozinhaModel adicionar(@RequestBody @Valid CozinhaInput cozinhaInput) {
        Cozinha cozinha = cozinhaInputDisassembler.toDomainObject(cozinhaInput);
        cozinha = cadastroCozinha.salvar(cozinha);

        return cozinhaModelAssembler.toModel(cozinha);
    }

    @CheckSecurity.Cozinhas.PodeEditar
    @PutMapping("/{cozinhaId}")
    public CozinhaModel atualizar(@PathVariable Long cozinhaId,
                                  @RequestBody @Valid CozinhaInput cozinhaInput) {
        Cozinha cozinhaAtual = cadastroCozinha.buscarOuFalhar(cozinhaId);
        cozinhaInputDisassembler.copyToDomainObject(cozinhaInput, cozinhaAtual);
        cozinhaAtual = cadastroCozinha.salvar(cozinhaAtual);

        return cozinhaModelAssembler.toModel(cozinhaAtual);
    }

    @CheckSecurity.Cozinhas.PodeEditar
    @DeleteMapping("/{cozinhaId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void remover(@PathVariable Long cozinhaId) {
        cadastroCozinha.excluir(cozinhaId);
    }


//    @Autowired
//    private CozinhaRepository cozinhaRepository;
//
//    @Autowired
//    private CadastroCozinhaService cadastroCozinha;
//
//    //    @GetMapping(produces = { MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE })
//    @GetMapping
//    public List<Cozinha> listar() {
////        return cozinhaRepository.listar();
//        return cozinhaRepository.findAll();
//    }
//
//    @GetMapping(produces = MediaType.APPLICATION_XML_VALUE)
//    public CozinhasXmlWrapper listarXml() {
////        return new CozinhasXmlWrapper(cozinhaRepository.listar());
//        return new CozinhasXmlWrapper(cozinhaRepository.findAll());
//    }
//
//    @GetMapping("/{cozinhaId}")
//    public Cozinha buscar(@PathVariable Long cozinhaId) {
//        return cadastroCozinha.buscarOuFalhar(cozinhaId);
//    }
//
//////    @ResponseStatus(HttpStatus.OK)
////    @GetMapping("/{cozinhaId}")
////    public ResponseEntity<Cozinha> buscar(@PathVariable Long cozinhaId) {
//////        Cozinha cozinha = cozinhaRepository.buscar(cozinhaId);
//////
//////        if (cozinha != null) {
////        Optional<Cozinha> cozinha = cozinhaRepository.findById(cozinhaId);
////
////        if (cozinha.isPresent()) {
//////            return ResponseEntity.ok(cozinha);
////            return ResponseEntity.ok(cozinha.get());
////        }
////
//////		return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
////        return ResponseEntity.notFound().build();
////    }
//////    public ResponseEntity<Cozinha> buscar(@PathVariable Long cozinhaId) {
//////        Cozinha cozinha = cozinhaRepository.buscar(cozinhaId);
//////
////////        return ResponseEntity.status(HttpStatus.OK).body(cozinha);
////////        return ResponseEntity.ok(cozinha);
//////
//////        // para testar no postman precisa desativar em Preferences a opcao Automatically follow redirects
//////        HttpHeaders headers = new HttpHeaders();
//////        headers.add(HttpHeaders.LOCATION, "http://api.algafood.local:8080/cozinhas");
//////
//////        return ResponseEntity
//////                .status(HttpStatus.FOUND)
//////                .headers(headers)
//////                .build();
//////    }
//
//    @PostMapping
//    @ResponseStatus(HttpStatus.CREATED)
//    public Cozinha adicionar(@RequestBody @Valid Cozinha cozinha) {
//        return cadastroCozinha.salvar(cozinha);
////        return cozinhaRepository.salvar(cozinha);
//    }
//
//    @PutMapping("/{cozinhaId}")
//    public Cozinha atualizar(@PathVariable Long cozinhaId,
//                             @RequestBody @Valid Cozinha cozinha) {
//        Cozinha cozinhaAtual = cadastroCozinha.buscarOuFalhar(cozinhaId);
//
//        BeanUtils.copyProperties(cozinha, cozinhaAtual, "id");
//
//        return cadastroCozinha.salvar(cozinhaAtual);
//    }
//
////    @PutMapping("/{cozinhaId}")
////    public ResponseEntity<Cozinha> atualizar(@PathVariable Long cozinhaId,
////                                             @RequestBody Cozinha cozinha) {
//////        Cozinha cozinhaAtual = cozinhaRepository.buscar(cozinhaId);
//////
//////        if (cozinhaAtual != null) {
////
////        Optional<Cozinha> cozinhaAtual = cozinhaRepository.findById(cozinhaId);
////
////        if (cozinhaAtual.isPresent()) {
////            BeanUtils.copyProperties(cozinha, cozinhaAtual.get(), "id");
//////			cozinhaAtual.setNome(cozinha.getNome());
//////            BeanUtils.copyProperties(cozinha, cozinhaAtual, "id");
////
//////            cozinhaAtual = cadastroCozinha.salvar(cozinhaAtual);
//////            return ResponseEntity.ok(cozinhaAtual);
////            Cozinha cozinhaSalva = cadastroCozinha.salvar(cozinhaAtual.get());
////            return ResponseEntity.ok(cozinhaSalva);
////        }
////
////        return ResponseEntity.notFound().build();
////    }
//
////    @DeleteMapping("/{cozinhaId}")
////    public ResponseEntity<Cozinha> remover(@PathVariable Long cozinhaId) {
////        try {
////            cadastroCozinha.excluir(cozinhaId);
////            return ResponseEntity.noContent().build();
////
////        } catch (EntidadeNaoEncontradaException e) {
////            return ResponseEntity.notFound().build();
////
////        } catch (EntidadeEmUsoException e) {
////            return ResponseEntity.status(HttpStatus.CONFLICT).build();
////        }
////
//////        try {
//////            Cozinha cozinha = cozinhaRepository.buscar(cozinhaId);
//////
//////            if (cozinha != null) {
//////                cozinhaRepository.remover(cozinha);
//////
//////                return ResponseEntity.noContent().build();
//////            }
//////
//////            return ResponseEntity.notFound().build();
//////        } catch (DataIntegrityViolationException e) {
//////            return ResponseEntity.status(HttpStatus.CONFLICT).build();
//////        }
////    }
//
////	@DeleteMapping("/{cozinhaId}")
////	public ResponseEntity<?> remover(@PathVariable Long cozinhaId) {
////		try {
////			cadastroCozinha.excluir(cozinhaId);
////			return ResponseEntity.noContent().build();
////
////		} catch (EntidadeNaoEncontradaException e) {
////			return ResponseEntity.notFound().build();
////
////		} catch (EntidadeEmUsoException e) {
////			return ResponseEntity.status(HttpStatus.CONFLICT)
////					.body(e.getMessage());
////		}
////	}
//
//    @DeleteMapping("/{cozinhaId}")
//    @ResponseStatus(HttpStatus.NO_CONTENT)
//    public void remover(@PathVariable Long cozinhaId) {
//        cadastroCozinha.excluir(cozinhaId);
//    }

}
