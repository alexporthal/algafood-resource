package com.algaworks.algafood.infrastructure.repository;

import com.algaworks.algafood.domain.model.Restaurante;
import com.algaworks.algafood.domain.repository.RestauranteRepository;
import com.algaworks.algafood.domain.repository.RestauranteRepositoryQueries;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Repository;
import org.springframework.util.StringUtils;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.criteria.Predicate;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import static com.algaworks.algafood.infrastructure.repository.spec.RestauranteSpecs.comFreteGratis;
import static com.algaworks.algafood.infrastructure.repository.spec.RestauranteSpecs.comNomeSemelhante;

@Repository
public class RestauranteRepositoryImpl implements RestauranteRepositoryQueries {

    @PersistenceContext
    private EntityManager manager;

    @Lazy
    @Autowired
    private RestauranteRepository restauranteRepository;

    @Override
    public List<Restaurante> find(String nome,
                                  BigDecimal taxaFreteInicial, BigDecimal taxaFreteFinal) {

//        var jpql = "from Restaurante where nome like :nome "
//                + "and taxaFrete between :taxaInicial and :taxaFinal";
//
//        return manager.createQuery(jpql, Restaurante.class)
//                .setParameter("nome", "%" + nome + "%")
//                .setParameter("taxaInicial", taxaFreteInicial)
//                .setParameter("taxaFinal", taxaFreteFinal)
//                .getResultList();

//        OU

//        var jpql = new StringBuilder();
//        jpql.append("from Restaurante where 0 = 0 ");
//
//        var parametros = new HashMap<String, Object>();
//
//        if (StringUtils.hasLength(nome)) {
//            jpql.append("and nome like :nome ");
//            parametros.put("nome", "%" + nome + "%");
//        }
//
//        if (taxaFreteInicial != null) {
//            jpql.append("and taxaFrete >= :taxaInicial ");
//            parametros.put("taxaInicial", taxaFreteInicial);
//        }
//
//        if (taxaFreteFinal != null) {
//            jpql.append("and taxaFrete <= :taxaFinal ");
//            parametros.put("taxaFinal", taxaFreteFinal);
//        }
//
//        TypedQuery<Restaurante> query = manager
//                .createQuery(jpql.toString(), Restaurante.class);
//
//        parametros.forEach((chave, valor) -> query.setParameter(chave, valor));


//        CriteriaBuilder builder = manager.getCriteriaBuilder();
//
//        CriteriaQuery<Restaurante> criteria = builder.createQuery(Restaurante.class);
//        criteria.from(Restaurante.class);
//
//        TypedQuery<Restaurante> query = manager.createQuery(criteria);


//        CriteriaBuilder builder = manager.getCriteriaBuilder();
//
//        CriteriaQuery<Restaurante> criteria = builder.createQuery(Restaurante.class);
//        Root<Restaurante> root = criteria.from(Restaurante.class);
//
//        Predicate nomePredicate = builder.like(root.get("nome"), "%" + nome + "%");
//
//        Predicate taxaInicialPredicate = builder
//                .greaterThanOrEqualTo(root.get("taxaFrete"), taxaFreteInicial);
//
//        Predicate taxaFinalPredicate = builder
//                .lessThanOrEqualTo(root.get("taxaFrete"), taxaFreteFinal);
//
//        criteria.where(nomePredicate, taxaInicialPredicate, taxaFinalPredicate);
//
//        TypedQuery<Restaurante> query = manager.createQuery(criteria);


        var builder = manager.getCriteriaBuilder();

        var criteria = builder.createQuery(Restaurante.class);
        var root = criteria.from(Restaurante.class);

        var predicates = new ArrayList<Predicate>();

        if (StringUtils.hasText(nome)) {
            predicates.add(builder.like(root.get("nome"), "%" + nome + "%"));
        }

        if (taxaFreteInicial != null) {
            predicates.add(builder.greaterThanOrEqualTo(root.get("taxaFrete"), taxaFreteInicial));
        }

        if (taxaFreteFinal != null) {
            predicates.add(builder.lessThanOrEqualTo(root.get("taxaFrete"), taxaFreteFinal));
        }

        criteria.where(predicates.toArray(new Predicate[0]));

        var query = manager.createQuery(criteria);

        return query.getResultList();

    }

    @Override
    public List<Restaurante> findComFreteGratis(String nome) {
        return restauranteRepository.findAll(comFreteGratis()
                .and(comNomeSemelhante(nome)));
    }

}


//package com.algaworks.algafood.infrastructure.repository;
//
//import com.algaworks.algafood.domain.model.Restaurante;
//import com.algaworks.algafood.domain.repository.RestauranteRepository;
//import org.springframework.stereotype.Repository;
//import org.springframework.transaction.annotation.Transactional;
//
//import javax.persistence.EntityManager;
//import javax.persistence.PersistenceContext;
//import java.util.List;
//
//@Repository
//public class RestauranteRepositoryImpl implements RestauranteRepository {
//
//    @PersistenceContext
//    private EntityManager manager;
//
//    @Override
//    public List<Restaurante> listar() {
//        return manager.createQuery("from Restaurante", Restaurante.class)
//                .getResultList();
//    }
//
//    @Override
//    public Restaurante buscar(Long id) {
//        return manager.find(Restaurante.class, id);
//    }
//
//    @Transactional
//    @Override
//    public Restaurante salvar(Restaurante restaurante) {
//        return manager.merge(restaurante);
//    }
//
//    @Transactional
//    @Override
//    public void remover(Restaurante restaurante) {
//        restaurante = buscar(restaurante.getId());
//        manager.remove(restaurante);
//    }
//}
