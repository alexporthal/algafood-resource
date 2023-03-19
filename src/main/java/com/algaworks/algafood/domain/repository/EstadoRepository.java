package com.algaworks.algafood.domain.repository;

import com.algaworks.algafood.domain.model.Estado;
import org.springframework.stereotype.Repository;

@Repository
public interface EstadoRepository extends CustomJpaRepository<Estado, Long> {

//    List<Estado> listar();
//    Estado buscar(Long id);
//    Estado salvar(Estado estado);
//    void remover(Long id);

}