package com.algaworks.algafood.domain.repository;

import com.algaworks.algafood.domain.model.Cozinha;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CozinhaRepository extends CustomJpaRepository<Cozinha, Long> {

//    List<Cozinha> listar();

//    prefixos que podem ser usados:
//    find
//    query
//    read
//    get
//    stream
//    List<Cozinha> findQualquerCoisaByNome(String nome);
    List<Cozinha> findTodasByNome(String nome);

    List<Cozinha> findTodasByNomeContaining(String nome);
    Optional<Cozinha> findByNome(String nome);

    boolean existsByNome(String nome);

//    Cozinha buscar(Long id);
//
//    Cozinha salvar(Cozinha cozinha);
//
//    void remover(Long id);
}
