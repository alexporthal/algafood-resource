package com.algaworks.algafood.domain.repository;

import com.algaworks.algafood.domain.model.Permissao;
import org.springframework.stereotype.Repository;

@Repository
public interface PermissaoRepository extends CustomJpaRepository<Permissao, Long> {

//    List<Permissao> listar();
//    Permissao buscar(Long id);
//    Permissao salvar(Permissao permissao);
//    void remover(Permissao permissao);

}