package com.algaworks.algafood.api.controller;

import java.lang.reflect.Field;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import com.algaworks.algafood.api.assembler.RestauranteInputDisassembler;
import com.algaworks.algafood.api.assembler.RestauranteModelAssembler;
import com.algaworks.algafood.api.model.CozinhaModel;
import com.algaworks.algafood.api.model.RestauranteModel;
import com.algaworks.algafood.api.model.input.RestauranteInput;
import com.algaworks.algafood.api.model.view.RestauranteView;
import com.algaworks.algafood.core.validation.ValidacaoException;
import com.algaworks.algafood.domain.exception.CidadeNaoEncontradaException;
import com.algaworks.algafood.domain.exception.CozinhaNaoEncontradaException;
import com.algaworks.algafood.domain.exception.NegocioException;
import com.algaworks.algafood.domain.exception.RestauranteNaoEncontradoException;
import com.algaworks.algafood.domain.model.Cozinha;
import com.algaworks.algafood.domain.service.CadastroRestauranteService;
import com.fasterxml.jackson.annotation.JsonView;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.http.server.ServletServerHttpRequest;
import org.springframework.util.ReflectionUtils;
import org.springframework.validation.BeanPropertyBindingResult;
import org.springframework.validation.SmartValidator;
import org.springframework.web.bind.annotation.*;

import com.algaworks.algafood.domain.model.Restaurante;
import com.algaworks.algafood.domain.repository.RestauranteRepository;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

@RestController
@RequestMapping(value = "/restaurantes")
public class RestauranteController {

    @Autowired
    private RestauranteRepository restauranteRepository;

    @Autowired
    private CadastroRestauranteService cadastroRestaurante;

    @Autowired
    private RestauranteModelAssembler restauranteModelAssembler;

    @Autowired
    private RestauranteInputDisassembler restauranteInputDisassembler;

//    @Autowired
//    private SmartValidator validator;


    @JsonView(RestauranteView.Resumo.class)
    @GetMapping
    public List<RestauranteModel> listar() {
        return restauranteModelAssembler.toCollectionModel(restauranteRepository.findAll());
    }

    @JsonView(RestauranteView.ApenasNome.class)
    @GetMapping(params = "projecao=apenas-nome")
    public List<RestauranteModel> listarApenasNomes() {
        return listar();
    }

//	@GetMapping
//	public MappingJacksonValue listar(@RequestParam(required = false) String projecao) {
//		List<Restaurante> restaurantes = restauranteRepository.findAll();
//		List<RestauranteModel> restaurantesModel = restauranteModelAssembler.toCollectionModel(restaurantes);
//
//		MappingJacksonValue restaurantesWrapper = new MappingJacksonValue(restaurantesModel);
//
//		restaurantesWrapper.setSerializationView(RestauranteView.Resumo.class);
//
//		if ("apenas-nome".equals(projecao)) {
//			restaurantesWrapper.setSerializationView(RestauranteView.ApenasNome.class);
//		} else if ("completo".equals(projecao)) {
//			restaurantesWrapper.setSerializationView(null);
//		}
//
//		return restaurantesWrapper;
//	}

//	@GetMapping
//	public List<RestauranteModel> listar() {
//		return restauranteModelAssembler.toCollectionModel(restauranteRepository.findAll());
//	}
//
//	@JsonView(RestauranteView.Resumo.class)
//	@GetMapping(params = "projecao=resumo")
//	public List<RestauranteModel> listarResumido() {
//		return listar();
//	}
//
//	@JsonView(RestauranteView.ApenasNome.class)
//	@GetMapping(params = "projecao=apenas-nome")
//	public List<RestauranteModel> listarApenasNomes() {
//		return listar();
//	}



//    @GetMapping
//    public List<RestauranteModel> listar() {
//        return restauranteModelAssembler.toCollectionModel(restauranteRepository.findAll());
//    }
//    public List<Restaurante> listar() {
////        if(true){
////            throw new IllegalArgumentException("teste tratamento exception erro n esperado");
////        }
//        List<Restaurante> restaurantes = restauranteRepository.findAll();
//
////		System.out.println("O nome da cozinha é:");
////		restaurantes.get(0).getCozinha().getNome();
//
//        return restaurantes;
//    }

    @GetMapping("/{restauranteId}")
    public RestauranteModel buscar(@PathVariable Long restauranteId) {
        Restaurante restaurante = cadastroRestaurante.buscarOuFalhar(restauranteId);

        return restauranteModelAssembler.toModel(restaurante);
    }
//    public ResponseEntity<Restaurante> buscar(@PathVariable Long restauranteId) {
//        Optional<Restaurante> restaurante = restauranteRepository.findById(restauranteId);
//
//        if (restaurante.isPresent()) {
//            return ResponseEntity.ok(restaurante.get());
//        }
//
//        return ResponseEntity.notFound().build();
//    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public RestauranteModel adicionar(@RequestBody @Valid RestauranteInput restauranteInput) {
        try {
            Restaurante restaurante = restauranteInputDisassembler.toDomainObject(restauranteInput);

            return restauranteModelAssembler.toModel(cadastroRestaurante.salvar(restaurante));
        } catch (CozinhaNaoEncontradaException | CidadeNaoEncontradaException e) {
            throw new NegocioException(e.getMessage());
        }
    }
//    public Restaurante adicionar(@RequestBody @Validated(Groups.CadastroRestaurante.class) Restaurante restaurante) {
//    public Restaurante adicionar(@RequestBody @Valid Restaurante restaurante) {
//        try {
//            return cadastroRestaurante.salvar(restaurante);
//        } catch (CozinhaNaoEncontradaException e) {
//            throw new NegocioException(e.getMessage());
//        }
//    }

//    @PostMapping
//    public ResponseEntity<?> adicionar(@RequestBody Restaurante restaurante) {
//        try {
//            restaurante = cadastroRestaurante.salvar(restaurante);
//
//            return ResponseEntity.status(HttpStatus.CREATED)
//                    .body(restaurante);
//        } catch (EntidadeNaoEncontradaException e) {
//            return ResponseEntity.badRequest()
//                    .body(e.getMessage());
//        }
//    }

    @PutMapping("/{restauranteId}")
    public RestauranteModel atualizar(@PathVariable Long restauranteId, @RequestBody @Valid RestauranteInput restauranteInput) {
        try {
//            Restaurante restaurante = restauranteInputDisassembler.toDomainObject(restauranteInput);

            Restaurante restauranteAtual = cadastroRestaurante.buscarOuFalhar(restauranteId);

            restauranteInputDisassembler.copyToDomainObject(restauranteInput, restauranteAtual);

//            BeanUtils.copyProperties(restaurante, restauranteAtual,
//                    "id", "formasPagamento", "endereco", "dataCadastro", "produtos");

            return restauranteModelAssembler.toModel(cadastroRestaurante.salvar(restauranteAtual));
        } catch (CozinhaNaoEncontradaException | CidadeNaoEncontradaException e) {
            throw new NegocioException(e.getMessage());
        }
    }
//    public Restaurante atualizar(@PathVariable Long restauranteId, @RequestBody @Valid Restaurante restaurante) {
//        try {
//            Restaurante restauranteAtual = cadastroRestaurante.buscarOuFalhar(restauranteId);
//
//            BeanUtils.copyProperties(restaurante, restauranteAtual, "id", "formasPagamento", "endereco", "dataCadastro", "produtos");
//
//            return cadastroRestaurante.salvar(restauranteAtual);
//        } catch (CozinhaNaoEncontradaException e) {
//            throw new NegocioException(e.getMessage());
//        }
//    }

    @PutMapping("/{restauranteId}/ativo")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void ativar(@PathVariable Long restauranteId) {
        cadastroRestaurante.ativar(restauranteId);
    }

    @DeleteMapping("/{restauranteId}/ativo")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void inativar(@PathVariable Long restauranteId) {
        cadastroRestaurante.inativar(restauranteId);
    }

    @PutMapping("/ativacoes")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void ativarMultiplos(@RequestBody List<Long> restauranteIds) {
        try {
            cadastroRestaurante.ativar(restauranteIds);
        } catch (RestauranteNaoEncontradoException e) {
            throw new NegocioException(e.getMessage(), e);
        }
    }

    @DeleteMapping("/ativacoes")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void inativarMultiplos(@RequestBody List<Long> restauranteIds) {
        try {
            cadastroRestaurante.inativar(restauranteIds);
        } catch (RestauranteNaoEncontradoException e) {
            throw new NegocioException(e.getMessage(), e);
        }
    }

    @PutMapping("/{restauranteId}/abertura")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void abrir(@PathVariable Long restauranteId) {
        cadastroRestaurante.abrir(restauranteId);
    }

    @PutMapping("/{restauranteId}/fechamento")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void fechar(@PathVariable Long restauranteId) {
        cadastroRestaurante.fechar(restauranteId);
    }


//    @PutMapping("/{restauranteId}")
//    public ResponseEntity<?> atualizar(@PathVariable Long restauranteId,
//                                       @RequestBody Restaurante restaurante) {
//        try {
//            Optional<Restaurante> restauranteAtual = restauranteRepository.findById(restauranteId);
//
//            if (restauranteAtual.isPresent()) {
//                BeanUtils.copyProperties(restaurante, restauranteAtual.get(),
//                        "id", "formasPagamento", "endereco", "dataCadastro", "produtos");
//
//                Restaurante restauranteSalvo = cadastroRestaurante.salvar(restauranteAtual.get());
//                return ResponseEntity.ok(restauranteSalvo);
//            }
//
//            return ResponseEntity.notFound().build();
//
//        } catch (EntidadeNaoEncontradaException e) {
//            return ResponseEntity.badRequest()
//                    .body(e.getMessage());
//        }
//    }

//    @PatchMapping("/{restauranteId}")
//    public Restaurante atualizarParcial(@PathVariable Long restauranteId, @RequestBody Map<String, Object> campos,
//                                        HttpServletRequest request) {
//        Restaurante restauranteAtual = cadastroRestaurante.buscarOuFalhar(restauranteId);
//
//        merge(campos, restauranteAtual, request);
//        validate(restauranteAtual, "restaurante");
//
//        return atualizar(restauranteId, restauranteAtual);
//    }

//    private void validate(Restaurante restaurante, String objectName) {
//        BeanPropertyBindingResult bindingResult = new BeanPropertyBindingResult(restaurante, objectName);
//        validator.validate(restaurante, bindingResult);
//
//        if (bindingResult.hasErrors()) {
//            throw new ValidacaoException(bindingResult);
//        }
//    }

//    private RestauranteModel toModel(Restaurante restaurante) {
//        CozinhaModel cozinhaModel = new CozinhaModel();
//        cozinhaModel.setId(restaurante.getCozinha().getId());
//        cozinhaModel.setNome(restaurante.getCozinha().getNome());
//
//        RestauranteModel restauranteModel = new RestauranteModel();
//        restauranteModel.setId(restaurante.getId());
//        restauranteModel.setNome(restaurante.getNome());
//        restauranteModel.setTaxaFrete(restaurante.getTaxaFrete());
//        restauranteModel.setCozinha(cozinhaModel);
//        return restauranteModel;
//    }
//
//    private List<RestauranteModel> toCollectionModel(List<Restaurante> restaurantes) {
//        return restaurantes.stream()
//                .map(restaurante -> toModel(restaurante))
//                .collect(Collectors.toList());
//    }
//
//    private Restaurante toDomainObject(RestauranteInput restauranteInput) {
//        Restaurante restaurante = new Restaurante();
//        restaurante.setNome(restauranteInput.getNome());
//        restaurante.setTaxaFrete(restauranteInput.getTaxaFrete());
//
//        Cozinha cozinha = new Cozinha();
//        cozinha.setId(restauranteInput.getCozinha().getId());
//
//        restaurante.setCozinha(cozinha);
//
//        return restaurante;
//    }

//    @PatchMapping("/{restauranteId}")
//    public ResponseEntity<?> atualizarParcial(@PathVariable Long restauranteId,
//                                              @RequestBody Map<String, Object> campos) {
//        Optional<Restaurante> restauranteAtual = restauranteRepository.findById(restauranteId);
//
//        if (!restauranteAtual.isPresent()) {
//            return ResponseEntity.notFound().build();
//        }
//
//        merge(campos, restauranteAtual.get());
//
//        return atualizar(restauranteId, restauranteAtual.get());
//    }

    private void merge(Map<String, Object> dadosOrigem, Restaurante restauranteDestino, HttpServletRequest request) {
        ServletServerHttpRequest serverHttpRequest = new ServletServerHttpRequest(request);

        try {
            ObjectMapper objectMapper = new ObjectMapper();
            objectMapper.configure(DeserializationFeature.FAIL_ON_IGNORED_PROPERTIES, true);
            objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, true);

            Restaurante restauranteOrigem = objectMapper.convertValue(dadosOrigem, Restaurante.class);

            dadosOrigem.forEach((nomePropriedade, valorPropriedade) -> {
                Field field = ReflectionUtils.findField(Restaurante.class, nomePropriedade);
                field.setAccessible(true);

                Object novoValor = ReflectionUtils.getField(field, restauranteOrigem);

                ReflectionUtils.setField(field, restauranteDestino, novoValor);
            });
        } catch (IllegalArgumentException e) {
            Throwable rootCause = ExceptionUtils.getRootCause(e);
            throw new HttpMessageNotReadableException(e.getMessage(), rootCause, serverHttpRequest);
        }
    }

//    @DeleteMapping("/{restauranteId}")
//    @ResponseStatus(HttpStatus.NO_CONTENT)
//    public void remover(@PathVariable Long restauranteId) {
//        cadastroRestaurante.excluir(restauranteId);
//    }

}