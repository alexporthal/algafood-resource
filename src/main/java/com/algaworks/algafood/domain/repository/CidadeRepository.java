package com.algaworks.algafood.domain.repository;

import com.algaworks.algafood.domain.model.Cidade;
import org.springframework.stereotype.Repository;

@Repository
public interface CidadeRepository extends CustomJpaRepository<Cidade, Long> {

//    List<Cidade> listar();
//    Cidade buscar(Long id);
//    Cidade salvar(Cidade cidade);
//    void remover(Long id);

}